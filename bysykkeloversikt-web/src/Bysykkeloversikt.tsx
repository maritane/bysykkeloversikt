import React, { useEffect, useState } from "react";
import { StationResponse, Station } from "./types/types";
import useRequest from "./useRequest";

export function Bysykkeloversikt() {
    const {data, isLoading, isError } = useRequest<StationResponse, undefined>("/api/stations")

    const[stations, setStations] = useState<Station[]>()

    useEffect(() => setStations(data?.data?.stations), [data])

    if (isLoading) {
        return <div>Data lastes...</div>
    }
    if (isError) {
        console.log("Error");
        return <div>Det har skjedd noe feil, dessverre</div>
    }

    return <div>
        <h1>Bysykkelstasjoner</h1>
        <ul>
            {stations?.map((it: Station) => <li>{it.name + " Ledige sykler: " + it.num_bikes_available + " Ledige låser" + it.num_docks_available}</li>)}
        </ul>
    </div>
}
