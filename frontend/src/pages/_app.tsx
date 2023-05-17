import type { AppProps } from 'next/app';
import Head from 'next/head';

import '@/styles/global.scss';

import { AuthContextProvider } from '@/context/auth';

export default function App({ Component, pageProps }: AppProps) {
  return (
    <AuthContextProvider>
      <Head>
        <link rel="icon" type="image/x-icon" href="/favicon.ico" />
        <title>Capstone</title>
      </Head>
      <Component {...pageProps} />
    </AuthContextProvider>
  );
}
