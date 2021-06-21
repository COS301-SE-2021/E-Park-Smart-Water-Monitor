import React,{useState, useEffect} from "react";

import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Typography from "@material-ui/core/Typography";
import componentStyles from "assets/theme/views/admin/admin";
import Button from "@material-ui/core/Button";
import Modal from "../Modals/Modal";
import AddUserBody from "./AddUserBody";
import axios from "axios";
import EditUserBody from "./EditUserBody";
// import disableScroll from 'disable-scroll';

// import AdminModal from 'admin-modal'


const useStyles = makeStyles(componentStyles);

const UserTable = () => {
    const classes = useStyles();
    const theme = useTheme();
    const [showAdd, setShowAdd] = useState(false);
    const [showEdit, setShowEdit] = useState(false);
    const [response, setResponse] = useState([]);
    const [user, setUser] = useState({});

    // on delete of a user
    const removeUser = (id) => {
        return ()=>{
            axios.post('http://localhost:8080/api/user/deleteUser', {
                id: id
            }).then((res)=> {
                window.location.reload()
            })
        }
    }


    useEffect(() => {
        // get all users
        axios.get('http://localhost:8080/api/user/getAllUsers').then((res)=>{
            // console.log("res: "+JSON.stringify(res))
            const m = res.data.allUsers.map((user) =>
                <TableRow key={ user.id } >
                    <TableCell
                        classes={{
                            root:
                                classes.tableCellRoot +
                                " " +
                                classes.tableCellRootBodyHead,
                        }}
                        scope="row"
                        style={{verticalAlign:'middle',width:'25%'}}
                    >
                        { `${user.name} ${user.surname}` }
                    </TableCell>
                    <TableCell classes={{ root: classes.tableCellRoot }}
                               style={{verticalAlign:'middle',width:'20%'}}>
                        { user.role }
                    </TableCell>
                    <TableCell classes={{ root: classes.tableCellRoot }}
                               style={{verticalAlign:'middle',width:'34%'}}>
                        { user.park }
                    </TableCell>
                    <TableCell classes={{ root: classes.tableCellRoot }}
                               style={{verticalAlign:'middle'}}>
                        <Button
                            size="small"
                            onClick={() => { setShowEdit(true); setUser(user)}}
                        >
                            Edit
                        </Button>
                    </TableCell>
                    <TableCell classes={{ root: classes.tableCellRoot }}
                               style={{verticalAlign:'middle'}}>
                        <Button
                            size="small"
                            onClick={ removeUser(user.id) }
                        >
                            Remove
                        </Button>
                    </TableCell>
                </TableRow>
            );
            setResponse(m);
        });

    },[])

    return (
        <>
            <Container
                maxWidth={false}
                component={Box}
                marginTop="-6rem"
                classes={{ root: classes.containerRoot }}
            >
                <Modal  title="Add User" onClose={() => setShowAdd(false)} show={ showAdd }>
                    <AddUserBody/>
                </Modal>

                { user && <Modal title="Edit User" onClose={() => setShowEdit(false)} show={ showEdit }>
                    <EditUserBody userDetails={ user } closeModal={()=>{ setShowEdit(false) }}/>
                </Modal> }

                <Grid container component={Box} marginTop="3rem">
                    <Grid
                        item
                        xs={12}
                        xl={12}
                        component={Box}
                        classes={{ root: classes.gridItemRoot }}
                    >
                        <Card classes={{
                                root: classes.cardRoot,
                            }}>
                            <CardHeader
                                subheader={
                                    <Grid
                                        container
                                        component={Box}
                                        alignItems="center"
                                        justifyContent="space-between"
                                    >
                                        <Grid item xs="auto">
                                            <Box
                                                component={Typography}
                                                variant="h2"
                                                marginBottom="0!important"
                                            >
                                                Users
                                            </Box>
                                        </Grid>
                                        <Grid item xs="auto">
                                            <Box
                                                justifyContent="flex-end"
                                                display="flex"
                                                flexWrap="wrap"
                                            >
                                            </Box>
                                        </Grid>
                                    </Grid>
                                }
                                classes={{ root: classes.cardHeaderRoot }}

                            >
                                </CardHeader>
                            <TableContainer>
                                <Box
                                    component={Table}
                                    alignItems="center"
                                    marginBottom="0!important"
                                >
                                    <TableHead>
                                        <TableRow style={{background: 'rgb(243 243 243)'}}>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >
                                                Name
                                            </TableCell>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >
                                                Role
                                            </TableCell>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >
                                                Park
                                            </TableCell>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >

                                            </TableCell>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >

                                            </TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>


                                        {response}


                                    </TableBody>
                                </Box>
                            </TableContainer>
                        </Card>
                    </Grid>
                    <Grid
                        item
                        xs={6}
                        xl={10}
                        component={Box}
                        marginBottom="3rem!important"
                        classes={{ root: classes.gridItemRoot }}
                    >
                    </Grid>
                    <Grid
                        item
                        justify="end"
                        xs={6}
                        xl={2}
                        component={Box}
                        marginBottom="3rem!important"
                        classes={{ root: classes.gridItemRoot }}
                        style={{ display: "flex", justifyContent: "flex-end", marginTop: "5px"}}>
                        <Button
                            variant="contained"
                            color="primary"
                            size="medium"
                            style={{width:'200px'}}
                            onClick={() => setShowAdd(true)}
                        >
                            Add User
                        </Button>
                    </Grid>
                </Grid>

            </Container>
        </>
    );
};

export default UserTable;
