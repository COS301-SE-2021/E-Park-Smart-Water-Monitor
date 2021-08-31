import {BrowserRouter, Redirect, Route, Switch} from "react-router-dom";
import AuthLayout from "./layouts/Auth";
import DashboardLayout from "./layouts/Dashboard";
import AdminLayout from "./layouts/Admin";
import React, {useContext} from "react";
import {UserContext} from "./Context/UserContext";

const Routing = () => {

    // Context explained
    // https://medium.com/nerd-for-tech/using-context-api-in-react-with-functional-components-dbc653c7d485
    // https://medium.com/@danfyfe/using-react-context-with-functional-components-153cbd9ba214
    const user = useContext(UserContext)

    return (
            <BrowserRouter>
                <Switch>
                    <Route path="/auth/index" render={(props) => <AuthLayout {...props}/>} />
                    <Route path="/dashboard/index" render={(props) => (user.isLogin()? <DashboardLayout {...props} /> : <Redirect to={"/auth/index"} /> ) } />
                    <Route path="/admin/index" render={(props) => ( (user.isLogin() && (user.role === "ADMIN" || user.role === "FIELD_ENGINEER")) ? <AdminLayout {...props} />: <Redirect to={"/auth/index"} /> ) } />
                    <Redirect from="/" to="/auth/index" />
                </Switch>
            </BrowserRouter>

    )
};

export default Routing;