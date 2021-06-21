import React,{useState} from "react";
import {Button, Form} from 'react-bootstrap';

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../assets/css/addDevice.css";
import axios from "axios";



const useStyles = makeStyles(componentStyles);

const AddInspectionBody = () => {
    const classes = useStyles();
    const theme = useTheme();

    const [device, setDevice] = useState("")
    const [site, setSite] = useState("33de6fd6-5f26-4020-a58f-f41b0de7b839")
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
        deviceId: device,
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
                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Device</Form.Label>
                    <Form.Control type="text" placeholder="Enter device id" onChange={selectDevice}/>
                </Form.Group>

                {/* <Form.Group controlId="formBasicPassword">
                    <Form.Label>Site</Form.Label>
                    <Form.Control type="text" placeholder="Enter a site id" onChange={selectSite}/>
                </Form.Group> */}

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Description</Form.Label>
                    <Form.Control type="text" placeholder="Enter a description" onChange={selectDescription}/>
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Due date</Form.Label>
                    <Form.Control type="date" placeholder="Enter a due date" onChange={selectDate}/>
                </Form.Group>

                <Button background-color="primary" variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default AddInspectionBody;
