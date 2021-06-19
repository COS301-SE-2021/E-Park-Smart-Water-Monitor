import React, {useEffect, useState} from "react";
import {Button, Form} from 'react-bootstrap';

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../assets/css/addDevice.css";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Select from "react-select";
import axios from "axios";
// import AdminModal from 'admin-modal.js'



const useStyles = makeStyles(componentStyles);

const AddDeviceBody = () => {
    const classes = useStyles();
    const theme = useTheme();

    // retrieved items from the DB to populate the select components
    const [parkOptions, setParkOptions] = useState("")
    const [siteOptions, setSiteOptions] = useState("")

    // selected/provided items by the user
    const [name, setName] = useState("")
    const [park, setPark] = useState("")
    const [site, setSite] = useState("")
    const [model, setModel] = useState("")
    const [latitude, setLatitude] = useState("")
    const [longitude, setLongitude] = useState("")

    useEffect(() => {
        axios.get('http://localhost:8080/api/park/getAllParks'
        ).then((res)=>{

            let options = res.data.allParks.map((p)=>{
                return {value: p.id, label: p.parkName}
            })
            alert("options "+JSON.stringify(options))
            setParkOptions(options)


        }).catch((res)=>{
            alert("get park not working")
            console.log("response:"+JSON.stringify(res))
        });
    },[])

    // get sites for this park when the park selected changes
    useEffect(() => {
        axios.get('http://localhost:8080/api/park/getParkWaterSites',
            {
                parkId: park.value
            }
        ).then((res)=>{

            let options = res.data.allParks.map((p)=>{
                return {value: p.id, label: p.parkName}
            })
            alert("options "+JSON.stringify(options))
            setParkOptions(options)

        });
    },[park])


    // {
    //     "parkName": "Rietvlei Nature Reserve",
    //     "siteId": "2f9f5bbf-5c81-4996-ab1f-20b196fe0ebb",
    //     "deviceModel":"ESP32",
    //     "deviceName":"REEEEEEE4",
    //     "latitude":-25.899494434,
    //     "longitude": 28.280765508
    // }

    return (
        <>
            <Form>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Device Name</Form.Label>
                            <Form.Control required={"required"} type="text" placeholder="Device Name" name="name" value={name} onChange={e => setName(e.target.value)}/>
                        </Form.Group>

                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Label>Park</Form.Label>
                        <Select required={"required"} isClearable={true} className="mb-3" name="park" options={ parkOptions } value={park} onChange={e => setPark(e)}/>
                    </Col>
                </Row>


                <Button background-color="primary" variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default AddDeviceBody;
