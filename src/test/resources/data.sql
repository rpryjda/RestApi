INSERT INTO user_profile (name, surname, academic_year, course_of_study)
VALUES ('Jan', 'NowakTest', 'second', 'Civil Engineering'),
       ('Adam', 'KowalskiTest', 'third', 'Computer Science'),
       ('Piotr', 'RybkaTest', 'first', 'Medicine'),
       ('Marcel', 'ChrobryTest', 'second', 'Computer Science'),
       ('Robert', 'MakTest', 'first', 'Chemistry');

INSERT INTO user (email, password, index_number, enabled, user_profile_id)
VALUES ('jan.nowak@gmail.com', '12345678', 102001, true, 1),
       ('adam.kowalski@gmail.com', '22345678', 102022, true, 2),
       ('piotr.rybka@gmail.com', '32345678', 102033, true, 3),
       ('marcel.chrobry@gmail.com', '42345678', 102000, true, 4),
       ('robert.mak@gmail.com', '52345678', 102008, true, 5);

INSERT INTO lecture (title, description, lecturer, date)
VALUES
       ('JavaDev 1 TEST', 'JAVA programming - JAVA SE', 'James Smith', '2018-11-26 16:30:00'),
       ('JavaDev 2 TEST', 'JAVA programming - JAVA JEE', 'James Smith', '2018-12-03 16:30:00'),
       ('JavaDev 3 TEST', 'JAVA programming - GIT', 'Robert Johnson', '2018-12-10 16:30:00'),
       ('JavaDev 4 TEST', 'JAVA programming - databases', 'Robert Johnson', '2018-12-17 16:30:00'),
       ('JavaDev 5 TEST', 'JAVA programming - Spring', 'Robert Johnson', '2019-01-07 16:30:00'),
       ('JavaDev 6 TEST', 'JAVA programming - REST API', 'David Garcia', '2019-01-14 16:30:00');

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