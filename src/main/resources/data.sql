DROP TRIGGER IF EXISTS after_student_insert_first;
DROP TRIGGER IF EXISTS after_student_insert_second;

CREATE TRIGGER after_student_insert_first
AFTER INSERT
ON student
FOR EACH ROW
INSERT INTO users(username, password, enabled)
VALUES (NEW.email, CONCAT('{noop}',NEW.password), true);

CREATE TRIGGER after_student_insert_second
AFTER INSERT
ON student
FOR EACH ROW
INSERT INTO user_roles(username, role)
VALUES (NEW.email, 'ROLE_USER');

INSERT INTO student (name, surname, email, password, academic_year, course_of_study, index_number)
VALUES
('Jan', 'Nowak', 'jan.nowak@gmail.com', '12345678', 'second', 'Civil Engineering', 102001),
('Adam', 'Kowalski', 'adam.kowalski@gmail.com', '22345678', 'third', 'Computer Science', 102022),
('Piotr', 'Rybka', 'piotr.rybka@gmail.com', '32345678', 'first', 'Medicine', 102033),
('Marcel', 'Chrobry', 'marcel.chrobry@gmail.com', '42345678', 'second', 'Computer Science', 102000),
('Robert', 'Mak', 'robert.mak@gmail.com', '52345678', 'first', 'Chemistry', 102008);


INSERT INTO lecture (title, description, lecturer)
VALUES
('JavaDev', 'JAVA programming - JAVA SE', 'James Smith'),
('JavaDev', 'JAVA programming - JAVA JEE', 'James Smith'),
('JavaDev', 'JAVA programming - GIT', 'Robert Johnson'),
('JavaDev', 'JAVA programming - databases', 'Robert Johnson'),
('JavaDev', 'JAVA programming - Spring', 'Robert Johnson'),
('JavaDev', 'JAVA programming - REST API', 'David Garcia');


INSERT INTO student_lecture (student_id, lecture_id)
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