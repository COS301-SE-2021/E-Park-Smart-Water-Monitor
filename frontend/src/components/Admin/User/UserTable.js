import React, {useState, useEffect, useContext} from "react";

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
import Modal from "../../Modals/Modal";
import AddUserBody from "./AddUserBody";
import axios from "axios";
import EditUserBody from "./EditUserBody";
import IconButton from "@material-ui/core/IconButton";
import EditIcon from '@material-ui/icons/Edit';

import DeleteIcon from '@material-ui/icons/Delete';
import {Tooltip} from "@material-ui/core";
import AdminContext from "../AdminContext";
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
    // for reloads of the component values
    const [value, setValue] = useState(0);

    const context = useContext(AdminContext)
    const toggleLoading = context.toggleLoading

    const reloadUserTable = () => {
        setValue(value => value+1)
    }

    // on delete of a user
    const removeUser = (id) => {

        const config = {
            headers: { Authorization: `Bearer ${sessionStorage.getItem('token')}` }
        };

        return ()=>{
            toggleLoading()
            axios.post('http://localhost:8080/api/user/deleteUser', {
                id: id
            }, config).then((res)=> {
                toggleLoading()
                reloadUserTable()
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
                        { user.username }
                    </TableCell>
                    <TableCell classes={{ root: classes.tableCellRoot }}
                               style={{verticalAlign:'middle',width:'20%'}}>
                        { user.role }
                    </TableCell>
                    {/*<TableCell classes={{ root: classes.tableCellRoot }}*/}
                    {/*           style={{verticalAlign:'middle',width:'34%'}}>*/}
                    {/*    { user.parkName }*/}
                    {/*</TableCell>*/}
                    <TableCell classes={{ root: classes.tableCellRoot }}
                               style={{verticalAlign:'middle',width:'34%'}}>
                        { user.idNumber }
                    </TableCell>
                    <TableCell classes={{ root: classes.tableCellRoot }}
                               style={{verticalAlign:'middle',width:'34%'}}>
                        { user.cellNumber }
                    </TableCell>
                    <TableCell classes={{ root: classes.tableCellRoot }}
                               style={{verticalAlign:'middle'}}>
                        <Tooltip title="Edit" arrow>
                            <IconButton aria-label="edit"
                                        onClick={() => { setShowEdit(true); setUser(user)}}
                            >
                                <EditIcon />
                            </IconButton>
                        </Tooltip>
                    </TableCell>
                    <TableCell classes={{ root: classes.tableCellRoot }}
                               style={{verticalAlign:'middle'}}>
                        <Tooltip title="Delete" arrow>
                            <IconButton aria-label="delete"
                                        onClick={ removeUser(user.id) }
                            >
                                <DeleteIcon />
                            </IconButton>
                        </Tooltip>
                    </TableCell>
                </TableRow>
            );
            setResponse(m);
        });

    },[value])

    return (
        <>
            <Container
                maxWidth={false}
                component={Box}
                marginTop="-6rem"
                classes={{ root: classes.containerRoot }}

            >
                <Modal  title="Add User" onClose={() => setShowAdd(false)} show={ showAdd }>
                    <AddUserBody reloadUserTable={ reloadUserTable } closeModal={() => setShowAdd(false)} />
                </Modal>

                { user && <Modal title="Edit User" onClose={() => setShowEdit(false)} show={ showEdit }>
                    <EditUserBody userDetails={ user } reloadUserTable={ reloadUserTable } closeModal={()=>{ setShowEdit(false) }}/>
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
                            <TableContainer
                                style={{maxHeight:"300px",overflowY:"auto"}}>
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
                                                Username
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
                                            {/*<TableCell*/}
                                            {/*    classes={{*/}
                                            {/*        root:*/}
                                            {/*            classes.tableCellRoot +*/}
                                            {/*            " " +*/}
                                            {/*            classes.tableCellRootHead,*/}
                                            {/*    }}*/}
                                            {/*>*/}
                                            {/*    Park*/}
                                            {/*</TableCell>*/}
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >
                                                ID Number
                                            </TableCell>
                                            <TableCell
                                                classes={{
                                                    root:
                                                        classes.tableCellRoot +
                                                        " " +
                                                        classes.tableCellRootHead,
                                                }}
                                            >
                                                Cellphone Number
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
