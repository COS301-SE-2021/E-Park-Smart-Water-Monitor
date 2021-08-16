import React, {useContext, useEffect, useState} from "react";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";

// core components
import componentStyles from "assets/theme/components/card-stats.js";
import CardHeader from "@material-ui/core/CardHeader";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import {CardContent} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import EditDeviceMetrics from "./EditDeviceMetrics";
import Modal from "../../Modals/Modal";
import {UserContext} from "../../../Context/UserContext";
import Divider from "@material-ui/core/Divider";
import {BatteryStd, CheckCircle} from "@material-ui/icons";
import Clear from "@material-ui/icons/Clear";


const useStyles = makeStyles(componentStyles);

function DeviceDetails(props) {
  const classes = useStyles();
  const [device, setDevice] = useState(null)
  const [access, setAccess] = useState(false)
  const [showEdit, setShowEdit] = useState(false)

    const user = useContext(UserContext)

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
        }else{
            console.log("no device prop added")
        }

    },[props.device])

  return (
    <>

        { device && <Modal title="Edit Device Frequency" onClose={() => setShowEdit(false)} show={ showEdit }>
            <EditDeviceMetrics deviceDetails={ device } closeModal={()=>{ setShowEdit(false) }}/>
        </Modal> }

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
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        Coordinates
                    </Grid>
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
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        Uptime
                    </Grid>
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
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        Lifetime
                    </Grid>
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
                        <b>Metrics</b>
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
                        >
                            { access &&
                            <Button
                                variant={"outlined"}
                                size={"small"}
                                onClick={ ()=>{setShowEdit(true)} }
                            >
                                Edit Metrics
                            </Button>
                            }
                        </Box>
                    </Grid>

                </Grid>


            </CardContent>
        </Card>
    </>
  );
}

export default DeviceDetails;
