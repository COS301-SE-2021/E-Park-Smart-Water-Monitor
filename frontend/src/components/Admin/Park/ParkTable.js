import React, {useEffect, useState, useContext} from "react";

import { makeStyles } from "@material-ui/core/styles";
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
import AddParkBody from "./AddParkBody";
import axios from "axios";
import EditIcon from "@material-ui/icons/Edit";
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from "@material-ui/icons/Delete";
import EditParkBody from "./EditParkBody";
import {Tooltip} from "@material-ui/core";
import AdminContext from '../AdminContext'


const useStyles = makeStyles(componentStyles);

const ParkTable = (props) => {
    const classes = useStyles();
    const [show, setShow] = useState(false);
    const [showEdit, setShowEdit] = useState(false);
    const [response, setResponse] = useState(false);
    const [park, setPark] = useState({});
    const [hover, setHover] = useState(true)
    const [value, setValue] = useState(0);

    // get the parks context with the sites from the Admin parent component
    // if you make this a state then the parent will rerender
    const parksAndSites = (useContext( AdminContext ));

    useEffect( () => {

        let hoverStyle;
        if (hover) {
            hoverStyle = {cursor: 'pointer'}
        } else {
            hoverStyle = {cursor: 'default'}
        }


        let obj = null;

        if (parksAndSites && parksAndSites.parks) {
            obj = parksAndSites.parks.map((park) =>
                <TableRow key={park.id}
                          onClick={handleParkSelection(park)} // send through the whole park object
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
                        scope="row"
                        style={{verticalAlign: 'middle', width: '80%'}}
                    >
                        {park.parkName}
                    </TableCell>
                    <TableCell classes={{root: classes.tableCellRoot}}
                               style={{verticalAlign: 'middle'}}>
                        <Tooltip title="Edit" arrow>
                            <IconButton aria-label="edit"
                                        onClick={() => {
                                            setShowEdit(true);
                                            setPark(park)
                                        }}
                            >
                                <EditIcon/>
                            </IconButton>
                        </Tooltip>
                    </TableCell>
                    <TableCell classes={{root: classes.tableCellRoot}}
                               style={{verticalAlign: 'middle'}}>
                        <Tooltip title="Delete" arrow>
                            <IconButton aria-label="delete"
                                        onClick={removePark(park.id)}
                            >
                                <DeleteIcon/>
                            </IconButton>
                        </Tooltip>
                    </TableCell>
                </TableRow>
            )

            setResponse(obj)
        }else{
            alert("error parksAndSites not loaded, it contains: "+ JSON.stringify(parksAndSites))
        }


    }, [])



    const handleParkSelection = (details) => {
        return function () {
            props.select(details);
        }
    }

    // on delete of a park
    const removePark = (id) => {
        return ()=>{
            alert("removing "+id)
            axios.delete('http://localhost:8080/api/park/deletePark', {
                data: {
                    parkId: id
                }
            }).then((res)=> {
                // window.location.reload()
                alert("forcing update")
                setValue(value => value+1 ) // returns an updated value
            }).catch((res)=>{
                alert("didn't work")
                console.log(JSON.stringify(res))
            })
        }
    }

    const toggleHover = ()=>{
        setHover(!hover)
    }


    return (
        <>
            <Container
                maxWidth={false}
                component={Box}
                classes={{ root: classes.containerRoot }}
            >
                <Modal title="Add Park" onClose={() => setShow(false)} show={show}>
                    <AddParkBody/>
                </Modal>

                { park && <Modal title="Edit Park" onClose={() => setShowEdit(false)} show={ showEdit }>
                    <EditParkBody parkDetails={ park } closeModal={()=>{ setShowEdit(false) }}/>
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
                                                Parks
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

                                        {/*{ renderParks(parksAndSites) }*/}
                                        { response }

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
                            Add Park
                        </Button>
                    </Grid>
                </Grid>

            </Container>
        </>
    );
};

export default ParkTable;
