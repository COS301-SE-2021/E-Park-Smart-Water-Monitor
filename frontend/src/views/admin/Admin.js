import React, {useState} from "react";
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
import Stats from "../../components/Admin/Stats/Stats";
import SiteTable from "../../components/Admin/Site/SiteTable";


const useStyles = makeStyles(componentStyles);
const parkAndSitesStyle = {
    background: '#F3F3F3',
    boxShadow: '0 8px 8px -4px lightblue',
    padding: '3rem'
}


function Admin() {
    const classes = useStyles();
    const [park, setPark] = useState("")

    const selectPark = (details) => {
        setPark(details)
    }

    return (
        <>
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
                    <Grid container
                       style = { parkAndSitesStyle }
                    >
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
                            { park && <SiteTable park={ park }/> }
                        </Grid>
                    </Grid>

                </Grid>
            </Container>

        </>
    );
}

export default Admin;

