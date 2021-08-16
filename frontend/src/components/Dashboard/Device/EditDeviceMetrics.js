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
    const [value, setValue] = useState("")
    const [show, setShow] = useState(false)
    const [loading, setLoading] = useState(false)

    const toggleLoading = useContext(LoadingContext).toggleLoading

    useEffect(() => {

        // get the device details by id
        axios.post('http://localhost:8080/api/devices/getById', {
                deviceID: props.deviceDetails.deviceId
            }
        ).then((res)=>{

            let freq = res.data.device.deviceData.deviceConfiguration.filter((elem)=>{
                return elem.settingType==="reportingFrequency"
            })
            setValue(freq[0].value)

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
            value: value
        }

        axios.post('http://localhost:8080/api/devices/setMetricFrequency', obj
        ).then((res)=>{
            
            toggleLoading()
            props.closeModal()

        }).catch((res)=>{

            toggleLoading()
            console.log("response setMetricFrequency:"+JSON.stringify(res))
        });

    }


    return (
        <>
            <Form onSubmit={ submit }>
                <Row>
                    <Col>
                        { value &&
                            <>
                                <Form.Label>Hourly Frequency: {value}</Form.Label>
                                <input
                                style={{width:"100%"}}
                                type="range"
                                min="0" max="48"
                                value={value}
                                onChange={e => setValue(e.target.value)}
                                step="0.1"/>

                                <Form.Text className="text-muted">
                                eg. {value} would map to a reading every {value*60} mins
                                </Form.Text>
                            </>
                        }
                        { !value &&
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
