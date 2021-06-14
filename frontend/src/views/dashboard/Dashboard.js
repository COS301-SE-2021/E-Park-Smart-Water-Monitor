import React, {useEffect, useState} from "react";
// javascript plugin for creating charts
import Chart from "chart.js";
// react plugin used to create charts
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Typography from "@material-ui/core/Typography";

import Map from "components/Cards/Map.js"
import BarChart from "components/Cards/BarChart.js"
import LineChart from "components/Cards/LineChart.js"
import DeviceTable from "../../components/Cards/DeviceTable";
import DeviceDetails from "../../components/Cards/DeviceDetails";


// core components
import Header from "components/Headers/Header.js";

import {
  chartOptions,
  parseOptions
} from "variables/charts.js";

import axios from "axios";
import componentStyles from "assets/theme/views/dashboard/dashboard.js";
const useStyles = makeStyles(componentStyles);

function Dashboard() {
  const classes = useStyles();
  // const [response, setResponse] = useState(null)
  const [devices, setDevices] = useState([])
  const [device, setDevice] = useState(null)

  if (window.Chart) {
    parseOptions(Chart, chartOptions());
  }

  //scroll into view on click of device


  // MONOLITH of SITES
  useEffect(() => {
    axios.post('http://localhost:8080/api/devices/getParkDevices', {
      parkId: "b026bea2-17a4-4939-bbbb-80916d8cf44e"
    }).then((res)=>{
      if(res.data)
      {
        const site = res.data.site; // site array
        setDevices(site)

        if(site && site[0])
        {
          setDevice(site[0])
        }
        console.log(JSON.stringify(site))

      }else{
        console.log('res.data null')
      }
    });
  }, []) // second param [] is a list of dependency to watch and run useEffect

  const load_device = (device_id) =>
  {
      // get the new device metrics data
      // axios.post('http://localhost:8080/api/devices/getDevice', {
      //   deviceID: "b026bea2-17a4-4939-bbbb-80916d8cf44e"
      // }).then((res)=>{
      //   if(res.data)
      //   {
      //     const site = res.data.site; // site array
      //     setDevices(site)
      //
      //     if(site && site[0])
      //     {
      //       setDevice(site[0])
      //     }
      //     console.log(JSON.stringify(site))
      //
      //   }else{
      //     console.log('res.data null')
      //   }
      // });


    // get the device from the monolith of devices to render the specific details
    setDevice(null)

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
            { devices && <Map devices={ devices }></Map> }
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
            { devices && <DeviceTable onSelectDevice={load_device} devices={ devices }></DeviceTable> }
          </Grid>

          <Grid
              item
              xs={12}
              xl={6}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            { device && <DeviceDetails device={ device }></DeviceDetails> }
          </Grid>
        </Grid>

        <Grid container>
          <Grid
              item
              xs={12}
              xl={12}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            <LineChart></LineChart>
          </Grid>
        </Grid>


        <Grid container component={Box} marginTop="3rem!important">
          <Grid
              item
              xs={12}
              xl={4}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            <BarChart></BarChart>
          </Grid>
          <Grid
              item
              xs={12}
              xl={4}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            <BarChart></BarChart>
          </Grid>
          <Grid
              item
              xs={12}
              xl={4}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            <BarChart></BarChart>
          </Grid>

        </Grid>

      </Container>
      <br/><br/>
    </>
  );
}

export default Dashboard;
