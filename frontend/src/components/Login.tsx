import axios from "axios";
import {useEffect} from "react";

export default function Login() {
    function loginUser() {
        const host: string = window.location.host === "localhost:5173" ? "http://localhost:8080" : window.location.origin;
        window.open(host + "/oauth2/authorization/github", "_self")
    }
    function logoutUser() {
        const host: string = window.location.host === "localhost:5173" ? "http://localhost:8080" : window.location.origin;
        window.open(host + "/logout", "_self")
    }

    const loadUser = () => {
        axios.get("/api/auth").then((response) => console.log(response.data))
    }

    useEffect(() => {
        loadUser()
    },[]);

    return (
    <>
        <button className="navbar-btn" onClick={loginUser}>Login</button>
        <button className="navbar-btn" onClick={logoutUser}>Logout</button>
    </>
    )
}