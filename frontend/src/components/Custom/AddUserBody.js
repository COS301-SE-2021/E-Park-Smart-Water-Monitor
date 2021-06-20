import React,{useState} from "react";

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../views/admin/modals/addUser.css";
import Select from 'react-select';
// Be sure to include styles at some point, probably during your bootstrapping
// import 'react-select/dist/react-select.css';

//import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import axios from "axios";
import {Marker, Popup} from "react-leaflet";
const { Form } = require( "react-bootstrap" );



const useStyles = makeStyles(componentStyles);

const AddUserBody = () => {
    const classes = useStyles();
    const theme = useTheme();

    let parkOptions = [
        { value: 'one', label: 'One' },
        { value: 'two', label: 'Two' }
    ];

    const logChange = () =>{
        "heyo"
    }

    const handleSubmit = () => {
        axios.post('http://localhost:8080/api/park/getParkWaterSites', {
            parkId: "b026bea2-17a4-4939-bbbb-80916d8cf44e",
            idNumber: "9871233577123",
            email: "dynative@gmail.com",
            password: "dynative",
            name: "team",
            surname: "dynative",
            username: "ETOSHA-ENGINEER",
            role: "FIELD_ENGINEER",
            cellNumber: "0125643466"
        }).then((res)=>{
            if(res)
            {

            }
        });
    }

    return (
        <>
            <Form>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" placeholder="Enter email" />
                            <Form.Text className="text-muted">
                                Make sure their email is valid.
                            </Form.Text>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password" />
                        </Form.Group>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Firstname</Form.Label>
                            <Form.Control type="text" placeholder="Firstname" />
                        </Form.Group>

                    </Col>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Surname</Form.Label>
                            <Form.Control type="text" placeholder="Surname" />
                        </Form.Group>

                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Nation Identification Number</Form.Label>
                            <Form.Control type="text" placeholder="ID Number" />
                        </Form.Group>

                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Cell Number</Form.Label>
                            <Form.Control type="text" placeholder="Cell Number" />
                        </Form.Group>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="text" placeholder="Username" />
                        </Form.Group>

                    </Col>
                </Row>
                <Row>
                    <Col>
                        {/*<div key={`default`} className="mb-3">*/}
                        {/*    <Form.Label>Park</Form.Label>*/}
                        {/*    <Form.Check*/}
                        {/*        type={'radio'}*/}
                        {/*        id={``}*/}
                        {/*        label={`default `}*/}
                        {/*    />*/}
                        {/*</div>*/}
                        <Select name="form-field-name" value="one" options={parkOptions} onChange={logChange}/>
                    </Col>
                </Row>


                <Button variant="primary" type="submit"  onClick={handleSubmit}>
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default AddUserBody;