import React, {useContext, useEffect, useState} from "react";
import "../../../assets/css/addDevice.css";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {Button, Form} from "react-bootstrap";
import {MapContainer, Marker, Popup, TileLayer, useMapEvents} from "react-leaflet";
import axios from "axios";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";

const mapStyles = {
    width: `100%`,
    height: `100%`
};

const EditParkBody = (props) => {
    const [name, setName] = useState("")
    const [latitude, setLatitude] = useState(-25.899494434)
    const [longitude, setLongitude] = useState(28.280765508)
    // eslint-disable-next-line no-unused-vars
    const [error, setError] = useState("")

    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading

    function MapEvents() {
        const map = useMapEvents({
            click: (e) => {
                setLatitude(e.latlng.lat)
                setLongitude(e.latlng.lng)
            }
        })
        map.setView({lat: props.parkDetails.latitude, lng: props.parkDetails.longitude} )
        return null
    }

    useEffect(() => {
        if(props && props.parkDetails)
        {
            setName(props.parkDetails.parkName)
            setLatitude(props.parkDetails.latitude)
            setLongitude(props.parkDetails.longitude)
        }
    },[])

    const submit = (e) => {
        toggleLoading()
        e.preventDefault()

        if(props && props.parkDetails)
        {

            let obj = {
                parkId: props.parkDetails.id,
                parkName: name,
                latitude: latitude,
                longitude: longitude
            }

            axios.post('/park/editPark', obj, {
                    headers: {
                        'Authorization': "Bearer " + user.token
                    }
                }
            ).then((res)=>{

                if(res.data.success == "false")
                {
                    toggleLoading()
                    setError(res.data.status)
                }else{
                    toggleLoading()
                    props.closeModal()
                    props.reloadParkTable()
                }

                // eslint-disable-next-line no-unused-vars
            }).catch((res)=>{
            });
        }

    }


    return (
        <>
            <Form onSubmit={ submit }>
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
                    Edit
                </Button>
            </Form>
        </>
    );
};

export default EditParkBody;
