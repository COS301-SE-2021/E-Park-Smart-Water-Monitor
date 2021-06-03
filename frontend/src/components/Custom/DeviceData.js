import React, { useState, useEffect } from 'react';
import axios from 'axios';
// import { makeStyles } from "@material-ui/core/styles";
// import componentStyles from "../../assets/theme/views/admin/dashboard";
// import TableCell from "@material-ui/core/TableCell";
// import TableRow from "@material-ui/core/TableRow";

export default function DeviceData() {
    const [response, setResponse] = useState({});
    // const useStyles = makeStyles(componentStyles);
    // const classes = useStyles();

    useEffect(() => {
        fetchResponse();
        console.log(response);
    }, []);

    const fetchResponse = () => {
        axios
            .post('http://localhost:8080/api/park/getParkWaterSites',
                {parkId: "40844ac0-b8f7-4a89-a9b9-285f0c097d49"})
            .then(res => setResponse(res))
    };

    return (
        <>
        {/*    response?.map((values)=>(*/}
        {/*        <TableRow>*/}
        {/*        <TableCell*/}
        {/*        classes={{*/}
        {/*          root:*/}
        {/*          classes.tableCellRoot +*/}
        {/*          " " +*/}
        {/*          classes.tableCellRootBodyHead,*/}
        {/*        }}*/}
        {/*        component="th"*/}
        {/*        variant="head"*/}
        {/*        scope="row"*/}
        {/*        >*/}
        {/*          {values.deviceName}*/}
        {/*        </TableCell>*/}
        {/*        <TableCell classes={{ root: classes.tableCellRoot }}>*/}
        {/*          {values.deviceData.latitude}*/}
        {/*        </TableCell>*/}
        {/*        <TableCell classes={{ root: classes.tableCellRoot }}>*/}
        {/*          {values.deviceData.longitude}*/}
        {/*        </TableCell>*/}
        {/*        <TableCell classes={{ root: classes.tableCellRoot }}>*/}
        {/*          {values.deviceData.deviceStatus}*/}
        {/*        </TableCell>*/}
        {/*        <TableCell classes={{ root: classes.tableCellRoot }}>*/}
        {/*          {values.deviceData.battery}*/}
        {/*        </TableCell>*/}
        {/*        </TableRow>*/}
        {/*))*/}
        </>
    );
}