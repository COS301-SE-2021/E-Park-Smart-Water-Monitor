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


const useStyles = makeStyles(componentStyles);

function DeviceDetails(props) {
  const classes = useStyles();
  const [device, setDevice] = useState(null)


    useEffect(() => {
        if(props.device)
        {
            setDevice(props.device);
        }else{
            console.log("no device prop added")
        }
    },[props.device])

  return (
    <>
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
                        { device && device.deviceData.deviceStatus }
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
                        { device && device.deviceData.battery }
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
                        { device && device.deviceData.latitude }
                    </Grid>
                </Grid>

            </CardContent>
        </Card>
    </>
  );
}

export default DeviceDetails;
