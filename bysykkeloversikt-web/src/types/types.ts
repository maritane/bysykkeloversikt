export interface Station {
    station_id: string,
    name: string,
    lat?: number,
    lon?: number,
    address: string,
    last_reported: number,
    num_bikes_available: number,
    num_docks_available: number,
    capacity?: number
}

export interface StationResponse {
    last_updated: number,
    ttl: number,
    version: string,
    data: StationList
}

interface StationList {
    stations: Station[]
}

