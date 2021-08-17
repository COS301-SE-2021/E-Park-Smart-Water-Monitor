import React, {useEffect, useState} from "react";
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


const useStyles = makeStyles(componentStyles);

function DeviceDetails(props) {
  const classes = useStyles();
  const [device, setDevice] = useState(null)
  const [access, setAccess] = useState(false)
  const [showEdit, setShowEdit] = useState(false)


    useEffect(() => {

        // access will be updated depending on the user priveleges
        setAccess(true)

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
                                Device Details
                            </Box>
                        </Grid>
                        <Grid item xs="auto">
                            <Box
                                justifyContent="flex-end"
                                display="flex"
                                flexWrap="wrap"
                            >
                                { access &&
                                    <Button
                                        color="primary"
                                        variant="contained"
                                        onClick={ ()=>{setShowEdit(true)} }
                                    >
                                        Metrics
                                    </Button>
                                }
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
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        Name
                    </Grid>
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        { device && device.deviceName }
                    </Grid>
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        Status
                    </Grid>
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        { device && device.deviceData && device.deviceData.deviceStatus }
                    </Grid>
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        Battery
                    </Grid>
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        { device && device.deviceData && device.deviceData.battery }%
                    </Grid>
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        Latitude
                    </Grid>
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        { device && device.deviceData && device.deviceData.latitude }
                    </Grid>
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        Longitude
                    </Grid>
                    <Grid
                        item
                        xs={6}
                        xl={6}
                        component={Box}
                        marginBottom="1rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        { device && device.deviceData && device.deviceData.longitude }
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
                </Grid>


            </CardContent>
        </Card>
    </>
  );
}

export default DeviceDetails;
