import { Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";
import React, { useState } from "react";

function EditCar(props) {
    const [open, setOpen] = useState(false);
    const [car, setCar] = useState({
        brand: '', model: '', color: '', year: '', price: ''
    });

    //Open the modal form and update the car state
    const handleClickOpen = () => {
        setCar({
            brand: props.data.row.brand,
            model: props.data.model,
            color: props.data.row.color,
            year: props.data.row.year,
            price: props.data.row.price
        })
        setOpen(true);
    };

    //Close the modal form
    const handleClose = () => {
        setOpen(false);
    };

    const handleChange = (event) => {
        setCar({...car, [event.target.name]: event.target.value});
    }

    //Update car and close modal form
    const handleSave = () => {
        props.updateCar(car, props.data.id);
        handleClose();
    }

    return(
        <div>
            <button onClick={handleClickOpen}>Edit</button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Edit car</DialogTitle>
                <DialogContent>
                    <input placeholder="Brand" name="brand" value={car.brand}onChange={handleChange}/><br/>
                    <input placeholder="Model" name="model" value={car.model}onChange={handleChange}/><br/>
                    <input placeholder="Color" name="color" value={car.color}onChange={handleChange}/><br/>
                    <input placeholder="Year" name="year" value={car.year}onChange={handleChange}/><br/>
                    <input placeholder="Price" name="price" value={car.price}onChange={handleChange}/><br/>
                </DialogContent>
                <DialogActions>
                    <button onClick={handleClose}>Cancel</button>
                    <button onClick={handleSave}>Save</button>
                </DialogActions>
            </Dialog>
            
        </div>
    );
}

export default EditCar;