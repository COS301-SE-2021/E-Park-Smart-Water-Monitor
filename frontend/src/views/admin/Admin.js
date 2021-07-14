import React from "react";
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


const useStyles = makeStyles(componentStyles);


function Admin() {
    const classes = useStyles();

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
                        <ParkTable/>
                    </Grid>
                    <Grid
                        item
                        xs={12}
                        xl={5}
                        component={Box}
                        marginBottom="3rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                        <ParkTable/>
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
                        <ParkTable/>
                    </Grid>

                </Grid>
            </Container>

        </>
    );
}

export default Admin;

