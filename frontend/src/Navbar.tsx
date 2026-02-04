import {useNavigate} from "react-router-dom";

export default function Navbar() {

    const nav = useNavigate()

    function navHome() {
        nav("/")
    }

    function navMovies() {
        nav("/movie")
    }

    function navSeries() {
        nav("/series")
    }


    return (
        <>
            <div className="navbar-div">
                <button className="navbar-btn" onClick={navHome}>Home</button>
                <button className="navbar-btn" onClick={navSeries}>Series</button>
                <button className="navbar-btn" onClick={navMovies}>Movies</button>
            </div>
        </>
    )
}