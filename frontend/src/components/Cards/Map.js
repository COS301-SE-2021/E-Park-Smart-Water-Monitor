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

function Map() {
  const classes = useStyles();
  const theme = useTheme();
  // const [result, setResult] = useState(null)
  const [response, setResponse] = useState(null)

    useEffect(() => {
        axios.post('http://localhost:8080/api/park/getParkWaterSites', {
            parkId: "2ea5ba27-9d8e-41a4-9628-485f0ae2fb57"
        }).then((res)=>{
            if(res)
            {
                // console.log("result water data: "+JSON.stringify(res))

                // console.log("devices: "+JSON.stringify(devices))
                const site = res.data.site; // site array
                const site_devices = []
                for (let i = 0; i < site.length ; i++) {
                    for (let p = 0; p < site[i].waterSourceDevices.length ; p++) {
                        site_devices.push(site[i].waterSourceDevices[p]);
                    }
                }

                // console.log("site devices: "+JSON.stringify(site_devices))

                const m = site_devices.map((device) =>
                    // <ListItem key={number.toString()}
                    //           value={number} />
                    <Marker key={device.deviceName} position={[ device.deviceData.latitude , device.deviceData.longitude ]}>
                        <Popup>
                            { device.deviceName }
                        </Popup>
                    </Marker>
                );
                setResponse(m);
                console.log("markers: "+JSON.stringify(m))
            }
        });
    }, []) // second param [] is a list of dependency to watch and run useEffect

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
                {/*<Marker position={[  -25.88248274150901, 28.26649806207706 ]}>*/}
                {/*    <Popup>*/}
                {/*        Site Name*/}
                {/*    </Popup>*/}
                {/*</Marker>*/}
                {/*<Marker position={[  -25.89, 28.27 ]}>*/}
                {/*    <Popup>*/}
                {/*        Site Name*/}
                {/*    </Popup>*/}
                {/*</Marker>*/}
              { response }
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