import {Route, Routes} from "react-router-dom";
import Movie from "./components/Movie.tsx";

function App() {


  return (
    <>
      <h1>Hello World!</h1>
        <Routes>
            <Route path="/movie" element={<Movie />}></Route>
        </Routes>
    </>
  )
}

export default App
