import React,{useState} from "react";
import {Button, Form} from 'react-bootstrap';

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../assets/css/addDevice.css";
// import AdminModal from 'admin-modal.js'



const useStyles = makeStyles(componentStyles);

const AddDeviceBody = () => {
    const classes = useStyles();
    const theme = useTheme();

    return (
        <>
            <Form>
                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Name</Form.Label>
                    <Form.Control type="text" placeholder="Enter device name" />
                    {/*<Form.Text className="text-muted">*/}
                    {/*    We'll never share your email with anyone else.*/}
                    {/*</Form.Text>*/}
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
