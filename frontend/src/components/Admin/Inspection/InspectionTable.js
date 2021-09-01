import React, {useState, useEffect, useContext} from "react";

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
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Typography from "@material-ui/core/Typography";
import componentStyles from "assets/theme/views/admin/admin";
import Modal from "../../Modals/Modal";
import axios from "axios";
import {UserContext} from "../../../Context/UserContext";
import {Refresh} from "@material-ui/icons";
import {Tooltip} from "@material-ui/core";
import EditIcon from "@material-ui/icons/Edit";
import ForumIcon from '@material-ui/icons/Forum';
import EditInspection from "./EditInspection";
import Comments from "./Comments";
import {ScaleLoader} from "react-spinners";

const useStyles = makeStyles(componentStyles);

const InspectionTable = () => {
    const classes = useStyles();
    const [showEditInspection, setShowEditInspection] = useState(false)
    const [response, setResponse] = useState([])
    const [value, setValue] =useState(0)
    const [inspec, setInspec] =useState({})
    const [showComments, setShowComments] =useState(false)
    const [reload, setReload] =useState(false)

    const reloadInspectionTable = () => {
        setValue(value => value+1)
    }

    const toggleshowEditInspection = ()=>{
        setShowEditInspection(showEditInspection=>!showEditInspection)
    }

    const toggleshowComments = ()=>{
        setShowComments(showComments=>!showComments)
    }

    const user = useContext(UserContext)

    useEffect(() => {
        setReload(true)
        axios.get('/inspections/getAllInspections', {
            headers: {
                'Authorization': "Bearer " + user.token
            }
        }).then((res) => {
            setReload(false)
            if (res.data) {

                if(res.data && res.data.inspections)
                {
                    // get the inspections for the user logged in
                    let parkIndex = -1;
                    for(let i = 0; i < res.data.parkId.length; i++)
                    {
                        if(res.data.parkId[i] == user.parkID){
                            parkIndex = i;
                        }
                    }


                    let m = res.data.inspections[parkIndex].map((inspection) => (
                        <TableRow key={inspection.id}>
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
                                { inspection.dateDue?.split("T")[0] }
                            </TableCell>
                            <TableCell classes={{ root: classes.tableCellRoot }}>
                                { inspection.status }
                            </TableCell>
                            <TableCell className="table-sticky-column" classes={{ root: classes.tableCellRoot }}>
                                { inspection.description }
                            </TableCell>
                            <TableCell classes={{root: classes.tableCellRoot}}
                                       style={{verticalAlign: 'middle'}}>
                                <Tooltip title="Comments" arrow>
                                    <ForumIcon aria-label="forum"
                                              onClick={ () => {setShowComments(true); setInspec(inspection)} }>
                                    </ForumIcon>
                                </Tooltip>
                            </TableCell>
                            <TableCell classes={{root: classes.tableCellRoot}}
                                       style={{verticalAlign: 'middle'}}>
                                <Tooltip title="Edit" arrow>
                                    <EditIcon aria-label="edit"
                                              onClick={() => {
                                                  setShowEditInspection(true);
                                                  setInspec(inspection)
                                              }}>
                                    </EditIcon>
                                </Tooltip>
                            </TableCell>

                        </TableRow>
                    ))

                    setResponse(m)
                }
            }
        }).catch(()=>{
            setReload(false)
        })
      }, [value])

    return (
        <>
            <Container
                maxWidth={false}
                component={Box}
                marginTop="-3rem"
                classes={{ root: classes.containerRoot }}
            >
                <Modal title= "Edit Inspection" onClose={() => setShowEditInspection(false)} show={showEditInspection}>
                    <EditInspection reloadInspectionTable={ reloadInspectionTable } inspectionDetails={inspec} tog={() =>toggleshowEditInspection() }/>
                </Modal>
                <Modal title= "Inspection Comments" onClose={() => setShowComments(false)} show={showComments}>
                    <Comments reloadInspectionTable={ reloadInspectionTable } inspectionDetails={inspec} tog={() =>toggleshowComments() }/>
                </Modal>
                <Grid container component={Box} marginTop="3rem">
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
                                                variant="h3"
                                                marginBottom="0!important"
                                            >
                                                Inspections for {user.parkName}
                                            </Box>
                                        </Grid>
                                        <Grid item xs="auto">
                                            <Box
                                                justifyContent="flex-end"
                                                display="flex"
                                                flexWrap="wrap"
                                            >
                                                { !reload &&
                                                <Tooltip title="Refresh Inspections" arrow>
                                                    <Box
                                                        component={Refresh}
                                                        width="1.25rem!important"
                                                        height="1.25rem!important"
                                                        className={classes["text"]}
                                                        onClick={()=>{ reloadInspectionTable() }}
                                                    />
                                                </Tooltip>
                                                }
                                                { reload &&
                                                <ScaleLoader size={10} height={15} color={"#5E72E4"} speedMultiplier={1.5} />
                                                }
                                            </Box>
                                        </Grid>
                                    </Grid>
                                }
                                classes={{ root: classes.cardHeaderRoot }}

                            >
                            </CardHeader>
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
                                                Due Date
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
                                                Description
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
                                        { response }
                                    </TableBody>
                                </Box>
                            </TableContainer>
                        </Card>
                    </Grid>
                    <Grid
                        item
                        xs={4}
                        xl={10}
                        component={Box}
                        marginBottom="3rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                    </Grid>
                    <Grid
                        item
                        justify="end"
                        xs={8}
                        xl={2}
                        component={Box}
                        marginBottom="3rem!important"
                        classes={{ root: classes.gridItemRoot }}
                        style={{ display: "flex", justifyContent: "flex-end", marginTop: "5px"}}>
                        {/*<Button*/}
                        {/*    variant="contained"*/}
                        {/*    color="primary"*/}
                        {/*    size="medium"*/}
                        {/*    style={{width:'200px'}}*/}
                        {/*    onClick={() => setShow(!show)}*/}
                        {/*>*/}
                        {/*    Refresh Table*/}
                        {/*</Button>*/}
                    </Grid>
                </Grid>

            </Container>
        </>
    );
};

export default InspectionTable;
