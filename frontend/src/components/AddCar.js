import React, { useState } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Divider, TextField } from "@mui/material";
import { Stack } from "@mui/system";
import UploadFile from "./upload/UploadFile";
import UploadService from "./upload/UploadService";

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

    const [fileInfo, setFileInfo] = useState([]);
    const token = sessionStorage.getItem("jwt");

    //Open the modal form
    const handleClickOpen = () => {
        setOpen(true);
    };

    //Close modal form
    const handleClose = () => {
        remove();
        setOpen(false);
    };

    const handleChange = (event) => {
        setCar({...car, [event.target.name]: event.target.value});
    };

    const remove = () => {
        fileInfo &&
            fileInfo.map((file, index) => {
                //find the position of uuid. It follow 'download/' string. Search for 'd' character
                //then add the length of 'download/' string to get the position of uuid
                let pos = file.url.indexOf('download/') + 9;
                //Get uuid from link
                let uuid = file.url.substring(pos, file.url.length);
                return UploadService.deleteFile(token, uuid);
            });
            //todo: Remove file from list file
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
                        <UploadFile fileInfo={fileInfo} setFileInfo={setFileInfo} remove={remove}/>
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