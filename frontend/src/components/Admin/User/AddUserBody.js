import React, {useContext, useEffect, useState} from "react";
import "../../../assets/css/addUser.css";
import Select from 'react-select';
import { css } from "@emotion/react";
import ClipLoader from "react-spinners/ClipLoader";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import axios from "axios";
import AdminContext from "../AdminContext";
import {DotLoader} from "react-spinners";
const { Form } = require( "react-bootstrap" );

const styles = {
    col_left: {
        paddingRight:'3px'
    },
    col_right: {
        paddingLeft:0
    },
    input: {
        paddingLeft: 0,
        paddingRight: 0
    }
}

const AddUserBody = (props) => {
    const [park, setPark] = useState("")
    const [idNumber, setIDNumber] = useState("")
    const [email, setEmail] = useState("")
    const [name, setName] = useState("")
    const [surname, setSurname] = useState("")
    const [username, setUsername] = useState("")
    const [role, setRole] = useState("")
    const [cellNumber, setCellNumber] = useState("")
    const [parkOptions, setParkOptions] = useState("")


    let userRoles = [
        { value: 'ADMIN', label: 'Admin' },
        { value: 'FIELD_ENGINEER', label: 'Field Engineer' },
        { value: 'RANGER', label: 'Ranger' }
    ];

    const context = useContext(AdminContext)
    const parksAndSites = context.parksAndSites
    const toggleLoading = context.toggleLoading


    useEffect(() => {
        let options = parksAndSites.parks.map((p)=>{
            return {value: p.id, label: p.parkName}
        })

        setParkOptions(options)

        // set the defualt roles and parks on the model
        setRole(userRoles[0])
        setPark(options[0])
    },[])


    const submit = (e) => {
        toggleLoading()


        e.preventDefault()

        // add a new user
        let obj = {
            parkId: park.value,
            idNumber: idNumber,
            email: email,
            password: '',
            name: name,
            surname: surname,
            username: username,
            role: role.value,
            cellNumber: `+27${cellNumber.substring(1,cellNumber.length)}`
        }

        axios.post('http://localhost:8080/api/user/createUser', obj
        ).then((res)=>{

            // reload the parent to refetch the data with out reloading the whole page
            toggleLoading()
            props.closeModal()
            props.reloadUserTable();


        }).catch((res)=>{
            console.log("response:"+JSON.stringify(res))
        });

    }



    return (
        <>
            <Form onSubmit={ submit }>
                <Row>
                    <Col>
                        <Form.Label>Role</Form.Label>
                        <Select required={"required"} className="mb-3" name="role" options={ userRoles } value={role} onChange={e => setRole(e)}/>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Label>Park</Form.Label>
                        <Select required={"required"} className="mb-3" name="park" options={ parkOptions } value={park} onChange={e => setPark(e)}/>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="email" >
                            <Form.Label>Email address</Form.Label>
                            <Form.Control required={"required"} type="email" placeholder="Enter email" name="email" value={email} onChange={e => setEmail(e.target.value)}/>
                            <Form.Text className="text-muted">
                                An email will be sent to the user informing them of their registration on the system and their password will be automatically generated.
                            </Form.Text>
                        </Form.Group>

                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Firstname</Form.Label>
                            <Form.Control required={"required"} type="text" pattern="[a-zA-Z]*" placeholder="Firstname" name="name" value={name} onChange={e => setName(e.target.value)}/>
                        </Form.Group>

                    </Col>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Surname</Form.Label>
                            <Form.Control required={"required"} type="text" pattern="[a-zA-Z ]*" placeholder="Surname" name="surname" value={surname} onChange={e => setSurname(e.target.value)}/>
                        </Form.Group>

                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>National Identification Number</Form.Label>
                            <Form.Control required={"required"} type="text" minLength={13} maxLength={13} pattern="[0-9]*" placeholder="ID Number" name="id_number" value={idNumber} onChange={e => setIDNumber(e.target.value)}/>
                        </Form.Group>

                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Cell Number</Form.Label>

                                    <Row                                    >
                                        <Col
                                        xs={3}
                                        style={styles.col_left}
                                        >
                                            {/*Shows the +27 for the user so they know only to type the rest of the number*/}
                                            <Form.Control
                                                type="text"
                                                editable={false}
                                                placeholder="+27"
                                                value="+27"
                                                disabled={true}
                                            />
                                        </Col>
                                        <Col
                                        xs={9}
                                        style={styles.col_right}
                                        >
                                            <Form.Control
                                                required={"required"}
                                                type="text"
                                                minLength={9}
                                                maxLength={9}
                                                pattern="[0-9]*"
                                                placeholder="eg. 721619098"
                                                name="cell_number"
                                                value={cellNumber}
                                                onChange={e => setCellNumber(e.target.value)}/>
                                        </Col>
                                    </Row>

                        </Form.Group>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Username</Form.Label>
                            <Form.Control required={"required"} type="text" placeholder="Username" name="username" value={username} onChange={e => setUsername(e.target.value)}/>
                        </Form.Group>

                    </Col>
                </Row>


                <Button variant="primary" type="submit" >
                    Submit
                </Button>

            </Form>

        </>
    );
};

export default AddUserBody;
