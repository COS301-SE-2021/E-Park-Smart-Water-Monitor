import React,{useState} from "react";

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../views/admin/modals/addUser.css";




const useStyles = makeStyles(componentStyles);

const AddUserBody = () => {
    const classes = useStyles();
    const theme = useTheme();

    return (
        <>
            <div>
                <br/>
                <label id="name" for="Name">Name</label>
                <br/>
                <input type="text" name="Name" />
                <br/><br/>
                <label id="surname" for="Surname">Surname</label>
                <br/>
                <input type="text" name="Surname" />
            </div>

            <br/><br/>
            <label id="roleLabel" >Role</label>
            <div id="role" >
                <input id="ranger" class="radio" type="radio" name="roles"  value="Ranger"/>Ranger<br/>
                <input id="fe" class="radio" type="radio"  name="roles" value="Field Engineer"/>Field Engineer<br/>
                <input id="admin" class="radio" type="radio" name="roles" value="Admin"/>Admin<br/><br/><br/>
            </div>
            <label id="roleLabel" >Park</label><br/>
            <select  id="parks" name="Parks"  >
                <option value="rietvlei">Riet Vlei</option>
            </select>
            <br/><br/><br/>
        </>
    );
};

export default AddUserBody;
