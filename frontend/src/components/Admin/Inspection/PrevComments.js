import React, {useContext, useEffect, useState} from "react";
import { makeStyles } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addDevice.css";

const useStyles = makeStyles(componentStyles);

const PrevComments = (props) => {
    const [response, setResponse] = useState([])

    useEffect(()=>{
        let com
        let m = props.comments.map((comment) => {
            com = comment.split("\n")
            if (com[0] == props.user+":"){
                return(
                    <div style={{
                    backgroundColor: "#4c7870",
                    padding: 10,
                    marginLeft: '50%',
                    marginTop: 5,
                    maxWidth: '50%',
                    alignSelf: 'flex-end',
                    borderRadius: 15,
                }}> <b><h9 style={{color: "#fff"}}> {com[0]}</h9></b>
                    <p style={{fontSize: 16, color: "#fff",overflowWrap: "break-word"}}> {com[1]}</p>
                </div>)
            }else{
                return(<div style={{
                    backgroundColor: "#dedede",
                    padding: 10,
                    marginTop: 5,
                    maxWidth: '50%',
                    alignSelf: 'flex-start',
                    borderRadius: 15,
                }}><b><h9 style={{color: "#000000"}}> {com[0]}</h9></b>
                    <p style={{fontSize: 16, color: "#000000", justifyContent: "center", overflowWrap: "break-word"}}> {com[1]}</p>
                </div>)
            }
        })
        setResponse(m)
    },[])

    return (
        <>
            {response}
        </>
    );
};

export default PrevComments;
