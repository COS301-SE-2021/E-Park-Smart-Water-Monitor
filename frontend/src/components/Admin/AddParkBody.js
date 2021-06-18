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
            <div>
                <br/>
                <label id="name" for="Name">Name</label>
                <br/>
                <input type="text" name="Name" />
                <br/>
            </div>

            <div>
                <br/><br/>
                <label id="lat" htmlFor="Latitude">Latitude</label>
                <br/>
                <input type="text" name="latitude"/>
                <br/><br/><br/>
                <label id="long" htmlFor="Longitude">Longitude</label>
                <br/>
                <input type="text" name="longitude"/>
            </div>
            <br/><br/><br/>
        </>
    );
};

export default AddParkBody;
