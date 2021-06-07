import React from "react";
// javascipt plugin for creating charts
import Chart from "chart.js";
// react plugin used to create charts
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
// import { useTheme } from "@material-ui/core/styles";
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

import DeviceData from "components/Custom/DeviceData"
import Map from "components/Cards/Map.js"
import BarChart from "components/Cards/BarChart.js"
import LineChart from "components/Cards/LineChart.js"
//import Axios from 'axios';
// @material-ui/icons components
// import ArrowDownward from "@material-ui/icons/ArrowDownward";
// import ArrowUpward from "@material-ui/icons/ArrowUpward";



// core components
import Header from "components/Headers/Header.js";

import {
  chartOptions,
  parseOptions,
  // chartExample1,
  // chartExample2,
} from "variables/charts.js";

import componentStyles from "assets/theme/views/admin/dashboard.js";

const useStyles = makeStyles(componentStyles);



function Dashboard() {
  const classes = useStyles();

  if (window.Chart) {
    parseOptions(Chart, chartOptions());
  }


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
          <Grid
              item
              xs={12}
              xl={12}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            <Map></Map>
          </Grid>
        </Grid>


        <Grid container component={Box} marginTop="3rem!important">
          <Grid
              item
              xs={12}
              xl={6}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            <BarChart></BarChart>
          </Grid>
          <Grid
              item
              xs={12}
              xl={6}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            <LineChart></LineChart>
          </Grid>
        </Grid>


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
