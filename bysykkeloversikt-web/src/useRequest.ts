import useSWR, {SWRConfig, SWRResponse} from "swr";
import axios, { AxiosRequestConfig, AxiosResponse, AxiosError, Axios} from "axios";

export type GetRequest = AxiosRequestConfig | null

interface Return<Data, Error> {
    data : Data | undefined,
    isLoading: boolean,
    isError: boolean
}

export default function useRequest<Data = unknown, Error = unknown>(
    path: string
): Return<Data, Error> {
    const request: GetRequest = {
        url: path
    }

    const {data: response, error, isValidating, mutate} = useSWR<AxiosResponse<Data>, AxiosError<Error>>(
        request && JSON.stringify(request), 
        () => axios.request<Data>(request!)
    )

    return {
        data: response && response?.data,
        isLoading: !response && !error,
        isError: error != null
    }
}