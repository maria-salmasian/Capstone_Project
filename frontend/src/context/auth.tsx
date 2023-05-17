import React, { ReactNode, useContext, useEffect, useState } from 'react';

import { useRouter } from 'next/router';

import { TOKEN_STORAGE_KEY } from '@/constants';
import { IRole } from '@/interfaces/role';
import { IUser } from '@/interfaces/user';
import fetch from '@/utils/fetch';

type PropsAuthContextProvider = {
  children: ReactNode;
};

type AuthTypes = {
  role: IRole | null;
  user: IUser | null;
  login: (code: string) => Promise<'success' | 'failure'>;
  logout: () => void;
};

export const AuthContext = React.createContext<AuthTypes | null>(null);

export const AuthContextProvider: React.FC<PropsAuthContextProvider> = ({
  children,
}) => {
  const [role, setRole] = useState<IRole>(null);
  const [user, setUser] = useState<IUser | null>(null);
  const { push } = useRouter();

  const login = async (code: string) => {
    try {
      const response = await fetch<{ role: IRole; token: string }>(
        `/user/login?code=${code}`
      );
      setRole(response.role);

      localStorage.setItem(TOKEN_STORAGE_KEY, response.token);
      await getUser();
      response.role && push(`/${response.role.toLowerCase()}`);

      return 'success';
    } catch (err) {
      console.error('err', err);
      return 'failure';
    }
  };

  const getUser = async () => {
    try {
      const response = await fetch<IUser>('/user/user');
      setUser(response);
      return 'success';
    } catch (err) {
      localStorage.removeItem(TOKEN_STORAGE_KEY);
      push('/');
      return 'failure';
    }
  };

  const logout = () => {
    try {
      localStorage.removeItem(TOKEN_STORAGE_KEY);
      push('/');
      return 'success';
    } catch (err) {
      return 'failure';
    }
  };

  useEffect(() => {
    if (localStorage.getItem(TOKEN_STORAGE_KEY)) {
      getUser();
    }
  }, []);

  return (
    <AuthContext.Provider
      value={{
        role,
        user,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext) as AuthTypes;
