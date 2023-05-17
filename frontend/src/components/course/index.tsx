import { FC, useState } from 'react';

import moment from 'moment';

import AttentionModal from '@/components/attention-modal';
import Error from '@/components/error';
import { IAttention } from '@/interfaces/attention';
import { ICourse } from '@/interfaces/course';
import fetch from '@/utils/fetch';

import styles from './styles.module.scss';

type CourseProps = {
  course: ICourse;
};

const Course: FC<CourseProps> = ({ course }) => {
  const [selectedDate, setSelectedDate] = useState<string>(
    moment().format('YYYY-MM-DD')
  );

  const [attention, setAttention] = useState<IAttention | null>(null);
  const [noAttention, setNoAttention] = useState(false);

  const onSelectDate = (e: any) => {
    const newDate = moment(e.target.value).format('YYYY-MM-DD');
    setSelectedDate(newDate);
    if (noAttention) {
      setNoAttention(false);
    }
  };

  const getAttention = (userId?: number) => {
    const path = `${userId ? `/user/${userId}/courses` : '/course'}/${
      course.id
    }/attention-average?date=${selectedDate}`;

    fetch<IAttention>(path)
      .then(res => {
        setAttention(res);
      })
      .catch(err => {
        if (err.response.status === 406) {
          setNoAttention(true);
        } else {
          console.error(err);
        }
      });
  };

  const closeAttentionModal = () => {
    setAttention(null);
  };

  return (
    <>
      <div className={styles.content}>
        <div className={styles.header}>
          <div className={styles.left}>
            <h3 className={styles.title}>{`${course.title} ${
              course.clusters[0] ? `/${course.clusters[0].name}` : ''
            }`}</h3>
            <div className="flex gap-x-4">
              <input type="date" value={selectedDate} onChange={onSelectDate} />
              {noAttention && (
                <span>
                  <Error message="No available records for given date" />
                </span>
              )}
            </div>
          </div>
          <button className={styles.report} onClick={() => getAttention()}>
            Get Course Attention Report
          </button>
        </div>
        <div className={styles.users}>
          <span>Registered Students</span>
          <div className={styles['users-list']}>
            {course.users.map(user => (
              <div key={user.id} className={styles.user}>
                <div>{user.name}</div>
                <div>{user.lastName}</div>
                <div>{user.username}</div>
                <button
                  className={styles.report}
                  onClick={() => getAttention(user.id)}
                >
                  Get User Report
                </button>
              </div>
            ))}
          </div>
        </div>
      </div>
      {attention && (
        <AttentionModal attention={attention} onClose={closeAttentionModal} />
      )}
    </>
  );
};

export default Course;
