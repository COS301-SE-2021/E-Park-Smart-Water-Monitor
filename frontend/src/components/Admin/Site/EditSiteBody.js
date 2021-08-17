import React, {useContext, useEffect, useState} from "react";
import "../../../assets/css/addDevice.css";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {Button, Form} from "react-bootstrap";
import Select from "react-select";
import {MapContainer, Marker, Popup, TileLayer, useMapEvents} from "react-leaflet";
import LoadingContext from "../../../Context/LoadingContext";
import {UserContext} from "../../../Context/UserContext";
import axios from "axios";

const mapStyles = {
    width: `100%`,
    height: `100%`
};

const EditSiteBody = (props) => {
    const [name, setName] = useState("")
    const [latitude, setLatitude] = useState(-25.899494434)
    const [longitude, setLongitude] = useState(28.280765508)
    const [shape, setShape] = useState("")
    const [length, setLength] = useState("")
    const [width, setWidth] = useState("")
    const [radius, setRadius] = useState("")

    const toggleLoading = useContext(LoadingContext).toggleLoading
    const user = useContext(UserContext)

    let shapeOptions = [
        { value: 'circle', label: 'Circle' },
        { value: 'rectangle', label: 'Rectangle' }
    ];

    useEffect(() => {
        setShape(shapeOptions[0])
    },[])

    function MapEvents() {
        const map = useMapEvents({
            click: (e) => {
                setLatitude(e.latlng.lat)
                setLongitude(e.latlng.lng)
            }
        })
        return null
    }

    const createSite = (e) => {
        toggleLoading()
        e.preventDefault()
        let obj = {
            parkId: user.parkID,
            siteName: name,
            latitude: latitude,
            longitude: longitude,
            shape: shape.value,
            length: length,
            width: width,
            radius: radius
        }

        console.log("Adding watersite: "+JSON.stringify(obj))
        axios.post('http://localhost:8080/api/sites/addSite',
            obj, {
                headers: {
                    'Authorization': "Bearer " + user.token
                }
            }
        ).then((res) => {

            console.log("added site: "+JSON.stringify(res))
            toggleLoading();
            props.closeModal()
            props.reloadSiteTable();

        }).catch((res) => {

            toggleLoading()
            console.log("error adding site: "+JSON.stringify(res))

        });
    }

    return (
        <>
            <Form onSubmit={createSite}>
                <Row>
                    <Col>
                        <Form.Group className="mb-3" >
                            <Form.Label>Watersite Name</Form.Label>
                            <Form.Control required={"required"} type="text" placeholder="Watersite Name" name="name" value={name} onChange={e => setName(e.target.value)}/>
                        </Form.Group>

                    </Col>
                </Row>

                {/*Location details*/}
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
                                <MapEvents/>
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

export default EditSiteBody;
