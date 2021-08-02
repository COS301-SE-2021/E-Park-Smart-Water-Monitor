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
import {DotLoader} from "react-spinners";

const useStyles = makeStyles(componentStyles);

function Admin() {
    const classes = useStyles();
    const [park, setPark] = useState("") // for passing from park table to site table
    const [parksAndSites, setParksAndSites] = useState(null) // all the parks and sites

    const selectPark = (details) => {
        setPark(details)
    }

    // get all the park and site data to populate the park and site tables
    // as well as the modals which require you select a park and a site
    // context will be passed down to all the children which require it
    // making load time faster and removes a lot of different API calls

    // You can get more than just parks and populate the context with may API
    // calls as they become necessary

    // A context object is created so that other inforamtion can be passed
    // to child components easily in future

    useEffect(() => {
        axios.get('http://localhost:8080/api/park/getAllParksAndSites').then((res)=>{
            if(res)
            {
                setParksAndSites(res.data)
            }
        });  
    },[])

    // Context explained
    // https://medium.com/nerd-for-tech/using-context-api-in-react-with-functional-components-dbc653c7d485
    // https://medium.com/@danfyfe/using-react-context-with-functional-components-153cbd9ba214

    if(parksAndSites === null){
        return (
            <>
                <AdminHeader/>
            </>
        )
    }
    else {
        return (
            <>
                <AdminProvider value={parksAndSites}>
                    <AdminHeader/>
                    <Container
                        maxWidth={false}
                        component={Box}
                        marginTop="-1rem"
                        classes={{root: classes.containerRoot}}
                    >
                        <Grid container>
                            <Grid
                                item
                                xs={12}
                                xl={12}
                                component={Box}
                                marginBottom="3rem!important"
                                classes={{root: classes.gridItemRoot}}
                            >
                                <UserTable/>
                                <DeviceTable/>
                                <InspectionTable/>
                            </Grid>

                            {parksAndSites &&
                            <Grid
                                item
                                xs={12}
                                xl={5}
                                component={Box}
                                marginBottom="3rem!important"
                                classes={{root: classes.gridItemRoot}}

                            >
                                <ParkTable select={selectPark}/>
                            </Grid>}

                            {/* Sites altered on the change of park */}
                            {parksAndSites &&
                            <Grid
                                item
                                xs={12}
                                xl={7}
                                component={Box}
                                marginBottom="3rem!important"
                                classes={{root: classes.gridItemRoot}}
                            >
                                <SiteTable park={park}/>
                            </Grid>
                            }

                            {!parksAndSites &&
                            <Grid
                                item
                                xs={12}
                                xl={12}
                                component={Box}
                                marginBottom="3rem!important"
                                classes={{root: classes.gridItemRoot}}
                            >
                                Loading Parks...
                            </Grid>
                            }

                        </Grid>
                    </Container>

                </AdminProvider>
            </>
        );
    }
}

export default Admin;

