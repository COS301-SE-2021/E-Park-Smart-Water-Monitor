import React, {useContext, useEffect, useState} from "react";

import { makeStyles } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addUser.css";
// Be sure to include styles at some point, probably during your bootstrapping
// import 'react-select/dist/react-select.css';

//import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import axios from "axios";
import {MapContainer, Marker, Popup, TileLayer} from "react-leaflet";
import AdminContext from "../AdminContext";
const { Form } = require( "react-bootstrap" );



const useStyles = makeStyles(componentStyles);
const mapStyles = {
    width: `100%`,
    height: `100%`
};

const EditDeviceBody = (props) => {

    // retrieved items from the DB to populate the select components
    const [parkOptions, setParkOptions] = useState("")
    const [siteOptions, setSiteOptions] = useState("")
    const [siteLoading, setSiteLoading] = useState(true)
    const [parkLoading, setParkLoading] = useState(true)

    // park must be selected before the site can be selected to maintain validity

    const [name, setName] = useState("")
    const [park, setPark] = useState("") // id and name stored
    const [site, setSite] = useState("") // id and name stored
    const [model, setModel] = useState("ESP32")
    const [latitude, setLatitude] = useState(-25.899494434)
    const [longitude, setLongitude] = useState(28.280765508)
    const [error, setError] = useState("")

    const context = useContext(AdminContext)
    const toggleLoading = context.toggleLoading

    useEffect(() => {

        // add the props to the variables so that the user can change the values in the components
        if(props && props.deviceDetails)
        {
            // set all your device details here
            setName(props.deviceDetails.deviceName)
        }
    },[props.deviceDetails])

    const submit = (e) => {
        toggleLoading()
        e.preventDefault()

        if(props && props.deviceDetails)
        {
            // device edit request
            let obj = {
                parkName: park,
                siteId: site,
                deviceModel: model,
                deviceType: "WaterSource",
                deviceName: name,
                latitude: latitude,
                longitude: longitude
            }


            axios.post('http://localhost:8080/api/user/editDevice', obj
            ).then((res)=>{
                toggleLoading()
                console.log("response:"+JSON.stringify(res))
                if(res.data.success === "false")
                {
                    setError(res.data.status)
                    console.log("error with editing device")
                }else{
                    props.closeModal()
                    props.reloadDeviceTable()
                }

            }).catch((res)=>{
                toggleLoading()
                console.log("response:"+JSON.stringify(res))
            });
        }


    }


    return (
        <>
            <Form onSubmit={ submit }>
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
                    Edit
                </Button>
            </Form>
        </>
    );
};

export default EditDeviceBody;
