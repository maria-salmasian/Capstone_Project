import { FC } from 'react';

import { IAttention } from '@/interfaces/attention';

import styles from './styles.module.scss';

type AttentionModalProps = {
  attention: IAttention;
  onClose: () => void;
};

const AttentionModal: FC<AttentionModalProps> = ({ attention, onClose }) => {
  const onOverlayClick = (e: any) => {
    e.preventDefault();
    e.stopPropagation();
    onClose();
  };

  return (
    <div className={styles.overlay} onClick={onOverlayClick}>
      <div className={styles.content}>
        <div className={styles.close}>
          <button>
            <img src="/close.svg" alt="Jiry lav txa a" />
          </button>
        </div>
        <div>{attention.identifier}</div>
        <div>Attention percentage: {attention?.percentage}%</div>
      </div>
    </div>
  );
};

export default AttentionModal;
