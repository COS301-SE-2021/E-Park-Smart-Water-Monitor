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
import Dialpad from '@material-ui/icons/Dialpad';
import Lock from '@material-ui/icons/Lock';
import {Alert} from "@material-ui/lab";

const ResetPassword = (props) => {
    const [username, setUsername] = useState("")
    const [code, setCode] = useState(false) // code on our side, can be used to match
    const [resetCode, setResetCode] = useState(false) // the code they type in to verify on backend on second API call
    const [error, setError] = useState(false)
    const [next, setNext] = useState(false)
    const [newPassword, setNewPassword] = useState(false)
    const [newPasswordConfirmed, setNewPasswordConfirmed] = useState(false)
    const [showAlert, setShowAlert] = useState(false)

    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading

    // send the username to the system to get an email
    const submitUsername = (e) => {
        e.preventDefault()
        toggleLoading()

        let obj = {
            username : username
        }

        axios.post('http://localhost:8080/api/user/resetPassword', obj).then((res) => {

            toggleLoading();

            console.log(JSON.stringify(res.data))

            if(res.data.code === "User not found"){
                setError(res.data.code)
            }else{
                setCode(res.data.code)
                setNext(true)
                console.log("code: "+code)
                console.log("next")
            }

        }).catch((res) => {

            toggleLoading()
            console.log("error sending username to reset password: "+JSON.stringify(res))

        });
    }

    useEffect(()=>{

    }, [next]);

    const submitNewPassword = (e) => {
        e.preventDefault()
        toggleLoading()


        console.log("submitting new password")

        let obj = {
            username: username,
            resetCode: resetCode,
            newPassword: newPassword,
            newPasswordConfirmed: newPasswordConfirmed
        }

        axios.post('http://localhost:8080/api/user/resetPasswordFinalize', obj).then((res) => {

            toggleLoading();
            console.log(JSON.stringify(res))

            if(res.data.success === "false")
            {
                setError(res.data.message)
            }else{
                setTimeout(()=>{
                    return
                }, 3000)
            }


        }).catch((res) => {

            toggleLoading()
            console.log("error sending username to reset password: "+JSON.stringify(res))

        });



    }

    return (
        <>

            {!next &&
            <Form onSubmit={submitUsername}>
                <Row>
                    <Col>
                        <Form.Group className="mb-3">

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
                        <Button background-color="danger" color="primary" variant="text" onClick={() => {
                            props.closeModal()
                        }}>
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

            }

            {   next &&
                <Form onSubmit={ submitNewPassword }>
                    <Row>
                        <Col>
                            <Form.Group className="mb-3">

                                Find the reset code in the email box for the user {username}
                                <FormControl fullWidth
                                             variant="outlined"
                                             type="text"
                                             name="code"
                                             value={resetCode}
                                             className={"mt-3"}
                                             onChange={e => setResetCode(e.target.value)}
                                >
                                    <InputLabel>
                                        Code
                                    </InputLabel>
                                    <OutlinedInput
                                        type="text"
                                        endAdornment={
                                            <InputAdornment position="end">
                                                <Box
                                                    component={Dialpad}
                                                    width="1.25rem!important"
                                                    height="1.25rem!important"
                                                />
                                            </InputAdornment>
                                        }

                                        labelWidth={40}
                                    />
                                </FormControl>

                                {/*New Password*/}
                                <FormControl fullWidth
                                             variant="outlined"
                                             type="text"
                                             name="newPassword"
                                             value={newPassword}
                                             className={"mt-3"}
                                             onChange={e => setNewPassword(e.target.value)}
                                >
                                    <InputLabel>
                                        New Password
                                    </InputLabel>
                                    <OutlinedInput
                                        type="text"
                                        endAdornment={
                                            <InputAdornment position="end">
                                                <Box
                                                    component={Lock}
                                                    width="1.25rem!important"
                                                    height="1.25rem!important"
                                                />
                                            </InputAdornment>
                                        }

                                        labelWidth={100}
                                    />
                                </FormControl>

                                {/*Confirm new password*/}
                                <FormControl fullWidth
                                             variant="outlined"
                                             type="text"
                                             name="confirmNewPassword"
                                             value={newPasswordConfirmed}
                                             className={"mt-3"}
                                             onChange={e => setNewPasswordConfirmed(e.target.value)}
                                >
                                    <InputLabel>
                                        Confirm New Password
                                    </InputLabel>
                                    <OutlinedInput
                                        type="text"
                                        endAdornment={
                                            <InputAdornment position="end">
                                                <Box
                                                    component={Lock}
                                                    width="1.25rem!important"
                                                    height="1.25rem!important"
                                                />
                                            </InputAdornment>
                                        }

                                        labelWidth={160}
                                    />
                                </FormControl>
                            </Form.Group>

                        </Col>
                    </Row>

                    <Grid container component={Box} marginTop="1rem">
                        <Grid item xs={6} component={Box} textAlign="left">
                            <Button color="primary" variant="text" onClick={() => {
                                props.closeModal()
                            }}>
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
            }
        </>
    );
};

export default ResetPassword;
