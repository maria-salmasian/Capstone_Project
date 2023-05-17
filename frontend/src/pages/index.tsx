import { FC } from 'react';

const HomePage: FC = () => {
  return (
    <main className="flex min-h-screen w-full items-center justify-center">
      <div className="flex w-2/5 items-center justify-around gap-4 rounded-lg border-2 border-primary p-4 shadow">
        <img className="w-2/5" src="/header-logo.png" alt="Under The Bridge" />
        <a
          className="rounded-md bg-primary px-4 py-2 text-white"
          href="https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=591326629537-4p1nhe4i6jl7u4jc2lpgb15soc2mktgo.apps.googleusercontent.com&redirect_uri=http://localhost:3000/login&scope=profile%20email"
        >
          Login
        </a>
      </div>
    </main>
  );
};

export default HomePage;
