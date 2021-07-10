import React, {useEffect, useState} from "react";

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addUser.css";
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

const EditUserBody = (props) => {
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
    const [error, setError] = useState("")

    let userRoles = [
        { value: 'ADMIN', label: 'Admin' },
        { value: 'FIELD_ENGINEER', label: 'Field Engineer' },
        { value: 'RANGER', label: 'Ranger' }
    ];

    useEffect(() => {

        // add the props to the variables so that the user can change the values in the components
        if(props && props.userDetails)
        {
            // set the role to be the same object notation with value and label
            let oldRole = props.userDetails.role
            for(let i =0; i< userRoles.length; i++){
                if(oldRole == userRoles[i].value){
                    setRole(userRoles[i])
                }
            }

            let p = props.userDetails

            let cell = p.cellNumber;
            cell = cell.substr(4)
            cell = '0'+cell

            setPark(p.park)
            setIDNumber(p.idNumber)
            setEmail(p.email)
            setPassword(p.password)
            setName(p.name)
            setSurname(p.surname)
            setUsername(p.username)
            // setRole(p.role)
            setCellNumber(cell)
        }
    },[props.userDetails])

    // parks not yet implemented as an editable property
    // // get the parks to populate the select item
    // useEffect(() => {
    //     // get the parks for populating the select component
    //     axios.get('http://localhost:8080/api/park/getAllParks'
    //     ).then((res)=>{
    //
    //         let options = res.data.allParks.map((p)=>{
    //             return {value: p.id, label: p.parkName}
    //         })
    //
    //         setParkOptions(options)
    //
    //     }).catch((res)=>{
    //         console.log(JSON.stringify(res))
    //     });
    // },[])


    const submit = (e) => {
        e.preventDefault()

        if(props && props.userDetails)
        {
            // accomodate for provided email is already in use error
            let temp_email = email
            if(email === props.userDetails.email)
            {
                //clear the value
                temp_email = ""
            }

            let temp_username = username
            if(username === props.userDetails.username)
            {
                //clear the value
                temp_username = ""
            }

            let obj = {
                username: props.userDetails.username,
                idNumber: idNumber,
                email: temp_email ,
                name: name,
                surname: surname,
                newUsername: temp_username,
                role: role.value,
                cellNumber: cellNumber
            }


            axios.post('http://localhost:8080/api/user/editUser', obj
            ).then((res)=>{

                console.log("response:"+JSON.stringify(res))
                if(res.data.success == "false")
                {
                    setError(res.data.status)
                    console.log("error with editing user")
                }else{
                    window.location.reload(); //need to get the new data from the db to populate the table again
                }

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
                        <Select required={"required"} className="mb-3" name="role" options={ userRoles } value={role} onChange={e => setRole(e)}/>
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
                            <Form.Label>National Identification Number</Form.Label>
                            <Form.Control required={"required"} type="text" minLength={13} maxLength={13} pattern="[0-9]*" placeholder="ID Number" name="id_number" value={idNumber} onChange={e => setIDNumber(e.target.value)}/>
                        </Form.Group>

                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Cell Number</Form.Label>
                            <Form.Control required={"required"} type="text" minLength={10} maxLength={10} pattern="[0-9]*" placeholder="Cell Number" name="cell_number" value={cellNumber} onChange={e => setCellNumber(e.target.value)}/>
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
                    Edit
                </Button>
            </Form>
        </>
    );
};

export default EditUserBody;
