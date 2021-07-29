import React, {useEffect, useState} from "react";
import AdminHeader from "../../components/Headers/AdminHeader";
import UserTable from "../../components/Admin/User/UserTable";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import componentStyles from "assets/theme/views/dashboard/dashboard.js";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import DeviceTable from "../../components/Admin/Device/DeviceTable";
import ParkTable from "../../components/Admin/Park/ParkTable";
import InspectionTable from "components/Admin/Inspection/InspectionTable";
import SiteTable from "../../components/Admin/Site/SiteTable";
import { AdminProvider } from '../../components/Admin/AdminContext'
import axios from "axios";

const useStyles = makeStyles(componentStyles);
const parkAndSitesStyle = {
    background: '#F3F3F3',
    boxShadow: '0 8px 8px -4px lightblue',
    padding: '3rem'
}


function Admin() {
    const classes = useStyles();
    const [park, setPark] = useState("") // for passing from park table to site table
    const [parks, setParks] = useState({}) // for initialising the context provider

    const selectPark = (details) => {
        setPark(details)
    }

    // get all the park and site data to populate the park and site tables
    // as well as the modals which require you select a park and a site
    // context will be passed down to all the children which require it
    // making load time faster and removes a lot of different API calls

    // You can get more than just parks and populate the context with may API
    // calls as they become necessary
    useEffect(() => {
        axios.get('http://localhost:8080/api/park/getAllParksAndSites').then((res)=>{
            if(res)
            {
                setParks(res.data)
            }
        });
    },[])

    return (
        <>
            <AdminProvider value={ parks }>
                <AdminHeader/>

                <Container
                    maxWidth={false}
                    component={Box}
                    marginTop="-1rem"
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
                            <UserTable/>
                            <DeviceTable/>
                            <InspectionTable/>
                        </Grid>

                        <Grid
                            item
                            xs={12}
                            xl={5}
                            component={Box}
                            marginBottom="3rem!important"
                            classes={{ root: classes.gridItemRoot }}

                        >
                            <ParkTable select={ selectPark }/>
                        </Grid>

                        {/* Sites altered on the change of park */}
                        <Grid
                            item
                            xs={12}
                            xl={7}
                            component={Box}
                            marginBottom="3rem!important"
                            classes={{ root: classes.gridItemRoot }}
                        >
                            <SiteTable park={ park }/>
                        </Grid>

                    </Grid>
                </Container>
            </AdminProvider>
        </>
    );
}

export default Admin;

