import React,{useState, useEffect} from "react";

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
import Button from "@material-ui/core/Button";
import Modal from "../../Modals/Modal";
import axios from "axios";
import AddInspectionBody from "./AddInspectionBody";



const useStyles = makeStyles(componentStyles);

const InspectionTable = () => {
    const classes = useStyles();
    const [show, setShow] = useState(false);

    const [inspections, setInspections] = useState([])

    useEffect(() => {
        axios.post('http://localhost:8080/api/inspections/getSiteInspections', {
            siteId: "10ad3cf6-59c3-4469-b1b0-17a75e93cf7f"
        }).then((res) => {
            if (res.data) {
            setInspections(res.data.inspectionList)
            }
        })
      }, [])

    return (
        <>
            <Container
                maxWidth={false}
                component={Box}
                marginTop="-3rem"
                classes={{ root: classes.containerRoot }}
            >
                <Modal title="Add Inspection" onClose={() => setShow(false)} show={show}>
                    <AddInspectionBody/>
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
                                                variant="h2"
                                                marginBottom="0!important"
                                            >
                                                Inspections
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
                                        {inspections.map((inspection) => (
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
                                            </TableRow>
                                        ))}
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
                        {/*    onClick={() => setShow(true)}*/}
                        {/*>*/}
                        {/*    Add Inspection*/}
                        {/*</Button>*/}
                    </Grid>
                </Grid>

            </Container>
        </>
    );
};

export default InspectionTable;
