import React, { useEffect, useState } from "react";
import { SERVER_URL } from "../constants";
import { DataGrid, GridToolbarContainer, GridToolbarExport } from "@mui/x-data-grid";
import { Snackbar } from "@mui/material";
import { gridClasses } from "@mui/system";
import AuthContext from "../AuthContext";

function CarlistUser() {
    const authContext = React.useContext(AuthContext);
    console.log(authContext);

    const [open, setOpen] = useState(false);

    const [cars, setCars] = useState([]);

    const columns = [
        {field: 'brand', headerName: 'Brand', width: 200},
        {field: 'model', headerName: 'Model', width: 200},
        {field: 'color', headerName: 'Color', width: 200},
        {field: 'year', headerName: 'Year', width: 150},
        {field: 'price', headerName: 'Price', width: 150}
    ];

    const token = sessionStorage.getItem("jwt");
    
    const fetchCars = () => {
        fetch(SERVER_URL + 'api/cars', {
            headers: {'Authorization' : token}
        })
        .then(response => response.json())
        .then(data => setCars(data._embedded.carEntities))
        .catch(err => console.error(err));
    }

    useEffect(() => {
        fetchCars();
    });


    //Add CSV export function
    function CustomToolbar() {
        return (
            <GridToolbarContainer className={gridClasses.toobarContainer}>
                    <GridToolbarExport/>
                </GridToolbarContainer>
        );
    }

    return(
        <React.Fragment>
            <div style={{ height: 500, width: '100%' }}>
            <DataGrid
                rows={cars}
                columns={columns}
                getRowId={row => row._links.self.href}
                components={{ Toolbar: CustomToolbar }}
            />
            <Snackbar
                open={open}
                autoHideDuration={2000}
                onClose={() => setOpen(false)}
                message="Car deleted"
            />
            </div>
        </React.Fragment>
    );
}

export default CarlistUser;