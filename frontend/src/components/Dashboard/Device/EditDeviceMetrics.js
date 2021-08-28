import React, {useContext, useEffect, useState} from "react";
import { makeStyles } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addUser.css";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import axios from "axios";
import { Range, getTrackBackground } from "react-range";
import AdminContext from "../../Admin/AdminContext";
import {PuffLoader, ScaleLoader} from "react-spinners";
import Modal from "../../Modals/Modal";
import {css} from "@emotion/react";
import LoadingContext from "../../../Context/LoadingContext";
import {UserContext} from "../../../Context/UserContext";
const { Form } = require( "react-bootstrap" );



const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
`;

const overlay = css`
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 10;
`;

const useStyles = makeStyles(componentStyles);

const EditDeviceMetricsBody = (props) => {
    const [seconds, setSeconds] = useState("") // value in seconds
    const [value, setValue] = useState("") // value in seconds
    const [readable, setReadable] = useState("")

    const toggleLoading = useContext(LoadingContext).toggleLoading
    const user = useContext(UserContext)

    useEffect(() => {

        // get the device details by id
        axios.post('http://localhost:8080/api/devices/getById', {
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

        axios.put('http://localhost:8080/api/devices/setMetricFrequency', obj
        ).then((res)=>{

            toggleLoading()
            props.closeModal()
            props.reloadDeviceTable()

        }).catch((res)=>{

            toggleLoading()
            // console.log("response setMetricFrequency:"+JSON.stringify(res))
        });

    }

    // https://stackoverflow.com/questions/846221/logarithmic-slider
    function logslider(position) {
        // position will be between 0 and 100
        var minp = 0;
        var maxp = 100;

        // The result should be between 100 an 10000000
        var minv = Math.log(100);
        var maxv = Math.log(10000000);

        // calculate adjustment factor
        var scale = (maxv-minv) / (maxp-minp);

        return Math.exp(minv + scale*(position-minp)); // will return number of seconds
    }

    // https://www.codegrepper.com/code-examples/javascript/js+convert+minutes+to+days+hours+minutes
    function secondsToDhms(seconds) {
        seconds = Number(seconds);
        var d = Math.floor(seconds / (3600*24));
        var h = Math.floor(seconds % (3600*24) / 3600);
        var m = Math.floor(seconds % 3600 / 60);
        var s = Math.floor(seconds % 60);

        var dDisplay = d > 0 ? d + (d === 1 ? " day, " : " days, ") : "";
        var hDisplay = h > 0 ? h + (h === 1 ? " hour, " : " hours, ") : "";
        var mDisplay = m > 0 ? m + (m === 1 ? " minute, " : " minutes, ") : "";
        var sDisplay = s > 0 ? s + (s === 1 ? " second" : " seconds") : "";
        return dDisplay + hDisplay + mDisplay + sDisplay;
    }

    function getSliderValue(val){
        setValue(val) // the real value from the slider
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
