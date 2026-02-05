import {useEffect, useState} from "react";
import axios from "axios";
import type {SeriesOut} from "../types/SeriesOut.ts";

export function useSeries(url: string) {

    const [Series, setSeries] = useState<SeriesOut[]>([])

    function getSeries() {
        axios.get(url)
            .then(res => setSeries(res.data))
            .catch(err => console.log(err));
    }

    useEffect(() => {
        getSeries()
    }, []);

    return { Series };
}