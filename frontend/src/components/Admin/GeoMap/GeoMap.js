import React, {useState, useEffect, useRef, useContext} from 'react';
import 'ol/ol.css';
import { fromLonLat, transformExtent, toLonLat } from 'ol/proj';
import Map from 'ol/Map'
import View from 'ol/View'
import {Control, defaults as defaultControls} from 'ol/control';
import componentStyles from "assets/theme/views/admin/admin";
import TileLayer from 'ol/layer/Tile'
import VectorLayer from 'ol/layer/Vector'
import VectorSource from 'ol/source/Vector'
import VectorImageLayer from 'ol/layer/VectorImage';
import XYZ from 'ol/source/XYZ'
import {transform} from 'ol/proj'
import {toStringXY} from 'ol/coordinate';
import mask from '@turf/mask'
import {Circle as CircleStyle,Icon, Fill, Stroke, Style} from 'ol/style';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import GeoJSON from 'ol/format/GeoJSON';
import "./GeoMap.css"
import Box from "@material-ui/core/Box";
import Modal from "../../Modals/Modal";
import EditInspection from "../Inspection/EditInspection";
import Comments from "../Inspection/Comments";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import Typography from "@material-ui/core/Typography";
import {Tooltip} from "@material-ui/core";
import {Refresh} from "@material-ui/icons";
import {ScaleLoader} from "react-spinners";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Container from "@material-ui/core/Container";
import {makeStyles} from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
// import HeightIcon from '@mui/icons-material/Height';
// import CellWifiIcon from '@mui/icons-material/CellWifi';
import { MdSettingsInputAntenna } from "react-icons/md";
import { MdTimeline } from "react-icons/md";
import ToggleButton from "@mui/material/ToggleButton";
import ToggleButtonGroup from "@mui/material/ToggleButtonGroup";
import LoadingContext from "../../../Context/LoadingContext";
import {UserContext} from "../../../Context/UserContext";
import axios from "axios";
let isOn=false;
let isOnE=false;
let previousToggle ="none"

const useStyles = makeStyles(componentStyles);

