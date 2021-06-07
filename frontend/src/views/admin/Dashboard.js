import React, {useEffect, useState} from "react";
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
import DeviceTable from "../../components/Cards/DeviceTable";
import axios from "axios";
import {Marker, Popup} from "react-leaflet";

const useStyles = makeStyles(componentStyles);



function Dashboard() {
  const classes = useStyles();
  const [response, setResponse] = useState(null)

  if (window.Chart) {
    parseOptions(Chart, chartOptions());
  }


  const devices = []
  const sites = []
  // MONOLITH of SITES

  useEffect(() => {
    axios.post('http://localhost:8080/api/park/getParkWaterSites', {
      parkId: "2ea5ba27-9d8e-41a4-9628-485f0ae2fb57"
    }).then((res)=>{
      if(res.data)
      {
        // console.log("result water data: "+JSON.stringify(res))

        // console.log("devices: "+JSON.stringify(devices))
        const site = res.data.site; // site array
        // const site_devices = []
        for (let i = 0; i < site.length ; i++) {
          for (let p = 0; p < site[i].waterSourceDevices.length ; p++) {
            devices.push(site[i].waterSourceDevices[p]);
          }
        }

        // console.log("site devices: "+JSON.stringify(site_devices))

        const m = devices.map((device) =>
            // <ListItem key={number.toString()}
            //           value={number} />
            <Marker key={device.deviceName} position={[ device.deviceData.latitude , device.deviceData.longitude ]}>
              <Popup>
                { device.deviceName }
              </Popup>
            </Marker>
        );
        setResponse(m);
        console.log("markers: "+JSON.stringify(m))
      }else{
        console.log('res.data null')
      }
    });
  }, []) // second param [] is a list of dependency to watch and run useEffect


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
              xl={8}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            <Map></Map>
          </Grid>
          <Grid
              item
              xs={12}
              xl={4}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            <DeviceTable devices={devices}></DeviceTable>
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

      </Container>
      <br/><br/>
    </>
  );
}

export default Dashboard;
