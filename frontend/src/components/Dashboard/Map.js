import React, {useState, useEffect, useContext} from "react";
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
import {MapContainer, Marker, Popup, TileLayer, useMapEvents} from "react-leaflet";
import {UserContext} from "../../Context/UserContext";

const useStyles = makeStyles(componentStyles);

const mapStyles = {
  width: `100%`,
  height: `100%`
};

function Map(props) {
    const classes = useStyles();
    const theme = useTheme();
    const [response, setResponse] = useState(null)

    const user = useContext(UserContext)

    // https://react-leaflet.js.org/docs/example-events/

    // functional component declared
    function MapEvents(p) {
        const map = useMapEvents({})
        let obj = { lat: p.device.deviceData.latitude, lng: p.device.deviceData.longitude }
        map.flyTo(obj)
        return null
    }


    useEffect(() => {
        if (props.devices) {
            const m = props.devices.map((device) =>
                <Marker
                    key={device.deviceId}
                    position={[device.deviceData.latitude, device.deviceData.longitude]}
                >
                    <Popup>
                        {device.deviceName}
                    </Popup>
                </Marker>
            );
            setResponse(m);
        }
    },[props.devices])



  return (
    <>
      <Card>
        <CardHeader
            title={
              <Box component="span" color={theme.palette.gray[600]}>
                device layout
              </Box>
            }
            subheader={user.parkName+" Map"}
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
        />
        <CardContent>

          <div style={ { height: 350 } }>
            {/*rietvlei centre*/}
            <MapContainer style={mapStyles} center={[-25.88536975144579, 28.277796392845673]} zoom={14} scrollWheelZoom={false}>
                  <TileLayer
                      attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                      url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                  />
                { response }
                <MapEvents device={props.device}/>
            </MapContainer>
          </div>

        </CardContent>
      </Card>
    </>
  );
}

export default Map;
