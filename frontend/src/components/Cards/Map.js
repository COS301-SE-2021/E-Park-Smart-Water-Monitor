import React, { useState, useEffect } from "react";
// import PropTypes from "prop-types";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";


// core components
import componentStyles from "assets/theme/components/card-stats.js";
import CardHeader from "@material-ui/core/CardHeader";
import {MapContainer, Marker, Popup, TileLayer} from "react-leaflet";
import axios from 'axios'

const useStyles = makeStyles(componentStyles);

const mapStyles = {
  width: `100%`,
  height: `100%`
};

function Map({}) {
  const classes = useStyles();
  const theme = useTheme();
  const [result, setResult] = useState(null)


  const data = {
    "status": "Successfully found site.",
      "success": true,
      "site": {
    "id": "7b75749a-d7b1-4012-9596-e5a50a92037d",
        "waterSiteName": "Rhino Water Site",
        "latitude": -25.88248274150901,
        "longitude": 28.26649806207706,
        "waterSourceDevices": [
      {
        "deviceId": "92666ea5-fcc6-441c-9522-704566ff3e75",
        "deviceModel": "ESP32",
        "deviceName": "Water3000",
        "deviceData": {
          "longitude": -25.881565737140885,
          "latitude": 28.265639755240308,
          "battery": 0.0,
          "deviceStatus": null,
          "upTime": 0.0,
          "lifeTime": 0.0
        },
        "measurementSet": []
      }
    ],
        "infrastructureDevices": []
  }
  }

  const siteName = data.site;
  const devices = data.site.waterSourceDevices;
  const markers = devices.map((device) =>
      // <ListItem key={number.toString()}
      //           value={number} />
      <Marker key={device.toString()} position={[ device.deviceData.latitude , device.deviceData.longitude ]}>
        <Popup>
          { device.deviceName }
        </Popup>
      </Marker>
  );


  useEffect(() => {
    axios.post('http://localhost:8080/api/park/getParkWaterSites', {
          parkId: ""
        }).then((res)=>{
            setResult(res.data)
    });
  }, []) // second param [] is a list of dependency to watch and run useEffect

  console.log("result: "+JSON.stringify(result))
  // {result === null ? 'loading' : result.name}

  return (
    <>
      <Card>
        <CardHeader
            title={
              <Box component="span" color={theme.palette.gray[600]}>
                device layout
              </Box>
            }
            subheader="Park Map"
            classes={{ root: classes.cardHeaderRoot }}
            titleTypographyProps={{
              component: Box,
              variant: "h6",
              letterSpacing: ".0625rem",
              marginBottom: ".25rem!important",
              classes: {
                root: classes.textUppercase,
              },
            }}
            subheaderTypographyProps={{
              component: Box,
              variant: "h2",
              marginBottom: "0!important",
              color: "initial",
            }}
        ></CardHeader>
        <CardContent>

          <div style={ { height: 350 } }>
            {/*rietvlei centre*/}
            <MapContainer style={mapStyles} center={[-25.88536975144579, 28.277796392845673]} zoom={14} scrollWheelZoom={false}>
              <TileLayer
                  attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                  url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
              />
              { markers }
            </MapContainer>
          </div>

        </CardContent>
      </Card>
    </>
  );
}

// CardStats.defaultProps = {
//   color: "bgPrimaryLight",
// };

// CardStats.propTypes = {
//   subtitle: PropTypes.string,
//   title: PropTypes.string,
//   footer: PropTypes.oneOfType([PropTypes.string, PropTypes.node]),
//   icon: PropTypes.oneOfType([
//     // i.e. an icon name from Nucleo Icons - e.g. ni ni-atom
//     // // or an icon name from Font Awesome - e.g. fa fa-heart
//     PropTypes.string,
//     // i.e. a component from @material-ui/icons
//     PropTypes.object,
//   ]),
//   color: PropTypes.oneOf([
//     "bgPrimary",
//     "bgPrimaryLight",
//     "bgError",
//     "bgErrorLight",
//     "bgWarning",
//     "bgWarningLight",
//     "bgInfo",
//     "bgInfoLight",
//   ]),
// };

export default Map;
