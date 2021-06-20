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

import "../../index.css"

const useStyles = makeStyles(componentStyles);

function InspectionTable({ inspections }) {
  const classes = useStyles();

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
            ></CardHeader>

            <div className="table-container">
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
            </div>
        </Card>
    </>
  );
}

export default InspectionTable;
