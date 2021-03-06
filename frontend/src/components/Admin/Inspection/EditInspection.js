import React, {useContext, useEffect, useState} from "react";
import {Button, Form} from 'react-bootstrap';
import "../../../assets/css/addDevice.css";
import axios from "axios";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";
import Select from "react-select";

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
        //toggleLoading()
      axios.post('/inspections/setStatus', body, {
          headers: {
              'Authorization': "Bearer " + user.token
          }
          // eslint-disable-next-line no-unused-vars
      }).then((res)=>{
            props.reloadInspectionTable()
          //props.tog()
          // eslint-disable-next-line no-unused-vars
      }).catch( (res)=> {
      });

        //set description
        body = {
            inspectionId: props.inspectionDetails.id,
            description: description,
        }
        axios.post('/inspections/setDescription', body, {
            headers: {
                'Authorization': "Bearer " + user.token
            }
            // eslint-disable-next-line no-unused-vars
        }).then((res)=>{
            props.reloadInspectionTable()
            props.tog()
            toggleLoading()
            // eslint-disable-next-line no-unused-vars
        }).catch( (res)=> {
        });
    }

    return (
        <>
            <Form onSubmit={handleSubmit}>

                <Form.Label>Status</Form.Label>
                <Select required={"required"} className="mb-3" name="park" options={ statusOptions } value={status} onChange={e => setStatus(e)}/>
                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Description</Form.Label>
                    <Form.Control type="text" Required={"required"} value={description} onChange={e => setDescription(e.target.value)}/>
                </Form.Group>

                <Button background-color="primary" variant="primary" type="submit" >
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default EditInspection;
