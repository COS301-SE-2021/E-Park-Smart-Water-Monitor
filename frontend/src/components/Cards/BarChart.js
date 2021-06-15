import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import { Bar } from "react-chartjs-2";

// core components
import componentStyles from "assets/theme/components/card-stats.js";
import CardHeader from "@material-ui/core/CardHeader";

import {
    chartExample2,
} from "variables/charts.js";


const useStyles = makeStyles(componentStyles);

function BarChart() {
  const classes = useStyles();
  const theme = useTheme();

  

  return (
    <>
      <Card classes={{ root: classes.cardRoot }}>
        <CardHeader
            title={
              <Box component="span" color={theme.palette.gray[600]}>
                Inspections
              </Box>
            }
            subheader="Water Refills"
            classes={{ root: classes.cardHeaderRoot }}
            titleTypographyProps={{
              component: Box,
              variant: "h6",
              letterSpacing: ".0625rem",
              marginBottom: ".25rem!important",
              classes: {
                root: classes.textUppercase,
              },
            }}
            subheaderTypographyProps={{
              component: Box,
              variant: "h2",
              marginBottom: "0!important",
              color: "initial",
            }}
        ></CardHeader>
        <CardContent>
          <Box position="relative" height="350px">
            <Bar
                data={chartExample2.data}
                options={chartExample2.options}
            />
          </Box>
        </CardContent>
      </Card>
    </>
  );
}

export default BarChart;
