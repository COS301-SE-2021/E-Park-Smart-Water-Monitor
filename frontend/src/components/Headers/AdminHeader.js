import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Container from "@material-ui/core/Container";
import componentStyles from "assets/theme/components/AdminHeader.js";

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
                />
            </div>
        </>
    );
};

export default AdminHeader;
