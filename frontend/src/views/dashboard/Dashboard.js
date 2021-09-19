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
  const [devices, setDevices] = useState(null)
  const [device, setDevice] = useState(null)
  const [inspections, setInspections] = useState(null)
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

      }else{
        console.log('res.data null')
      }
    }).catch((res)=>{
      console.log(JSON.stringify(res))
    });
  }, [value]) // second param [] is a list of dependency to watch and run useEffect


  // Get all inspections for the park
  useEffect(() => {
    axios.get('http://localhost:8080/api/inspections/getAllInspections', {
      headers: {
        'Authorization': "Bearer " + user.token
      }
    }).then((res) => {
      if (res.data) {

        if (res.data && res.data.inspections) {
          // get the inspections for the user logged in
          let parkIndex = -1;
          for (let i = 0; i < res.data.parkId.length; i++) {
            if (res.data.parkId[i] == user.parkID) {
              parkIndex = i;
            }
          }

          setInspections(res.data.inspections[parkIndex])

        }
      }
    })



  }, [])

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
            {/*if a device changes in the table then the map must rerender*/}
            { device && devices && <Map load_device={load_device} devices={ devices } device={device} />}
          </Grid>
        </Grid>

        <Grid container component={Box} marginTop="3rem!important">
          <Grid
              item
              xs={12}
              xl={5}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >

            { device && devices && <DeviceTable device={ device } reloadDeviceTable={ reloadDeviceTable } load_device={load_device} devices={ devices }/> }

          </Grid>

          <Grid
              item
              xs={12}
              xl={7}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            { device && <DeviceDetails reloadDeviceTable={ reloadDeviceTable } device={ device }/> }
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
            { device && <LineChart reloadDeviceTable={reloadDeviceTable} device={ device }/> }
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
            { inspections &&
              <InspectionTable inspections={inspections}/>
            }
          </Grid>
        </Grid>

      </Container>
      <br/><br/>
    </>
  );
}

export default Dashboard;
