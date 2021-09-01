import React, {useContext, useEffect, useState} from "react";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import componentStyles from "assets/theme/components/card-stats.js";
import CardHeader from "@material-ui/core/CardHeader";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import {CardContent, Tooltip} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import EditDeviceMetrics from "./EditDeviceMetrics";
import Modal from "../../Modals/Modal";
import {UserContext} from "../../../Context/UserContext";
import Divider from "@material-ui/core/Divider";
import {CheckCircle, Visibility} from "@material-ui/icons";
import axios from "axios";
import LoadingContext from "../../../Context/LoadingContext";
import {ScaleLoader} from "react-spinners";

const useStyles = makeStyles(componentStyles);

function DeviceDetails(props) {
  const classes = useStyles();
  const [device, setDevice] = useState(null)
    // eslint-disable-next-line no-unused-vars
  const [battery, setBattery] = useState(null)
  const [status, setStatus] = useState(null)
  const [measurements, setMeasurements] = useState(null)
  const [access, setAccess] = useState(false)
  const [showEdit, setShowEdit] = useState(false)
  const [showPing, setShowPing] = useState(false)
  const [pingMessage, setPingMessage] = useState("") // will show a loader while waiting for ping response
  const [pinging, setPinging] = useState(false)
  const [lastSeen, setLastSeen] = useState("")

    const user = useContext(UserContext)
    const toggleLoading = useContext(LoadingContext).toggleLoading

    useEffect(() => {

        // access will be updated depending on the user priveleges
        setAccess(true)

        if(user.role && user.role === "RANGER" )
        {
            setAccess(true)
        }

        if(props.device != null)
        {
            setDevice(props.device);
            setStatus(props.device.deviceData.deviceStatus)
            setBattery(props.device.deviceData.deviceBattery)
            setMeasurements(props.device.measurementSet)
            setLastSeen(props.device.deviceData.lastSeen)
            filterMetrics()

        }else{
            console.log("no device prop added")
        }

    },[props.device])

    const gridItem = (name)=>{
      return (<Grid
          item
          xs={5}
          xl={5}
          component={Box}
          marginBottom="1rem!important"
          classes={{ root: classes.gridItemRoot }}
      >
          {name}
      </Grid>)
    }

    const filterMetrics = ()=>{
        let filteredMetrics = props.device.deviceData.deviceConfiguration.map((elem)=>{
            if(elem.settingType === "reportingFrequency"){
                return {settingType: "Reporting Frequency", value: secondsToDhms(elem.value*60*60)}
            }
        })
        return filteredMetrics
    }

    const filterMeasurementSet = (measurements) => {
      if(measurements) {
          let filteredMetrics = measurements.map((elem) => {
              let obj
              if (elem.type === "WATER_QUALITY") {
                  obj = {type: "Water Quality", value: elem.value, measurement: "PH"}
              } else if (elem.type === "WATER_TEMP") {
                  obj =  {type: "Water Temperature", value: elem.value, measurement: "°C"}
              } else if (elem.type === "WATER_LEVEL") {
                  obj =  {type: "Water Depth", value: elem.value, measurement: "cm"}
              }
              if (elem.value === -999)
              {
                  obj.measurement = ""
                  obj.value = "NA"
              }
              return obj;

          })
          return filteredMetrics
      }
      return []
    }

    const ping = ()=>{
        setPinging(true)
        setPingMessage("")
        // call the device readings to see if device is active
        let obj = {
            deviceID: device.deviceId
        }
        axios.post('/devices/pingDevice', obj, {
                headers: {
                    'Authorization': "Bearer " + user.token
                }
            }
        ).then((res)=>{
            setPinging(false)
            setShowPing(true)
            console.log(JSON.stringify(res.data))
            if(res.data.success === true){
                //ping successful
                setPingMessage(res.data.status)
                setMeasurements(res.data.innerResponses.measurements)
                setStatus(res.data.deviceStatus)
                let last
                res.data.innerResponses.measurements.forEach((elem)=>{
                    alert(elem)
                if(elem.type === "WATER_QUALITY")
                    alert(elem)
                    {
                        last = elem.deviceDateTime
                    }
                })

                setLastSeen(last)

            }else
            {
                setPingMessage(res.data.status)
            }

        }).catch((res)=>{
            toggleLoading()
            console.log("response ping:"+JSON.stringify(res))
        });
    }

    function secondsToDhms(seconds) {
        seconds = Number(seconds);
        let d = Math.floor(seconds / (3600*24));
        let h = Math.floor(seconds % (3600*24) / 3600);
        let m = Math.floor(seconds % 3600 / 60);
        let s = Math.floor(seconds % 60);

        let dDisplay = d > 0 ? d + (d === 1 ? " day, " : " days, ") : "";
        let hDisplay = h > 0 ? h + (h === 1 ? " hour, " : " hours, ") : "";
        let mDisplay = m > 0 ? m + (m === 1 ? " minute, " : " minutes, ") : "";
        let sDisplay = s > 0 ? s + (s === 1 ? " second" : " seconds") : "";
        let result = dDisplay + hDisplay + mDisplay + sDisplay;

        if(sDisplay === "" || (mDisplay === "" && sDisplay === "") || (hDisplay === "" && mDisplay === "" && sDisplay === ""))
        {
            result = result.slice(0, -2)
        }

        return result;
    }

  return (
    <>

        { device &&
        <Modal title="Edit Device Frequency" onClose={() => setShowEdit(false)} show={ showEdit }>
            <EditDeviceMetrics reloadDeviceTable={props.reloadDeviceTable} deviceDetails={ device } closeModal={()=>{ setShowEdit(false) }}/>
        </Modal> }

        <Modal title="Ping Response" onClose={() => setShowPing(false)} show={ showPing }>
            <div>{ pingMessage }</div>
            <Button
                variant={"contained"}
                size={"small"}
                onClick={ () => setShowPing(false) }
                style={{marginTop:"1rem"}}
            >
                Accept
            </Button>
        </Modal>


        <Card
            classes={{
                root: classes.cardRoot,
            }}
        >
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
                                { device && device.deviceName } Details
                            </Box>
                        </Grid>
                        <Grid item xs="auto">
                            {/*battery*/}
                            <Box
                                display="flex"
                                justifyContent="space-between"
                                alignItems="center"
                                paddingLeft="1.25rem"
                                paddingRight="1.25rem"
                                // paddingBottom="1rem"
                                // className={classes.outlineNone}
                            >
                                {/*<Box>*/}
                                {/*    { device && device.deviceData && device.deviceData.battery }%*/}
                                {/*</Box>*/}
                                {/*<Box*/}
                                {/*    component={BatteryStd}*/}
                                {/*    width="1rem!important"*/}
                                {/*    height="1rem!important"*/}
                                {/*/>*/}
                                <Box
                                    paddingLeft="1.25rem"
                                >
                                    { status }
                                </Box>

                                <Box
                                    component={CheckCircle}
                                    width="1rem!important"
                                    height="1rem!important"
                                />
                                { !pinging &&
                                    <Box
                                        paddingLeft="1.25rem"
                                    >
                                        <Button
                                            variant={"contained"}
                                            size={"small"}
                                            onClick={() => {
                                                ping()
                                            }}
                                        >
                                            Ping
                                        </Button>
                                    </Box>
                                }
                                { pinging &&
                                    <Box
                                        paddingLeft="1.25rem"
                                    >
                                        <Tooltip title="Ping..." arrow>
                                            <ScaleLoader size={10} height={15} color={"#5E72E4"} speedMultiplier={1.5} />
                                        </Tooltip>
                                    </Box>
                                }
                            </Box>
                        </Grid>
                    </Grid>
                }
                classes={{ root: classes.cardHeaderRoot }}
            />
            <CardContent>
                <Grid container>
                    <Grid
                        item
                        xs={12}
                        xl={12}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        <Box minWidth="2.25rem" display="flex" alignItems="center">
                            <Box
                                component={Visibility}
                                width="1.25rem!important"
                                height="1.25rem!important"
                                marginRight="1.25rem!important"
                                className={classes["text" + "PrimaryLight"]}
                            />
                             { lastSeen ? "Last Seen "+lastSeen.substr(0,10) + "   at   "+lastSeen.substr(11,8) : "Not Connected" }
                        </Box>

                    </Grid>
                    <Grid
                        item
                        xs={12}
                        xl={12}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        <b>Coordinates</b>
                    </Grid>
                    {gridItem("Latitude")}
                    { device && device.deviceData && gridItem(device.deviceData.latitude)}
                    {gridItem("Longitude")}
                    { device && device.deviceData && gridItem(device.deviceData.longitude)}

                    <Grid
                        item
                        xs={12}
                        xl={12}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        <Box
                            component={Divider}
                            marginBottom="1rem!important"
                            marginLeft="1.25rem!important"
                            marginRight="1.25rem!important"
                        />
                    </Grid>
                    <Grid
                        item
                        xs={12}
                        xl={12}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        <Box
                            display="flex"
                            justifyContent="space-between"
                            alignItems="right"
                            marginRight="1.25rem"
                        >
                            <b>Metrics</b>
                            { access &&
                            <Button
                                variant={"contained"}
                                size={"small"}
                                onClick={ ()=>{setShowEdit(true)} }
                            >
                                Edit Metrics
                            </Button>
                            }
                        </Box>
                    </Grid>
                    {/*All metric data*/}

                    { filterMetrics().map((m)=>{
                            if(m){return (
                                <>
                                    { gridItem(m.settingType) }
                                    { gridItem(m.value) }
                                </>
                            )}
                        })
                    }
                    {/*<b>Metrics</b>*/}

                    {/*Latest Device Readings*/}
                    <Grid
                        item
                        xs={12}
                        xl={12}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        <Box
                            component={Divider}
                            marginBottom="1rem!important"
                            marginLeft="1.25rem!important"
                            marginRight="1.25rem!important"
                        />
                    </Grid>

                    <Grid
                        item
                        xs={12}
                        xl={12}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        <b>Latest Device Readings</b>
                    </Grid>

                    { measurements && filterMeasurementSet(measurements).map((item)=>{
                        return (
                            <>
                            { gridItem(item.type) }
                            { gridItem(item.value+" "+item.measurement) }
                            </>
                        )
                    })
                    }
                    { device && device.measurementSet.length===0 &&
                        gridItem("No measurements recorded.")
                    }

                </Grid>

            </CardContent>
        </Card>
    </>
  );
}

export default DeviceDetails;
