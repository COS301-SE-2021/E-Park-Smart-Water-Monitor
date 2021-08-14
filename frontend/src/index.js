import React from "react";
import ReactDOM from "react-dom";
import CssBaseline from "@material-ui/core/CssBaseline";
import { ThemeProvider } from "@material-ui/core/styles";
import theme from "assets/theme/theme.js";
import "assets/plugins/nucleo/css/nucleo.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import "assets/scss/argon-dashboard-react.scss";
import axios from "axios";
import Routing from "./Routing";
import {UserProvider} from "./Context/UserContext";

const App = () => {

    axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';
    axios.defaults.headers.get['Access-Control-Allow-Origin'] = '*';


    return (<ThemeProvider theme={theme}>
        <UserProvider>
            <CssBaseline />
            <Routing/>
        </UserProvider>
    </ThemeProvider>)

}

ReactDOM.render(<App/>, document.querySelector("#root"));
