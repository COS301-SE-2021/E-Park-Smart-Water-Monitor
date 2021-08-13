import React, {useContext, useState} from "react";

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addDevice.css";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {Button, Form} from "react-bootstrap";
import Select from "react-select";
import {MapContainer, Marker, Popup, TileLayer, useMapEvents} from "react-leaflet";
import AdminContext from "../AdminContext";
import axios from "axios";

const useStyles = makeStyles(componentStyles);
const mapStyles = {
    width: `100%`,
    height: `100%`
};

const AddParkBody = (props) => {
    const classes = useStyles();
    const theme = useTheme();
    const [name, setName] = useState("")
    const [latitude, setLatitude] = useState(-25.899494434)
    const [longitude, setLongitude] = useState(28.280765508)

    const context = useContext(AdminContext)
    const toggleLoading = context.toggleLoading

    // getting the clicked location on
    function MapEvents() {
        const map = useMapEvents({
            click: (e) => {
                setLatitude(e.latlng.lat)
                setLongitude(e.latlng.lng)
            }
        })
        return null
    }

    const createPark = (e) => {
        toggleLoading()
        e.preventDefault()
        let obj = {
            parkName: name,
            latitude: latitude,
            longitude: longitude
        }
        const config = {
            headers: { Authorization: `Bearer ${sessionStorage.getItem('token')}` }
        };
        console.log("Adding Park: "+JSON.stringify(obj))
        axios.post('http://localhost:8080/api/park/addPark',
            obj, config
        ).then((res) => {

            toggleLoading();
            props.closeModal()
            props.reloadParkTable();

        }).catch((res) => {

            toggleLoading()
            console.log("error adding park: "+JSON.stringify(res))

        });
    }

    return (
        <>
            <Form onSubmit={ createPark }>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Park Name</Form.Label>
                            <Form.Control required={"required"} type="text" placeholder="Park Name" name="name" value={name} onChange={e => setName(e.target.value)}/>
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
                        Click on the map to select the park location
                        <div style={ { height: 250 } } className="mb-3" >
                            {/*rietvlei centre*/}
                            {latitude && longitude && <MapContainer style={mapStyles} center={[-25.88536975144579, 28.277796392845673]} zoom={13} scrollWheelZoom={false}>
                                <TileLayer
                                    attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                                />
                                <Marker position={[latitude, longitude]}>
                                    <Popup>
                                        { name !== "" && `${name} location` }
                                        { name === "" && "Park Location" }
                                    </Popup>
                                </Marker>


                                <MapEvents/>
                            </MapContainer>}
                        </div>
                    </Col>
                </Row>


                <Button
                    background-color="primary"
                    variant="primary"
                    type="submit">
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default AddParkBody;
