import {useEffect, useState} from "react";
import type { MovieOut } from "../types/MovieOut.ts";
import axios from "axios";

export function useMovie(url: string) {

    const [Movies, setMovies] = useState<MovieOut[]>([])

    function getMovies() {
        axios.get(url)
            .then(res => setMovies(res.data))
            .catch(err => console.log(err));
    }

    useEffect(() => {
        getMovies()
    }, []);

    return { Movies };
}