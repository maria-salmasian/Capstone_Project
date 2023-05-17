import { FC } from 'react';

import styles from './styles.module.scss';

type ErrorProps = {
  message?: string;
};

const Error: FC<ErrorProps> = ({
  message = 'Oooooops! Something went wrong :/ Please try again later.',
}) => {
  return <div className={styles.error}>{message}</div>;
};

export default Error;
