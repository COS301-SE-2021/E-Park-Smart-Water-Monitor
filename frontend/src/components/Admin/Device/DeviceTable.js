import React, {useContext, useEffect, useState} from "react";

import {makeStyles} from "@material-ui/core/styles";
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
import Modal from "../../Modals/Modal";
import AddDeviceBody from "./AddDeviceBody";
import axios from "axios";
import EditIcon from "@material-ui/icons/Edit";
import DeleteIcon from "@material-ui/icons/Delete";
import AssignmentTurnedInIcon from '@material-ui/icons/AssignmentTurnedIn';
import EditDeviceBody from "./EditDeviceBody";
import TableHead from "@material-ui/core/TableHead";
import IconButton from "@material-ui/core/IconButton";
import {Tooltip} from "@material-ui/core";
import AddInspectionBody from "../Inspection/AddInspectionBody";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";
import {ScaleLoader} from "react-spinners";



const useStyles = makeStyles(componentStyles);

const DeviceTable = () => {
    const classes = useStyles();
    const [showEdit, setShowEdit] = useState(false);
    const [showInspection, setShowInspection] = useState(false);
    const [show, setShow] = useState(false);
    const [response, setResponse] = useState([]);
    const [device, setDevice] = useState({});
    const [value, setValue] = useState(0);
    const [parksAndSites, setParksAndSites] = useState(null)

    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading

    const reloadDeviceTable = () => {
        setValue(value => value+1)
    }

    // on delete of a device
    const removeDevice = (id) => {
        return ()=>{

            toggleLoading()
            axios.delete('http://localhost:8080/api/devices/deleteDevice', {
                headers: {
                    Authorization: "Bearer " + user.token
                },
                data: {
                    id: id,
                }
            }).then(()=> {
                toggleLoading()
                setValue(value => value+1 ) // returns an updated value
            }).catch((res)=>{
                JSON.stringify(res)
            })
        }
    }

    useEffect(() => {
        axios.get('http://localhost:8080/api/park/getAllParksAndSites', {
            headers: {
                'Authorization': "Bearer " + user.token
            }
        }).then((res)=>{
            if(res)
            {
                setParksAndSites(res.data)
            }
        });
    },[])

    useEffect(() => {
        // get all users
        axios.get('http://localhost:8080/api/devices/getAllDevices', {
            headers: {
                'Authorization': "Bearer " + user.token
            }
        }).then((res)=>{
            // console.log(res.data.site[0])
            const m = res.data.site.map((device) =>
                <TableRow key={ device.deviceId } >
                    <TableCell
                        classes={{
                            root:
                                classes.tableCellRoot +
                                " " +
                                classes.tableCellRootBodyHead,
                        }}
                        style={{verticalAlign: 'middle', width: '80%'}}
                        scope="row"
                    >
                        { device.deviceName }
                    </TableCell>
                    <TableCell classes={{root: classes.tableCellRoot}}
                               style={{verticalAlign: 'middle'}}>

                        {/*https://material-ui.com/components/tooltips/*/}
                        <Tooltip title="Edit" arrow>
                            <IconButton aria-label="edit"
                                        onClick={() => { setShowEdit(true); setDevice(device) }}>
                                <EditIcon />
                            </IconButton>
                        </Tooltip>

                    </TableCell>
                    <TableCell classes={{root: classes.tableCellRoot}}
                               style={{verticalAlign: 'middle'}}>
                        <Tooltip title="Delete" arrow>
                            <IconButton aria-label="delete"
                                        onClick={ removeDevice(device.deviceId) }>
                                <DeleteIcon />
                            </IconButton>
                        </Tooltip>

                    </TableCell>
                    <TableCell classes={{root: classes.tableCellRoot}}
                               style={{verticalAlign: 'middle'}}>
                        <Tooltip title="Add Inspection" arrow>
                            <IconButton aria-label="inspection"
                                        onClick={ () => { setShowInspection(true); setDevice(device)} }>
                                <AssignmentTurnedInIcon />
                            </IconButton>
                        </Tooltip>

                    </TableCell>
                </TableRow>
            );
            setResponse(m);
        });

    },[value])


    return (
        <>
            <Container
                maxWidth={false}
                component={Box}
                classes={{ root: classes.containerRoot }}
            >
                <Modal title="Add Device" onClose={() => setShow(false)} show={show}>
                    <AddDeviceBody parksAndSites={parksAndSites} reloadDeviceTable={ reloadDeviceTable } closeModal={()=>{ setShow(false) }}/>
                </Modal>

                { device && <Modal title="Edit Device" onClose={() => setShowEdit(false)} show={ showEdit }>
                    <EditDeviceBody parksAndSites={parksAndSites} deviceDetails={ device } reloadDeviceTable={ reloadDeviceTable } closeModal={()=>{ setShowEdit(false) }}/>
                </Modal> }

                <Modal title="Add Inspection" onClose={() => setShowInspection(false)} show={ showInspection }>
                    <AddInspectionBody device_id={ device.deviceId } closeModal={ () => setShowInspection(false) }/>
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
                                    </Grid>
                                }
                                classes={{ root: classes.cardHeaderRoot }}
                            />
                            <TableContainer
                                style={{maxHeight:"300px",overflowY:"auto"}}>
                                <Box
                                    component={Table}
                                    alignItems="center"
                                    marginBottom="0!important"
                                >
                                    <TableHead>
                                        <TableRow style={{background: 'rgb(243 243 243)'}}>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >
                                                Name
                                            </TableCell>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >

                                            </TableCell>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >

                                            </TableCell>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >

                                            </TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>

                                        {response}

                                    </TableBody>
                                </Box>
                            </TableContainer>
                        </Card>

                    </Grid>
                    <Grid
                        item
                        xs={6}
                        xl={10}
                        component={Box}
                        marginBottom="3rem!important"
                        classes={{ root: classes.gridItemRoot }}
                        >
                    </Grid>
                    <Grid
                          item
                          justify="end"
                          xs={6}
                          xl={2}
                          component={Box}
                          marginBottom="3rem!important"
                          classes={{ root: classes.gridItemRoot }}
                          style={{ display: "flex", justifyContent: "flex-end", marginTop: "5px" }}>
                        {parksAndSites &&
                        <Button
                            variant="contained"
                            color="primary"
                            size="medium"
                            style={{width: '200px'}}
                            onClick={() => setShow(true)}
                        >
                            Add Device
                        </Button>
                        }
                        { !parksAndSites &&
                            <ScaleLoader size={10} height={15} color={"#5E72E4"} speedMultiplier={1.5} />
                        }
                    </Grid>
                </Grid>

            </Container>
        </>
    );
};

export default DeviceTable;
