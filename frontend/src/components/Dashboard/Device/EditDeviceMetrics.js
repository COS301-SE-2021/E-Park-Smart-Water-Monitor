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
const { Form } = require( "react-bootstrap" );




const useStyles = makeStyles(componentStyles);

const EditDeviceMetricsBody = (props) => {
    const [value, setValue] = useState(4)

    const context = useContext(AdminContext)
    const toggleLoading = context.toggleLoading

    useEffect(() => {

        setValue(props.deviceDetails.deviceData.deviceConfiguration[0].value)

        // get the device details by id
        alert("device ID: "+JSON.stringify(props.deviceDetails.deviceId))
        axios.post('http://localhost:8080/api/devices/getById', {
                deviceID: props.deviceDetails.deviceId
            }
        ).then((res)=>{

            alert(JSON.stringify(res))
            setValue(res.deviceDetails.deviceData.deviceConfiguration[0].value)

        }).catch((res)=>{
            console.log("response:"+JSON.stringify(res))
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
            alert("after edit: "+JSON.stringify(res))

        }).catch((res)=>{

            toggleLoading()
            alert("nope")
            console.log("response:"+JSON.stringify(res))
        });

    }



    return (
        <>
            <Form onSubmit={ submit }>
                <Row>
                    <Col>
                        <Form.Label>Hourly Frequency: { value }</Form.Label>
                        <input
                            style={{ width:"100%" }}
                            type="range"
                            min="0" max="48"
                            value={value}
                            onChange={e => setValue(e.target.value)}
                            step="0.1"/>
                        <Form.Text className="text-muted">
                            eg. {value} would map to a reading every {value*60} mins
                        </Form.Text>
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
