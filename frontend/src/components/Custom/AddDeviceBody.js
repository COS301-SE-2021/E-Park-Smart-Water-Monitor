import React,{useState} from "react";
import {Button, Form} from 'react-bootstrap';

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../views/admin/modals/addDevice.css";
import axios from "axios";
// import AdminModal from 'admin-modal.js'



const useStyles = makeStyles(componentStyles);

const AddDeviceBody = () => {
    const classes = useStyles();
    const theme = useTheme();

    const addDevice = () => {
        axios.post('http://localhost:8080/api/park/getParkWaterSites', {
            parkName: "Rietvlei Nature Reserve",
            siteId: "2f9f5bbf-5c81-4996-ab1f-20b196fe0ebb",
            deviceModel:"ESP32",
            deviceName:"REEEEEEE4",
            latitude:-25.899494434,
            longitude: 28.280765508
        }).then((res)=>{
            if(res)
            {

            }
        });
    }

    return (
        <>
            <Form>
                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Name</Form.Label>
                    <Form.Control type="text" placeholder="Enter device name" />

                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Park</Form.Label>
                    <Form.Control type="text" placeholder="Park" />
                </Form.Group>


                <Button background-color="primary" variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default AddDeviceBody;
