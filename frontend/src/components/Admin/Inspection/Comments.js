import React, {useContext, useEffect, useState} from "react";
import {Button, Form} from 'react-bootstrap';
import "../../../assets/css/addDevice.css";
// eslint-disable-next-line no-unused-vars
import axios from "axios";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";
import PrevComments from "./PrevComments";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Grid from "@material-ui/core/Grid";
import {disconnectSocket, initiateSocket, subscribeToChat, sendMessage } from "./Socket/Socket";

const Comments = (props) => {

    // eslint-disable-next-line no-unused-vars
    const [newComments, setNewComments] = useState("")
    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)
    // eslint-disable-next-line no-unused-vars
    const toggleLoading = loader.toggleLoading

    // socket information
    // const [message, setMessage] = useState('');
    // const [chat, setChat] = useState([]);

    // socket connections
    useEffect(() => {
        if (props.inspectionDetails.id) initiateSocket(props.inspectionDetails.id);
        subscribeToChat((err, data) => {
            if(err) return;
            console.log(data)
            // setChat(oldChats =>[data, ...oldChats])
        });
        return () => {
            disconnectSocket();
        }
    }, [props.inspectionDetails.id]);


    const send = ()=>{
        alert(`sending`)
        let comm= user.username+":\n"+ newComments
        sendMessage(props.inspectionDetails.id, comm, user.token)
    };

    // eslint-disable-next-line no-unused-vars
    // const handleSubmit = (event) => {
    //
    //     // send message to socket server
    //
    //
    //     toggleLoading()
    //     event.preventDefault()
    //
    //     let comm= user.username+":\n"+ newComments
    //
    //         //set comments
    //         var body = {
    //         inspectionId: props.inspectionDetails.id,
    //         comments: comm,
    //         }
    //         console.log("body: ", body)
    //         axios.post('http://localhost:8080/api/inspections/setComments', body, {
    //           headers: {
    //               'Authorization': "Bearer " + user.token
    //           }
    //         }).then((res)=>{
    //             console.log(res)
    //             props.tog()
    //             toggleLoading()
    //             props.reloadInspectionTable()
    //         }).catch( (res)=> {
    //             console.log(JSON.stringify(res))
    //         });
    //
    // }

    // A new user will join this chat when this modal is rendered and the prev comments loaded


    return (
        <>
            <PrevComments comments={props.inspectionDetails.comments} inspection={props.inspectionDetails} user={user.username}/>
            <br/>
            <Form>

                    <Grid>
                        <Row >
                            <Col xs={12}>
                                <Form.Label>Add a comment</Form.Label>
                            </Col>
                            <Col xs={9} sm={10} m={0} p={0}>
                                <Form.Control type="text" Required={"required"} placeholder="" onChange={e => setNewComments(e.target.value)}/>
                            </Col>
                            <Col xs={3} sm={2} style={{ paddingLeft: 0 }}>
                                <Button background-color="primary" variant="primary" onClick={()=>{send()}}>
                                    Post
                                </Button>
                            </Col>
                        </Row>
                    </Grid>

            </Form>
        </>
    );
};

export default Comments;
