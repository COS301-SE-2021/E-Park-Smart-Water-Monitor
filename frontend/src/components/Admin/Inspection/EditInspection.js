import React, {useContext, useEffect, useState} from "react";
import {Button, Form} from 'react-bootstrap';

import { makeStyles } from "@material-ui/core/styles";
import componentStyles from "assets/theme/views/admin/admin";
import "../../../assets/css/addDevice.css";
import axios from "axios";
import AdminContext from "../AdminContext";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";
import Select from "react-select";

const useStyles = makeStyles(componentStyles);

const statusOptions = [
    { value: "NOT STARTED", label: "Not Started" },
    { value: "DONE", label: "Done" }
]

const EditInspection = (props) => {

    const [status, setStatus] = useState({})
    const [description, setDescription] = useState("")

    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading

    const selectDescription = (event) => {
      setDescription(event.target.value)
    }

    useEffect(() => {
        if (props){
            let oldStatus = props.inspectionDetails.status
            for(let i =0; i< statusOptions.length; i++){
                if(oldStatus == statusOptions[i].value){
                    setStatus(statusOptions[i])
                }
            }
            setDescription(props.inspectionDetails.description)
        }
    },[])


    const handleSubmit = (event) => {
        toggleLoading()
      event.preventDefault()

      //set status
      var body = {
        inspectionId: props.inspectionDetails.id,
        status: status.value,
      }
      console.log("body: ", body)
        //toggleLoading()
      axios.post('http://localhost:8080/api/inspections/setStatus', body, {
          headers: {
              'Authorization': "Bearer " + user.token
          }
      }).then((res)=>{
            console.log(res)
            //props.tog()
      }).catch( (res)=> {
            console.log(JSON.stringify(res))
      });

        //set description
        var body = {
            inspectionId: props.inspectionDetails.id,
            description: description,
        }
        console.log("body: ", body)
        axios.post('http://localhost:8080/api/inspections/setDescription', body, {
            headers: {
                'Authorization': "Bearer " + user.token
            }
        }).then((res)=>{
            console.log(res)
            //props.reloadInspectionTable()
            props.tog()
            toggleLoading()
        }).catch( (res)=> {
            console.log(JSON.stringify(res))
        });
    }

    return (
        <>
            <Form onSubmit={handleSubmit}>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Description</Form.Label>
                    <Form.Control type="text" Required={"required"} value={description} onChange={e => setDescription(e.target.value)}/>
                </Form.Group>
                <Form.Label>Status</Form.Label>
                <Select required={"required"} className="mb-3" name="park" options={ statusOptions } value={status} onChange={e => setStatus(e)}/>
                <Button background-color="primary" variant="primary" type="submit" >
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default EditInspection;
