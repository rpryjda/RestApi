INSERT INTO user_profile (name, surname, academic_year, course_of_study)
VALUES ('Jan', 'Nowak', 'second', 'Civil Engineering'),
       ('Adam', 'Kowalski', 'third', 'Computer Science'),
       ('Piotr', 'Rybka', 'first', 'Medicine'),
       ('Marcel', 'Chrobry', 'second', 'Computer Science'),
       ('Robert', 'Mak', 'first', 'Chemistry');

INSERT INTO user (email, password, index_number, enabled, user_profile_id)
VALUES ('jan.nowak@gmail.com', '$2a$10$Ookf3n66njkuSAykAo8S4uO/s7vxQdcFdU4Ykikt04Z6qYZH2E5oa', 102001, true, 1),
       ('adam.kowalski@gmail.com', '$2a$10$Ookf3n66njkuSAykAo8S4uO/s7vxQdcFdU4Ykikt04Z6qYZH2E5oa', 102022, true, 2),
       ('piotr.rybka@gmail.com', '$2a$10$Ookf3n66njkuSAykAo8S4uO/s7vxQdcFdU4Ykikt04Z6qYZH2E5oa', 102033, true, 3),
       ('marcel.chrobry@gmail.com', '$2a$10$Ookf3n66njkuSAykAo8S4uO/s7vxQdcFdU4Ykikt04Z6qYZH2E5oa', 102000, true, 4),
       ('robert.mak@gmail.com', '$2a$10$Ookf3n66njkuSAykAo8S4uO/s7vxQdcFdU4Ykikt04Z6qYZH2E5oa', 102008, true, 5);

INSERT INTO lecture (title, description, lecturer, date)
VALUES
       ('JavaDev 1', 'JAVA programming - Introduction', 'James Smith', '2018-10-22 15:30:00'),
       ('JavaDev 2', 'JAVA programming - Basics of Java', 'James Smith', '2018-10-29 16:30:00'),
       ('JavaDev 3', 'JAVA programming - Tools (GIT, Maven, Jenkins)', 'Robert Johnson', '2018-11-05 16:30:00'),
       ('JavaDev 4', 'JAVA programming - Database and ORM ', 'Robert Johnson', '2018-11-19 16:30:00'),
       ('JavaDev 5', 'JAVA programming - Consultation', 'Robert Johnson', '2018-11-26 16:30:00'),
       ('JavaDev 6', 'JAVA programming - Spring Framework #1', 'David Garcia', '2018-12-03 16:30:00'),
       ('JavaDev 7', 'JAVA programming - Spring Framework #2', 'Robert Johnson', '2018-12-10 16:30:00'),
       ('JavaDev 8', 'JAVA programming - Consultation', 'Robert Johnson', '2018-12-17 16:30:00'),
       ('JavaDev 9', 'JAVA programming - Testing', 'Robert Johnson', '2019-01-07 16:30:00'),
       ('JavaDev 10', 'JAVA programming - Cloud, AWS, DevOps, BigData', 'David Garcia', '2019-01-14 16:30:00');

INSERT INTO user_lecture (user_id, lecture_id)
VALUES
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (1, 3),
       (2, 3),
       (3, 3),
       (4, 3),
       (5, 3),
       (2, 4),
       (1, 5),
       (2, 5),
       (3, 5);

SET FOREIGN_KEY_CHECKS=0;

INSERT INTO user_role (user_id, role_id)
VALUES
       (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2);

SET FOREIGN_KEY_CHECKS=1;