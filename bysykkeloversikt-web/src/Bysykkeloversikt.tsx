import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faBicycle,
  faUnlock,
  IconDefinition,
} from "@fortawesome/free-solid-svg-icons";
import React, { useEffect, useState } from "react";
import { StationResponse, Station } from "./types/types";
import useRequest from "./useRequest";
import { StationRow } from "./StationRow";

export function Bysykkeloversikt() {
  const { data, isLoading, isError, errorMessage } = useRequest<StationResponse, unknown>(
    "/api/stations"
  );

  const [stations, setStations] = useState<Station[]>();
  const [filter, setFilter] = useState<string>("");

  useEffect(() => setStations(data?.data?.stations), [data]);

  if (isLoading) {
    return <div>Data lastes...</div>;
  }
  if (isError) {
    return <div>Det har skjedd noe feil, dessverre. {errorMessage}</div>;
  }

  function oppdaterFilter(e: React.FormEvent<HTMLInputElement>) {
    setFilter(e.currentTarget.value.toLowerCase());
  }

  function filterStation(station: Station): boolean {
    if (filter.length == 0) {
      return true;
    }
    return station.name.toLowerCase().startsWith(filter);
  }

  return (
    <div>
      <label htmlFor="filter">Søk</label>
      <input type="text" id="filter" onChange={oppdaterFilter} />
      <table>
        <tbody>
          <tr key="header">
            <th>Stasjon</th>
            <th>Ledige sykler</th>
            <th>Ledige låser</th>
          </tr>
          {stations
            ?.filter(filterStation)
            .sort((a, b) => a.name.localeCompare(b.name))
            .map((station: Station) => (
              <StationRow station={station} key={station.station_id} />
            ))}
        </tbody>
      </table>
    </div>
  );
}
