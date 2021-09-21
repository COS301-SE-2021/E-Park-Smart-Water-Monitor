import React, {useEffect, useState} from "react";
import axios from "axios";
import Container from "@material-ui/core/Container";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import CardStats from "../../Dashboard/CardStats";
import InsertChartOutlined from "@material-ui/icons/InsertChartOutlined";
import ArrowUpward from "@material-ui/icons/ArrowUpward";
import PieChart from "@material-ui/icons/PieChart";
import ArrowDownward from "@material-ui/icons/ArrowDownward";
import GroupAdd from "@material-ui/icons/GroupAdd";
import EmojiEvents from "@material-ui/icons/EmojiEvents";


const AllUsers = () => {
    const [result, setResult] = useState(null)

    useEffect(() => {
        axios.post('/devices/getNumDevices', {
            parkID: "2ea5ba27-9d8e-41a4-9628-485f0ae2fb57"
        }).then((res)=>{
            setResult(res.data)
        });
    }, []) // second param [] is a list of dependency to watch and run useEffect


    return (
        <>
            <div c>
                <Container
                    maxWidth={false}
                    component={Box}
                >
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
                </Container>
            </div>
        </>
    );
};

export default AllUsers;