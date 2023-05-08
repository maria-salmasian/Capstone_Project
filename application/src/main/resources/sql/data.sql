-- Sample data for start


insert into cluster (name)
values ('Arts & Humanities'),
       ('Social Sciences'),
       ('Quantitative Sciences');

insert into roles(id, role_name)
values (1, 'ADMIN'),
       (2, 'PROFESSOR'),
       (3, 'STUDENT');

insert into course(created_at, updated_at, title, description, is_deleted)
values (date(now()), date(now()), 'Introduction to Business', 'Introduction to Business', false),
       (date(now()), date(now()), 'Music Appreciation', 'Music Appreciation', false),
       (date(now()), date(now()), 'Calculus', 'Calculus', false),
       (date(now()), date(now()), 'Biodiversity', 'Biodiversity', false);


insert into link_course_cluster (course_id, cluster_id)
values (4, 3);

insert into link_user_course(user_id, course_id)
values (4, 3);