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
import axios from "axios";
import {UserProvider} from "./Context/UserContext";

const App = () => {

    axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';
    axios.defaults.headers.get['Access-Control-Allow-Origin'] = '*';

    const isLogin = () => {
        let tokenString = sessionStorage.getItem('token')
        return !(!tokenString || tokenString === "undefined");
    }

    return (<ThemeProvider theme={theme}>
        {/* CssBaseline kickstart an elegant, consistent, and simple baseline to build upon. */}
        <UserProvider>
            <CssBaseline />
            <BrowserRouter>
                <Switch>
                    <Route path="/auth/index" render={(props) => <AuthLayout {...props}/>} />
                    <Route path="/dashboard/index" render={(props) => (isLogin()? <DashboardLayout {...props} /> : <Redirect to={"/auth/index"} /> ) } />
                    <Route path="/admin/index" render={(props) => <AdminLayout {...props} />} />
                    <Redirect from="/" to="/auth/index" />
                </Switch>
            </BrowserRouter>
        </UserProvider>
    </ThemeProvider>)

}

ReactDOM.render(<App/>, document.querySelector("#root"));
