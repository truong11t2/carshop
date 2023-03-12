import React, { useState } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Divider, TextField } from "@mui/material";
import { Stack } from "@mui/system";
import UploadFile from "./upload/UploadFile";

function AddCar(props) {
    const [open, setOpen] = useState(false);
    const [car, setCar] = useState({
        brand: '',
        model: '',
        color: '',
        year: '',
        fuel: '',
        price: ''
    });

    //Open the modal form
    const handleClickOpen = () => {
        setOpen(true);
    };

    //Close modal form
    const handleClose = () => {
        setOpen(false);
    };

    const handleChange = (event) => {
        setCar({...car, [event.target.name]: event.target.value});
    };

    //Save car and close modal form
    const handleSave = () => {
        props.addCar(car);
        handleClose();
    }

    return(
        <div>
            <Button variant="contained" onClick={handleClickOpen}>New Car</Button>
            {/* <button onClick={handleClickOpen}>New Car</button>*/}
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>New Car</DialogTitle>
                <DialogContent>
                    {/*<input placeholder="Brand" name="brand" value={car.brand} onChange={handleChange}/><br/>
                    <input placeholder="Model" name="model" value={car.model} onChange={handleChange}/><br/>
                    <input placeholder="Color" name="color" value={car.color} onChange={handleChange}/><br/>
                    <input placeholder="Year" name="year" value={car.year} onChange={handleChange}/><br/>
                    <input placeholder="Price" name="price" value={car.price} onChange={handleChange}/><br/>*/}
                    <Stack spacing={2} mt={1}>
                        <TextField label="Brand" name="brand" autoFocus variant="standard" value={car.branch} onChange={handleChange}/>
                        <TextField label="Model" name="model" autoFocus variant="standard" value={car.model} onChange={handleChange}/>
                        <TextField label="Color" name="color" autoFocus variant="standard" value={car.color} onChange={handleChange}/>
                        <TextField label="Year" name="year" autoFocus variant="standard" value={car.year} onChange={handleChange}/>
                        <TextField label="Price" name="price" autoFocus variant="standard" value={car.price} onChange={handleChange}/>
                        {/*Adding some space */}
                        <p></p>
                        <Divider textAlign="center">Images</Divider>
                        {/*Calling upload function */}
                        <UploadFile />
                        {/* <label className="btn btn-default">
                            <input type="file" onChange={selectFile} />
                        </label>

                        <button
                            className="btn btn-success"
                            disabled={!selectedFiles}
                            onClick={<UploadFile selectedFiles={selectedFiles}/>}
                        >
                            Upload
                        </button> */}
                    </Stack>
                </DialogContent>
                <DialogActions>
                    {/*<button onClick={handleClose}>Cancel</button>
                    <button onClick={handleSave}>Save</button>*/}
                    <Button variant="contained" onClick={handleClose}>Cancel</Button>
                    <Button variant="contained" onClick={handleSave}>Save</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default AddCar;