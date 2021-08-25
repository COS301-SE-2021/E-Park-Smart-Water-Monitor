import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Container from "@material-ui/core/Container";
import componentStyles from "assets/theme/components/header.js";
import Stats from "../Stats/Stats";

const useStyles = makeStyles(componentStyles);

const Header = () => {
  const classes = useStyles();

  return (
    <>
      <div className={classes.header}>
        <Container
          maxWidth={false}
          component={Box}
          classes={{ root: classes.containerRoot }}
        >
          <Stats/>
        </Container>
      </div>
    </>
  );
};

export default Header;
