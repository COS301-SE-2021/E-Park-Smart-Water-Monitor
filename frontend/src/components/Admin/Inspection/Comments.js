import React, {useContext, useState} from "react";
import {Button, Form} from 'react-bootstrap';
import "../../../assets/css/addDevice.css";
import axios from "axios";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";
import PrevComments from "./PrevComments";
import Grid from "@material-ui/core/Grid";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

const Comments = (props) => {

    const [newComments, setNewComments] = useState("")
    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading

    const handleSubmit = (event) => {
        toggleLoading()
      event.preventDefault()

        let comm= user.username+":\n"+ newComments

      //set comments
      var body = {
        inspectionId: props.inspectionDetails.id,
        comments: comm,
      }
      console.log("body: ", body)
      axios.post('http://localhost:8080/api/inspections/setComments', body, {
          headers: {
              'Authorization': "Bearer " + user.token
          }
      }).then((res)=>{
            console.log(res)
            props.tog()
            toggleLoading()
            props.reloadInspectionTable()
      }).catch( (res)=> {
            console.log(JSON.stringify(res))
      });


    }

    return (
        <>
            <Form onSubmit={handleSubmit}>
                <PrevComments comments={props.inspectionDetails.comments} inspection={props.inspectionDetails} user={user.username}/>
                <br/>
                <Grid>
                    <Row>
                        <Col xs={12}>
                            <Form.Label>Add a comment</Form.Label>
                        </Col>
                        <Col xs={10} >
                            <Form.Control type="text" Required={"required"} placeholder="" onChange={e => setNewComments(e.target.value)}/>
                        </Col>
                        <Col xs={2} >
                            <Button background-color="primary" variant="primary" type="submit" >
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
