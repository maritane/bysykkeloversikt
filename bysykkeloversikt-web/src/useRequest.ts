import useSWR, { SWRConfig, SWRResponse } from "swr";
import axios, {
  AxiosRequestConfig,
  AxiosResponse,
  AxiosError,
  Axios,
} from "axios";

export type GetRequest = AxiosRequestConfig | null;

interface Return<Data, Error> {
  data: Data | undefined;
  isLoading: boolean;
  isError: boolean;
  errorMessage?: string;
}

export default function useRequest<Data = unknown, Error = unknown>(
  path: string
): Return<Data, Error> {
  const request: GetRequest = {
    url: path,
    timeout: 1000,
  };

  const {
    data: response,
    error,
    isValidating,
    mutate,
  } = useSWR<AxiosResponse<Data>, AxiosError<Error>>(
    request && JSON.stringify(request),
    () => axios.request<Data>(request!),
    { refreshInterval: 10000 } // hvert 10 sekund
  );

  var errorMessage;
  if (error?.response) {
    errorMessage = "Feil i returnerte data";
  } else if (error?.request) {
    errorMessage = "Kunne ikke laste data";
  } else {
    errorMessage = `Ukjent feil (${error?.message})`;
  }

  return {
    data: response && response?.data,
    isLoading: !response && !error,
    isError: error !== undefined,
    errorMessage: errorMessage,
  };
}
