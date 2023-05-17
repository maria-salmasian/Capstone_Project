import axios, { AxiosRequestConfig } from 'axios';

import { TOKEN_STORAGE_KEY } from '@/constants';

async function fetch<Type>(
  path: string,
  options?: AxiosRequestConfig
): Promise<Type> {
  const { method = 'GET', data = null, headers = {} } = options || {};

  const defaultHeaders: { [x: string]: string } = {
    'Content-Type': 'application/json',
  };

  const token = localStorage.getItem(TOKEN_STORAGE_KEY);

  if (token) {
    defaultHeaders['Authorization'] = token;
  }

  const requestParams = {
    url: `${process.env.NEXT_PUBLIC_API_URL}${path}`,
    method,
    headers: {
      ...defaultHeaders,
      ...headers,
    },
    data,
  };

  if (method === 'get') {
    delete requestParams.data;
  }

  try {
    const response = await axios(requestParams);

    return response.data;
  } catch (err) {
    console.error('>>> API Error ', err);
    throw err;
  }
}

export default fetch;
