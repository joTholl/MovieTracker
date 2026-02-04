import {useNavigate} from "react-router-dom";
import Login from "./components/Login.tsx";

export default function Navbar() {

    const nav = useNavigate()

    function navHome() {
        nav("/")
    }

    function navTodo() {
        nav("/movie")
    }

    function navDoing() {
        nav("/series")
    }

    return (
        <>
            <div className="navbar-div">
                <button className="navbar-btn" onClick={navHome}>Home</button>
                <button className="navbar-btn" onClick={navTodo}>Movies</button>
                <button className="navbar-btn" onClick={navDoing}>Series</button>
                <Login/>
            </div>
        </>
    )
}