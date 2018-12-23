INSERT INTO user_profile (name, surname, academic_year, course_of_study)
VALUES ('Jan', 'Nowak', 'second', 'Civil Engineering'),
       ('Adam', 'Kowalski', 'third', 'Computer Science'),
       ('Piotr', 'Rybka', 'first', 'Medicine'),
       ('Marcel', 'Chrobry', 'second', 'Computer Science'),
       ('Robert', 'Mak', 'first', 'Chemistry');

INSERT INTO user (email, password, index_number, enabled, user_profile_id)
VALUES ('jan.nowak@gmail.com', '12345678', 102001, true, 1),
       ('adam.kowalski@gmail.com', '22345678', 102022, true, 2),
       ('piotr.rybka@gmail.com', '32345678', 102033, true, 3),
       ('marcel.chrobry@gmail.com', '42345678', 102000, true, 4),
       ('robert.mak@gmail.com', '52345678', 102008, true, 5);

INSERT INTO lecture (title, description, lecturer)
VALUES
       ('JavaDev 1', 'JAVA programming - JAVA SE', 'James Smith'),
       ('JavaDev 2', 'JAVA programming - JAVA JEE', 'James Smith'),
       ('JavaDev 3', 'JAVA programming - GIT', 'Robert Johnson'),
       ('JavaDev 4', 'JAVA programming - databases', 'Robert Johnson'),
       ('JavaDev 5', 'JAVA programming - Spring', 'Robert Johnson'),
       ('JavaDev 6', 'JAVA programming - REST API', 'David Garcia');

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