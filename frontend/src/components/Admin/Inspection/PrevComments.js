import React, {useContext, useEffect, useState} from "react";
import {Form} from 'react-bootstrap';

import { makeStyles } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addDevice.css";
import {UserContext} from "../../../Context/UserContext";
import {TextareaAutosize} from "@material-ui/core";
import { View, Text } from "react";


const useStyles = makeStyles(componentStyles);


const PrevComments = (props) => {
    const user = useContext(UserContext)
    const [response, setResponse] = useState([])


    useEffect(() => {
        let com
        let m = props.comments.map((comment) => { com= comment.split("\n")
        return(
            <>
                <label>{com[0]}</label>
                <br/>
                <TextareaAutosize value={com[1]}/>
                <br/><br/>




            </>
        )})
       setResponse(m)
    },[])

    /*useEffect(() => {
        let com
        let m = props.comments.map((comment) => {
            com = comment.split("\n")
            if (com[0] == props.user) { //sent a message
                return (
                    <View style={{
                        backgroundColor: "#0078fe",
                        padding: 10,
                        marginLeft: '45%',
                       // borderRadius: 5,

                        marginTop: 5,
                        marginRight: "5%",
                        maxWidth: '50%',
                        alignSelf: 'flex-end',
                        borderRadius: 20,
                    }}>

                        <Text style={{fontSize: 16, color: "#fff",}}> {com[1]}</Text>
                        <View style={{
                            position: "absolute",
                            backgroundColor: "#0078fe",
                            //backgroundColor:"red",
                            width: 20,
                            height: 25,
                            bottom: 0,
                            borderBottomLeftRadius: 25,
                            right: -10
                        }}>
                        </View>
                        <View style={{
                            position: "absolute",
                            backgroundColor: "#eeeeee",
                            //backgroundColor:"green",
                            width: 20,
                            height: 35,
                            bottom: -6,
                            borderBottomLeftRadius: 18,
                            right: -20
                        }}></View>
                    </View>
                )
            } else {
                return ( //not sent by user currently logged in
                    <View style={{
                        backgroundColor: "#dedede",
                        padding: 10,
                        //borderRadius: 5,
                        marginTop: 5,
                        marginLeft: "5%",
                        maxWidth: '50%',
                        alignSelf: 'flex-start',
                        //maxWidth: 500,
                        //padding: 14,

                        //alignItems:"center",
                        borderRadius: 20,
                    }}>
                        <Text style={{fontSize: 16, color: "#000", justifyContent: "center"}}> {com[1]}</Text>
                        <View style={{
                            position: "absolute",
                            backgroundColor: "#dedede",
                            width: 20,
                            height: 25,
                            bottom: 0,
                            borderBottomRightRadius: 25,
                            left: -10
                        }}>
                        </View>
                        <View style={{
                            position: "absolute",
                            backgroundColor: "#eeeeee",
                            width: 20,
                            height: 35,
                            bottom: -6,
                            borderBottomRightRadius: 18,
                            left: -20
                        }}></View>
                    </View>
                )
            }
        })

        setResponse(m)
    },[])*/

    return (
        <>
            {response}
        </>
    );
};

export default PrevComments;
