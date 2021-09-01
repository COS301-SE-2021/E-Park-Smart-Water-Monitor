import React, {useContext, useEffect, useState} from "react";
import "../../../assets/css/addUser.css";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import axios from "axios";
import {ScaleLoader} from "react-spinners";
import LoadingContext from "../../../Context/LoadingContext";
import {UserContext} from "../../../Context/UserContext";
import { Form } from "react-bootstrap";


const EditDeviceMetricsBody = (props) => {
    const [seconds, setSeconds] = useState("") // value in seconds
    const [readable, setReadable] = useState("")

    const toggleLoading = useContext(LoadingContext).toggleLoading
    const user = useContext(UserContext)

    useEffect(() => {

        // get the device details by id
        axios.post('/devices/getById', {
                deviceID: props.deviceDetails.deviceId
            },
            {
                headers: {
                    'Authorization': "Bearer " + user.token
                }
            }
        ).then((res)=>{

            // console.log(JSON.stringify(res.data))

            let freq = res.data.device.deviceData.deviceConfiguration.filter((elem)=>{
                return elem.settingType==="reportingFrequency"
            })
            let sec = freq[0].value*60*60
            setSeconds(sec)
            setReadable(secondsToDhms(sec))

        }).catch((res)=>{
            console.log("response getById:"+JSON.stringify(res))
        });

    },[])


    const submit = (e) => {
        e.preventDefault()
        toggleLoading()

        // set the frequency
        let obj = {
            id: props.deviceDetails.deviceId,
            value: Number((seconds/60/60).toFixed(6))
        }

        axios.put('http://localhost:8080/api/devices/setMetricFrequency', obj ,
            {
                headers: {
                    'Authorization': "Bearer " + user.token
                }
            }
        ).then(()=>{

            toggleLoading()
            props.closeModal()
            props.reloadDeviceTable()

        }).catch(()=>{

            toggleLoading()
        });

    }

    // https://stackoverflow.com/questions/846221/logarithmic-slider
    function logslider(position) {
        // position will be between 0 and 100
        let minp = 0;
        let maxp = 100;

        // The result should be between 100 an 10000000
        let minv = Math.log(100);
        let maxv = Math.log(10000000);

        // calculate adjustment factor
        let scale = (maxv-minv) / (maxp-minp);

        return Math.exp(minv + scale*(position-minp)); // will return number of seconds
    }

    // https://www.codegrepper.com/code-examples/javascript/js+convert+minutes+to+days+hours+minutes
    function secondsToDhms(seconds) {
        seconds = Number(seconds);
        let d = Math.floor(seconds / (3600*24));
        let h = Math.floor(seconds % (3600*24) / 3600);
        let m = Math.floor(seconds % 3600 / 60);
        let s = Math.floor(seconds % 60);

        let dDisplay = d > 0 ? d + (d === 1 ? " day, " : " days, ") : "";
        let hDisplay = h > 0 ? h + (h === 1 ? " hour, " : " hours, ") : "";
        let mDisplay = m > 0 ? m + (m === 1 ? " minute, " : " minutes, ") : "";
        let sDisplay = s > 0 ? s + (s === 1 ? " second" : " seconds") : "";
        let result = dDisplay + hDisplay + mDisplay + sDisplay;

        if(sDisplay === "" || (mDisplay === "" && sDisplay === "") || (hDisplay === "" && mDisplay === "" && sDisplay === ""))
        {
            result = result.slice(0, -2)
        }

        return result;
    }

    function getSliderValue(val){
        let log = logslider(val) // get number of seconds
        setReadable(secondsToDhms(log)) // make seconds something to read
        setSeconds(log)
    }


    return (
        <>
            <Form onSubmit={ submit }>
                
                <Row>
                    <Col>
                        { seconds &&
                            <>
                                <Form.Label>The device will take a reading every <br/> {readable}</Form.Label>
                                <input
                                style={{width:"100%"}}
                                type="range"
                                min="0" max="100"
                                // value={Number(seconds/60/60).toFixed(0)} // get the hour equivalent of the seconds
                                onChange={e => getSliderValue(e.target.value)}
                                step="0.1"/>

                            </>
                        }
                        { !seconds &&
                            <ScaleLoader size={50} color={"#5E72E4"} speedMultiplier={1.5} />
                        }
                    </Col>
                </Row>


                <Button variant="primary" type="submit" style={{marginTop: "2rem"}}>
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default EditDeviceMetricsBody;
