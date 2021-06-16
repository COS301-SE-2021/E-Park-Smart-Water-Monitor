import React, {useEffect, useState} from "react";

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
    const [parks, setParks] = useState([]);


    // get park options on load
    useEffect(() => {
        // axios for getting the parks
        axios.post('http://localhost:8080/api/park/getAllParks').then((res)=>{

            let parkOptions = [
                { value: 'one', label: 'One' },
                { value: 'two', label: 'Two' }
            ];
            let parkNames = res.data.map((item)=>{
                return item.parkName;
            })

            console.log("park names: "+JSON.stringify(parkNames))

            setParks(res.data)
        });
    }, [])



    const logParkChange = () =>{
        "par change"
    }


    const onFormSubmit = e => {
        e.preventDefault()
        const formData = new FormData(e.target),
            form = Object.fromEntries(formData.entries())
        console.log(form)

            axios.post('http://localhost:8080/api/park/getParkWaterSites', {
                parkId: "b026bea2-17a4-4939-bbbb-80916d8cf44e",
                idNumber: form.id_number,
                email: form.email,
                password: form.password,
                name: form.first_name,
                surname: form.surname,
                username: form.username,
                role: "FIELD_ENGINEER",
                cellNumber: form.cell_number
            }).then((res)=>{
                if(res)
                {

                }
            });
    }

    return (
        <>
            <Form onSubmit={onFormSubmit}>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" placeholder="Enter email" name="email"/>
                            <Form.Text className="text-muted">
                                Make sure their email is valid.
                            </Form.Text>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password" name="password"/>
                        </Form.Group>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Firstname</Form.Label>
                            <Form.Control type="text" placeholder="Firstname" name="firstname"/>
                        </Form.Group>

                    </Col>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Surname</Form.Label>
                            <Form.Control type="text" placeholder="Surname" name="surname"/>
                        </Form.Group>

                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Nation Identification Number</Form.Label>
                            <Form.Control type="text" placeholder="ID Number" name="id_number"/>
                        </Form.Group>

                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Cell Number</Form.Label>
                            <Form.Control type="text" placeholder="Cell Number" name="cell_number"/>
                        </Form.Group>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="text" placeholder="Username" name="username"/>
                        </Form.Group>

                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Select className="mb-3" name="park" value="one" options={ parks } onChange={logParkChange}/>
                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Button variant="primary" type="submit" >
                            Add User
                        </Button>
                    </Col>
                </Row>
            </Form>
        </>
    );
};

export default AddUserBody;
