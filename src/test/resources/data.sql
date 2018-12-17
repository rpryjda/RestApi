

INSERT INTO student (name, surname, email, password, academic_year, course_of_study, index_number)
VALUES
('Jan', 'NowakTest', 'jan.nowak@gmail.com', '12345678', 'second', 'Civil Engineering', 102001),
('Adam', 'KowalskiTest', 'adam.kowalski@gmail.com', '22345678', 'third', 'Computer Science', 102022),
('Piotr', 'RybkaTest', 'piotr.rybka@gmail.com', '32345678', 'first', 'Medicine', 102033),
('Marcel', 'ChrobryTest', 'marcel.chrobry@gmail.com', '42345678', 'second', 'Computer Science', 102000),
('Robert', 'MakTest', 'robert.mak@gmail.com', '52345678', 'first', 'Chemistry', 102008);


INSERT INTO lecture (title, description, lecturer)
VALUES
('JavaDevTest 1', 'JAVA programming - JAVA SE', 'James Smith'),
('JavaDevTest 2', 'JAVA programming - JAVA JEE', 'James Smith'),
('JavaDevTest 3', 'JAVA programming - GIT', 'Robert Johnson'),
('JavaDevTest 4', 'JAVA programming - databases', 'Robert Johnson'),
('JavaDevTest 5', 'JAVA programming - Spring', 'Robert Johnson'),
('JavaDevTest 6', 'JAVA programming - REST API', 'David Garcia');


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