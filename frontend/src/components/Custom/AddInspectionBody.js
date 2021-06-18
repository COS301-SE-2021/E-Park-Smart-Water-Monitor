import React,{useState} from "react";
import {Button, Form} from 'react-bootstrap';

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../views/admin/modals/addDevice.css";



const useStyles = makeStyles(componentStyles);

const AddInspectionBody = () => {
    const classes = useStyles();
    const theme = useTheme();

    return (
        <>
            <Form>
                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Device</Form.Label>
                    <Form.Control type="text" placeholder="Enter device id" />
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Site</Form.Label>
                    <Form.Control type="text" placeholder="Enter a site id" />
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Description</Form.Label>
                    <Form.Control type="text" placeholder="Enter a description" />
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Due date</Form.Label>
                    <Form.Control type="date" placeholder="Enter a due date" />
                </Form.Group>


                <Button background-color="primary" variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default AddInspectionBody;
