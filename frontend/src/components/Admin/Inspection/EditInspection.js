import React, {useContext, useState} from "react";
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

const EditInspection = (props) => {

    const [status, setStatus] = useState("NOT STARTED")
    const [description, setDescription] = useState("")

    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading

    const selectDescription = (event) => {
      setDescription(event.target.value)
    }

    const handleSubmit = (event) => {
      event.preventDefault()

      var body = {
        description: description,
          status: status,
      }

      console.log("body: ", body)

        toggleLoading()
      axios.post('http://localhost:8080/api/inspections/addInspection', body, {
          headers: {
              'Authorization': "Bearer " + user.token
          }
      }).then((res)=>{
            console.log(res)
            props.closeModal()
            toggleLoading()
      }).catch( (res)=> {
            console.log(JSON.stringify(res))
            toggleLoading()
      });
    }

    return (
        <>
            <Form onSubmit={handleSubmit}>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Description</Form.Label>
                    <Form.Control type="text" Required={"required"} placeholder="Enter a description" onChange={selectDescription}/>
                </Form.Group>
                <Form.Label>Status</Form.Label>
                <Select required={"required"} className="mb-3" name="park" options={ statusOptions } value={status} onChange={e => setStatus(e)}/>
                <Button background-color="primary" variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default EditInspection;
