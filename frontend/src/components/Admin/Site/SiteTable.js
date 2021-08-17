import React, {useContext, useEffect, useState} from "react";

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
import AdminContext from "../AdminContext";
import Select from "react-select";
import LoadingContext from "../../../Context/LoadingContext";
import {UserContext} from "../../../Context/UserContext";
import {Replay} from "@material-ui/icons";



const useStyles = makeStyles(componentStyles);

const SiteTable = (props) => {
    const classes = useStyles();
    const [show, setShow] = useState(false);
    const [showEdit, setShowEdit] = useState(false);
    const [response, setResponse] = useState(false);
    const [site, setSite] = useState({});
    const [park, setPark] = useState({});
    const [value, setValue] = useState({});
    const [parkOptions, setParkOptions] = useState("")

    const context = useContext(AdminContext)
    const parksAndSites = context.parksAndSites
    const toggleLoading = useContext(LoadingContext).toggleLoading
    const user = useContext(UserContext)

    const reloadSiteTable = () => {
        setValue(value => value+1)
    }


    // on delete of a site
    const removeSite = (id) => {
        return ()=>{
            toggleLoading()
            axios.get('http://localhost:8080/api/sites/deleteWaterSite', {
                id: id
            }, {
                headers: {
                    'Authorization': "Bearer " + user.token
                }
            }).then((res)=> {
                toggleLoading()
                reloadSiteTable()
            })
        }
    }

    // get all the parks and sites on initial load
    useEffect(() => {

        // get the park and watersites and adjust when the value changes
        let options = parksAndSites.parks.map((p)=>{
            return {value: p.id, label: p.parkName}
        })

        setParkOptions(options)
        setPark(options[0])

        // get the sites dependent on what the user parkId is


    },[])

    // when updates or deletes are made to a watersite, get the watersites for the selected park again
    useEffect(() => {
        setTable()
    },[value])

    const setTable = () =>{
        axios.post('http://localhost:8080/api/park/getParkWaterSites', {
                parkId: user.parkID
            },
            {
                headers: {
                    'Authorization': "Bearer " + user.token
                }
            }
        ).then((res)=>{

            const m = res.data.site.map((site) =>
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
                        {site.waterSiteName}
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
                                        onClick={ removeSite(site.id) }
                            >
                                <DeleteIcon />
                            </IconButton>
                        </Tooltip>
                    </TableCell>
                </TableRow>
            )
            setResponse(m);
        }).catch((res)=>{
            console.log("error occurred getting watersites: "+JSON.stringify(res))
        });

    }


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
                                                Watersites for {user.parkName}

                                                {/*Reload Button*/}
                                                {/*<Box*/}
                                                {/*    component={Replay}*/}
                                                {/*    width="1rem!important"*/}
                                                {/*    height="1rem!important"*/}
                                                {/*    marginLeft="1.25rem"*/}
                                                {/*    onClick={()=>{return reloadSiteTable}}*/}
                                                {/*/>*/}
                                            </Box>

                                        </Grid>
                                        {/*Select Park Dropdown*/}
                                        {/*<Grid item xs="auto"  xs={"12"} md={"6"}>*/}
                                        {/*    <Box>*/}
                                        {/*        /!*dropdown*!/*/}
                                        {/*        <Select required={"required"} className="mb-3" name="park" options={ parkOptions } value={ park } onChange={e => setPark(e)}/>*/}
                                        {/*    </Box>*/}
                                        {/*</Grid>*/}
                                    </Grid>
                                }
                                classes={{ root: classes.cardHeaderRoot }}
                            ></CardHeader>
                            <TableContainer
                                style={{maxHeight:"300px",overflowY:"auto"}}>
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
                        xl={7}
                        component={Box}
                        marginBottom="3rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                    </Grid>
                    <Grid
                        item
                        justify="end"
                        xs={6}
                        xl={5}
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
                            Add Watersite to park
                        </Button>
                    </Grid>
                </Grid>

            </Container>
        </>
    );
};

export default SiteTable;
