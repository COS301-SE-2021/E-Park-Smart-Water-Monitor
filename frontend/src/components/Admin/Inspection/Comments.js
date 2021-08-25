import React, {useContext, useEffect, useState} from "react";
import {Button, Form} from 'react-bootstrap';

import { makeStyles } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addDevice.css";
import axios from "axios";
import AdminContext from "../AdminContext";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";
import Select from "react-select";

const useStyles = makeStyles(componentStyles);

const statusOptions = [
    { value: "NOT STARTED", label: "Not Started" },
    { value: "DONE", label: "Done" }
]

const Comments = (props) => {

    const [commentss, setCommentss] = useState("")
    const [newComments, setNewComments] = useState("")

    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading

    useEffect(() => {
        setCommentss(props.inspectionDetails.comments)
    },[])


    const handleSubmit = (event) => {
        toggleLoading()
      event.preventDefault()

        let comm= commentss +user.username+":\n "+ newComments+ "\n\n"

      //set comments
      var body = {
        inspectionId: props.inspectionDetails.id,
        comments: comm,
      }
      console.log("body: ", body)
        //toggleLoading()
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
                {commentss}
                <Form.Group controlId="formBasicPassword">
                    <Form.Control type="text" Required={"required"} placeholder="..." onChange={e => setNewComments(e.target.value)}/>
                </Form.Group>
                <br/>
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
