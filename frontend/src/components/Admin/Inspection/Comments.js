import React, {useContext, useState} from "react";
import {Button, Form} from 'react-bootstrap';
import "../../../assets/css/addDevice.css";
import axios from "axios";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";
import PrevComments from "./PrevComments";

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
      axios.post('/api/inspections/setComments', body, {
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
                <PrevComments comments={props.inspectionDetails.comments} user={user.username}/>
                <br/>
                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Add a comment:</Form.Label>
                    <Form.Control type="text" Required={"required"} placeholder="..." onChange={e => setNewComments(e.target.value)}/>
                </Form.Group>
                <Button background-color="primary" variant="primary" type="submit" >
                    Post
                </Button>
            </Form>
        </>
    );
};

export default Comments;
