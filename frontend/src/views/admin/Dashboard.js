import React, {useEffect, useState} from "react";
// javascript plugin for creating charts
import Chart from "chart.js";
// react plugin used to create charts
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";

import Map from "components/Cards/Map.js"
import BarChart from "components/Cards/BarChart.js"
import LineChart from "components/Cards/LineChart.js"
import DeviceTable from "../../components/Cards/DeviceTable";




// core components
import Header from "components/Headers/Header.js";

import {
  chartOptions,
  parseOptions
} from "variables/charts.js";

import componentStyles from "assets/theme/views/admin/dashboard.js";
import axios from "axios";
import {Marker, Popup} from "react-leaflet";

const useStyles = makeStyles(componentStyles);



function Dashboard() {
  const classes = useStyles();
  const [response, setResponse] = useState(null)
  const [devices, setDevices] = useState()

  if (window.Chart) {
    parseOptions(Chart, chartOptions());
  }


  // MONOLITH of SITES
  useEffect(() => {
    axios.post('http://localhost:8080/api/park/getParkWaterSites', {
      parkId: "2ea5ba27-9d8e-41a4-9628-485f0ae2fb57"
    }).then((res)=>{
      if(res.data)
      {
        const site = res.data.site; // site array
        const site_devices = []
        if(site)
        {
          for (let i = 0; i < site.length ; i++) {
            for (let p = 0; p < site[i].waterSourceDevices.length ; p++) {
              site_devices.push(site[i].waterSourceDevices[p]);
            }
          }
        }
        setDevices(site_devices)

        // get the device info
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
            { devices && <Map devices={ devices }></Map> }
          </Grid>
          <Grid
              item
              xs={12}
              xl={4}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            { devices && <DeviceTable devices={ devices }></DeviceTable> }
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
            <card>
              Device Details
            </card>
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
