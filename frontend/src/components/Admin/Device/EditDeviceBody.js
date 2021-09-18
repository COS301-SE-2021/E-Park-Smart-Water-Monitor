import React, {useContext, useEffect, useState} from "react";
import "../../../assets/css/addUser.css";
// Be sure to include styles at some point, probably during your bootstrapping
// import 'react-select/dist/react-select.css';

//import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import axios from "axios";
import {MapContainer, Marker, Popup, TileLayer, useMapEvents} from "react-leaflet";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";
import { Form } from  "react-bootstrap";
import {Alert} from "@material-ui/lab";

const mapStyles = {
    width: `100%`,
    height: `100%`
};

const EditDeviceBody = (props) => {

    // park must be selected before the site can be selected to maintain validity

    const [name, setName] = useState("")
    const [model] = useState("ESP32")
    const [latitude, setLatitude] = useState(-25.899494434)
    const [longitude, setLongitude] = useState(28.280765508)
    const [error, setError] = useState("")

    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading

    function MapEvents(props) {
        const map = useMapEvents({
            click: (e) => {
                setLatitude(e.latlng.lat)
                setLongitude(e.latlng.lng)
            }
        })
        map.setView({lat: props.deviceDetails.deviceData.latitude, lng: props.deviceDetails.deviceData.longitude} )
        return null
    }

    useEffect(() => {

        // add the props to the variables so that the user can change the values in the components
        if(props && props.deviceDetails)
        {
            // set all your device details here
            setName(props.deviceDetails.deviceName)
            setLatitude(props.deviceDetails.deviceData.latitude)
            setLongitude(props.deviceDetails.deviceData.longitude)
        }
    },[props.deviceDetails])

    const submit = (e) => {
        toggleLoading()
        e.preventDefault()
        setError(null)

        if(props && props.deviceDetails)
        {
            // device edit request
            let obj = {
                deviceId: props.deviceDetails.deviceId,
                deviceModel: model,
                deviceType: "WaterSource",
                deviceName: name,
                latitude: latitude,
                longitude: longitude
            }

            axios.put('http://localhost:8080/api/devices/editDevice', obj, {
                    headers: {
                        'Authorization': "Bearer " + user.token
                    }
                }
            ).then((res)=>{
                toggleLoading()
                console.log("response:"+JSON.stringify(res))
                if(res.data.success === false)
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
                                        <p><b>{name}</b></p>
                                        location
                                    </Popup>
                                </Marker>
                                <MapEvents deviceDetails={props.deviceDetails}/>
                            </MapContainer>}
                        </div>
                    </Col>
                </Row>

                { error &&
                    <Alert severity={"warning"} className="mb-3">{error}</Alert>
                }

                <Button background-color="primary" variant="primary" type="submit">
                    Edit
                </Button>
            </Form>
        </>
    );
};

export default EditDeviceBody;
