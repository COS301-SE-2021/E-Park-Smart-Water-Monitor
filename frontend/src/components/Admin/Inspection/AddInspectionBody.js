import React,{useState} from "react";
import {Button, Form} from 'react-bootstrap';

import { makeStyles } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addDevice.css";
import axios from "axios";

const useStyles = makeStyles(componentStyles);

const AddInspectionBody = (props) => {

    const [device, setDevice] = useState("")
    const [site, setSite] = useState("91d05eb1-2a35-4e44-9726-631d83121edb")
    const [description, setDescription] = useState("")
    const [date, setDate] = useState(null)

    const selectDevice = (event) => {
      setDevice(event.target.value)
    }

    const selectSite = (event) => {
      setSite(event.target.value)
    }

    const selectDescription = (event) => {
      setDescription(event.target.value)
    }

    const selectDate = (event) => {
      setDate(event.target.value)
      console.log(event.target.value)
    }

    const handleSubmit = (event) => {
      event.preventDefault()

      var body = {
        deviceId: props.device_id,
        siteId: site,
        dateDue: date,
        description: description,
      }

      console.log("body: ", body)


      axios.post('http://localhost:8080/api/inspections/addInspection', body).then((res)=>{
        console.log(res)
      });
    }

    return (
        <>
            <Form onSubmit={handleSubmit}>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Description</Form.Label>
                    <Form.Control type="text" Required={"required"} placeholder="Enter a description" onChange={selectDescription}/>
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Due date</Form.Label>
                    <Form.Control type="date" Required={"required"}  placeholder="Enter a due date" onChange={selectDate}/>
                </Form.Group>

                <Button background-color="primary" variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default AddInspectionBody;
