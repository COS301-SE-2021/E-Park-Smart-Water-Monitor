import React, {useContext, useEffect, useState} from "react";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";

// core components
import componentStyles from "assets/theme/components/card-stats.js";
import CardHeader from "@material-ui/core/CardHeader";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import {CardContent, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import EditDeviceMetrics from "./EditDeviceMetrics";
import Modal from "../../Modals/Modal";
import {UserContext} from "../../../Context/UserContext";
import Divider from "@material-ui/core/Divider";
import {BatteryStd, CheckCircle} from "@material-ui/icons";
import Clear from "@material-ui/icons/Clear";
import axios from "axios";
import ResetPassword from "../../Auth/ResetPassword";
import LoadingContext from "../../../Context/LoadingContext";


const useStyles = makeStyles(componentStyles);

function DeviceDetails(props) {
  const classes = useStyles();
  const [device, setDevice] = useState(null)
  const [access, setAccess] = useState(false)
  const [showEdit, setShowEdit] = useState(false)
  const [showPing, setShowPing] = useState(false)
  const [metrics, setMetrics] = useState("")
  const [pingMessage, setPingMessage] = useState("") // will show a loader while waiting for ping response

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
            if(device)
            {
                filterMetrics()
            }

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
      let filteredMetrics = device.deviceData.deviceConfiguration.map((elem)=>{
          if(elem.settingType === "phSensitivity"){
              return {settingType: "PH Sensitivity", value: elem.value}
          }else if(elem.settingType === "reportingFrequency"){
              return {settingType: "Reporting Frequency", value: elem.value}
          }else if(elem.settingType === "temperatureSensitivity"){
              return {settingType: "Temperature Sensitivity", value: elem.value}
          }else if(elem.settingType === "waterDepthSensitivity"){
              return {settingType: "Water Depth Sensitivity", value: elem.value}
          }
      })
        setMetrics(filteredMetrics)
    }

    const ping = ()=>{
        toggleLoading()
        setPingMessage("")
        // call the device readings to see if device is active
        let obj = {
            deviceName: device.deviceName,
            numResults: 1, // will get the ping results
            sorted: true
        }
        axios.post('http://localhost:8080/api/devices/getDeviceData', obj, {
                headers: {
                    'Authorization': "Bearer " + user.token
                }
            }
        ).then((res)=>{
            toggleLoading()
            setShowPing(true)
            console.log(JSON.stringify(res.data))
            if(res.data.success === true){
                //ping successful
                setPingMessage(res.data.status)
            }else
            {
                setPingMessage(res.data.status)
            }

        }).catch((res)=>{
            toggleLoading()
            console.log("response getDeviceData:"+JSON.stringify(res))
        });
    }

  return (
    <>

        { device &&
        <Modal title="Edit Device Frequency" onClose={() => setShowEdit(false)} show={ showEdit }>
            <EditDeviceMetrics deviceDetails={ device } closeModal={()=>{ setShowEdit(false) }}/>
        </Modal> }

        <Modal title="Ping Response" onClose={() => setShowPing(false)} show={ showPing }>
            <div>{ pingMessage }</div>
        </Modal>


        <Dialog
            open={showPing}
            onClose={() => {
                return setShowPing(false)
            }}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogContent
                className={"mb-0"}
            >
                <h4 className="mb-4">Ping Response</h4>
                <DialogContentText id="alert-dialog-description">
                    {pingMessage}
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={() => {
                    return setShowPing(false)
                }} color="primary" autoFocus>
                    Accept
                </Button>
            </DialogActions>
        </Dialog>


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
                                <Box>
                                    { device && device.deviceData && device.deviceData.battery }%
                                </Box>
                                <Box
                                    component={BatteryStd}
                                    width="1rem!important"
                                    height="1rem!important"
                                />
                                <Box
                                    paddingLeft="1.25rem"
                                >
                                    { device && device.deviceData && device.deviceData.deviceStatus }
                                </Box>

                                <Box
                                    component={CheckCircle}
                                    width="1rem!important"
                                    height="1rem!important"
                                />
                                <Box
                                    paddingLeft="1.25rem"
                                >
                                    <Button
                                        variant={"contained"}
                                        size={"small"}
                                        onClick={ ping }
                                    >
                                        Ping
                                    </Button>
                                </Box>

                            </Box>
                        </Grid>
                    </Grid>
                }
                classes={{ root: classes.cardHeaderRoot }}
            ></CardHeader>
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
                        <b>General</b>
                    </Grid>
                    {gridItem("Coordinates")}


                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        Lat: { device && device.deviceData && device.deviceData.latitude } <br/> Long: { device && device.deviceData && device.deviceData.longitude }
                    </Grid>
                    {gridItem("Uptime")}
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        { device && device.deviceData && device.deviceData.upTime }
                    </Grid>
                    {gridItem("Lifetime")}
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        { device && device.deviceData && device.deviceData.lifeTime }
                    </Grid>
                    {/*Metric information*/}
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
                    { metrics && metrics.map((item)=>{
                        return (
                            <>
                                { gridItem(item.settingType) }
                                { gridItem(item.value) }
                            </>
                        )
                    })}
                </Grid>


            </CardContent>
        </Card>
    </>
  );
}

export default DeviceDetails;
