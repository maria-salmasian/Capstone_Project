import Courses from '@/components/courses';
import Header from '@/components/header';
import { useAuth } from '@/context/auth';

const ProfessorPage = () => {
  const { user } = useAuth();

  return (
    <div>
      <Header />
      <main className="flex flex-col gap-y-16 p-8">
        <div className="text-center text-3xl font-extrabold text-primary">
          {`Welcome Professor ${user?.name}!`}
        </div>
        <Courses isProfessor />
      </main>
    </div>
  );
};

export default ProfessorPage;
