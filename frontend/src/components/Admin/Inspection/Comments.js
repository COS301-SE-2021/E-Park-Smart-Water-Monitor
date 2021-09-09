import React, {useContext, useEffect, useRef, useState} from "react";
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
    const [newComment, setNewComment] = useState("")
    const [comments, setComments] = useState(null)
    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)

    const commentsEndRef = useRef(null);
    // eslint-disable-next-line no-unused-vars
    const toggleLoading = loader.toggleLoading


    // socket connections
    useEffect(() => {

        // on load of page make a temporary array for when inspections get added from the socket or the user
        setComments(props.inspectionDetails.comments)

        // initialise socket
        if (props.inspectionDetails.id) initiateSocket(props.inspectionDetails.id);
        subscribeToChat((err, data) => {
            if(err) return;
            // add the message to the local messages array
            setComments(comments => [...comments, data])

        });
        return () => {
            disconnectSocket();
        }
    }, []);


    const send = ()=>{
        let comm= user.username+":\n"+ newComment
        // on the socket server the message is sent to the database via an API
        // the message will be sent to all clients in the room
        sendMessage(comm, props.inspectionDetails.id, user.token)

        if (comments && commentsEndRef.current) {
            const timer = setTimeout(() => {
                commentsEndRef.current.scrollIntoView();
            }, 5);
            return () => clearTimeout(timer);
        }
    };

    // scroll to bottom when new message is added
    useEffect(()=>{
        if (comments && commentsEndRef.current) {
            const timer = setTimeout(() => {
                commentsEndRef.current.scrollIntoView();
            }, 5);
            return () => clearTimeout(timer);
        }
    },[comments]);

    return (
        <div>
            <div style={{ position:"sticky", top:"0", backgroundColor:"#9F9F9F", padding:"10px", borderRadius:"10px", color:"white"}} className="mt-3 mb-3" >Conversation on &quot;{ props.inspectionDetails.description}&quot;.</div>
            { comments && <PrevComments comments={ comments } inspection={props.inspectionDetails} user={user.username}/> }
            <br/>

            {/*<Form style={{position: "sticky", bottom: "0", backgroundColor:"white"}}>*/}
            <Form >

                    <Grid>
                        <Row >
                            <Col xs={12}>
                                <Form.Label>Add a comment</Form.Label>
                            </Col>
                            <Col xs={9} sm={10} m={0} p={0}>
                                <Form.Control type="text" Required={"required"} placeholder="" onChange={e => setNewComment(e.target.value)}/>
                            </Col>
                            <Col xs={3} sm={2} style={{ paddingLeft: 0 }}>
                                <Button background-color="primary" variant="primary" onClick={()=>{send()}}>
                                    Post
                                </Button>
                            </Col>
                        </Row>
                    </Grid>

            </Form>




            { comments && <div ref={ commentsEndRef }/> }
        </div>
    );
};

export default Comments;
