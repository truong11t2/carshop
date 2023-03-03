import React, { useEffect, useState } from "react";
import { SERVER_URL } from "../constants";
import { DataGrid, GridToolbarContainer, GridToolbarExport } from "@mui/x-data-grid";
import { IconButton, Snackbar, Stack } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import AddCar from "./AddCar";
import EditCar from "./EditCar";
import { gridClasses } from "@mui/system";
import AuthContext from "../AuthContext";

function CarlistAdmin() {
    const authContext = React.useContext(AuthContext);
    console.log(authContext);

    const [open, setOpen] = useState(false);

    const [cars, setCars] = useState([]);

    const columns = [
        {field: 'brand', headerName: 'Brand', width: 200},
        {field: 'model', headerName: 'Model', width: 200},
        {field: 'color', headerName: 'Color', width: 200},
        {field: 'year', headerName: 'Year', width: 150},
        {field: 'price', headerName: 'Price', width: 150},
        {
            field: '_links.car.href',
            headerName: '',
            sortable: false,
            filterable: false,
            renderCell: row => <EditCar data={row} updateCar={updateCar}/>
        },
        {
            field: '_links.self.href',
            headerName: '',
            sortable: false,
            filterable: false,
            renderCell: row => 
            /*<button
                onClick={() => onDelClick(row.id)}>Delete
            </button>*/
            <IconButton onClick={() => onDelClick(row.id)}>
                <DeleteIcon color="error"/>
            </IconButton>
        }
    ];

    const onDelClick = (url) => {
        if(window.confirm("Are your sure to delete?")) {
            fetch(url,
            {
                method: 'DELETE',
                headers: {'Authorization' : token}
            })
            .then(response => {
                if(response.ok) {
                    fetchCars();
                    setOpen(true);
                } else {
                    alert('Something went wrong!');
                }
        })
        .catch(err => console.error(err))
        }
    }

    const token = sessionStorage.getItem("jwt");
    
    const fetchCars = () => {
        //Read the token from the session storage
        //and include it to Authorization header
        const token = sessionStorage.getItem("jwt");

        fetch(SERVER_URL + 'api/cars', {
            headers: {'Authorization' : token}
        })
        .then(response => response.json())
        .then(data => setCars(data._embedded.carEntities))
        .catch(err => console.error(err));
    }

    useEffect(() => {
        fetchCars();
    }, []);

    //Add a new car
    const addCar = (car) => {
        fetch(SERVER_URL + 'api/cars',
        {
            method: 'POST',
            headers: { 'Content-Type':'application/json',
            'Authorization' : token },
            body: JSON.stringify(car)
        })
        .then(response => {
            if(response.ok) {
                fetchCars();
            } else {
                alert('Something went wrong!');
            }
        })
        .catch(err => console.error(err))
    }

    //Update car
    const updateCar = (car, link) => {
        fetch(link,
            {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json',
                'Authorization' : token },
                body: JSON.stringify(car)
            })
            .then(response => {
                if(response.ok) {
                    fetchCars();
                } else {
                    alert('Something went wrong!');
                }
            })
            .catch(err => console.error(err))
    }

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
            {/*Enable if the role are ADMIN or MODERATOR*/}
            <Stack mt={2} mb={2}>
                <AddCar addCar={addCar} />
            </Stack>
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

export default CarlistAdmin;