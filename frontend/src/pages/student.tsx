import { useState } from 'react';

import Clusters from '@/components/clusters';
import Courses from '@/components/courses';
import Header from '@/components/header';
import { useAuth } from '@/context/auth';

const StudentPage = () => {
  const [cluster, setCluster] = useState<string | undefined>(undefined);

  const { user } = useAuth();

  const onClusterSelect = (cluster: string | undefined) => {
    setCluster(cluster);
  };

  return (
    <div>
      <Header />
      <main className="flex flex-col gap-y-8 p-8">
        <div className="text-center text-3xl font-extrabold text-primary">
          {`Welcome Student ${user?.name}!`}
        </div>
        <Clusters onClusterSelect={onClusterSelect} />
        <Courses cluster={cluster} />
      </main>
    </div>
  );
};

export default StudentPage;
