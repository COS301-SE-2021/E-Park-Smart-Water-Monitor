import React, {useContext, useEffect, useState} from "react";
import {Button, Form} from 'react-bootstrap';
import "../../../assets/css/addDevice.css";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Select from "react-select";
import axios from "axios";
import {MapContainer, Marker, Popup, TileLayer, useMapEvents} from "react-leaflet";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";
import {Alert} from "@material-ui/lab";


const mapStyles = {
    width: `100%`,
    height: `100%`
};

const AddDeviceBody = (props) => {

    // retrieved items from the DB to populate the select components
    const [parkOptions, setParkOptions] = useState("")
    const [siteOptions, setSiteOptions] = useState("")

    // selected/provided items by the user
    const [name, setName] = useState("")
    const [park, setPark] = useState("") // id and name stored
    const [site, setSite] = useState("") // id and name stored
    const [model] = useState("ESP32")
    const [latitude, setLatitude] = useState(-25.899494434)
    const [longitude, setLongitude] = useState(28.280765508)
    const [error, setError] = useState(null)

    // use the context supplied from the admin component to get the parks and sites
    const user = useContext(UserContext)
    const toggleLoading = useContext(LoadingContext).toggleLoading

    // getting the clicked location on
    function MapEvents() {
        useMapEvents({
            click: (e) => {
                setLatitude(e.latlng.lat)
                setLongitude(e.latlng.lng)
            }
        })
        return null
    }



    const assignSiteOptions = (selectedPark) => {

        if(selectedPark && selectedPark.parkWaterSites)
        {
            let options = selectedPark.parkWaterSites.map((s) => {
                return {value: s.id, label: s.waterSiteName}
            })

            // set site to the topmost park
            setSiteOptions(options)
            setSite(options[0])
        }else{
            console.log("park watersites cannot be obtained")
        }

    }

    // get the parks to populate the select
    useEffect(() => {

        if(props.parksAndSites)
        {
            let options = props.parksAndSites.parks.map((p)=>{
                return {value: p.id, label: p.parkName}
            })

            //set all park options in select
            setParkOptions(options)
            // set the defult park option to the first item, will cause other useEffect to be sparked
            setPark(options[0])
        }



    },[])

    // get sites for this park when the park selected changes
    useEffect(() => {
        if(park && park.value) {
            // find the park object in the parksAndSites
            // object using the park ID to retreive the
            // relevant sites to display
            let selectedPark = props.parksAndSites.parks.filter( p => p.id === park.value )
            assignSiteOptions(selectedPark[0])
        }
    },[park])



    const createDevice = (e) => {
        toggleLoading()
        e.preventDefault()
        setError(null)


        let obj = {
            parkName: park.label,
            siteId: site.value,
            deviceType: "WaterSource", // and infrastructure
            deviceModel: model,
            deviceName: name,
            latitude: latitude,
            longitude: longitude
        }
        console.log("Adding Device: "+JSON.stringify(obj))
        axios.post('http://localhost:8080/api/devices/addDevice', obj, {
            headers: {
                'Authorization': "Bearer " + user.token
            }
        }).then((res) => {
            toggleLoading();
            if(res.data.success === false)
            {
                setError(res.data.status)
            }else{
                props.closeModal()
                props.reloadDeviceTable()
            }

        }).catch((res) => {

            toggleLoading()
            props.closeModal()
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
                            <Form.Control required={"required"} type="text" placeholder="Device Name" pattern={"[a-zA-Z0-9:_-]+"} name="name" value={name} onChange={e => setName(e.target.value)}/>
                            <Form.Text className="text-muted">
                                Use a dash or fullstop instead of a space
                            </Form.Text>
                        </Form.Group>

                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Label>Park</Form.Label>
                        <Select required={"required"} className="mb-3" name="park" options={ parkOptions } value={park} onChange={e => setPark(e)}/>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form.Label>Site</Form.Label>
                        <Select required={"required"}  className="mb-3" name="site" options={ siteOptions } value={site} onChange={e => setSite(e)}/>
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
                        Click on the map to select the device location
                        <div style={ { height: 250 } } className="mb-3" >
                            {/*rietvlei centre*/}
                            {latitude && longitude && <MapContainer
                                style={mapStyles}
                                center={[-25.88536975144579, 28.277796392845673]}
                                zoom={13}
                                draggable={true}
                                >
                                <TileLayer
                                    attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                                />
                                <Marker position={[latitude, longitude]}>
                                    <Popup>
                                        { name !== "" && `${name} location` }
                                        { name === "" && "Device Location" }
                                    </Popup>
                                </Marker>

                                <MapEvents/>

                            </MapContainer>}
                        </div>
                    </Col>
                </Row>

                { error &&
                    <Alert severity={"warning"} className="mb-3">{error}</Alert>
                }

                <Button background-color="primary" variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default AddDeviceBody;
