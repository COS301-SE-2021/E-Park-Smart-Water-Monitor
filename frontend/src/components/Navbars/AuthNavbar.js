import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Box from "@material-ui/core/Box";
import Container from "@material-ui/core/Container";
import Toolbar from "@material-ui/core/Toolbar";

// core components
import componentStyles from "assets/theme/components/auth-navbar.js";

const useStyles = makeStyles(componentStyles);

export default function AuthNavbar() {
  const classes = useStyles();

  return (
    <>
      <AppBar position="absolute" color="transparent" elevation={0}>
        <Toolbar>
          <Container
            display="flex!important"
            justifyContent="flex-start"
            alignItems="center"
            marginTop=".75rem"
            component={Box}
            maxWidth="xl"
          >
            <Box
              alt="..."
              height="60px"
              component="img"
              className={classes.headerImg}
              /* eslint-disable-next-line no-undef */
              src={require("assets/img/brand/ERP-logo.png").default}
            />
          </Container>
        </Toolbar>
      </AppBar>
    </>
  );
}
