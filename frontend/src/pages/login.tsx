import { FC, useEffect, useState } from 'react';

import { useRouter } from 'next/router';

import Spinner from '@/components/spinner';
import { useAuth } from '@/context/auth';
import { IAppState } from '@/interfaces/app-state';

const LoginPage: FC = () => {
  const [status, setStatus] = useState<IAppState>('pending');

  const { login } = useAuth();

  const router = useRouter();

  useEffect(() => {
    if (router.query.code && status === 'pending') {
      setStatus('loading');

      login(`${router.query.code}`)
        .then(() => {
          setStatus('success');
        })
        .catch(() => {
          setStatus('error');
        });
    }
  }, [router]);

  return (
    <div className="flex min-h-screen w-full items-center justify-center">
      {status === 'pending' || (status === 'loading' && <Spinner />)}
      {status === 'error' && <div>Something went wrong. Please try later</div>}
    </div>
  );
};

export default LoginPage;
