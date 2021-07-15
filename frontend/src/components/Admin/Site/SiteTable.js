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
import Modal from "../../Modals/Modal";
import disableScroll from "disable-scroll";
import AddSiteBody from "./AddSiteBody";
import axios from "axios";
import EditIcon from "@material-ui/icons/Edit";
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from "@material-ui/icons/Delete";
import EditDeviceBody from "../Device/EditDeviceBody";
import EditSiteBody from "./EditSiteBody";
import {Tooltip} from "@material-ui/core";



const useStyles = makeStyles(componentStyles);

const SiteTable = (props) => {
    const classes = useStyles();
    const [show, setShow] = useState(false);
    const [showEdit, setShowEdit] = useState(false);
    const [response, setResponse] = useState(false);
    const [site, setSite] = useState({});

    // on delete of a site
    const removeSite = (id) => {
        return ()=>{
            axios.get('http://localhost:8080/api/device/deleteSite', {
                id: id
            }).then((res)=> {
                window.location.reload()
            })
        }
    }

    useEffect(() => {

        let parkID = {
            parkID: "2ea5ba27-9d8e-41a4-9628-485f0ae2fb57" // use props park_id instead
        }

        axios.post('http://localhost:8080/api/park/getParkWaterSites', parkID).then((res)=>{
            const m = res.data.allParks.map((site) =>
                <TableRow key={ site.id } >
                    <TableCell
                        classes={{
                            root:
                                classes.tableCellRoot +
                                " " +
                                classes.tableCellRootBodyHead,
                        }}
                        scope="row"
                        style={{verticalAlign:'middle', width:'80%'}}
                    >
                        {park.parkName}
                    </TableCell>
                    <TableCell classes={{ root: classes.tableCellRoot }}
                               style={{verticalAlign:'middle'}}>
                        <Tooltip title="Edit" arrow>
                            <IconButton aria-label="edit"
                                        onClick={() => { setShowEdit(true); setSite(site)}}
                            >
                                <EditIcon />
                            </IconButton>
                        </Tooltip>
                    </TableCell>
                    <TableCell classes={{ root: classes.tableCellRoot }}
                               style={{verticalAlign:'middle'}}>
                        <Tooltip title="Delete" arrow>
                            <IconButton aria-label="delete"
                                        onClick={ removeSite(park.id) }
                            >
                                <DeleteIcon />
                            </IconButton>
                        </Tooltip>
                    </TableCell>
                </TableRow>
            );
            setResponse(m);
        });

    },[])

    return (
        <>
            <Container
                maxWidth={false}
                component={Box}
                classes={{ root: classes.containerRoot }}
            >
                <Modal title="Add Site" onClose={() => setShow(false)} show={show}>
                    <AddSiteBody/>
                </Modal>

                { site && <Modal title="Edit Site" onClose={() => setShowEdit(false)} show={ showEdit }>
                    <EditSiteBody siteDetails={ site } closeModal={()=>{ setShowEdit(false) }}/>
                </Modal> }


                <Grid container component={Box} >
                    <Grid
                        item
                        xs={12}
                        xl={12}
                        component={Box}
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
                                                Watersites for - Park
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
                            <TableContainer
                                style={{maxHeight:"300px",overflowY:"scroll"}}>
                                <Box
                                    component={Table}
                                    alignItems="center"
                                    marginBottom="0!important"
                                >
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
                        <Button
                            variant="contained"
                            color="primary"
                            size="medium"
                            style={{width:'200px'}}
                            onClick={() => setShow(true)}
                        >
                            Add Park
                        </Button>
                    </Grid>
                </Grid>

            </Container>
        </>
    );
};

export default SiteTable;
