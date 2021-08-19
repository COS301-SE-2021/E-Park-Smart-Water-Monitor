import React, {useEffect, useState} from "react";

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addDevice.css";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {Button, Form} from "react-bootstrap";
import Select from "react-select";
import {MapContainer, Marker, Popup, TileLayer} from "react-leaflet";
import axios from "axios";

const useStyles = makeStyles(componentStyles);
const mapStyles = {
    width: `100%`,
    height: `100%`
};

const EditSiteBody = (props) => {
    const [name, setName] = useState("")
    const [latitude, setLatitude] = useState(-25.899494434)
    const [longitude, setLongitude] = useState(28.280765508)
    const [error, setError] = useState("")

    useEffect(() => {
        if(props && props.parkDetails)
        {
            setName(props.parkDetails.parkName)
            setLatitude(props.parkDetails.latitude)
            setLongitude(props.parkDetails.longitude)
        }
    },[])

    const submit = (e) => {
        e.preventDefault()

        if(props && props.parkDetails)
        {

            let obj = {
                parkId: props.parkDetails.id,
                parkName: name,
                latitude: latitude,
                longitude: longitude
            }

            alert(JSON.stringify(obj))


            axios.post('http://localhost:8080/api/park/editPark', obj
            ).then((res)=>{

                console.log("response:"+JSON.stringify(res))
                if(res.data.success == "false")
                {
                    setError(res.data.status)
                    console.log("error with editing park")
                }else{
                    window.location.reload(); //need to get the new data from the db to populate the table again
                }

            }).catch((res)=>{
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

export default EditSiteBody;
