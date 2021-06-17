import React from "react";
// import PropTypes from "prop-types";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import { Line } from "react-chartjs-2";



// core components
import componentStyles from "assets/theme/components/card-stats.js";
import CardHeader from "@material-ui/core/CardHeader";

import {
    // chartExample1,
    // chartOptions,
    // parseOptions,
    // chartExample2,
} from "variables/charts.js";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";


const useStyles = makeStyles(componentStyles);

function LineChart() {
  const classes = useStyles();
  const theme = useTheme();
  // const [activeNav, setActiveNav] = React.useState(1);
  // const [chartExample1Data, setChartExample1Data] = React.useState("data1");

  // const toggleNavs = (index) => {
  //   setActiveNav(index);
  //   setChartExample1Data("data" + index);
  // };

    // console.log("chartExample1Data: "+ chartExample1[chartExample1Data])
    // chart data structure
    /*
      chartExample1Data: function data1() {
      return {
        labels: ["May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        datasets: [{
          label: "Performance",
          data: [0, 20, 10, 30, 15, 40, 20, 60, 60]
        }]
      };
    }
    */

    // Example
    /*

    data: {
          datasets: [{
              type: 'bar',
              label: 'Bar Dataset',
              data: [10, 20, 30, 40]
          }, {
              type: 'line',
              label: 'Line Dataset',
              data: [50, 50, 50, 50],
          }],
          labels: ['January', 'February', 'March', 'April']
      },

     */

    let mock_device_measurements = {
        status: "Successfully retrieved data for device: Water12",
        success: true,
        deviceName: "Water12",
        innerResponses: [{
            status: "Success",
            success: true,
            deviceName: "Water12",
            measurements: [{
                type: "WATER_LEVEL",
                value: 8.8,
                unitOfMeasurement: "METER",
                deviceDateTime: "newDateTime",
                dataId: "fd9a0d47-b47e-4dda-b45d-f79d17b1d7e7",
                dateTime: "2021-06-16T09:35:22.580+00:00",
                id: "fd9a0d47-b47e-4dda-b45d-f79d17b1d7e7"
            }, {
                type: "WATER_LEVEL",
                value: 7.1,
                unitOfMeasurement: "METER",
                deviceDateTime: "newDateTime",
                dataId: "d29a24dd-2532-4934-a40e-b1c63d28197f",
                dateTime: "2021-06-16T09:35:22.580+00:00",
                id: "d29a24dd-2532-4934-a40e-b1c63d28197f"
            }, {
                type: "WATER_LEVEL",
                value: 6.6,
                unitOfMeasurement: "METER",
                deviceDateTime: "newDateTime",
                dataId: "8eccafa7-0462-4c29-a9ed-4a5c997b5fa1",
                dateTime: "2021-06-16T09:35:22.580+00:00",
                id: "8eccafa7-0462-4c29-a9ed-4a5c997b5fa1"
            }]
        }, {
            status: "Success",
            success: true,
            deviceName: "Water12",
            measurements: [{
                type: "WATER_LEVEL",
                value: 6.13,
                unitOfMeasurement: "METER",
                deviceDateTime: "newDateTime",
                dataId: "3f0263cf-5e2c-4f5a-8679-3c632493762a",
                dateTime: "2021-06-16T09:35:22.582+00:00",
                id: "3f0263cf-5e2c-4f5a-8679-3c632493762a"
            }, {
                type: "WATER_LEVEL",
                value: 7.18,
                unitOfMeasurement: "METER",
                deviceDateTime: "newDateTime",
                dataId: "dae3adc5-6f7a-4f4d-980c-582f5e6391a7",
                dateTime: "2021-06-16T09:35:22.582+00:00",
                id: "dae3adc5-6f7a-4f4d-980c-582f5e6391a7"
            }, {
                type: "WATER_LEVEL",
                value: 8.18,
                unitOfMeasurement: "METER",
                deviceDateTime: "newDateTime",
                dataId: "24088e32-2821-4bdc-a91a-86caf983ee39",
                dateTime: "2021-06-16T09:35:22.582+00:00",
                id: "24088e32-2821-4bdc-a91a-86caf983ee39"
            }]
        }]
    }

    let mock_device_measurements_2 = {
        status: "Successfully retrieved data for device: Water1",
        success: true,
        deviceName: "Water1",
        innerResponses: [{
            status: "Success",
            success: true,
            deviceName: "Water1",
            measurements: [{
                type: "WATER_LEVEL",
                value: 4.8,
                unitOfMeasurement: "METER",
                deviceDateTime: "newDateTime",
                dataId: "a7ac79dc-db1e-44af-9790-8c557dc1f87b",
                dateTime: "2021-06-17T20:15:05.568+00:00",
                id: "a7ac79dc-db1e-44af-9790-8c557dc1f87b"
            }, {
                type: "WATER_LEVEL",
                value: 9.1,
                unitOfMeasurement: "METER",
                deviceDateTime: "newDateTime",
                dataId: "cd505f11-41dd-48ba-9eee-0b4f5561c862",
                dateTime: "2021-06-17T20:15:05.568+00:00",
                id: "cd505f11-41dd-48ba-9eee-0b4f5561c862"
            }, {
                type: "WATER_LEVEL",
                value: 8.8,
                unitOfMeasurement: "METER",
                deviceDateTime: "newDateTime",
                dataId: "d4d36512-8cd1-4693-8a6d-e4c3aa0dbffc",
                dateTime: "2021-06-17T20:15:05.568+00:00",
                id: "d4d36512-8cd1-4693-8a6d-e4c3aa0dbffc"
            }]
        }, {
            status: "Success",
            success: true,
            deviceName: "Water1",
            measurements: [{
                type: "WATER_LEVEL",
                value: 7.55,
                unitOfMeasurement: "METER",
                deviceDateTime: "newDateTime",
                dataId: "527516a3-37a0-4bb5-b44e-12df8812a47e",
                dateTime: "2021-06-17T20:15:05.570+00:00",
                id: "527516a3-37a0-4bb5-b44e-12df8812a47e"
            }, {
                type: "WATER_LEVEL",
                value: 6.18,
                unitOfMeasurement: "METER",
                deviceDateTime: "newDateTime",
                dataId: "ce945d00-6e37-45b3-8fea-d1139ae1889a",
                dateTime: "2021-06-17T20:15:05.570+00:00",
                id: "ce945d00-6e37-45b3-8fea-d1139ae1889a"
            }, {
                type: "WATER_LEVEL",
                value: 9.18,
                unitOfMeasurement: "METER",
                deviceDateTime: "newDateTime",
                dataId: "9ead63f8-45ed-483f-bbfb-1a6311ae8998",
                dateTime: "2021-06-17T20:15:05.570+00:00",
                id: "9ead63f8-45ed-483f-bbfb-1a6311ae8998"
            }]
        }]
    }

    const newChartData = function chartData() {
        return {
            labels: ["May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
            datasets: [{
                type: 'line',
                label: 'Level',
                data: [10, 20, 27, 40, 10, 20, 27, 40],
                borderColor: "#11CDEF",
                backgroundColor: "#11CDEF",
            }, {
                type: 'line',
                label: 'Temperature',
                data: [50, 40, 45, 50, 50, 50, 45, 90],
                borderColor: "orange",
                backgroundColor: "orange",
                fill: false
            }],
            // labels: ['January', 'February', 'March', 'April']
        }
    }


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
                                {/*<Box component="span" color={theme.palette.white.main}>*/}
                                <Box component="span">
                                    Device Readings
                                </Box>
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
            <CardContent>
                <Box position="relative" height="350px">
                    <Line
                        // data={chartExample1[chartExample1Data]}
                        data={newChartData}
                        // options={chartExample1.options}
                        // options={chartOptions}
                        getDatasetAtEvent={(e) => console.log(e)}
                    />
                </Box>
            </CardContent>
        </Card>
    </>
  );
}

export default LineChart;
