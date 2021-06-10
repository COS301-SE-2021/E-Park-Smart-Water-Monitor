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


const useStyles = makeStyles(componentStyles);

function DeviceTable(props) {
  const classes = useStyles();
  const [response, setResponse] = useState(null)


    useEffect(() => {
        if(props.devices)
        {
            const m = props.devices.map((device) =>
                <TableRow key={device.deviceName}>
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
                    <TableCell classes={{ root: classes.tableCellRoot }}>
                        { device.deviceData.battery }%
                    </TableCell>
                </TableRow>
            );
            setResponse(m);
        }else{
            console.log("no device prop added")
        }
    },[props.devices])

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

            <TableContainer>
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
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        { response }
                    </TableBody>
                </Box>
            </TableContainer>
        </Card>
    </>
  );
}

export default DeviceTable;
