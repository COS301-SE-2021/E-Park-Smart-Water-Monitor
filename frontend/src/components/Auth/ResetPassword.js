import React, {useContext, useEffect, useState} from "react";
import {Button, Form} from 'react-bootstrap';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import axios from "axios";
import {UserContext} from "../../../Context/UserContext";



const resetPassword = (props) => {

    const user = useContext(UserContext)

    // send the username to the system to get an email
    const submitUsername = (e) => {

        axios.post('http://localhost:8080/api/user/resetPassword', obj, {
            headers: {
                'Authorization': "Bearer " + user.token
            }
        }).then((res) => {

            toggleLoading();
            props.closeModal()
            props.reloadDeviceTable();

        }).catch((res) => {

            toggleLoading()
            console.log("error adding device: "+JSON.stringify(res))

        });
    }

    return (
        <>
            <Form onSubmit={ createDevice }>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Username</Form.Label>
                            <Form.Control required={"required"} type="text" placeholder="Device Name" pattern={"[a-zA-Z0-9:_-]+"} name="name" value={name} onChange={e => setUserame(e.target.value)}/>
                            <Form.Text className="text-muted">
                                Use a dash or fullstop instead of a space
                            </Form.Text>
                        </Form.Group>

                    </Col>
                </Row>

                <Button background-color="primary" variant="primary" type="submit">
                    Cancel
                </Button>
            </Form>
        </>
    );
};

export default ResetPassword;
