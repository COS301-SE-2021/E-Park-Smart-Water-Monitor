import React,{useState} from "react";

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../views/admin/modals/addUser.css";

//import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
const { Form } = require( "react-bootstrap" );



const useStyles = makeStyles(componentStyles);

const AddUserBody = () => {
    const classes = useStyles();
    const theme = useTheme();

    return (
        <>
            {/*<div>*/}
            {/*    <br/>*/}
            {/*    <label id="name" for="Name">Name</label>*/}
            {/*    <br/>*/}
            {/*    <input type="text" name="Name" />*/}
            {/*    <br/><br/>*/}
            {/*    <label id="surname" for="Surname">Surname</label>*/}
            {/*    <br/>*/}
            {/*    <input type="text" name="Surname" />*/}
            {/*</div>*/}

            {/*<br/><br/>*/}
            {/*<label id="roleLabel" >Role</label>*/}
            {/*<div id="role" >*/}
            {/*    <input id="ranger" class="radio" type="radio" name="roles"  value="Ranger"/>Ranger<br/>*/}
            {/*    <input id="fe" class="radio" type="radio"  name="roles" value="Field Engineer"/>Field Engineer<br/>*/}
            {/*    <input id="admin" class="radio" type="radio" name="roles" value="Admin"/>Admin<br/><br/><br/>*/}
            {/*</div>*/}
            {/*<label id="roleLabel" >Park</label><br/>*/}
            {/*<select  id="parks" name="Parks"  >*/}
            {/*    <option value="rietvlei">Riet Vlei</option>*/}
            {/*</select>*/}
            {/*<br/><br/><br/>*/}
            <Form>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" placeholder="Enter email" />
                            <Form.Text className="text-muted">
                                We'll never share your email with anyone else.
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
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="text" placeholder="Username" />
                        </Form.Group>

                    </Col>

                </Row>



                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default AddUserBody;
