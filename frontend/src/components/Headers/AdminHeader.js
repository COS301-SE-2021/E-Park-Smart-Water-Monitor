import React from "react";
import { makeStyles } from "@material-ui/core/styles";

import Box from "@material-ui/core/Box";
import Container from "@material-ui/core/Container";
import componentStyles from "assets/theme/components/AdminHeader.js";
import Stats from "../Admin/Stats/Stats";


const useStyles = makeStyles(componentStyles);

const AdminHeader = () => {
    const classes = useStyles();

    return (
        <>
            <div className={classes.header}>
                <Container
                    maxWidth={false}
                    component={Box}
                    classes={{ root: classes.containerRoot }}
                >
                {/*<Stats/>*/}
                </Container>
            </div>
        </>
    );
};

export default AdminHeader;
