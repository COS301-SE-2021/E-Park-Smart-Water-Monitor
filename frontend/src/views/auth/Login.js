import React, {useEffect, useState} from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardHeader from "@material-ui/core/CardHeader";
// import Checkbox from "@material-ui/core/Checkbox";
import FilledInput from "@material-ui/core/FilledInput";
import FormControl from "@material-ui/core/FormControl";
// import FormControlLabel from "@material-ui/core/FormControlLabel";
import Grid from "@material-ui/core/Grid";
import InputAdornment from "@material-ui/core/InputAdornment";
// @material-ui/icons components
import Lock from "@material-ui/icons/Lock";
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import routes from "routes.js";
// core components
import componentStyles from "assets/theme/views/auth/login.js";
import axios from "axios";
import {ScaleLoader} from "react-spinners";
import PropTypes from 'prop-types';
import {useHistory} from "react-router-dom";

const useStyles = makeStyles(componentStyles);

function Login() {
  const classes = useStyles();
  const theme = useTheme();
  const history = useHistory();

  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")
  const [loading, setLoading] = useState(false)

  const login = () => {

    setLoading(true)
    let obj = {
      username: username,
      password: password
    }

    axios.post('http://localhost:8080/api/user/login', obj
    ).then((res)=>{
        setLoading(false)
        console.log("login response: "+JSON.stringify(res))


        let jwt = res.data.jwt
        axios.defaults.headers.common['Authorization'] = "Bearer " +jwt; // allow all axios requests to work
        axios.defaults.headers.delete['Authorization'] = jwt; // allow all axios requests to work
        sessionStorage.setItem('token', jwt)
        // setToken(jwt) // allow for authorisation of a user for the other pages

        history.push("/dashboard/index");

    }).catch((res)=>{
      console.log("response:"+JSON.stringify(res))
    });
  }

  return (
    <>
      <Grid item xs={12} lg={5} md={7}>
        <Card classes={{ root: classes.cardRoot }}>
          <CardHeader
            className={classes.cardHeader}
            title={
              <Box
                fontSize="120%"
                fontWeight="400"
                component="small"
                color={theme.palette.gray[600]}
              >
                Login
              </Box>
            }
            titleTypographyProps={{
              component: Box,
              textAlign: "center",
              marginBottom: "1rem!important",
              marginTop: "0.5rem!important",
              fontSize: "1rem!important",
            }}
          ></CardHeader>
          <CardContent classes={{ root: classes.cardContent }}>
            <Box
              color={theme.palette.gray[600]}
              textAlign="center"
              marginBottom="1rem"
              // marginTop=".5rem"
              fontSize="1rem"
            >
            </Box>
            <FormControl
              variant="filled"
              component={Box}
              width="100%"
              marginBottom="1rem!important"
            >
              <FilledInput
                // autoComplete="off"
                type="username"
                placeholder="Username"
                value={username}
                onChange={e => setUsername(e.target.value)}
                startAdornment={
                  <InputAdornment position="start">
                    <AccountCircleIcon />
                  </InputAdornment>
                }
              />
            </FormControl>
            <FormControl
              variant="filled"
              component={Box}
              width="100%"
              marginBottom="1rem!important"
            >
              <FilledInput
                // autoComplete="off"
                type="password"
                placeholder="Password"
                value={password}
                onChange={e => setPassword(e.target.value)}
                startAdornment={
                  <InputAdornment position="start">
                    <Lock />
                  </InputAdornment>
                }
              />
            </FormControl>
            {/*<FormControlLabel*/}
            {/*  value="end"*/}
            {/*  control={<Checkbox color="primary" />}*/}
            {/*  label="Remeber me"*/}
            {/*  labelPlacement="end"*/}
            {/*  classes={{*/}
            {/*    root: classes.formControlLabelRoot,*/}
            {/*    label: classes.formControlLabelLabel,*/}
            {/*  }}*/}
            {/*/>*/}

            <Box textAlign="center" marginTop="1.5rem" marginBottom="1.5rem">
              { loading &&
              <ScaleLoader size={50} color={"#5E72E4"} loading={loading} speedMultiplier={1.5} />
              }
              { !loading &&
              <Button color="primary" variant="contained" onClick={ login }>
                Sign in
              </Button>
              }
            </Box>
          </CardContent>
        </Card>
        <Grid container component={Box} marginTop="1rem">
          <Grid item xs={6} component={Box} textAlign="left">
            <a
              href="#admui"
              onClick={(e) => e.preventDefault()}
              className={classes.footerLinks}
            >
              Forgot password
            </a>
          </Grid>
          {/*<Grid item xs={6} component={Box} textAlign="right">*/}
          {/*  <a*/}
          {/*    href="#admui"*/}
          {/*    onClick={(e) => e.preventDefault()}*/}
          {/*    className={classes.footerLinks}*/}
          {/*  >*/}
          {/*    Create new account*/}
          {/*  </a>*/}
          {/*</Grid>*/}
        </Grid>
      </Grid>
    </>
  );
}


export default Login;
