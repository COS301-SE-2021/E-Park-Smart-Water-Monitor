import React,{useState} from "react";

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../views/admin/modals/addDevice.css";




const useStyles = makeStyles(componentStyles);

const AddDeviceBody = () => {
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

            <br/><br/>
            <label id="roleLabel" >Park</label><br/>
            <select  id="parks" name="Parks"  >
                <option value="rietvlei">Riet Vlei</option>
            </select>
            <br/><br/>

            <div>
                <br/>
                <label id="lat" htmlFor="Latitude">Latitude</label>
                <br/>
                <input type="text" name="Name"/>
                <br/><br/><br/>
                <label id="long" htmlFor="Longitude">Longitude</label>
                <br/>
                <input type="text" name="Surname"/>
            </div>
            <br/><br/><br/>
        </>
    );
};

export default AddDeviceBody;
