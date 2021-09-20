import React, {useContext, useState} from "react";
import "../../assets/css/addUser.css";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import axios from "axios";
import {UserContext} from "../../Context/UserContext";
import EditProfileContext from "../../Context/EditProfileContext";
import { Form } from "react-bootstrap" ;
import LoadingContext from "../../Context/LoadingContext";
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

const EditProfile = (props) => {
    const user = useContext(UserContext)

    const [idNumber, setIDNumber] = useState(user.IDNumber)
    const [email, setEmail] = useState(user.email)
    const [name, setName] = useState(user.name)
    const [surname, setSurname] = useState(user.surname)
    const [username, setUsername] = useState(user.username)
    //remove the +27
    let cell = user.cellNumber;
    cell = cell.substr(3)
    const [cellNumber, setCellNumber] = useState(cell)
    // eslint-disable-next-line no-unused-vars
    const [error, setError] = useState("")


    const editProfile = useContext(EditProfileContext)
    // eslint-disable-next-line no-unused-vars
    const toggleEditProfile = editProfile.toggleEditProfile
    const toggleLoading = useContext(LoadingContext).toggleLoading


    // submit the edit of the user
    const submit = (e) => {
        e.preventDefault()
        toggleLoading()

        let temp_email = email
        if(email === user.email) {
            temp_email = ""
        }

        let temp_username = username
        if(username === user.username)
        {
            temp_username = ""
        }

        let obj = {
            username: user.username,
            idNumber: idNumber,
            email: temp_email ,
            name: name,
            surname: surname,
            role: user.role,
            newUsername: temp_username,
            cellNumber: "+27"+cellNumber
        }

        axios.put('/user/editUser', obj, {
                headers: {
                    'Authorization': "Bearer " + user.token
                }
            }
        ).then((res)=>{

            // props.togglee()

            console.log("response:"+JSON.stringify(res))
            if(res.data.success === "false")
            {
                setError(res.data.status)
                console.log("error with editing user")
            }else{
                toggleLoading()
                props.closeModall()

                if (temp_username===""){
                    temp_username=username
                }
                if (temp_email===""){
                    temp_email=email
                }
                user.setName(name)
                user.setUsername(temp_username)
                user.setEmail(temp_email)
                user.setSurname(surname)
                user.setCellNumber("+27"+cellNumber)
                user.setIDNumber(idNumber)
            }

        }).catch((res)=>{
            console.log("response:"+JSON.stringify(res))
        });


    }


    return (
        <>
            <Form onSubmit={ submit }>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" controlId="email" >
                            <Form.Label>Email address</Form.Label>
                            <Form.Control required={"required"} type="email" placeholder="Enter email" name="email" value={email} onChange={e => setEmail(e.target.value)}/>
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

                <Button variant="primary" type="submit">
                    Save
                </Button>
            </Form>
        </>
    );
};

export default EditProfile;
