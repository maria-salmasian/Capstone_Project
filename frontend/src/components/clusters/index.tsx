import { ChangeEventHandler, FC, useEffect, useState } from 'react';

import Error from '@/components/error';
import Spinner from '@/components/spinner';
import { IAppState } from '@/interfaces/app-state';
import fetch from '@/utils/fetch';

import styles from './styles.module.scss';

type ClustersProps = {
  onClusterSelect: (cluster: string | undefined) => void;
};

const Clusters: FC<ClustersProps> = ({ onClusterSelect }) => {
  const [clusters, setClusters] = useState<string[]>([]);
  const [status, setStatus] = useState<IAppState>('pending');
  const [selectedCluster, setSelectedCluster] = useState<string>();

  const handleClusterSelect: ChangeEventHandler<HTMLSelectElement> = e => {
    setSelectedCluster(e.target.value);
  };

  useEffect(() => {
    setStatus('loading');
    fetch<string[]>('/cluster')
      .then(res => {
        setClusters(res);
        setStatus('success');
      })
      .catch(err => {
        console.error(err);
        setStatus('error');
      });
  }, []);

  useEffect(() => {
    onClusterSelect(selectedCluster === 'All' ? undefined : selectedCluster);
  }, [selectedCluster]);

  return (
    <div className={styles.root}>
      {status === 'error' && <Error message="Cannot get clusters" />}
      {status === 'success' && (
        <select onChange={handleClusterSelect}>
          <option>All</option>
          {clusters.map(cluster => (
            <option key={cluster}>{cluster}</option>
          ))}
        </select>
      )}
      {status === 'loading' && <Spinner />}
    </div>
  );
};

export default Clusters;
