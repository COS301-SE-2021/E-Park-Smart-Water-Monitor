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
    chartExample1,
    // chartOptions,
    // parseOptions,
    // chartExample2,
} from "variables/charts.js";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";


const useStyles = makeStyles(componentStyles);

function LineChart() {
  const classes = useStyles();
  const theme = useTheme();
  const [activeNav, setActiveNav] = React.useState(1);
  const [chartExample1Data, setChartExample1Data] = React.useState("data1");

  const toggleNavs = (index) => {
    setActiveNav(index);
    setChartExample1Data("data" + index);
  };

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
