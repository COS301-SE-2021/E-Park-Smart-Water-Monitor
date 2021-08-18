import React, {useContext, useEffect} from "react";
// import PropTypes from "prop-types";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import { Line } from "react-chartjs-2";



// core components
import CardHeader from "@material-ui/core/CardHeader";

import {
    chartExample1,
    chartOptions,
    parseOptions,
} from "variables/charts.js";

import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import axios from "axios";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";
import Chart from "chart.js";
import Button from "@material-ui/core/Button";


import componentStyles from "assets/theme/views/dashboard/dashboard.js";
import {ScaleLoader} from "react-spinners";
import {Add, ArrowDropDown, ArrowDropUp, Remove} from "@material-ui/icons";
const useStyles = makeStyles(componentStyles);

function LineChart(props) {
  const classes = useStyles();
  const theme = useTheme();
  const [activeNav, setActiveNav] = React.useState(1);
  const [readingType, setReadingType] = React.useState("waterlevel");
  const [chartExample1Data, setChartExample1Data] = React.useState("data1");
  const [projectionsData, setProjectionsData] = React.useState(""); // this will be a function like "data1" passed
  const [numPredictions, setNumPredictions] = React.useState(0); // this will be a function like "data1" passed
    // in the chartOptions1 in the chart.js file

    if (window.Chart) {
        parseOptions(Chart, chartOptions());
    }

    // move between prediction types
      const toggleNavs = (index) => {
        setActiveNav(index);
        setChartExample1Data("data" + index);
      };


    const user = useContext(UserContext)
    const toggleLoading = useContext(LoadingContext).toggleLoading

    // GET THE PROJECTION DATA
    useEffect(()=>{
        setProjectionsData("")
        setNumPredictions(5)
        let obj = {
            id: props.device.deviceId,
            type: readingType,
            length: numPredictions
        }
        console.log(JSON.stringify(obj))
        axios.post('http://localhost:8080/api/analytics/deviceProjection', obj, {
                headers: {
                    'Authorization': "Bearer " + user.token
                }
            }
        ).then((res)=>{
            console.log(JSON.stringify(res.data))
            let data = res.data.optimisticProjections // just one of the lines
            let labels = res.data.labelDates

            let x = res.data

            let indexToSplit = x.labelDates.length;
            let first = x.optimisticProjections.slice(0, indexToSplit);
            let second = x.optimisticProjections.slice(indexToSplit);
            console.log({first, second});

            let predictions = []
            for(let i =0; i<numPredictions;i++){
                labels.push("Prediction "+(i+1))
            }

            setProjectionsData( () => {
                return {
                    labels: labels,
                    datasets: [
                        {
                            label: "Optimistic",
                            data: x.optimisticProjections,
                            // borderDash: [11],
                            fill: false,

                        },
                        {
                            label: "Realistic",
                            data: x.realisticProjections,
                            backgroundColor: "orange",
                            borderColor: "orange",
                            borderDash: [12],
                            fill: false,
                        },
                        {
                            label: "Conservative",
                            data: x.concervativeProjections,
                            backgroundColor: "red",
                            borderColor: "red",
                            borderDash: [12],
                            fill: false,
                        },
                    ],
                };
            })

            console.log(projectionsData)

        }).catch((res)=>{
            console.log("response projections:"+JSON.stringify(res))
        });
    },[props.device])

    // let x_axis = measurements.innerResponses.map((item)=>{
    //     return item.measurements[0].value
    // })
    // let y_axis = measurements.innerResponses.map((item)=>{
    //     let date = item.measurements[0].dateTime
    //     return `${date.substring(0,10)} ${date.substring(14,19)}`
    // })
    // const newChartData = function chartData() {
    //     return {
    //         labels: y_axis,
    //         datasets: [{
    //             type: 'line',
    //             label: 'Level',
    //             data: x_axis,
    //             borderColor: "#11CDEF",
    //             backgroundColor: "#11CDEF",
    //             fill:false
    //         // }, {
    //         //     type: 'line',
    //         //     label: 'Temperature',
    //         //     data: [50, 40, 45, 50, 50, 50, 45, 90],
    //         //     borderColor: "orange",
    //         //     backgroundColor: "orange",
    //         //     fill: false
    //          }
    //         ],
    //         options: {
    //             scales: {
    //                 yAxes: [{
    //                     ticks: {
    //                         stepSize: 1
    //                     }
    //                 }]
    //             }
    //         }
    //         // labels: ['January', 'February', 'March', 'April']
    //     }
    // }

  return (
    <>
    {/*    <Card*/}
    {/*        classes={{*/}
    {/*            root: classes.cardRoot + " " + classes.cardRootBgGradient,*/}
    {/*        }}*/}
    {/*    >*/}
    {/*        <CardHeader*/}
    {/*            subheader={*/}
    {/*                <Grid*/}
    {/*                    container*/}
    {/*                    component={Box}*/}
    {/*                    alignItems="center"*/}
    {/*                    justifyContent="space-between"*/}
    {/*                >*/}
    {/*                    <Grid item xs="auto">*/}
    {/*                        <Box*/}
    {/*                            component={Typography}*/}
    {/*                            variant="h6"*/}
    {/*                            letterSpacing=".0625rem"*/}
    {/*                            marginBottom=".25rem!important"*/}
    {/*                            className={classes.textUppercase}*/}
    {/*                        >*/}
    {/*                            <Box component="span" color={theme.palette.gray[400]}>*/}
    {/*                                Overview*/}
    {/*                            </Box>*/}
    {/*                        </Box>*/}
    {/*                        <Box*/}
    {/*                            component={Typography}*/}
    {/*                            variant="h2"*/}
    {/*                            marginBottom="0!important"*/}
    {/*                        >*/}
    {/*                            /!*<Box component="span" color={theme.palette.white.main}>*!/*/}
    {/*                            <Box component="span">*/}
    {/*                                Device Readings*/}
    {/*                            </Box>*/}
    {/*                        </Box>*/}
    {/*                    </Grid>*/}
    {/*                    <Grid item xs="auto">*/}
    {/*                        <Box*/}
    {/*                            justifyContent="flex-end"*/}
    {/*                            display="flex"*/}
    {/*                            flexWrap="wrap"*/}
    {/*                        >*/}
    {/*                        </Box>*/}
    {/*                    </Grid>*/}
    {/*                </Grid>*/}
    {/*            }*/}
    {/*            classes={{ root: classes.cardHeaderRoot }}*/}
    {/*        ></CardHeader>*/}
    {/*        <CardContent>*/}
    {/*            <Box position="relative" height="350px">*/}
    {/*                <Line*/}
    {/*                    data={chartExample1[chartExample1Data]}*/}
    {/*                    // data={newChartData}*/}
    {/*                    options={chartExample1.options}*/}
    {/*                    options={chartOptions}*/}
    {/*                    // getDatasetAtEvent={(e) => console.log(e)}*/}
    {/*                />*/}
    {/*            </Box>*/}
    {/*        </CardContent>*/}
    {/*    </Card>*/}
        <Card
            classes={{
                root: classes.cardRoot + " " + classes.cardRootBgGradient,
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
                                variant="h6"
                                letterSpacing=".0625rem"
                                marginBottom=".25rem!important"
                                className={classes.textUppercase}
                            >
                                <Box component="span" color={theme.palette.gray[400]}>
                                    Overview
                                </Box>
                            </Box>
                            <Box
                                component={Typography}
                                variant="h2"
                                marginBottom="0!important"
                            >
                                <Box component="span" color={theme.palette.white.main}>
                                    {props.device.deviceName} Waterlevel Readings
                                </Box>
                            </Box>
                        </Grid>
                        <Grid item xs="auto">
                            <Box
                                component={Typography}
                                variant="h6"
                                letterSpacing=".0625rem"
                                marginBottom=".25rem!important"
                                className={classes.textUppercase}
                            >
                                <Box component="span" color={theme.palette.gray[400]}>
                                    Number of Predictions
                                </Box>
                            </Box>

                            <Box
                                justifyContent="flex-end"
                                display="flex"
                                flexWrap="wrap"
                            >


                                <Button
                                    variant="contained"
                                    color="primary"
                                    size="small"
                                    component={Box}
                                    marginRight="1rem!important"
                                    onClick={() => toggleNavs(1)}
                                    classes={{
                                        root:
                                            activeNav === 1
                                                ? ""
                                                : classes.buttonRootUnselected,
                                    }}
                                >
                                    <Box
                                        component={Remove}
                                        width="1.25rem!important"
                                        height="1.25rem!important"
                                        className={classes["text"]}
                                    />
                                </Button>

                                <Button
                                    variant="contained"
                                    color="primary"
                                    size="small"
                                    onClick={() => toggleNavs(2)}
                                    classes={{
                                        root:
                                            activeNav === 2
                                                ? ""
                                                : classes.buttonRootUnselected,
                                    }}
                                >
                                    <Box
                                        component={Add}
                                        width="1.25rem!important"
                                        height="1.25rem!important"
                                        className={classes["text"]}
                                    />
                                </Button>
                            </Box>
                        </Grid>
                    </Grid>
                }
                classes={{ root: classes.cardHeaderRoot }}
            ></CardHeader>
            <CardContent>
            { projectionsData &&
                <Box position="relative" height="350px">
                    <Line
                        data={projectionsData} // your own chart info obtained from request
                        options={chartExample1.options}
                        getDatasetAtEvent={(e) => console.log(e)}
                    />
                </Box>
            }
            { !projectionsData &&
                <ScaleLoader size={50} color={"#5E72E4"} speedMultiplier={1.5} />
            }
            </CardContent>

        </Card>
    </>
  );
}

export default LineChart;
