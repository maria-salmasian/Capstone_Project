import { FC } from 'react';

import { useAuth } from '@/context/auth';

const Header: FC = () => {
  const { logout } = useAuth();

  return (
    <div className="flex w-full items-center justify-between p-8">
      <img className="h-8" src="/header-logo.png" alt="minecraft" />
      <button
        className="rounded-md bg-primary px-4 py-2 text-white"
        onClick={logout}
      >
        Logout
      </button>
    </div>
  );
};

export default Header;
