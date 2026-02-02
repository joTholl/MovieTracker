import {useNavigate} from "react-router-dom";

export default function Navbar() {

    const nav = useNavigate()

    function navHome() {
        nav("/")
    }

    function navTodo() {
        nav("/movie")
    }

    function navDoing() {
        nav("/doing")
    }

    function navDone() {
        nav("/done")
    }

    return (
        <>
            <div className="navbar-div">
                <button className="navbar-btn" onClick={navHome}>Home</button>
                <button className="navbar-btn" onClick={navTodo}>Todos</button>
                <button className="navbar-btn" onClick={navDoing}>Doing</button>
                <button className="navbar-btn" onClick={navDone}>Done</button>
            </div>
        </>
    )
}