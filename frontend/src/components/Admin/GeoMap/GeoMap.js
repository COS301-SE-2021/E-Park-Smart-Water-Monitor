import React, { useState, useEffect, useRef } from 'react';
import { fromLonLat, transformExtent, toLonLat } from 'ol/proj';
import Map from 'ol/Map'
import View from 'ol/View'
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

let isOn=false;

function GeoMap(props) {

    const [ map, setMap ] = useState()
    const [ borderLayer, setBorderLayer ] = useState()
    const [ elevationLayer, setElevationLayer ] = useState()
    const [ gatewayLayer, setGatewayLayer ] = useState()
    const [ selectedCoord , setSelectedCoord ] = useState()

    const [ gatewayOn , setGatewayOn,getGatewayOn ] = useState(props.gatewayOn)
    const [ reserveBorder , setReserveBorder ] = useState(props.mapO)

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
            controls:[]
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

        if(isOn) {
            console.log("WE REQUESTING BOI")
            const requestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJUaGV5U2VlTWVSb2xhbiIsInJvbGVzIjoiQURNSU4iLCJleHAiOjE2MzIwNjQzMjd9.VQCkK2XCwaf8-TRftyqmZMrjrZv6qLyXvSSJiJPCDMK3anmFciiiMxbr3J8HtSuX42_a-9H3soUSVvh_KFspFA"

                },
                body: JSON.stringify({x: transormedCoord[0], y: transormedCoord[1]})
            };

            fetch(new URL("http:localhost:8080/api/geodata/getSignalLoss"),
                requestOptions)
                .then(response => response.json())
                .then((fetchedFeatures) => {

                    // parse fetched geojson into OpenLayers features
                    //  use options to convert feature from EPSG:4326 to EPSG:3857
                    const wktOptions =
                        {
                            featureProjection: 'EPSG:3857'
                        }
                    const parsedFeatures = new GeoJSON().readFeatures((fetchedFeatures),
                        {
                            featureProjection: 'EPSG:3857'
                        }
                    )

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
        if (borderLayer.getVisible())
        {
            // props.outlineChanger([])
            console.log("OFF")
            borderLayer.setVisible(false)
            gatewayLayer.setVisible(false)
            elevationLayer.setVisible(false)

        }
        else
        {
            console.log("ON")

            borderLayer.setVisible(true)

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
        <div>

            <div ref={(mapElement)} className="map-container"></div>
            <div >
                {/*<p>{ (selectedCoord) ? toStringXY(selectedCoord, 5) : '' }</p>*/}
                <p>{(gatewayOn) }</p>

            </div>

            <div className="clicked-coord-label">
                <p>{ (selectedCoord) ? toStringXY(selectedCoord, 5) : '' }</p>
            </div>
            <button onClick={toggleBorder}>Toggle borderOverlay</button>
            <button onClick={toggleElevation}>Toggle elevation</button>
            <button onClick={toggleGateway}>Toggle gateway</button>

        </div>
    )

}

export default GeoMap