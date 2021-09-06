import React, {useEffect, useState} from "react";
import "../../../assets/css/addDevice.css";


const PrevComments = (props) => {
    const [response, setResponse] = useState(null)

    useEffect(()=>{
        let com
        if(props.comments.length > 0)
        {
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
        }


    },[])

    return (
        <>
            {response}
            { !response && <div>Chat for &quot;{props.inspection.description}&quot; is empty.</div> }
        </>
    );
};

export default PrevComments;
