import React, {useEffect, useState} from "react";
// javascript plugin for creating charts
import Chart from "chart.js";
// react plugin used to create charts
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import Map from "components/Dashboard/Map.js"
import LineChart from "components/Dashboard/LineChart.js"
import DeviceTable from "../../components/Dashboard/Device/DeviceTable";
import DeviceDetails from "../../components/Dashboard/Device/DeviceDetails";


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
  // const [response, setResponse] = useState(null)
  const [devices, setDevices] = useState([])
  const [device, setDevice] = useState(null)
  const [inspections, setInspections] = useState([])

  if (window.Chart) {
    parseOptions(Chart, chartOptions());
  }


  // axios.defaults.headers.post['Access-Control-Allow-Origin'] = 'http://localhost:3000';


  // let temp_jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDSEljaGkyIiwicm9sZXMiOiJGSUVMRF9FTkdJTkVFUiIsImV4cCI6MTYyODc2MzA3NH0.Q6P7CwsJCWrG212X0gDw68663EAiNbkNoylXlwvVWVKdJM1jWyaACDWbc9F5nmi2BRoPtlhVgyfooyDP7sCmvw"
  let temp_jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDSEljaGkyIiwicm9sZXMiOiJGSUVMRF9FTkdJTkVFUiIsImV4cCI6MTYyODc2Mzg1NX0.mBZQjxJuuErfibzV6zRtweppy4XhbLA-V4khXINlk31ZYJlSWExLX4p7lD-9NtBIZYMHd5OIS9KLMSXzODGMjg"
  // axios.defaults.headers.get['Authorization'] = 'Bearer '+ temp_jwt;
  let config = {
    headers: {
      "Authorization": "Bearer "+ temp_jwt
    }
  }

  useEffect(() => {
    axios.get('http://localhost:8080/api/devices/getAllDevices', { headers: {
        "Authorization" : `Bearer ${temp_jwt}`,
        "Access-Control-Allow-Origin" : 'http://localhost:3000'
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
  }, []) // second param [] is a list of dependency to watch and run useEffect

  useEffect(() => {
    if (device != null) {
      axios.post('http://localhost:8080/api/inspections/getDeviceInspections', {
        deviceId: device.deviceId
      }, {
        headers: {
          "Authorization": "Bearer "+ temp_jwt
        }
      }).then((res) => {
        if (res.data) {
          setInspections(res.data.inspectionList)
        }
      })
    }
  }, [device])

  const load_device = (device_id) =>
  {
    // get the device from the monolith of devices to render the specific details
    for(let i =0; i<devices.length;i++)
    {
      if(devices[i].deviceId == device_id)
      {
        setDevice(devices[i])
      }
    }

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


        <Grid container component={Box} marginTop="3rem!important">
          <Grid
              item
              xs={12}
              xl={12}
              component={Box}
              marginBottom="3rem!important"
              classes={{ root: classes.gridItemRoot }}
          >
            <InspectionTable inspections={inspections}></InspectionTable>
          </Grid>
        </Grid>

      </Container>
      <br/><br/>
    </>
  );
}

export default Dashboard;
