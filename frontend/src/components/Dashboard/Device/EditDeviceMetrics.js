import React, {useEffect, useState} from "react";
import { makeStyles } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addUser.css";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import axios from "axios";
import { Range, getTrackBackground } from "react-range";
const { Form } = require( "react-bootstrap" );




const useStyles = makeStyles(componentStyles);

const EditDeviceMetricsBody = (props) => {
    const [value, setValue] = useState(4)

    useEffect(() => {

        setValue(props.deviceDetails.deviceData.deviceConfiguration[0].value)

        // get the parks for populating the select component
        axios.get('http://localhost:8080/api/park/getAllParks'
        ).then((res)=>{

            let options = res.data.allParks.map((p)=>{
                return {value: p.id, label: p.parkName}
            })

            setParkOptions(options)
            setPark(options[0])

        }).catch((res)=>{
            console.log(JSON.stringify(res))
        });
    },[])


    const submit = (e) => {
        e.preventDefault()

        // set the frequency
        let obj = {
            id: props.id,
            value: frequency
        }

        axios.post('http://localhost:8080/api/user/createUser', obj
        ).then((res)=>{

            window.location.reload()

        }).catch((res)=>{
            console.log("response:"+JSON.stringify(res))
        });

    }



    return (
        <>
            <Form onSubmit={ submit }>
                <Row>
                    <Col>
                        <Form.Label>Hourly Frequency of Readings: { value }</Form.Label>
                        <input
                            style={{ width:"100%" }}
                            type="range"
                            min="0" max="50"
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
