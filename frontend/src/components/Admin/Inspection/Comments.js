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

    const [comments, setComments] = useState("")
    const [newComments, setNewComment] = useState("")

    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading

    useEffect(() => {
        setComments(props.inspectionDetails.comments)
    },[])


    const handleSubmit = (event) => {
        toggleLoading()
      event.preventDefault()

      //set comments
      var body = {
        inspectionId: props.inspectionDetails.id,
        comments: comments + "/n/n" +user.username+":/n "+newComments,
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
      }).catch( (res)=> {
            console.log(JSON.stringify(res))
      });


    }

    return (
        <>
            <Form onSubmit={handleSubmit}>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Comments</Form.Label>
                    <Form.Control type="text" Required={"required"} value={comments} onChange={e => setComments(e.target.value)}/>
                </Form.Group>
                {/*<Form.Label>Status</Form.Label>*/}
                {/*<Select required={"required"} className="mb-3" name="park" options={ statusOptions } value={status} onChange={e => setStatus(e)}/>*/}
                <Button background-color="primary" variant="primary" type="submit" >
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default Comments;
