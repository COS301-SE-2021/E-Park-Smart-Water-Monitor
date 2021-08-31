import React, {useEffect, useState} from "react";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";

// core components
import componentStyles from "assets/theme/components/card-stats.js";
import CardHeader from "@material-ui/core/CardHeader";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import "../../../index.css"
import {Refresh} from "@material-ui/icons";
import {Tooltip} from "@material-ui/core";

const useStyles = makeStyles(componentStyles);

function DeviceTable(props) {
  const classes = useStyles();
  const [response, setResponse] = useState(null)
  const [hover, setHover] = useState(true)
  const [value, setValue] = useState(0)

    const handleDeviceSelection = (device) => {
        // so that it doesn't run on render
        return () =>{
            props.load_device(device);
        }
    }

    const reloadDeviceTable = () =>{
      setValue(value=>value+1)
    }



    const toggleHover = ()=>{
      setHover(!hover)
    }

    useEffect(() => {

        let hoverStyle;
        if (hover) {
            hoverStyle = {cursor: 'pointer'}
        } else {
            hoverStyle = {cursor: 'default'}
        }

        if(props.devices)
        {
            const m = props.devices.map((device) =>
                <TableRow
                    key={device.deviceId}
                    onClick={ handleDeviceSelection(device) }
                    style={hoverStyle}
                    onMouseEnter={toggleHover}
                    onMouseLeave={toggleHover}
                >
                    <TableCell
                        classes={{
                            root:
                                classes.tableCellRoot +
                                " " +
                                classes.tableCellRootBodyHead,
                        }}
                        component="th"
                        variant="head"
                        scope="row"
                    >
                        { device.deviceName }
                    </TableCell>

                    <TableCell classes={{ root: classes.tableCellRoot }}>
                        { device.deviceData.deviceStatus }
                    </TableCell>
                    <TableCell className="table-sticky-column" classes={{ root: classes.tableCellRoot }}>
                        { device.deviceData.battery }%
                    </TableCell>
                    <TableCell className="table-sticky-column" classes={{ root: classes.tableCellRoot }}>
                        { device.deviceData.lastSeen ? device.deviceData.lastSeen.substr(0,10) : "Not Connected"}
                    </TableCell>
                </TableRow>


            );
            setResponse(m);
        }else{
            console.log("no device prop added")
        }
    },[props.devices, value])

  return (
    <>
        <Card
            classes={{
                root: classes.cardRoot,
            }}
        >
            <CardHeader
                style={{paddingBottom:"27.4px",paddingTop:"29.4px"}}
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
                                All Devices
                            </Box>
                        </Grid>
                        <Grid item xs="auto">
                            <Box
                                justifyContent="flex-end"
                                display="flex"
                                flexWrap="wrap"
                            >

                                <Tooltip title="Refresh Devices" arrow>
                                    <Box
                                        component={Refresh}
                                        width="1.25rem!important"
                                        height="1.25rem!important"
                                        className={classes["text"]}
                                        onClick={()=>{ reloadDeviceTable() }}
                                    />
                                </Tooltip>
                            </Box>
                        </Grid>
                    </Grid>
                }
                classes={{ root: classes.cardHeaderRoot }}
            />

            <div className="table-container">
                <TableContainer style={{maxHeight:"400px",overflowY:"auto"}}>
                    <Box
                        component={Table}
                        alignItems="center"
                        marginBottom="0!important"
                    >
                        <TableHead>
                            <TableRow>
                                <TableCell
                                    classes={{
                                        root:
                                            classes.tableCellRoot +
                                            " " +
                                            classes.tableCellRootHead,
                                    }}
                                >
                                    Device Name
                                </TableCell>
                                <TableCell
                                    classes={{
                                        root:
                                            classes.tableCellRoot +
                                            " " +
                                            classes.tableCellRootHead,
                                    }}
                                >
                                    Status
                                </TableCell>
                                <TableCell
                                    classes={{
                                        root:
                                            classes.tableCellRoot +
                                            " " +
                                            classes.tableCellRootHead,
                                    }}
                                >
                                    Battery Level
                                </TableCell>
                                <TableCell
                                    classes={{
                                        root:
                                            classes.tableCellRoot +
                                            " " +
                                            classes.tableCellRootHead,
                                    }}
                                >
                                    Last Seen
                                </TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            { response }
                        </TableBody>
                    </Box>
                </TableContainer>
            </div>
        </Card>
    </>
  );
}

export default DeviceTable;
