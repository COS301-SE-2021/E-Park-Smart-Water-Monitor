import React, {useContext, useEffect, useState} from "react";
import AdminHeader from "../../components/Headers/AdminHeader";
import UserTable from "../../components/Admin/User/UserTable";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import componentStyles from "assets/theme/views/dashboard/dashboard.js";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import DeviceTable from "../../components/Admin/Device/DeviceTable";
import ParkTable from "../../components/Admin/Park/ParkTable";
import InspectionTable from "components/Admin/Inspection/InspectionTable";
import SiteTable from "../../components/Admin/Site/SiteTable";
import { AdminProvider } from '../../components/Admin/AdminContext'
import axios from "axios";
import {BeatLoader, DotLoader, PuffLoader} from "react-spinners";
import {css} from "@emotion/react";
import AddParkBody from "../../components/Admin/Park/AddParkBody";
import Modal from "../../components/Modals/Modal";
import {UserContext} from "../../Context/UserContext";
import LoadingContext from "../../Context/LoadingContext";
import Map from "components/Dashboard/Map.js"
import GeoMap from "../../components/Admin/GeoMap/GeoMap";
import "../../components/Admin/GeoMap/GeoMap.css"


import mask from '@turf/mask'

// openlayers
import GeoJSON from 'ol/format/GeoJSON'
import Feature from 'ol/Feature';

// components
import {transform} from "ol/proj";
const useStyles = makeStyles(componentStyles);

const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
`;

const overlay = css`
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 10;
`;

const gmap = css`
  height: 50vh;
  width: 100%;
`;
function Admin() {
    const classes = useStyles();
    const [park, setPark] = useState("") // for passing from park table to site table
    const [parksAndSites, setParksAndSites] = useState(null) // all the parks and sites
    const [loading, setLoading] = useState(false)
    const [show, setShow] = useState(false)
    const [value, setValue] = useState(0)

    const [ elevation, setElevation ] = useState([])
    const [ mapOutline, setMapOutline ] = useState([])
    const [ gateway, setGateway ] = useState([])
    const [ gatewayOn, setGatewayOn ] = useState(false)


    const user = useContext(UserContext)

    const selectPark = (details) => {
        setPark(details)
    }


    // get all the park and site data to populate the park and site tables
    // as well as the modals which require you select a park and a site
    // context will be passed down to all the children which require it
    // making load time faster and removes a lot of different API calls

    // You can get more than just parks and populate the context with may API
    // calls as they become necessary

    // A context object is created so that other inforamtion can be passed
    // to child components easily in future

    useEffect(() => {
        axios.get('http://localhost:8080/api/park/getAllParksAndSites', {
            headers: {
                'Authorization': "Bearer " + user.token
            }
        }).then((res)=>{
            if(res)
            {
                setParksAndSites(res.data)
            }
        });


        fetch('/lossAMid.json')
            // fetch('/rOutline.json')
            .then(response => response.json())
            .then( (fetchedFeatures) => {

                const wktOptions = {
                    dataProjection: 'EPSG:4326',
                    featureProjection: 'EPSG:3857'
                }
                const parsedFeatures = new GeoJSON().readFeatures(fetchedFeatures, wktOptions)

                // set features into state (which will be passed into OpenLayers
                //  map component as props)
                console.log(parsedFeatures)
                setGateway(parsedFeatures)

            })

        fetch('/elevation.json')
            // fetch('/rOutline.json')
            .then(response => response.json())
            .then( (fetchedFeatures) => {

                const wktOptions = {
                    dataProjection: 'EPSG:4326',
                    featureProjection: 'EPSG:3857'
                }
                const parsedFeatures = new GeoJSON().readFeatures(fetchedFeatures, wktOptions)

                // set features into state (which will be passed into OpenLayers
                //  map component as props)
                setElevation(parsedFeatures)

            })

        fetch('/rOutline.json')
            .then(response => response.json())
            .then( (fetchedFeatures) => {

                // parse fetched geojson into OpenLayers features
                //  use options to convert feature from EPSG:4326 to EPSG:3857
                const wktOptions =
                    {
                        featureProjection: 'EPSG:3857'
                    }
                const parsedFeatures = new GeoJSON().readFeatures( mask(fetchedFeatures),
                    {
                        featureProjection: 'EPSG:3857'
                    }

                )

                // set features into state (which will be passed into OpenLayers
                //  map component as props)
                console.log(parsedFeatures)
                setMapOutline(parsedFeatures)

            })
    },[value])

    // Context explained
    // https://medium.com/nerd-for-tech/using-context-api-in-react-with-functional-components-dbc653c7d485
    // https://medium.com/@danfyfe/using-react-context-with-functional-components-153cbd9ba214

    return (
        <>
                <AdminHeader/>
                <Modal onClose={() => setShow(false)} show={show}>
                    <div className="sweet-loading" style={ overlay }>
                        <PuffLoader css={override} size={150} color={"#123abc"} loading={loading} speedMultiplier={1.5} />
                    </div>
                </Modal>
                <Container
                    maxWidth={false}
                    component={Box}
                    marginTop="-1rem"
                    classes={{root: classes.containerRoot}}
                >
                    <Grid container>
                        <Grid
                            item
                            xs={12}
                            xl={12}
                            component={Box}
                            marginBottom="3rem!important"
                            classes={{root: classes.gridItemRoot}}
                        >
                            {user.role === "ADMIN" &&
                            <UserTable/>
                            }
                            {/*<Map/>*/}
                            <GeoMap

                                elevation={elevation}
                                gateway={gateway}
                                gatewayOn={gatewayOn}
                                gatewayChanger={setGateway}
                                mapO={mapOutline}
                                outlineChanger={setMapOutline}
                                gatewayOnChanger={setGatewayOn}
                                ree={""}


                            />
                            <DeviceTable/>
                            <InspectionTable/>
                        </Grid>

                        {/* Sites altered on the change of park */}
                        {user.role === "ADMIN" &&
                        <Grid
                            item
                            xs={12}
                            xl={7}
                            component={Box}
                            marginBottom="3rem!important"
                            classes={{root: classes.gridItemRoot}}
                        >
                            <SiteTable park={park}/>
                        </Grid>
                        }



                        {user.role === "ADMIN" &&
                        <Grid
                            item
                            xs={12}
                            xl={5}
                            component={Box}
                            marginBottom="3rem!important"
                            classes={{root: classes.gridItemRoot}}

                        >
                            <ParkTable select={selectPark}/>
                        </Grid>
                        }

                    </Grid>
                </Container>
        </>
    );

}

export default Admin;

