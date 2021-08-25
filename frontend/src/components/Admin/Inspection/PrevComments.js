import React, {useContext, useEffect, useState} from "react";
import {Form} from 'react-bootstrap';

import { makeStyles } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addDevice.css";
import {UserContext} from "../../../Context/UserContext";
import {TextareaAutosize} from "@material-ui/core";


const useStyles = makeStyles(componentStyles);


const PrevComments = (props) => {
    const user = useContext(UserContext)

    return (
        <>
            <label>{props.user}:</label><br/>
            <TextareaAutosize value={"hello world"} />
            <br/><br/>
            <label>{props.user}:</label><br/>
            <TextareaAutosize value={"hello world dej;oiewhjdo'iewhjd;o1iweh3d;o1u  h"} />
        </>
    );
};

export default PrevComments;
