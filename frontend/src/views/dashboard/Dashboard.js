import React, {useContext, useEffect, useState} from "react";
// javascript plugin for creating charts
import Chart from "chart.js";
// react plugin used to create charts
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import Map from "components/Dashboard/Map.js"
import LineChart from "components/Dashboard/Device/LineChart.js"
import DeviceTable from "../../components/Dashboard/Device/DeviceTable";
import DeviceDetails from "../../components/Dashboard/Device/DeviceDetails";
import { UserContext } from '../../Context/UserContext';


// core components
import Header from "components/Headers/Header.js";


import {
  chartOptions,
  parseOptions
} from "variables/charts.js";

import axios from "axios";
import componentStyles from "assets/theme/views/dashboard/dashboard.js";
import InspectionTable from "components/Dashboard/InspectionTable.js";

const useStyles = makeStyles(componentStyles);

function Dashboard() {
  const classes = useStyles();
  const [devices, setDevices] = useState([])
  const [device, setDevice] = useState(null)
  const [inspections, setInspections] = useState([])
  const [value, setValue] = useState(0)

  const user = useContext(UserContext)

  if (window.Chart) {
    parseOptions(Chart, chartOptions());
  }

  const reloadDeviceTable = () => {
    setValue(value => value+1)
  }

  useEffect(() => {
    axios.get('http://localhost:8080/api/devices/getAllDevices',{
      headers: {
        'Authorization': "Bearer " + user.token
      }
    }).then((res)=>{
      if(res.data)
      {
        const site = res.data.site; // site array
        setDevices(site)

        if(site && site[0])
        {
          setDevice(site[0])
        }
        // console.log(JSON.stringify(site))

      }else{
        console.log('res.data null')
      }
    }).catch((res)=>{
      console.log(JSON.stringify(res))
    });
  }, [value]) // second param [] is a list of dependency to watch and run useEffect

  useEffect(() => {
    if (device != null) {
      axios.post('http://localhost:8080/api/inspections/getDeviceInspections', {
        deviceId: device.deviceId
      }).then((res) => {
        if (res.data) {
          setInspections(res.data.inspectionList)
        }
      })
    }
  }, [device])

  const load_device = (device) =>
  {
    // console.log(JSON.stringify(device))
    setDevice(device)
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
            { devices && <Map load_device={load_device} devices={ devices }></Map> }
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

            { devices && <DeviceTable load_device={load_device} devices={ devices }></DeviceTable> }

          </Grid>

          <Grid
              item
              xs={12}
              xl={6}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            { device && <DeviceDetails reloadDeviceTable={reloadDeviceTable} device={ device }></DeviceDetails> }
          </Grid>
        </Grid>

        <Grid container component={Box} marginTop="3rem!important">
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


        {/*<Grid container component={Box} marginTop="3rem!important">*/}
        {/*  <Grid*/}
        {/*      item*/}
        {/*      xs={12}*/}
        {/*      xl={12}*/}
        {/*      component={Box}*/}
        {/*      marginBottom="3rem!important"*/}
        {/*      classes={{ root: classes.gridItemRoot }}*/}
        {/*  >*/}
        {/*    <InspectionTable inspections={inspections}></InspectionTable>*/}
        {/*  </Grid>*/}
        {/*</Grid>*/}

      </Container>
      <br/><br/>
    </>
  );
}

export default Dashboard;
