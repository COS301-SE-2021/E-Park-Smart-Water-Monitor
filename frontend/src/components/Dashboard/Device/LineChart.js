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
    chartExample2,
    chartOptions,
    parseOptions,
} from "variables/charts.js";

import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import axios from "axios";
import {UserContext} from "../../../Context/UserContext";
import Chart from "chart.js";
import Button from "@material-ui/core/Button";


import componentStyles from "assets/theme/views/dashboard/dashboard.js";
import {ScaleLoader} from "react-spinners";
import {Add, Remove} from "@material-ui/icons";
const useStyles = makeStyles(componentStyles);

function LineChart(props) {
  const classes = useStyles();
  const theme = useTheme();
  const [activeNav, setActiveNav] = React.useState(1);
  const [readingType, setReadingType] = React.useState("waterlevel");
  const [projectionsData, setProjectionsData] = React.useState(""); // this will be a function like "data1" passed
  const [numPredictions, setNumPredictions] = React.useState(5); // this will be a function like "data1" passed
  const [reset, setReset] = React.useState(false); // this will be a function like "data1" passed
    // in the chartOptions1 in the chart.js file

    if (window.Chart) {
        parseOptions(Chart, chartOptions());
    }

    // move between prediction types
      const toggleNavs = (index) => {
        setActiveNav(index);
        //setChartExample1Data("data" + index);
          // 1 is waterlevel
          // 2 is PH
            if(index === 1)
            {
                setReadingType("waterlevel")
            }
            if(index === 2)
            {
              setReadingType("ph")
            }
      };

    const increaseNumProjections = () =>{
        if(numPredictions < 10){
            setNumPredictions(numPredictions=>numPredictions+1)
        }
    }
    const decreaseNumProjections = () =>{
        if(numPredictions > 0){
            setNumPredictions(numPredictions=>numPredictions-1)
        }
    }


    const user = useContext(UserContext)

    // GET THE PROJECTION DATA
    useEffect(()=>{
        console.log(props.device)

        setProjectionsData(() => {
            return {
                labels: [],
                datasets: [
                    {
                        label: "Optimistic",
                        data: [],
                        fill: false,

                    },
                    {
                        label: "Realistic",
                        data: [],
                        backgroundColor: "orange",
                        borderColor: "orange",
                        borderDash: [12],
                        fill: false,
                    },
                    {
                        label: "Conservative",
                        data: [],
                        backgroundColor: "red",
                        borderColor: "red",
                        borderDash: [12],
                        fill: false,
                    },
                ],
            };
        })
        setReset(false)
        let obj = {
            id: props.device.deviceId,
            type: readingType,
            length: numPredictions
        }

        axios.post('http://localhost:8080/api/analytics/deviceProjection', obj, {
                headers: {
                    'Authorization': "Bearer " + user.token
                }
            }
        ).then((res)=>{
            setReset(true)
            let labels = res.data.labelDates

            let x = res.data

            let roundingValue = 3

            let realisticProjections
            // only for waterlevel
            let optimisticProjections
            let concervativeProjections

            let displayObj = {}
            displayObj.labels = labels
            displayObj.datasets =[]

            if(x.realisticProjections)
            {
                realisticProjections = x.realisticProjections.map(function(each_element){
                    return Number(each_element.toFixed(roundingValue));
                });
                displayObj.datasets.push({
                    label: "Realistic",
                    data: realisticProjections,
                    fill: false,
                    backgroundColor: "#5E72E4",
                    borderColor: "#5E72E4",
                    borderDash: [0],

                })
            }

            if(x.optimisticProjections)
            {
                optimisticProjections = x.optimisticProjections.map(function(each_element){
                    return Number(each_element.toFixed(roundingValue));
                });
                displayObj.datasets.push({
                    label: "Optimistic",
                    data: optimisticProjections,
                    backgroundColor: "orange",
                    borderColor: "orange",
                    borderDash: [12],
                })
            }


            if(x.concervativeProjections)
            {
                concervativeProjections = x.concervativeProjections.map(function (each_element) {
                    return Number(each_element.toFixed(roundingValue));
                });
                displayObj.datasets.push({
                    label: "Conservative",
                    data: concervativeProjections,
                    backgroundColor: "red",
                    borderColor: "red",
                    borderDash: [12],
                    fill: false,
                })
            }


            setProjectionsData( () => {
                return displayObj
            })


        }).catch(()=>{
            console.log("Projections failed.")
        });
    },[props.device, numPredictions, readingType])



  return (
    <>
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
                                    {props.device.deviceName} Readings
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
                                    Prediction Type
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
                                    onClick={() => {toggleNavs(1); }  }
                                    classes={{
                                        root:
                                            activeNav === 1
                                                ? ""
                                                : classes.buttonRootUnselected,
                                    }}
                                >
                                   Waterlevel
                                </Button>

                                <Button
                                    variant="contained"
                                    color="primary"
                                    size="small"
                                    onClick={() => {toggleNavs(2);} }
                                    classes={{
                                        root:
                                            activeNav === 2
                                                ? ""
                                                : classes.buttonRootUnselected,
                                    }}
                                >
                                    PH
                                </Button>
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
                                    onClick={() => { decreaseNumProjections()}  }
                                    classes={{
                                        root: classes.buttonRootUnselected,
                                    }}
                                >
                                    <Box
                                        component={Remove}
                                        width="1.25rem!important"
                                        height="1.25rem!important"
                                        className={classes["text"]}
                                    />
                                </Button>
                                <Box
                                    component={Typography}
                                    variant="h2"
                                    marginBottom="0!important"
                                >
                                    <Box component="span" marginRight="1rem" color={theme.palette.white.main}>
                                        { numPredictions }
                                    </Box>
                                </Box>
                                <Button
                                    variant="contained"
                                    color="primary"
                                    size="small"
                                    onClick={() => { increaseNumProjections()} }
                                    classes={{
                                        root: classes.buttonRootUnselected,
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
            />
            <CardContent>
            { reset &&
                <Box position="relative" height="350px">
                    { readingType === "waterlevel" &&
                        <Line
                            data={projectionsData} // your own chart info obtained from request
                            options={ chartExample1.options }
                            getDatasetAtEvent={(e) => console.log(e)}
                        />
                    }
                    { readingType === "ph" &&
                        <Line
                            data={projectionsData} // your own chart info obtained from request
                            options={ chartExample2.options }
                            getDatasetAtEvent={(e) => console.log(e)}
                        />
                    }

                </Box>
            }
            { !reset &&
                <ScaleLoader size={50} color={"#5E72E4"} speedMultiplier={1.5} />
            }
            </CardContent>

        </Card>
    </>
  );
}

export default LineChart;
