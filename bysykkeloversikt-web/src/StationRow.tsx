import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faBicycle,
  faUnlock,
  IconDefinition,
} from "@fortawesome/free-solid-svg-icons";
import { Station } from "./types/types";

interface StationRowProps {
  station: Station;
}

export function StationRow({ station }: StationRowProps) {
  function getColorForAwailability(num: number) {
    if (num === 0) return "red";
    else if (num < 3) return "orange";
    else return "green";
  }

  function cellWithIcon(num: number, icon: IconDefinition) {
    return (
      <td>
        <FontAwesomeIcon icon={icon} color={getColorForAwailability(num)} />{" "}
        {num}
      </td>
    );
  }

  return (
    <tr>
      <td>{station.name}</td>
      {cellWithIcon(station.num_bikes_available, faBicycle)}
      {cellWithIcon(station.num_docks_available, faUnlock)}
    </tr>
  );
}
