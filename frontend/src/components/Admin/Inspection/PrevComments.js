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
    const [response, setResponse] = useState([])

    useEffect(() => {
        let com
        let m = props.comments[props.comments.length].map((comment) => (
            // com= comment.split("\n")
            // <label>{com[0]}</label>
            // <br/>
            // <TextareaAutosize value={com[1]}/>
            // <br/><br/>
            <TextareaAutosize value={comment}/>
        ))
        setResponse(m)
    },[])

    return (
        <>
            {response}
        </>
    );
};

export default PrevComments;
