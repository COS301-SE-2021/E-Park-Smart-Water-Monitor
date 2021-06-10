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
import DeviceDetails from "../../components/Cards/DeviceDetails";




// core components
import Header from "components/Headers/Header.js";

import {
  chartOptions,
  parseOptions
} from "variables/charts.js";

import componentStyles from "assets/theme/views/admin/dashboard.js";
import axios from "axios";

const useStyles = makeStyles(componentStyles);

function Dashboard() {
  const classes = useStyles();
  const [response, setResponse] = useState(null)
  const [devices, setDevices] = useState([])
  const [device, setDevice] = useState(null)

  if (window.Chart) {
    parseOptions(Chart, chartOptions());
  }


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
            { device && <DeviceDetails device={ device }></DeviceDetails> }
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
