import React, {useEffect, useState} from "react";
import Grid from "@material-ui/core/Grid";
import InsertChartOutlined from "@material-ui/icons/InsertChartOutlined";
import Box from "@material-ui/core/Box";
import ArrowUpward from "@material-ui/icons/ArrowUpward";
import PieChart from "@material-ui/icons/PieChart";
import ArrowDownward from "@material-ui/icons/ArrowDownward";
import GroupAdd from "@material-ui/icons/GroupAdd";
import EmojiEvents from "@material-ui/icons/EmojiEvents";
import {useTheme} from "@material-ui/core/styles";
import CardStats from "./CardStats";


const Stats = () => {
    const theme = useTheme();

    const result = {
        numDevices: 2
    };

    return (
        <>
            <div>
            <Grid container>
                <Grid item xl={3} lg={6} xs={12}>
                    <CardStats
                        subtitle="Devices"
                        title={result === null ? 'loading' : result.numDevices}
                        icon={InsertChartOutlined}
                        color="bgError"
                        footer={
                            <>
                                <Box
                                    component="span"
                                    fontSize=".875rem"
                                    color={theme.palette.success.main}
                                    marginRight=".5rem"
                                    display="flex"
                                    alignItems="center"
                                >
                                    <Box
                                        component={ArrowUpward}
                                        width="1.5rem!important"
                                        height="1.5rem!important"
                                    />{" "}
                                    3.48%
                                </Box>
                                <Box component="span" whiteSpace="nowrap">
                                    Since last month
                                </Box>
                            </>
                        }
                    />
                </Grid>
                <Grid item xl={3} lg={6} xs={12}>
                    <CardStats
                        subtitle="Inspections"
                        title="2,356"
                        icon={PieChart}
                        color="bgWarning"
                        footer={
                            <>
                                <Box
                                    component="span"
                                    fontSize=".875rem"
                                    color={theme.palette.error.main}
                                    marginRight=".5rem"
                                    display="flex"
                                    alignItems="center"
                                >
                                    <Box
                                        component={ArrowDownward}
                                        width="1.5rem!important"
                                        height="1.5rem!important"
                                    />{" "}
                                    3.48%
                                </Box>
                                <Box component="span" whiteSpace="nowrap">
                                    Since last week
                                </Box>
                            </>
                        }
                    />
                </Grid>
                <Grid item xl={3} lg={6} xs={12}>
                    <CardStats
                        subtitle="Users"
                        title="924"
                        icon={GroupAdd}
                        color="bgWarningLight"
                        footer={
                            <>
                                <Box
                                    component="span"
                                    fontSize=".875rem"
                                    color={theme.palette.warning.main}
                                    marginRight=".5rem"
                                    display="flex"
                                    alignItems="center"
                                >
                                    <Box
                                        component={ArrowDownward}
                                        width="1.5rem!important"
                                        height="1.5rem!important"
                                    />{" "}
                                    1.10%
                                </Box>
                                <Box component="span" whiteSpace="nowrap">
                                    Since yesterday
                                </Box>
                            </>
                        }
                    />
                </Grid>
                <Grid item xl={3} lg={6} xs={12}>
                    <CardStats
                        subtitle="Predictions"
                        title="49,65%"
                        icon={EmojiEvents}
                        color="bgInfo"
                        footer={
                            <>
                                <Box
                                    component="span"
                                    fontSize=".875rem"
                                    color={theme.palette.success.main}
                                    marginRight=".5rem"
                                    display="flex"
                                    alignItems="center"
                                >
                                    <Box
                                        component={ArrowUpward}
                                        width="1.5rem!important"
                                        height="1.5rem!important"
                                    />{" "}
                                    10%
                                </Box>
                                <Box component="span" whiteSpace="nowrap">
                                    Since last month
                                </Box>
                            </>
                        }
                    />
                </Grid>
            </Grid>
            </div>
        </>
    );
};

export default Stats;
