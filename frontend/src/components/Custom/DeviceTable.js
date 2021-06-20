import React, {useEffect, useState} from "react";

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableRow from "@material-ui/core/TableRow";
import Typography from "@material-ui/core/Typography";
import componentStyles from "assets/theme/views/admin/admin";
import Button from "@material-ui/core/Button";
import Modal from "../../views/admin/modals/Modal";
import AddDeviceBody from "./AddDeviceBody";
import disableScroll from "disable-scroll";


const useStyles = makeStyles(componentStyles);

const DeviceTable = () => {
    const classes = useStyles();
    const theme = useTheme();
   // const [result, setResult] = useState(null)
    const [show, setShow] = useState(false);


   /* useEffect(() => {
        axios.post('http://localhost:8080/api/devices/getNumDevices', {
            parkID: "2ea5ba27-9d8e-41a4-9628-485f0ae2fb57"
        }).then((res)=>{
            setResult(res.data)
        });
    }, [])*/

    useEffect(() => {
        if (show == true) disableScroll.on()
        if (show == false) disableScroll.off()
    }, [show])


    return (
        <>
            <Container
                maxWidth={false}
                component={Box}
                classes={{ root: classes.containerRoot }}
            >
                <Modal title="Add Device" onClose={() => setShow(false)} show={show}>
                    <AddDeviceBody/>
                </Modal>

                <Grid container component={Box}>
                    <Grid
                        item
                        xs={12}
                        xl={12}
                        component={Box}
                        //marginBottom="3rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        <Card classes={{
                                root: classes.cardRoot,
                            }}>
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
                                                variant="h2"
                                                marginBottom="0!important"
                                            >
                                                Devices
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
                                        <Grid item xs="auto">
                                            <select name="Parks" id="parks" style={{width:'200px',padding:'5px', borderRadius:'5px'}}>
                                                <option value="all parks">All Parks</option>
                                            </select>
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
                                    <TableBody>
                                        <TableRow>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootBodyHead,
                                                }}
                                                style={{verticalAlign:'middle', width: '80%'}}
                                                scope="row"
                                            >
                                                WATER5000
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}
                                                       style={{verticalAlign:'middle'}}>
                                                <Button
                                                    size="small"
                                                >
                                                    Edit
                                                </Button>
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}
                                                       style={{verticalAlign:'middle'}}>
                                                <Button
                                                    size="small"
                                                >
                                                    Remove
                                                </Button>
                                            </TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Box>
                            </TableContainer>
                        </Card>

                    </Grid>
                    <Grid
                        item
                        xs={10}
                        xl={10}
                        component={Box}
                        marginBottom="3rem!important"
                        classes={{ root: classes.gridItemRoot }}
                        >
                    </Grid>
                    <Grid
                          item
                          justify="end"
                          xs={2}
                          xl={2}
                          component={Box}
                          marginBottom="3rem!important"
                          classes={{ root: classes.gridItemRoot }}
                          style={{ display: "flex", justifyContent: "flex-end", marginTop: "5px" }}>

                        <Button
                            variant="contained"
                            color="primary"
                            size="medium"
                            style={{width:'200px'}}
                            onClick={() => setShow(true)}
                        >
                            Add Device
                        </Button>
                    </Grid>
                </Grid>

            </Container>
        </>
    );
};

export default DeviceTable;
