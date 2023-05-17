import { FC, useState } from 'react';

import axios from 'axios';
import Link from 'next/link';

import { useAuth } from '@/context/auth';
import { ICourse } from '@/interfaces/course';

import styles from './styles.module.scss';

type CourseItemProps = {
  course: ICourse;
  isProfessor?: boolean;
};

const CourseItem: FC<CourseItemProps> = ({ course, isProfessor = false }) => {
  const [link, setLink] = useState<string>('');

  const { user } = useAuth();

  const onAttendClass = () => {
    try {
      axios(
        `${process.env.NEXT_PUBLIC_PYTHON_APP_URL}/face_detection/${user?.id}/${course.id}`,
        { method: 'POST' }
      );
      window.open(link.includes('http') ? link : `https://${link}`, '_blank');
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <div className={styles.course}>
      <h3 className={styles.title}>{course.title}</h3>
      <p className={styles.description}>{course.description}</p>
      {isProfessor ? (
        <Link href={`/course/${course.id}`}>
          <button>Go To Report Page</button>
        </Link>
      ) : (
        <>
          <input
            className="rounded-md border-2 border-primary p-2 placeholder:text-gray-400"
            value={link}
            onChange={e => setLink(e.target.value)}
            placeholder="Enter valid meeting url"
          />
          {link && (
            <button className="w-fit" onClick={onAttendClass}>
              Attend Class
            </button>
          )}
        </>
      )}
    </div>
  );
};

export default CourseItem;
