import React, {useContext, useEffect, useState} from "react";
import {Form} from 'react-bootstrap';
import Button from "@material-ui/core/Button";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import axios from "axios";
import LoadingContext from "../../Context/LoadingContext";
import {UserContext} from "../../Context/UserContext";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import InputLabel from "@material-ui/core/InputLabel";
import OutlinedInput from "@material-ui/core/OutlinedInput";
import InputAdornment from "@material-ui/core/InputAdornment";
import FormControl from "@material-ui/core/FormControl";
import AccountCircleIcon from '@material-ui/icons/AccountCircle';

const ResetPassword = (props) => {
    const [username, setUsername] = useState("")
    const [code, setCode] = useState(false)

    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading

    // send the username to the system to get an email
    const submitUsername = (e) => {

        let obj = {
            username : username
        }

        axios.post('http://localhost:8080/api/user/resetPassword', obj, {
            headers: {
                'Authorization': "Bearer " + user.token
            }
        }).then((res) => {

            toggleLoading();

        }).catch((res) => {

            toggleLoading()
            console.log("error sending username to reset password: "+JSON.stringify(res))

        });
    }

    const submitNewPassword = ()=>{
        console.log("submitting new password")
        props.closeModal() // do this after giving a success message.
    }

    return (
        <>
            <Form onSubmit={ submitUsername }>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" >

                            <FormControl fullWidth
                                 variant="outlined"

                                         type="text"
                                 pattern={"[a-zA-Z0-9:_-]+"}
                                 name="username"
                                 value={username}
                                 onChange={e => setUsername(e.target.value)}
                            >
                                <InputLabel>
                                    Username
                                </InputLabel>
                                <OutlinedInput
                                    type="text"
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <Box
                                                component={AccountCircleIcon}
                                                width="1.25rem!important"
                                                height="1.25rem!important"
                                            />
                                        </InputAdornment>
                                    }

                                    labelWidth={65}
                                />
                            </FormControl>
                            <Form.Text className="text-muted">
                                A confirmation email will be sent to the account matching this username.
                            </Form.Text>
                        </Form.Group>

                    </Col>
                </Row>

                <Grid container component={Box} marginTop="1rem">
                    <Grid item xs={6} component={Box} textAlign="left">
                        <Button background-color="danger" color="primary" variant="text" onClick={ ()=>{props.closeModal()} }>
                            Cancel
                        </Button>
                    </Grid>
                    <Grid item xs={6} component={Box} textAlign="right">
                        <Button color="primary" variant="contained" type="submit">
                            Submit
                        </Button>
                    </Grid>
                </Grid>
            </Form>

            {
                code &&
                <Form onSubmit={ submitNewPassword }>
                    <Row>
                        <Col>
                            <Form.Group className="mb-3" >
                                <Form.Label>Username</Form.Label>
                                <Form.Control required={"required"} type="text" placeholder="Username" pattern={"[a-zA-Z0-9:_-]+"} name="username" value={username} onChange={e => setUsername(e.target.value)}/>
                                <Form.Text className="text-muted">
                                    Use a dash or fullstop instead of a space
                                </Form.Text>
                            </Form.Group>

                        </Col>
                    </Row>

                    <Button background-color="primary" variant="primary" onClick={ ()=>{props.closeModal()} }>
                        Cancel
                    </Button>
                    <Button background-color="primary" variant="primary" type="submit">
                        Submit
                    </Button>
                </Form>
            }
        </>
    );
};

export default ResetPassword;
