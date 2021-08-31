import React, {useContext, useState} from "react";
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
import {UserContext} from "../../Context/UserContext";

const useStyles = makeStyles(componentStyles);

function Admin() {
    const classes = useStyles();
    const [park, setPark] = useState("") // for passing from park table to site table

    const user = useContext(UserContext)

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
                            {user.role === "ADMIN" &&
                            <UserTable/>
                            }
                            <DeviceTable/>
                            <InspectionTable/>
                        </Grid>

                        {/* Sites altered on the change of park */}
                        {user.role === "ADMIN" &&
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


                        {user.role === "ADMIN" &&
                        <Grid
                            item
                            xs={12}
                            xl={5}
                            component={Box}
                            marginBottom="3rem!important"
                            classes={{root: classes.gridItemRoot}}

                        >
                            <ParkTable select={selectPark}/>
                        </Grid>
                        }

                    </Grid>
                </Container>
        </>
    );

}

export default Admin;

