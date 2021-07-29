import React, {useEffect, useState} from "react";
import {Button, Form} from 'react-bootstrap';

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addDevice.css";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Select from "react-select";
import axios from "axios";
import {MapContainer, Marker, Popup, TileLayer} from "react-leaflet";
// import AdminModal from 'admin-modal.js'



const useStyles = makeStyles(componentStyles);

const mapStyles = {
    width: `100%`,
    height: `100%`
};

const AddDeviceBody = () => {
    const classes = useStyles();
    const theme = useTheme();

    // retrieved items from the DB to populate the select components
    const [parkOptions, setParkOptions] = useState("")
    const [siteOptions, setSiteOptions] = useState("")
    const [siteLoading, setSiteLoading] = useState(true)
    const [parkLoading, setParkLoading] = useState(true)

    // selected/provided items by the user
    const [name, setName] = useState("")
    const [park, setPark] = useState("") // id and name stored
    const [site, setSite] = useState("") // id and name stored
    const [model, setModel] = useState("ESP32")
    const [latitude, setLatitude] = useState(-25.899494434)
    const [longitude, setLongitude] = useState(28.280765508)

    // get the parks to populate the select
    useEffect(() => {
        setParkLoading(true)
        axios.get('http://localhost:8080/api/park/getAllParks'
        ).then((res)=>{

            let options = res.data.allParks.map((p)=>{
                return {value: p.id, label: p.parkName}
            })

            //set all park options in select
            setParkOptions(options)
            // set the defult park option to the first item
            setPark(options[0])
            // stop the loading symbol
            setParkLoading(false)


        }).catch((res)=>{
            console.log("response:"+JSON.stringify(res))
        });
    },[])

    // get sites for this park when the park selected changes
    useEffect(() => {
        if(park && park.value) {
            // loading symbol of the select component
            setSiteLoading(true)
            //clear the current selection
            setSite("")

            axios.post('http://localhost:8080/api/park/getParkWaterSites',
                {
                    parkId: park.value
                }
            ).then((res) => {

                let options = res.data.site.map((s) => {
                    return {value: s.id, label: s.waterSiteName}
                })
                
                setSiteOptions(options)
                setSite(options[0])
                setSiteLoading(false)


            }).catch((res) => {
                console.log("error getting water sites for park "+park.label)
            });

        }
    },[park])



    const createDevice = (e) => {
        e.preventDefault()
        axios.post('http://localhost:8080/api/devices/addDevice',
            {
                parkName: park.label,
                siteId: site.value,
                deviceModel: model,
                deviceName: name,
                latitude: latitude,
                longitude: longitude
            }
        ).then((res) => {

            window.location.reload()

        }).catch((res) => {
            console.log("error adding device: "+JSON.stringify(res))
        });
    }

    return (
        <>
            <Form onSubmit={ createDevice }>
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
                        <Select required={"required"} isLoading={parkLoading} className="mb-3" name="park" options={ parkOptions } value={park} onChange={e => setPark(e)}/>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Label>Site</Form.Label>
                        <Select required={"required"} isLoading={siteLoading} className="mb-3" name="site" options={ siteOptions } value={site} onChange={e => setSite(e)}/>
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

                <Row>
                    <Col>
                        <div style={ { height: 250 } } className="mb-3" >
                            {/*rietvlei centre*/}
                            {latitude && longitude && <MapContainer style={mapStyles} center={[-25.88536975144579, 28.277796392845673]} zoom={13} scrollWheelZoom={false}>
                                <TileLayer
                                    attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                                />
                                <Marker position={[latitude, longitude]}>
                                    <Popup>
                                        <h3>{name}</h3>
                                        location
                                    </Popup>
                                </Marker>
                            </MapContainer>}
                        </div>
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
