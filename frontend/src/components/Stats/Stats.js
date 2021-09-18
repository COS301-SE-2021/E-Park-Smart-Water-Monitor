import React, {useContext, useEffect, useState} from "react";
import Grid from "@material-ui/core/Grid";
import InsertChartOutlined from "@material-ui/icons/InsertChartOutlined";
import Box from "@material-ui/core/Box";
import CardStats from "./CardStats";
import axios from "axios";
import {UserContext} from "../../Context/UserContext";
import {AssignmentTurnedIn, Error as ErrorIcon, CheckCircleOutline} from "@material-ui/icons";


const Stats = () => {
    const [numConnected, setNumConnected] = useState(0)
    const [numCritical, setNumCritical] = useState(0)
    const [numFine, setNumFine] = useState(0)
    const [numInspections, setNumInspections] = useState(0)
    const [loaded, setLoaded] = useState(false)
    const [loadedInspections, setLoadedInspections] = useState(false)

    const user = useContext(UserContext)

    useEffect(()=>{
        setNumConnected(0)
        setNumCritical(0)
        setNumFine(0)
        setNumInspections(0)
        setLoaded(false)
        setLoadedInspections(false)

        // get the devices
        axios.get('http://localhost:8080/api/devices/getAllDevices',{
            headers: {
                'Authorization': "Bearer " + user.token
            }
        }).then((res)=>{
            if(res.data)
            {
                const site = res.data.site; // site array

                site.forEach(elem => {
                    // count number connected
                    // eslint-disable-next-line no-unused-vars
                    let a = elem.deviceData.deviceStatus !== "NOT CONNECTED" ? setNumConnected(numConnected => numConnected +1) : ""
                    // count number critical and not critical
                    // eslint-disable-next-line no-unused-vars
                    let b = elem.deviceData.deviceStatus === "CRITICAL" ? setNumCritical(numCritical => numCritical +1) : ""
                    // eslint-disable-next-line no-unused-vars
                    let c = elem.deviceData.deviceStatus === "FINE" ? setNumFine(numFine => numFine +1) : ""
                })
                setLoaded(true)

            }else{
                console.log('res.data null')
            }
        }).catch((res)=>{
            console.log(JSON.stringify(res))
        });

        axios.get('http://localhost:8080/api/inspections/getAllInspections', {
            headers: {
                'Authorization': "Bearer " + user.token
            }
        }).then((res) => {

            if (res.data) {

                if(res.data && res.data.inspections) {
                    // get the inspections for the user logged in
                    let parkIndex = -1;
                    for (let i = 0; i < res.data.parkId.length; i++) {
                        if (res.data.parkId[i] == user.parkID) {
                            parkIndex = i;
                        }
                    }

                    res.data.inspections[parkIndex].forEach((inspection) => {
                        // eslint-disable-next-line no-unused-vars
                        let a = inspection.status !== "DONE" ? setNumInspections(numInspections => numInspections +1) : ""
                    })
                    setLoadedInspections(true)
                }
            }
        })
    }, [])

    return (
        <>
            <div>
            <Grid container>
                <Grid item xl={3} lg={6} xs={12}>
                    <CardStats
                        subtitle="Connected"
                        title={loaded === false ? '...' : numConnected}
                        icon={InsertChartOutlined}
                        color="bgError"
                        footer={
                            <>
                                <Box component="span" whiteSpace="nowrap">
                                    Devices giving readings
                                </Box>
                            </>
                        }
                    />
                </Grid>
                <Grid item xl={3} lg={6} xs={12}>
                    <CardStats
                        subtitle="Critical"
                        title={loaded === false ? '...' : numCritical}
                        icon={ErrorIcon}
                        color="bgWarning"
                        footer={
                            <>
                                <Box component="span" whiteSpace="nowrap">
                                    Devices need attention
                                </Box>
                            </>
                        }
                    />
                </Grid>
                <Grid item xl={3} lg={6} xs={12}>
                    <CardStats
                        subtitle="Fine"
                        title={loaded === false ? '...' : numFine}
                        icon={CheckCircleOutline}
                        color="bgWarningLight"
                        footer={
                            <>
                                <Box component="span" whiteSpace="nowrap">
                                    Devices running smoothly
                                </Box>
                            </>
                        }
                    />
                </Grid>
                <Grid item xl={3} lg={6} xs={12}>
                    <CardStats
                        subtitle="Inspections"
                        title={loadedInspections === false ? '...' : numInspections}
                        icon={AssignmentTurnedIn}
                        color="bgInfo"
                        footer={
                            <>
                                <Box component="span" whiteSpace="nowrap">
                                    Inspections not completed
                                </Box>
                            </>
                        }
                    />
                </Grid>
            </Grid>
            </div>
        </>
    );
};

export default Stats;
