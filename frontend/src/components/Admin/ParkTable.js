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
import Modal from "../Modals/Modal";
import disableScroll from "disable-scroll";
import AddParkBody from "./AddParkBody";
import axios from "axios";



const useStyles = makeStyles(componentStyles);

const ParkTable = () => {
    const classes = useStyles();
    const theme = useTheme();
    const [show, setShow] = useState(false);
    const [response, setResponse] = useState(false);

    useEffect(() => {
        if (show == true) disableScroll.on()
        if (show == false) disableScroll.off()
    }, [show])

    useEffect(() => {
        axios.get('http://localhost:8080/api/park/getAllParks').then((res)=>{
            const m = res.data.allParks.map((park) =>
                <TableRow key={ park.id } >
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
                <Modal title="Add Park" onClose={() => setShow(false)} show={show}>
                    <AddParkBody/>
                </Modal>
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
                            <TableContainer>
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

export default ParkTable;