function GeoMap(props) {
    const classes = useStyles();
    const [ map, setMap ] = useState()
    const [ borderLayer, setBorderLayer ] = useState()
    const [ elevationLayer, setElevationLayer ] = useState()
    const [ gatewayLayer, setGatewayLayer ] = useState()
    const [ selectedCoord , setSelectedCoord ] = useState()
    const [view, setView] = React.useState("elevation");

    const [ gatewayOn , setGatewayOn,getGatewayOn ] = useState(props.gatewayOn)
    const [ reserveBorder , setReserveBorder ] = useState(props.mapO)

    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading
    const user = useContext(UserContext)

    const [ maskLayer , setMaskLayer ] = useState(

        new VectorLayer({
            source: new VectorSource({
                features: reserveBorder
                // format: new GeoJSON(),
            }),
            updateWhileAnimating:true,
            updateWhileInteracting_:true,
            hover: true,
            style:

                new Style({
                    fill: new Fill({
                        color: [255,255,255,1]
                    }),
                    stroke: new Stroke
                    (
                        {
                            color:'#fff',
                            width:2
                        }
                    )

                })

        })


    )

    // create state ref that can be accessed in OpenLayers onclick callback function
    //  https://stackoverflow.com/a/60643670
    // pull refs

    const handleChange = (event, nextView) => {
        setView(nextView);
        console.log("nv: " +nextView)
        // alert(nextView.valueOf());

        if (nextView==="elevation")
        {            // toggleElevation()


            isOn=false
            gatewayLayer.setVisible(false)
            elevationLayer.setVisible(true)
            previousToggle="elevation"

        }
        if(nextView==="gateway")
        {

            isOn=true
            elevationLayer.setVisible(false)
            gatewayLayer.setVisible(true)
            previousToggle="gateway"
        }

        if(nextView==null)
        {
            isOn=false
            if (previousToggle=="gateway")
            {
                gatewayLayer.setVisible(false)

            }
            if (previousToggle=="elevation"||previousToggle=="none")
            {
                elevationLayer.setVisible(false)

            }
            if (previousToggle=="none")
            {
                elevationLayer.setVisible(false)
                gatewayLayer.setVisible(false)
            }

            previousToggle=null
        }

    };
    const mapElement = useRef()

    const mapRef = useRef()
    mapRef.current = map
    const allowClick = useRef()
    allowClick.current=false


    // initialize map on first render - logic formerly put into componentDidMount
    useEffect( () => {
    console.log(allowClick.current)
        // fetch('/rOutline.json')
        //     .then(response => response.json())
        //     .then( (fetchedFeatures) => {
        //
        //         // parse fetched geojson into OpenLayers features
        //         //  use options to convert feature from EPSG:4326 to EPSG:3857
        //         const wktOptions =
        //         {
        //             featureProjection: 'EPSG:3857'
        //         }
        //         const parsedFeatures = new GeoJSON().readFeatures(mask(fetchedFeatures), wktOptions)
        //
        //         // set features into state (which will be passed into OpenLayers
        //         //  map component as props)
        //         console.log("aweeee")
        //         console.log(parsedFeatures)
        //         props.outlineChanger(parsedFeatures)
        //
        //     })


        const initialElevationLayer =
            new VectorImageLayer({
                imageRatio: 2,
                source: new VectorSource
                ({
                    features: props.elevation
                }),
                style:function (feature)
                {
                    return new Style({
                    fill: new Fill
                    ({
                        color: feature.get("fill")+"A8",
                    }),

                })
                }
            })

        const gatewayL =
            new VectorImageLayer({
                imageRatio: 2,
                visible:false,
                source: new VectorSource
                ({
                    features: props.gateway
                }),
                style:function (feature)
                {
                    return new Style({
                        fill: new Fill
                        ({
                            color: feature.get("fill")+"A8",
                        }),

                    })
                }
            })

        // create map
        const initialMap = new Map({
            target: mapElement.current,
            controls: defaultControls(),
            layers: [

                new TileLayer({
                    preload: Infinity,
                    source: new XYZ({
                        url: 'https://mt{0-3}.google.com/vt/lyrs=y&x={x}&y={y}&z={z}&hl=en',
                    }),
                }),

                initialElevationLayer,
                gatewayL,
                maskLayer
                // vectorLayer
            ],

            view: new View({
                center: fromLonLat([ 28.29145, -25.87687]), // south africa
                zoom: 12.5,
                // zoom: 14.7,
                enableRotation: false,
                minZoom: 12,
                // visibility:0.2
                //minZoom: 4,
            }),
        })

        // set map onclick handler
        initialMap.on('click', handleMapClick)


        // save map and vector layer references to state
        setElevationLayer(initialElevationLayer)
        setGatewayLayer(gatewayL)
        setMap(initialMap)
        setBorderLayer(maskLayer)
        // iconLayer.setSource(vectorSource)
        // gatewayLayer.setVisible(false)



    },[])

    useEffect(() => {
        // action on update of movies
    }, [getGatewayOn,setGatewayOn]);
    // update map if features prop changes - logic formerly put into componentDidUpdate
    useEffect( () => {

        if (props.elevation.length || reserveBorder.length) { // may be null on first render

            // set features to map
            elevationLayer.setSource(
                new VectorSource({
                    features: props.elevation // make sure features is an array
                })
            )

            borderLayer.setSource(
                new VectorSource({
                    features: props.mapO // make sure features is an array
                })
            )

            gatewayLayer.setSource(
                new VectorSource({
                    features: props.gateway // make sure features is an array
                })

            )
            // borderLayer.setVisible(true)
            // iconLayer.setSource(vectorSource)

        }

        setGatewayOn(props.gatewayOn)

    },[props.elevation,props.mapO,props.gateway,props.gatewayOn,gatewayLayer])

    // map click handler
    const handleMapClick = (event) =>
    {
        // get clicked coordinate using mapRef to access current React state inside OpenLayers callback
        //  https://stackoverflow.com/a/60643670
        const clickedCoord = mapRef.current.getCoordinateFromPixel(event.pixel);
        // transform coord to EPSG 4326 standard Lat Long
        const transormedCoord = transform( clickedCoord, 'EPSG:3857', 'EPSG:4326')
        setSelectedCoord( transormedCoord )
        console.log(transormedCoord[0])
        console.log(transormedCoord[1])

        // click gateway tab to send request, add loader
        if(isOn) {
            toggleLoading()
            console.log("WE REQUESTING BOI")
            const requestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + user.token

                },
                body: JSON.stringify({x: transormedCoord[0], y: transormedCoord[1]})
            };


            // fetch(new URL("http:localhost:8080/api/geodata/getSignalLoss"),
            //     requestOptions)
            //     .then(response => {response.json(); toggleLoading()})
            //     .then((fetchedFeatures) => {
            //
            //         // parse fetched geojson into OpenLayers features
            //         //  use options to convert feature from EPSG:4326 to EPSG:3857
            //         const wktOptions =
            //             {
            //                 featureProjection: 'EPSG:3857'
            //             }
            //             console.log(fetchedFeatures)
            //         const parsedFeatures = new GeoJSON().readFeatures((fetchedFeatures),
            //             {
            //                 featureProjection: 'EPSG:3857'
            //             }
            //         )
            //
            //         // set features into state (which will be passed into OpenLayers
            //         //  map component as props)
            //         console.log(parsedFeatures)
            //         props.gatewayChanger(parsedFeatures)
            //         // setMapOutline(parsedFeatures)
            //
            //     })


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
                    props.gatewayChanger(parsedFeatures)
                    // setMapOutline(parsedFeatures)

                })
        }

    }

    const toggleBorder =()=>
    {
        if (isOnE)
        {
            // props.outlineChanger([])
            console.log("OFF")
            borderLayer.setVisible(false)
            gatewayLayer.setVisible(false)
            elevationLayer.setVisible(false)
            isOnE=false
        }
        else
        {
            console.log("ON")
            isOnE=true
            borderLayer.setVisible(true)
            elevationLayer.setVisible(false)


        }
        isOn=false
        console.log("Allow VAL"+ isOn)

    }

    const toggleElevation =()=>
    {
        if (elevationLayer.getVisible())
        {
            // props.outlineChanger([])
            console.log("OFF")
            elevationLayer.setVisible(false)

        }
        else
        {
            console.log("ON")

            elevationLayer.setVisible(true)
            gatewayLayer.setVisible(false)

        }
        isOn=false
        isOnE=false
        console.log("Allow VAL"+ isOn)

    }

    const toggleGateway =()=>
    {
        //as aan
        if (gatewayLayer.getVisible())
        {
            console.log("OFF")
            gatewayLayer.setVisible(false)
            setGatewayOn(false)
            // props.gatewayOnChanger(false)
            isOn=false
            console.log("Allow VAL"+ isOn)
            // props.clickFlipper()


        }
        else
        {
            console.log("ON")
            gatewayLayer.setVisible(true)
            elevationLayer.setVisible(false)
            isOn=true
            console.log("ALLOW VAL"+isOn)


        }


    }
    // render component
    return (
        <>
    <Container
        maxWidth={false}
        component={Box}
        marginTop="-3rem"
        classes={{ root: classes.containerRoot }}
    >
        {/*<Modal title= "Edit Inspection" onClose={() => setShowEditInspection(false)} show={showEditInspection}>*/}
        {/*    <EditInspection reloadInspectionTable={ reloadInspectionTable } inspectionDetails={inspec} tog={() =>toggleshowEditInspection() }/>*/}
        {/*</Modal>*/}
        {/*<Modal title= "Inspection Comments" onClose={() => setShowComments(false)} show={showComments}>*/}
        {/*    <Comments reloadInspectionTable={ reloadInspectionTable } inspectionDetails={inspec} tog={() =>toggleshowComments() }/>*/}
        {/*</Modal>*/}
        <Grid container component={Box} marginTop="3rem">
            <Grid
                item
                xs={12}
                xl={12}
                component={Box}
                classes={{ root: classes.gridItemRoot }}
            >
                <Card classes={{
                    root: classes.cardRoot,
                }}>
                    <CardHeader
                        subheader={
                            <Grid
                                container
                                component={Box}
                                alignItems="center"
                                justifyContent="space-between"
                            >
                                <Grid item xs="auto">
                                    <Box
                                        component={Typography}
                                        variant="h3"
                                        marginBottom="0!important"
                                    >
                                        Elevation and Signal Map
                                    </Box>
                                </Grid>
                                <Grid item xs="auto">
                                    <Box
                                        justifyContent="flex-end"
                                        display="flex"
                                        flexWrap="wrap"
                                    >
                                        {/*<Box style={{float:'right'}}>*/}

                                    </Box>
                                </Grid>
                            </Grid>
                        }
                        classes={{ root: classes.cardHeaderRoot }}

                    >
                    </CardHeader>
                    <Box style={{float:'right'}}>

                        <ToggleButtonGroup   color="primary"
                                             value={view}
                                             exclusive onChange={handleChange}
                                             style={{background:'white',right:'0', marginTop:'1%',marginRight:'3%',zIndex: '100',position: 'absolute'}}>
                            <ToggleButton value="elevation" aria-label="elevation">
                                <MdTimeline />
                                Elevation
                            </ToggleButton>
                            <ToggleButton value="gateway" aria-label="gateway">
                                <MdSettingsInputAntenna />
                                Gateway
                            </ToggleButton>
                        </ToggleButtonGroup>

                        {/*<Button*/}
                        {/*    variant="contained"*/}
                        {/*    color="primary"*/}
                        {/*    size="medium"*/}
                        {/*    style={{width: '100px'}}*/}
                        {/*    onClick={toggleBorder}>Toggle<br/> mask</Button> <br/>*/}
                        {/*<Button*/}
                        {/*    variant="contained"*/}
                        {/*    color="primary"*/}
                        {/*    size="medium"*/}
                        {/*    style={{width: '100px'}}*/}
                        {/*    onClick={toggleElevation}>Toggle elevation</Button><br/>*/}
                        {/*<Button*/}
                        {/*    variant="contained"*/}
                        {/*    color="primary"*/}
                        {/*    size="medium"*/}
                        {/*    style={{width: '100px'}}*/}
                        {/*    onClick={toggleGateway}>Toggle gateway</Button>*/}
                    </Box>
                    <Grid
                        container
                        component={Box}
                        alignItems="center"
                        justifyContent="space-between"
                    >
                        <Grid item xs={12}>
                            <Box>
                                <div ref={(mapElement)} className="map-container"></div>
                            </Box>
                        </Grid>
                        <Grid item xs={2} style={{height: '100%',float:'top'}}>
                            <Box>
                                <div className="clicked-coord-label">
                                    <p>{ (selectedCoord) ? toStringXY(selectedCoord, 5) : '' }</p>
                                </div>
                            </Box>
                        </Grid>
                    </Grid>

                </Card>
            </Grid>
            <Grid
                item
                xs={4}
                xl={10}
                component={Box}
                marginBottom="3rem!important"
                classes={{ root: classes.gridItemRoot }}
            >
            </Grid>
            <Grid
                item
                justify="end"
                xs={8}
                xl={2}
                component={Box}
                marginBottom="3rem!important"
                classes={{ root: classes.gridItemRoot }}
                style={{ display: "flex", justifyContent: "flex-end", marginTop: "5px"}}>
                {/*<Button*/}
                {/*    variant="contained"*/}
                {/*    color="primary"*/}
                {/*    size="medium"*/}
                {/*    style={{width:'200px'}}*/}
                {/*    onClick={() => setShow(!show)}*/}
                {/*>*/}
                {/*    Refresh Table*/}
                {/*</Button>*/}
            </Grid>
        </Grid>

    </Container>
        </>
    )

}

export default GeoMap