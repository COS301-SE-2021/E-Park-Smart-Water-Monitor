import React, {useEffect, useState} from "react";

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../assets/css/addUser.css";
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

const AddUserBody = (props) => {
    const classes = useStyles();
    const theme = useTheme();
    const [park, setPark] = useState("")
    const [idNumber, setIDNumber] = useState("")
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
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

    useEffect(() => {

        // add the props to the variables so that the user can change the values
        if(props && props.userDetails)
        {
            // set the parks to be the same object notation with value and label
            let oldRole = props.userDetails.role
            alert(oldRole)
            alert("role: "+JSON.stringify(role))
            for(let i =0; i< userRoles.length; i++){
                alert(userRoles[i].value)
                if(oldRole == userRoles[i].value){
                    alert("user roles[i]: "+userRoles[i])
                    setRole(userRoles[i])
                }
            }


            alert(JSON.stringify(props.userDetails))
            let p = props.userDetails
            setPark(p.park)
            setIDNumber(p.idNumber)
            setEmail(p.email)
            setPassword(p.password)
            setName(p.name)
            setSurname(p.surname)
            setUsername(p.username)
            setRole(p.role)
            setCellNumber(p.cellNumber)
        }
    },[props.userDetails])

    useEffect(() => {
        // get the parks for populating the select component
        axios.get('http://localhost:8080/api/park/getAllParks'
        ).then((res)=>{

            let options = res.data.allParks.map((p)=>{
                return {value: p.id, label: p.parkName}
            })

            setParkOptions(options)

        }).catch((res)=>{
            console.log(JSON.stringify(res))
        });
    },[])


    const submit = (e) => {
        e.preventDefault()

        if(props && props.userDetails)
        {
            let obj = {
                username: props.userDetails.username,
                idNumber: idNumber,
                email: email ,
                name: name,
                surname: surname,
                newUsername: username,
                role: role,
                cellNumber: cellNumber
            }

            axios.post('http://localhost:8080/api/user/editUser', obj
            ).then((res)=>{

                window.location.reload()

            }).catch((res)=>{
                console.log("response:"+JSON.stringify(res))
            });
        }else
        {
            // add a new user
            let obj = {
                parkId: park.value,
                idNumber: idNumber,
                email: email,
                password: password,
                name: name,
                surname: surname,
                username: username,
                role: role.value,
                cellNumber: `+27${cellNumber}`
            }

            axios.post('http://localhost:8080/api/user/createUser', obj
            ).then((res)=>{

                // console.log("response:"+JSON.stringify(res))
                window.location.reload()

            }).catch((res)=>{
                console.log("response:"+JSON.stringify(res))
            });
        }


    }



    return (
        <>
            <Form onSubmit={ submit }>
                <Row>
                    <Col>
                        <Form.Label>Role</Form.Label>
                        <Select required={"required"} isClearable={true} className="mb-3" name="role" options={ userRoles } value={role} onChange={e => {setRole(e); alert("role after set: "+JSON.stringify(role))}}/>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Label>Park</Form.Label>
                        <Select required={"required"} isClearable={true} className="mb-3" name="park" options={ parkOptions } value={park} onChange={e => setPark(e)}/>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="email" >
                            <Form.Label>Email address</Form.Label>
                            <Form.Control required={"required"} type="email" placeholder="Enter email" name="email" value={email} onChange={e => setEmail(e.target.value)}/>
                            <Form.Text className="text-muted">
                                An email will be sent to the user informing them of their registration on the system.
                            </Form.Text>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="password">
                            <Form.Label>Password</Form.Label>
                            <Form.Control required={"required"} type="password" placeholder="Password" name="password" value={password} onChange={e => setPassword(e.target.value)}/>
                        </Form.Group>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Firstname</Form.Label>
                            <Form.Control required={"required"} type="text" placeholder="Firstname" name="name" value={name} onChange={e => setName(e.target.value)}/>
                        </Form.Group>

                    </Col>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Surname</Form.Label>
                            <Form.Control required={"required"} type="text" placeholder="Surname" name="surname" value={surname} onChange={e => setSurname(e.target.value)}/>
                        </Form.Group>

                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Nation Identification Number</Form.Label>
                            <Form.Control required={"required"} type="text" placeholder="ID Number" name="id_number" value={idNumber} onChange={e => setIDNumber(e.target.value)}/>
                        </Form.Group>

                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Cell Number</Form.Label>
                            <Form.Control required={"required"} type="text" placeholder="Cell Number" name="cell_number" value={cellNumber} onChange={e => setCellNumber(e.target.value)}/>
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
