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
    const [park, setPark] = useState("") // id and name stored
    const [site, setSite] = useState("") // id and name stored
    const [model, setModel] = useState("ESP32")
    const [latitude, setLatitude] = useState(-25.899494434)
    const [longitude, setLongitude] = useState(28.280765508)

    useEffect(() => {
        axios.get('http://localhost:8080/api/park/getAllParks'
        ).then((res)=>{

            let options = res.data.allParks.map((p)=>{
                return {value: p.id, label: p.parkName}
            })
            setParkOptions(options)


        }).catch((res)=>{
            console.log("response:"+JSON.stringify(res))
        });
    },[])

    // get sites for this park when the park selected changes
    useEffect(() => {
        if(park && park.value) {

            axios.post('http://localhost:8080/api/park/getParkWaterSites',
                {
                    parkId: park.value
                }
            ).then((res) => {

                let options = res.data.site.map((s) => {
                    return {value: s.id, label: s.waterSiteName}
                })

                setSiteOptions(options)


            }).catch((res) => {
                console.log("error getting water sites for park "+park.label)
            });

        }
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
                <Row>
                    <Col>
                        <Form.Label>Site</Form.Label>
                        <Select required={"required"} isClearable={true} className="mb-3" name="site" options={ siteOptions } value={site} onChange={e => setSite(e)}/>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Latitude</Form.Label>
                            <Form.Control required={"required"} type="number" step={0.001} placeholder="latitude" name="latitude" value={latitude} onChange={e => setLatitude(e.target.value)}/>
                        </Form.Group>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Longitude</Form.Label>
                            <Form.Control required={"required"} type="number" step={0.001} placeholder="longitude" name="longitude" value={longitude} onChange={e => setLongitude(e.target.value)}/>
                        </Form.Group>
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
