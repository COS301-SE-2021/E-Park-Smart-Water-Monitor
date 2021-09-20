/* eslint-disable */
import React, {useContext, useEffect, useState} from "react";
import {Form} from 'react-bootstrap';
import Button from "@material-ui/core/Button";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import axios from "axios";
import LoadingContext from "../../Context/LoadingContext";
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
import {useTheme} from "@material-ui/core/styles";

const ResetPassword = (props) => {
    const theme = useTheme();
    const [username, setUsername] = useState("")
    const [resetCode, setResetCode] = useState(false) // the code they type in to verify on backend on second API call
    const [error, setError] = useState(false)
    const [errorConfirmation, setErrorConfirmation] = useState(false)
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

        axios.post('/user/resetPassword', obj).then((res) => {

            toggleLoading();
            if(res.data.code === "User not found"){
                setError(res.data.code)
            }else{
                setNext(true)
            }

        }).catch((res) => {

            toggleLoading()

        });
    }

    function setCharAt(str,index,chr) {
        if(index > str.length-1) return str;
        return str.substring(0,index) + chr + str.substring(index+1);
    }

    useEffect(()=>{

    }, [next]);

    const submitNewPassword = (e) => {
        e.preventDefault()

        setErrorConfirmation("")

        if(newPassword !== newPasswordConfirmed)
        {
            setErrorConfirmation("Passwords do not match")
        }else {
            let passwordRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,}$/;
            if (!passwordRegex.test(newPassword)) {
                setErrorConfirmation("The password does not comply with the minimum requirements. " +
                    "Your password should contain atleast 10 characters, one capital letter, one lower case letter, " +
                    "one number and finally a minimum of one special character: #, ?, !, @, $, %, ^, &, *, -")
            }
            else{
                toggleLoading()

                let num1= Math.floor((Math.random() * newPassword.length) + 1)
                let num2= newPassword.length- num1
                let x = Math.floor((Math.random() * 3) + 2)
                const alpha = "abc"
                let c= alpha[Math.floor(Math.random()*alpha.length)]
                let d = Math.floor((Math.random() * 3) + 1)
                let codes=["z13b76cCHh!+gfdI68[egs=sD3skr@kb9L;rk9df",
                           "0bHd8pb=4N4iKLhek)8fbgl3lSdi-ickt!~dw4^",
                           "#a6bF&;5c&4dlF/r*lbp+N]fu;Fzzzb98aQlkbl;"]

                let pieces= []
                let pieces2= []
                for (let i = 0, charsLength = newPassword.length; i < charsLength; i += (x-1)) {
                    pieces.push(newPassword.substring(i, i + (x-1)));
                }
                for (let i = 0, charsLength = newPasswordConfirmed.length; i < charsLength; i += (x-1)) {
                    pieces2.push(newPasswordConfirmed.substring(i, i + (x-1)));
                }

                let word=""
                if (c=="a"){
                    word = codes[0]
                }else if (c=="b"){
                    word = codes[1]
                }else if (c=="c"){
                    word = codes[2]
                }

                let scramble1 =""
                let scramble2 =""
                for (let i =0; i<pieces.length; i++){
                    scramble1+=pieces[i]+word[i]
                    scramble2+=pieces2[i]+word[i]
                }

                for (let i =(x+d-1); i<scramble1.length;i+=(x+d)){
                    scramble1= setCharAt(scramble1,i,String.fromCharCode((scramble1[i].charCodeAt())+d))
                    scramble2= setCharAt(scramble2,i,String.fromCharCode((scramble2[i].charCodeAt())+d))
                }

                const randoms = "abcdefghijklASDFGJKLMNBVCX123456789^&/@}{[]"
                let f= randoms[Math.floor(Math.random()*randoms.length)]

                scramble1+=d+"|"+num1+"?"+num2+"*"+x+f
                scramble2+=d+"|"+num1+"?"+num2+"*"+x+f
                
                let obj = {
                    username: username,
                    resetCode: resetCode,
                    newPassword: scramble1,
                    newPasswordConfirmed: scramble2
                }
                axios.post('http://localhost:8080/api/user/resetPasswordFinalize', obj).then((res) => {
                    toggleLoading();

                    if (res.data.success === false) {
                        setErrorConfirmation(res.data.message)
                    } else {
                        setShowAlert(true)
                        setTimeout(() => {
                            setShowAlert(false)
                            props.closeModal()
                        }, 5000)
                    }
                }).catch((res) => {
                    toggleLoading()
                });
        }
        }
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


                { error &&
                <Box
                    fontWeight="400"
                    component="large"
                    color={theme.palette.error}
                >
                    <Alert severity={"warning"}>{error}</Alert>
                </Box>
                }

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
                                             type="password"
                                             name="newPassword"
                                             value={newPassword}
                                             className={"mt-3"}
                                             onChange={e => setNewPassword(e.target.value)}
                                >
                                    <InputLabel>
                                        New Password
                                    </InputLabel>
                                    <OutlinedInput
                                        type="password"
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
                                             type="password"
                                             name="confirmNewPassword"
                                             value={newPasswordConfirmed}
                                             className={"mt-3"}
                                             onChange={e => setNewPasswordConfirmed(e.target.value)}
                                >
                                    <InputLabel>
                                        Confirm New Password
                                    </InputLabel>
                                    <OutlinedInput
                                        type="password"
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

                    { showAlert &&
                    <Box
                        fontWeight="400"
                        component="large"
                        color={theme.palette.success}
                    >
                        <Alert severity={"success"}>Successfully changed password for {username}</Alert>
                    </Box> }

                    { errorConfirmation &&
                    <Box
                        fontWeight="400"
                        component="large"
                        color={theme.palette.error}
                    >
                        <Alert severity={"warning"}>{errorConfirmation}</Alert>
                    </Box> }

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
