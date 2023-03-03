import React, { useState } from "react";
import { SERVER_URL } from "../constants";
import { Button, Snackbar, Stack, TextField } from "@mui/material";
import CarlistAdmin from "./CarlistAdmin";
import CarlistModerator from "./CarlistModerator";
import CarlistUser from "./CarlistUser";
//import AuthContext from "../AuthContext";

let roles = [];

function Login() {
    const [user, setUser] = useState({
        username: '',
        password: ''
    });

    const [open, setOpen] = useState(false);

    const [isAuthenticated, setAuth] = useState(false);
    const handleChange = (event) => {
        setUser({...user, [event.target.name] : event.target.value});
    }

    const login = async () => {
        const response = 
        fetch(SERVER_URL + 'auth/signin', {
            method: 'POST',
            headers: { 'Content-Type':'application/json' },
            body: JSON.stringify(user)
        })
        .then(res => res.json())
        .then(data => JSON.stringify(data))
        .catch(err => console.error(err));

        const strRes = await response;
        const objRes = JSON.parse(strRes);
        //const roles = objRes.roles;
        roles = objRes.roles.slice();
        console.log(roles);

        if(objRes !== null) {
            sessionStorage.setItem("jwt", objRes.type + ' ' + objRes.token);
            setAuth(true);
        } else {
            setOpen(true);
        }
    }

    const role = getRole(...roles);

    const logout = () => {
        sessionStorage.removeItem("jwt");
        setAuth(false);
    }

    if(isAuthenticated) {
        console.log('login successfully with role: '+ role);
        //return <Carlist />
        //upload file anh, tao ra nut bam mua hang, tao ra paypal

        // <AuthContext.Provider value={(role)}>
        //     <CarlistAdmin />
        // </AuthContext.Provider>);

        if(role === "ROLE_ADMIN") { return (<CarlistAdmin />); }
        else if (role === "ROLE_MODERATOR") { return (<CarlistModerator />); }
        else return (<CarlistUser />)

    } else {
        return(
            <div>
                <Stack spacing={2} alignItems='center' mt={2} width={400}>
                    <TextField name="username" label="Username" onChange={handleChange} />
                    <TextField name="password" label="Password" onChange={handleChange} />
                    <Button variant="outlined" color="primary" onClick={login}>Login</Button>
                </Stack>
                <Snackbar open={open} autoHideDuration={3000} onClose={() => setOpen(false)}
                message="Login failed: Check your username and password" />
            </div>
        )
    };
}

function getRole(...roles) {
    let role;
    role = roles.find((element) => 
        "ROLE_ADMIN".localeCompare(element) === 0
    )
    if (role === "ROLE_ADMIN") return role;

    role = roles.find((element) => 
        "ROLE_MODERATOR".localeCompare(element) === 0
    )
    if (role === "ROLE_MODERATOR") return role;

    return "ROLE_USER";
}

export default Login;