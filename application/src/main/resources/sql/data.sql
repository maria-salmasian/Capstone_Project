-- Sample data for start


insert into face_recognition.cluster (id, name)
values (1, 'Arts & Humanities'),
       (2, 'Social Sciences'),
       (3, 'Quantitative Sciences');

insert into face_recognition.roles(id, role_name)
values (1, 'ADMIN'),
       (2, 'PROFESSOR'),
       (3, 'STUDENT');

insert into face_recognition.course(created_at, updated_at, title, description, is_deleted)
values (date(now()), date(now()), 'Introduction to Environmental Sciences', 'The course is designed for students to gain an understanding of basic principles of environmental sciences, including an introduction to the structure and functioning of ecosystems and their physical and biogeochemical cycles.', false),
       (date(now()), date(now()), 'Music Appreciation', 'This course aims to equip students to listen to, understand and discuss music as a cultural and aesthetic form of human creativity through acquaintance with Western European classical music from various periods. ', false),
       (date(now()), date(now()), 'Calculus', 'Calculus', false),
       (date(now()), date(now()), 'Biodiversity', 'This course is designed for undergraduate students to develop basic quantitative skills for deeper understanding of the causes and consequences of the current worldwide loss of plant, animal and other species.', false);


insert into face_recognition.link_course_cluster (course_id, cluster_id)
values (1, 3), (1, 2), (2,1),(2,2),(4, 3);

insert into face_recognition.link_user_course(user_id, course_id)
values (1, 3), (1,2), (1,1);

insert into face_recognition.link_user_course(user_id, course_id)
values (3, 1), (3,4);