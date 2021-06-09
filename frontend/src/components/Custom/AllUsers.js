import React from "react";

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
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Typography from "@material-ui/core/Typography";
import componentStyles from "assets/theme/views/admin/admin";
import Button from "@material-ui/core/Button";



const useStyles = makeStyles(componentStyles);

const UserTable = () => {
    const classes = useStyles();
    const theme = useTheme();


    return (
        <>
            <Container
                maxWidth={false}
                component={Box}
                marginTop="-6rem"
                classes={{ root: classes.containerRoot }}
            >
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
                                                Users
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
                                                User Name
                                            </TableCell>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >
                                                Role
                                            </TableCell>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >
                                                Park
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


                                        {/*default values:*/}
                                        <TableRow>
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
                                                Jane Doe
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}>
                                                Ranger
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}>
                                                Riet Vlei
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}>
                                                <Button
                                                    size="small"
                                                >
                                                    Edit
                                                </Button>
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}>
                                                <Button
                                                    size="small"
                                                >
                                                    Remove
                                                </Button>
                                            </TableCell>
                                        </TableRow>
                                        <TableRow>
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
                                                John Doe
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}>
                                                Field Engineer
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}>
                                                Riet Vlei
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}>
                                                <Button
                                                    size="small"
                                                >
                                                    Edit
                                                </Button>
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}>
                                                <Button
                                                    size="small"
                                                >
                                                    Remove
                                                </Button>
                                            </TableCell>
                                        </TableRow>
                                        <TableRow>
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
                                                Matthew Evans
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}>
                                                Admin
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}>
                                                Riet Vlei
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}>
                                                <Button
                                                    size="small"
                                                >
                                                    Edit
                                                </Button>
                                            </TableCell>
                                            <TableCell classes={{ root: classes.tableCellRoot }}>
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
                        classes={{ root: classes.gridItemRoot }}>
                        <Button
                            variant="contained"
                            color="primary"
                            size="medium"
                        >
                            Add User
                        </Button>
                    </Grid>
                </Grid>

            </Container>
        </>
    );
};

export default UserTable;
