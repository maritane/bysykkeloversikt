import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBicycle, faUnlock, IconDefinition } from "@fortawesome/free-solid-svg-icons";
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

    function getColorForAwailability(num: number) {
        if (num === 0) return "red"
        if (num < 3) return "orange"
        else return "green"
    }

    function rowWithIcon(num: number, icon: IconDefinition) {
        return <td><FontAwesomeIcon icon={icon} color={getColorForAwailability(num)}/> {num}</td>
    }

    function stationRow(station: Station) {
        return <tr key={station.station_id}>
            <td>{station.name}</td>
            {rowWithIcon(station.num_bikes_available, faBicycle)}
            {rowWithIcon(station.num_docks_available, faUnlock)}
        </tr>
    }

    return <div>
        <h1>Bysykkelstasjoner i Oslo</h1>
        <table>
        <tr>
            <th>Stasjon</th>
            <th>Ledige sykler</th>
            <th>Ledige låser</th>
        </tr>
        {stations?.sort((a, b) =>a.name.localeCompare(b.name)).map((station: Station) => stationRow(station))}
        </table>
    </div>
}
