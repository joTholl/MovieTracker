import {useEffect, useState} from "react";
import type {Movie} from "../types/Movie.ts";
import axios from "axios";

export function useMovie(url: string) {

    const [Movies, setMovies] = useState<Movie[]>([])

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