import React from "react";
import AdminHeader from "../../components/Headers/AdminHeader";
import UserTable from "../../components/Custom/UserTable";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import componentStyles from "assets/theme/views/dashboard/dashboard.js";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import DeviceTable from "../../components/Custom/DeviceTable";
import ParkTable from "../../components/Custom/ParkTable";
import InspectionTable from "components/Custom/InspectionTable";


const useStyles = makeStyles(componentStyles);


function Admin() {
    const classes = useStyles();

    return (
        <>
            <AdminHeader/>
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
                        <UserTable/>
                        <DeviceTable/>
                        <ParkTable/>
                        <InspectionTable/>
                    </Grid>

                </Grid>
            </Container>

        </>
    );
}

export default Admin;

