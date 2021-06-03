import React from "react";
// javascipt plugin for creating charts
import Chart from "chart.js";
// react plugin used to create charts
import { Line, Bar } from "react-chartjs-2";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardHeader from "@material-ui/core/CardHeader";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Typography from "@material-ui/core/Typography";

import DeviceData from "components/Custom/DeviceData"
import Map from "components/Cards/Map.js"
//import Axios from 'axios';
// @material-ui/icons components
// import ArrowDownward from "@material-ui/icons/ArrowDownward";
// import ArrowUpward from "@material-ui/icons/ArrowUpward";



// core components
import Header from "components/Headers/Header.js";

import {
  chartOptions,
  parseOptions,
  chartExample1,
  chartExample2,
} from "variables/charts.js";

import componentStyles from "assets/theme/views/admin/dashboard.js";

const useStyles = makeStyles(componentStyles);



function Dashboard() {
  const classes = useStyles();
  const theme = useTheme();
  const [activeNav, setActiveNav] = React.useState(1);
  const [chartExample1Data, setChartExample1Data] = React.useState("data1");


  // //for devices:
  // const [name,setName]= useState()
  // const [battery,setBattery]= useState()
  // const [status,setStatus]= useState()
  // const [long,setLong]= useState()
  // const [lat,setLat]= useState()

  console.log("chartExample1Data: "+ chartExample1[chartExample1Data])
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


  // CHART 2 - Bar Chart
  console.log("chartExample2.data")
  console.log(chartExample2.data)
  console.log("chartExample2.options")
  console.log(chartExample2.options)



  const newChartData = function chartData() {
    return {
      // labels: ["May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
      datasets: [{
        type: 'line',
        label: 'Line Dataset',
        data: [10, 20, 27, 40]
      }, {
        type: 'line',
        label: 'Line Dataset',
        data: [50, 50, 45, 50],
        color: "orange"
      }],
      labels: ['January', 'February', 'March', 'April']
    }
  }


  // get controller data
  // axios.post('localhost:8080/api/park/getParkWaterSites',{
  //   parkId: "dda99bb7-4116-4e8a-ac35-9dad7e8c1b3f"}).
  //   then(function (response) {
  //     console.log("get water sites")
  //     console.log(response);
  //   }).catch(err => console.log(err));
  //


  if (window.Chart) {
    parseOptions(Chart, chartOptions());
  }

  const toggleNavs = (index) => {
    setActiveNav(index);
    setChartExample1Data("data" + index);
  };
  return (
    <>

      <Header />
      {/* Page content */}
      <Container
        maxWidth={false}
        component={Box}
        marginTop="-6rem"
        classes={{ root: classes.containerRoot }}
      >

        <Grid container>

          {/*MAP*/}
          <Grid
              item
              xs={12}
              xl={12}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            <Map></Map>
            {/*<Card*/}
            {/*    // classes={{*/}
            {/*    //   root: classes.cardRoot + " " + classes.cardRootBgGradient,*/}
            {/*    // }}*/}
            {/*>*/}
            {/*  <CardHeader*/}
            {/*      title={*/}
            {/*        <Box component="span" color={theme.palette.gray[600]}>*/}
            {/*          device layout*/}
            {/*        </Box>*/}
            {/*      }*/}
            {/*      subheader="Park Map"*/}
            {/*      classes={{ root: classes.cardHeaderRoot }}*/}
            {/*      titleTypographyProps={{*/}
            {/*        component: Box,*/}
            {/*        variant: "h6",*/}
            {/*        letterSpacing: ".0625rem",*/}
            {/*        marginBottom: ".25rem!important",*/}
            {/*        classes: {*/}
            {/*          root: classes.textUppercase,*/}
            {/*        },*/}
            {/*      }}*/}
            {/*      subheaderTypographyProps={{*/}
            {/*        component: Box,*/}
            {/*        variant: "h2",*/}
            {/*        marginBottom: "0!important",*/}
            {/*        color: "initial",*/}
            {/*      }}*/}
            {/*  ></CardHeader>*/}
            {/*  <CardContent>*/}

            {/*    <div style={ { height: 350 } }>*/}
            {/*      /!*rietvlei centre*!/*/}
            {/*      <MapContainer style={mapStyles} center={[-25.88536975144579, 28.277796392845673]} zoom={14} scrollWheelZoom={false}>*/}
            {/*        <TileLayer*/}
            {/*            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'*/}
            {/*            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"*/}
            {/*        />*/}
            {/*        <Marker position={[-25.8825,28.2639 ]}>*/}
            {/*          <Popup>*/}
            {/*            Site Buffalo*/}
            {/*          </Popup>*/}
            {/*        </Marker>*/}
            {/*        <Marker position={[-25.8840,28.27 ]}>*/}
            {/*          <Popup>*/}
            {/*            Site Rhino*/}
            {/*          </Popup>*/}
            {/*        </Marker>*/}
            {/*        <Marker position={[-25.89,28.28 ]}>*/}
            {/*          <Popup>*/}
            {/*            Site Cheetah*/}
            {/*          </Popup>*/}
            {/*        </Marker>*/}
            {/*      </MapContainer>*/}
            {/*    </div>*/}

            {/*  </CardContent>*/}
            {/*</Card>*/}
          </Grid>
        </Grid>


        <Grid container component={Box} marginTop="3rem!important">
          {/*Bar GRAPH*/}
          <Grid
              item
              xs={12}
              xl={6}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            <Card classes={{ root: classes.cardRoot }}>
              <CardHeader
                  title={
                    <Box component="span" color={theme.palette.gray[600]}>
                      Inspections
                    </Box>
                  }
                  subheader="Water Refills"
                  classes={{ root: classes.cardHeaderRoot }}
                  titleTypographyProps={{
                    component: Box,
                    variant: "h6",
                    letterSpacing: ".0625rem",
                    marginBottom: ".25rem!important",
                    classes: {
                      root: classes.textUppercase,
                    },
                  }}
                  subheaderTypographyProps={{
                    component: Box,
                    variant: "h2",
                    marginBottom: "0!important",
                    color: "initial",
                  }}
              ></CardHeader>
              <CardContent>
                <Box position="relative" height="350px">
                  <Bar
                      data={chartExample2.data}
                      options={chartExample2.options}
                  />
                </Box>
              </CardContent>
            </Card>
          </Grid>
          {/*  /!*SECOND BAR GRAPH*!/*/}
          {/*  <Grid item*/}
          {/*        xs={12}*/}
          {/*        xl={4}*/}
          {/*        marginBottom="3rem!important">*/}
          {/*    <Card classes={{ root: classes.cardRoot }}>*/}
          {/*      <CardHeader*/}
          {/*          title={*/}
          {/*            <Box component="span" color={theme.palette.gray[600]}>*/}
          {/*              Inspections*/}
          {/*            </Box>*/}
          {/*          }*/}
          {/*          subheader="Total orders"*/}
          {/*          classes={{ root: classes.cardHeaderRoot }}*/}
          {/*          titleTypographyProps={{*/}
          {/*            component: Box,*/}
          {/*            variant: "h6",*/}
          {/*            letterSpacing: ".0625rem",*/}
          {/*            marginBottom: ".25rem!important",*/}
          {/*            classes: {*/}
          {/*              root: classes.textUppercase,*/}
          {/*            },*/}
          {/*          }}*/}
          {/*          subheaderTypographyProps={{*/}
          {/*            component: Box,*/}
          {/*            variant: "h2",*/}
          {/*            marginBottom: "0!important",*/}
          {/*            color: "initial",*/}
          {/*          }}*/}
          {/*      ></CardHeader>*/}
          {/*      <CardContent>*/}
          {/*        <Box position="relative" height="350px">*/}
          {/*          <Bar*/}
          {/*              data={chartExample2.data}*/}
          {/*              options={chartExample2.options}*/}
          {/*          />*/}
          {/*        </Box>*/}
          {/*      </CardContent>*/}
          {/*    </Card>*/}
          {/*  </Grid>*/}

          {/*Line Graph*/}
          <Grid
              item
              xs={12}
              xl={6}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
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
                          <Button
                              variant="contained"
                              color="primary"
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
                            Month
                          </Button>
                          <Button
                              variant="contained"
                              color="primary"
                              onClick={() => toggleNavs(2)}
                              classes={{
                                root:
                                    activeNav === 2
                                        ? ""
                                        : classes.buttonRootUnselected,
                              }}
                          >
                            Week
                          </Button>
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
                      options={chartExample1.options}
                      getDatasetAtEvent={(e) => console.log(e)}
                  />
                </Box>
              </CardContent>
            </Card>
          </Grid>


        </Grid>


        {/*<Grid container >*/}

        {/*/!*  /!*SECOND BAR GRAPH*!/*!/*/}
        {/*/!*  <Grid item*!/*/}
        {/*/!*        xs={12}*!/*/}
        {/*/!*        xl={4}*!/*/}
        {/*/!*        marginBottom="3rem!important">*!/*/}
        {/*/!*    <Card classes={{ root: classes.cardRoot }}>*!/*/}
        {/*/!*      <CardHeader*!/*/}
        {/*/!*          title={*!/*/}
        {/*/!*            <Box component="span" color={theme.palette.gray[600]}>*!/*/}
        {/*/!*              Inspections*!/*/}
        {/*/!*            </Box>*!/*/}
        {/*/!*          }*!/*/}
        {/*/!*          subheader="Total orders"*!/*/}
        {/*/!*          classes={{ root: classes.cardHeaderRoot }}*!/*/}
        {/*/!*          titleTypographyProps={{*!/*/}
        {/*/!*            component: Box,*!/*/}
        {/*/!*            variant: "h6",*!/*/}
        {/*/!*            letterSpacing: ".0625rem",*!/*/}
        {/*/!*            marginBottom: ".25rem!important",*!/*/}
        {/*/!*            classes: {*!/*/}
        {/*/!*              root: classes.textUppercase,*!/*/}
        {/*/!*            },*!/*/}
        {/*/!*          }}*!/*/}
        {/*/!*          subheaderTypographyProps={{*!/*/}
        {/*/!*            component: Box,*!/*/}
        {/*/!*            variant: "h2",*!/*/}
        {/*/!*            marginBottom: "0!important",*!/*/}
        {/*/!*            color: "initial",*!/*/}
        {/*/!*          }}*!/*/}
        {/*/!*      ></CardHeader>*!/*/}
        {/*/!*      <CardContent>*!/*/}
        {/*/!*        <Box position="relative" height="350px">*!/*/}
        {/*/!*          <Bar*!/*/}
        {/*/!*              data={chartExample2.data}*!/*/}
        {/*/!*              options={chartExample2.options}*!/*/}
        {/*/!*          />*!/*/}
        {/*/!*        </Box>*!/*/}
        {/*/!*      </CardContent>*!/*/}
        {/*/!*    </Card>*!/*/}
        {/*/!*  </Grid>*!/*/}

        {/*  /!*Line Graph*!/*/}
        {/*  <Grid*/}
        {/*    item*/}
        {/*    xs={12}*/}
        {/*    xl={6}*/}
        {/*    component={Box}*/}
        {/*    marginBottom="3rem!important"*/}
        {/*    classes={{ root: classes.gridItemRoot }}*/}
        {/*  >*/}
        {/*    <Card*/}
        {/*      classes={{*/}
        {/*        root: classes.cardRoot + " " + classes.cardRootBgGradient,*/}
        {/*      }}*/}
        {/*    >*/}
        {/*      <CardHeader*/}
        {/*        subheader={*/}
        {/*          <Grid*/}
        {/*            container*/}
        {/*            component={Box}*/}
        {/*            alignItems="center"*/}
        {/*            justifyContent="space-between"*/}
        {/*          >*/}
        {/*            <Grid item xs="auto">*/}
        {/*              <Box*/}
        {/*                component={Typography}*/}
        {/*                variant="h6"*/}
        {/*                letterSpacing=".0625rem"*/}
        {/*                marginBottom=".25rem!important"*/}
        {/*                className={classes.textUppercase}*/}
        {/*              >*/}
        {/*                <Box component="span" color={theme.palette.gray[400]}>*/}
        {/*                  Overview*/}
        {/*                </Box>*/}
        {/*              </Box>*/}
        {/*              <Box*/}
        {/*                component={Typography}*/}
        {/*                variant="h2"*/}
        {/*                marginBottom="0!important"*/}
        {/*              >*/}
        {/*                <Box component="span" color={theme.palette.white.main}>*/}
        {/*                  Device Readings*/}
        {/*                </Box>*/}
        {/*              </Box>*/}
        {/*            </Grid>*/}
        {/*            <Grid item xs="auto">*/}
        {/*              <Box*/}
        {/*                justifyContent="flex-end"*/}
        {/*                display="flex"*/}
        {/*                flexWrap="wrap"*/}
        {/*              >*/}
        {/*                <Button*/}
        {/*                  variant="contained"*/}
        {/*                  color="primary"*/}
        {/*                  component={Box}*/}
        {/*                  marginRight="1rem!important"*/}
        {/*                  onClick={() => toggleNavs(1)}*/}
        {/*                  classes={{*/}
        {/*                    root:*/}
        {/*                      activeNav === 1*/}
        {/*                        ? ""*/}
        {/*                        : classes.buttonRootUnselected,*/}
        {/*                  }}*/}
        {/*                >*/}
        {/*                  Month*/}
        {/*                </Button>*/}
        {/*                <Button*/}
        {/*                  variant="contained"*/}
        {/*                  color="primary"*/}
        {/*                  onClick={() => toggleNavs(2)}*/}
        {/*                  classes={{*/}
        {/*                    root:*/}
        {/*                      activeNav === 2*/}
        {/*                        ? ""*/}
        {/*                        : classes.buttonRootUnselected,*/}
        {/*                  }}*/}
        {/*                >*/}
        {/*                  Week*/}
        {/*                </Button>*/}
        {/*              </Box>*/}
        {/*            </Grid>*/}
        {/*          </Grid>*/}
        {/*        }*/}
        {/*        classes={{ root: classes.cardHeaderRoot }}*/}
        {/*      ></CardHeader>*/}
        {/*      <CardContent>*/}
        {/*        <Box position="relative" height="350px">*/}
        {/*          <Line*/}
        {/*            // data={chartExample1[chartExample1Data]}*/}
        {/*            data={newChartData}*/}
        {/*            options={chartExample1.options}*/}
        {/*            getDatasetAtEvent={(e) => console.log(e)}*/}
        {/*          />*/}
        {/*        </Box>*/}
        {/*      </CardContent>*/}
        {/*    </Card>*/}
        {/*  </Grid>*/}



        {/*</Grid>*/}
        <Grid container component={Box} marginTop="3rem">
          <Grid
            item
            xs={12}
            xl={12}
            component={Box}
            marginBottom="3rem!important"
            classes={{ root: classes.gridItemRoot }}
          >
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
                        Device Details
                      </Box>
                    </Grid>
                    <Grid item xs="auto">
                      <Box
                        justifyContent="flex-end"
                        display="flex"
                        flexWrap="wrap"
                      >
                        {/*<Button*/}
                        {/*  variant="contained"*/}
                        {/*  color="primary"*/}
                        {/*  size="small"*/}
                        {/*>*/}
                        {/*  See all*/}
                        {/*</Button>*/}
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
                        Device Name
                      </TableCell>
                      <TableCell
                          classes={{
                            root:
                                classes.tableCellRoot +
                                " " +
                                classes.tableCellRootHead,
                          }}
                      >
                        Latitude
                      </TableCell>
                      <TableCell
                          classes={{
                            root:
                                classes.tableCellRoot +
                                " " +
                                classes.tableCellRootHead,
                          }}
                      >
                        Longitude
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
                        Battery Level
                      </TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>


                    <DeviceData />

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
                        Water2000
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        -25.812494434
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        28.239765508
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        FINE
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        100%
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
                        Water5000
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        -25.112494434
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        28.129765508
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        FINE
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        100%
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
                        Water1000
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        -25.892494434
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        28.289765508
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        FINE
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        100%
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
                        Water3000
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        -25.112494434
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        28.129765508
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        FINE
                      </TableCell>
                      <TableCell classes={{ root: classes.tableCellRoot }}>
                        100%
                      </TableCell>
                    </TableRow>

                    {/*{axios.post('localhost:8080/api/park/getParkWaterSites',{*/}
                    {/*  parkId: "dda99bb7-4116-4e8a-ac35-9dad7e8c1b3f"}).*/}
                    {/*    then(function (response) {*/}
                    {/*      console.log(response);*/}
                    {/*    })*/}
                    {/*}*/}

                    {/*{Axios.post('localhost:8080/api/park/getParkWaterSites', {*/}
                    {/*parkId: "dda99bb7-4116-4e8a-ac35-9dad7e8c1b3f"})*/}
                    {/*.then(function (response) {*/}
                    {/*    response.map((values)=>(*/}
                    {/*      <>*/}
                    {/*        <TableRow>*/}
                    {/*        <TableCell*/}
                    {/*        classes={{*/}
                    {/*          root:*/}
                    {/*          classes.tableCellRoot +*/}
                    {/*          " " +*/}
                    {/*          classes.tableCellRootBodyHead,*/}
                    {/*        }}*/}
                    {/*        component="th"*/}
                    {/*        variant="head"*/}
                    {/*        scope="row"*/}
                    {/*        >*/}
                    {/*          {values.deviceName}*/}
                    {/*        </TableCell>*/}
                    {/*        <TableCell classes={{ root: classes.tableCellRoot }}>*/}
                    {/*          {values.deviceData.latitude}*/}
                    {/*        </TableCell>*/}
                    {/*        <TableCell classes={{ root: classes.tableCellRoot }}>*/}
                    {/*          {values.deviceData.longitude}*/}
                    {/*        </TableCell>*/}
                    {/*        <TableCell classes={{ root: classes.tableCellRoot }}>*/}
                    {/*          {values.deviceData.deviceStatus}*/}
                    {/*        </TableCell>*/}
                    {/*        <TableCell classes={{ root: classes.tableCellRoot }}>*/}
                    {/*          {values.deviceData.battery}*/}
                    {/*        </TableCell>*/}
                    {/*        </TableRow>*/}
                    {/*      </>*/}
                    {/*    ))*/}

                    {/*})}*/}

                  </TableBody>
                </Box>
              </TableContainer>
            </Card>
          </Grid>
        </Grid>
      </Container>
      <br/><br/>
    </>
  );
}

export default Dashboard;
