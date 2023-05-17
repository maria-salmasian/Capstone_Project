import { FC, useEffect, useState } from 'react';

import CourseItem from '@/components/courses/course-item';
import Error from '@/components/error';
import Spinner from '@/components/spinner';
import { useAuth } from '@/context/auth';
import { IAppState } from '@/interfaces/app-state';
import { ICourse } from '@/interfaces/course';
import fetch from '@/utils/fetch';

import styles from './styles.module.scss';

type CoursesProps = {
  cluster?: string;
  isProfessor?: boolean;
};

const Courses: FC<CoursesProps> = ({ cluster, isProfessor }) => {
  const [courses, setCourses] = useState<ICourse[]>([]);
  const [status, setStatus] = useState<IAppState>('pending');

  const { user } = useAuth();

  const fetchCourses = (path: string) => {
    setStatus('loading');
    fetch<ICourse[]>(path)
      .then(res => {
        setCourses(res);
        setStatus('success');
      })
      .catch(err => {
        setStatus('error');
        console.error(err);
      });
  };

  useEffect(() => {
    if (status === 'pending' && user) {
      fetchCourses(`/course/user/${user.id}`);
    }
  }, [user, status]);

  useEffect(() => {
    if (user) {
      fetchCourses(
        cluster
          ? `/course/users/${user.id}/cluster/${cluster.split(' ')[0]}`
          : `/course/user/${user.id}`
      );
    }
  }, [cluster]);

  return (
    <div className={styles.root}>
      {(status === 'pending' || status === 'loading') && <Spinner />}
      {status === 'success' &&
        courses.map(course => (
          <CourseItem
            key={course.id}
            course={course}
            isProfessor={isProfessor}
          />
        ))}
      {status === 'error' && <Error />}
    </div>
  );
};

export default Courses;
