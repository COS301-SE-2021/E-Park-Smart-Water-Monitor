import React, {useContext, useState} from "react";
import {Button, Form} from 'react-bootstrap';
import "../../../assets/css/addDevice.css";
import axios from "axios";
import {UserContext} from "../../../Context/UserContext";
import LoadingContext from "../../../Context/LoadingContext";

const AddInspectionBody = (props) => {

    const [description, setDescription] = useState("")
    const [date, setDate] = useState(null)
    const user = useContext(UserContext)
    const loader = useContext(LoadingContext)
    const toggleLoading = loader.toggleLoading

    const selectDescription = (event) => {
      setDescription(event.target.value)
    }

    const selectDate = (event) => {
      setDate(event.target.value)
    }

    const handleSubmit = (event) => {
      event.preventDefault()

      let body = {
        deviceId: props.device_id,
        dateDue: date,
        description: description,
      }


        toggleLoading()
      axios.post('/inspections/addInspection', body, {
          headers: {
              'Authorization': "Bearer " + user.token
          }
          // eslint-disable-next-line no-unused-vars
      }).then((res)=>{
            props.closeModal()
            toggleLoading()
          // eslint-disable-next-line no-unused-vars
      }).catch( (res)=> {
            toggleLoading()
      });
    }

    return (
        <>
            <Form onSubmit={handleSubmit}>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Description</Form.Label>
                    <Form.Control type="text" Required={"required"} placeholder="Enter a description" onChange={selectDescription}/>
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Due date</Form.Label>
                    <Form.Control type="date" Required={"required"}  placeholder="Enter a due date" onChange={selectDate}/>
                </Form.Group>

                <Button background-color="primary" variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </>
    );
};

export default AddInspectionBody;
