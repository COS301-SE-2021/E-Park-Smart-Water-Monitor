import React, {useState} from "react";
import ReactDOM from "react-dom";
import CssBaseline from "@material-ui/core/CssBaseline";
import { ThemeProvider } from "@material-ui/core/styles";
import theme from "assets/theme/theme.js";
import "assets/plugins/nucleo/css/nucleo.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import "assets/scss/argon-dashboard-react.scss";
import Routing from "./Routing";
import {UserProvider} from "./Context/UserContext";
import {LoadingProvider} from "./Context/LoadingContext";
import {EditProfileProvider} from "./Context/EditProfileContext";
import {PuffLoader} from "react-spinners";
import Modal from "./components/Modals/Modal";
import EditProfile from "./components/EditProfile/EditProfile";
import {css} from "@emotion/react";
import axios from "axios";


const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
`;


const App = () => {
    const [loading, setLoading] = useState(false)
    const [showEditProfile, setShowEditProfile] = useState(false)

    axios.defaults.baseURL = 'https://e-park-backend.herokuapp.com/api';

    const toggleLoading = ()=>{
        setLoading(loading=>!loading)
    }

    const toggleEditProfile = ()=>{
        setShowEditProfile(showEditProfile=>!showEditProfile)
    }

    return (<ThemeProvider theme={theme}>
        <UserProvider>
            <Modal onClose={() => setLoading(false)} show={loading}>
                <PuffLoader css={override} size={150} color={"#123abc"} loading={loading} speedMultiplier={1.5} />
            </Modal>
            <Modal title="Edit Profile" onClose={() => setShowEditProfile(false)} show={showEditProfile} >
                <EditProfile  closeModall={() =>setShowEditProfile(false) } togglee={() =>toggleLoading}/>

            </Modal>
            <CssBaseline />
            {/*Loading Modal*/}
            <EditProfileProvider value={ { toggleEditProfile: toggleEditProfile } } >
                <LoadingProvider value={ { toggleLoading: toggleLoading } } >
                    <Routing/>
                </LoadingProvider>
            </EditProfileProvider>
        </UserProvider>
    </ThemeProvider>)

}

ReactDOM.render(<App/>, document.querySelector("#root"));


