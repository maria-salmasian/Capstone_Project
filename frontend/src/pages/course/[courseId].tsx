import {useEffect, useState} from 'react';

import {useRouter} from 'next/router';

import Course from '@/components/course';
import Error from '@/components/error';
import Header from '@/components/header';
import Spinner from '@/components/spinner';
import {IAppState} from '@/interfaces/app-state';
import {ICourse} from '@/interfaces/course';
import fetch from '@/utils/fetch';

const CoursePage = () => {
    const [status, setStatus] = useState<IAppState>('pending');
    const [course, setCourse] = useState<ICourse>();

    const {query} = useRouter();

    useEffect(() => {
        if (status === 'pending' && query.courseId) {
            setStatus('loading');
            fetch<ICourse>(`/course/${query.courseId}`)
                .then(res => {
                    setCourse(res);
                    setStatus('success');
                })
                .catch(err => {
                    console.error(err);
                    setStatus('error');
                });
        }
    }, [status, query]);

    return (
        <div>
            <Header/>
            <main>
                {(status === 'pending' || status === 'loading') && (
                    <div className="flex min-h-screen w-full items-center justify-center">
                        <Spinner/>
                    </div>
                )}
                {status === 'success' && course && <Course course={course}/>}
                {status === 'error' && <Error/>}
            </main>
        </div>
    );
};

export default CoursePage;
