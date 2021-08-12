import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";

import CssBaseline from "@material-ui/core/CssBaseline";
import { ThemeProvider } from "@material-ui/core/styles";

import theme from "assets/theme/theme.js";

import "assets/plugins/nucleo/css/nucleo.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import "assets/scss/argon-dashboard-react.scss";

import DashboardLayout from "layouts/Dashboard.js";
import AuthLayout from "layouts/Auth.js";
import AdminLayout from "layouts/Admin.js"
import useToken from "./Hooks/useToken";

const App = () => {
    const { token, setToken } = useToken();

    const isLogin = () => {
        // const tokenString = localStorage.getItem('token');
        // const userToken = JSON.parse(tokenString);
        // alert("login status: "+userToken?.token)
        // return userToken?.token

        let login = !(!token || token === "undefined");
        alert("login status: "+login)
        return login
    }

    return (<ThemeProvider theme={theme}>
        {/* CssBaseline kickstart an elegant, consistent, and simple baseline to build upon. */}
        <CssBaseline />
        <BrowserRouter>
            <Switch>
                <Route path="/auth/index" render={(props) => <AuthLayout {...props} setToken={setToken}/>} />
                <Route path="/dashboard/index" render={(props) => (isLogin()? <DashboardLayout {...props} /> : <Redirect to={"/auth/index"} /> ) } />
                <Route path="/admin/index" render={(props) => <AdminLayout {...props} />} />
                <Redirect from="/" to="/auth/index" />
            </Switch>
        </BrowserRouter>
    </ThemeProvider>)

}

ReactDOM.render(<App/>, document.querySelector("#root"));
