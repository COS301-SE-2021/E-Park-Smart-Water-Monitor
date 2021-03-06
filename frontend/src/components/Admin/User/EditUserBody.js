import React, {useContext, useEffect, useState} from "react";
import "../../../assets/css/addUser.css";
import Select from 'react-select';
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import axios from "axios";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";
import {Alert} from "@material-ui/lab";
import { Form } from "react-bootstrap";

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


const EditUserBody = (props) => {

    const [idNumber, setIDNumber] = useState("")
    const [email, setEmail] = useState("")
    const [name, setName] = useState("")
    const [surname, setSurname] = useState("")
    const [username, setUsername] = useState("")
    const [role, setRole] = useState("")
    const [cellNumber, setCellNumber] = useState("")
    const [error, setError] = useState("")

    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading

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
            cell = cell.substr(3)

            setIDNumber(p.idNumber)
            setEmail(p.email)
            setName(p.name)
            setSurname(p.surname)
            setUsername(p.username)
            // setRole(p.role)
            setCellNumber(cell)
        }
    },[props.userDetails])


    // submit the edit of the user
    const submit = (e) => {
        e.preventDefault()

        toggleLoading()

        setError(false)

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
                cellNumber: "+27"+cellNumber
            }


            axios.put('/user/editUser', obj, {
                    headers: {
                        'Authorization': "Bearer " + user.token
                    }
                }
            ).then((res)=>{

                toggleLoading()
                if(res.data.success === false)
                {
                    setError(res.data.status)
                }else{
                    props.closeModal()
                    props.reloadUserTable()
                }

                // eslint-disable-next-line no-unused-vars
            }).catch((res)=>{
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

                            {/*<Form.Control required={"required"} type="text" minLength={10} maxLength={10} pattern="[0-9]*" placeholder="Cell Number" name="cell_number" value={cellNumber} onChange={e => setCellNumber(e.target.value)}/>*/}
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
                
                { error &&
                <Alert severity={"warning"} className="mb-3">{error}</Alert>
                }

                <Button variant="primary" type="submit" >
                    Edit
                </Button>
            </Form>
        </>
    );
};

export default EditUserBody;
