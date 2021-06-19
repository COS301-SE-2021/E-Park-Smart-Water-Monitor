import React,{useState} from "react";

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../assets/css/addDevice.css";

const useStyles = makeStyles(componentStyles);

const AddParkBody = () => {
    const classes = useStyles();
    const theme = useTheme();


    return (
        <>

        </>
    );
};

export default AddParkBody;
