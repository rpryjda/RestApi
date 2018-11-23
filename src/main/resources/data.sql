INSERT INTO student (id, name, surname, email, password, academic_year, course_of_study, index_number)
VALUES
(1, 'Jan', 'Nowak', 'jan.nowak@gmail.com', '12345678', 'second', 'Civil Engineering', 102001),
(2, 'Adam', 'Kowalski', 'adam.kowalski@gmail.com', '22345678', 'third', 'Computer Science', 102022),
(3, 'Piotr', 'Rybka', 'piotr.rybka@gmail.com', '32345678', 'first', 'Medicine', 102033),
(4, 'Marcel', 'Chrobry', 'marcel.chrobry@gmail.com', '42345678', 'second', 'Computer Science', 102000),
(5, 'Robert', 'Mak', 'robert.mak@gmail.com', '52345678', 'first', 'Chemistry', 102008);


INSERT INTO lecture (id, title, description, lecturer)
VALUES
(1, 'JavaDev', 'JAVA programming - JAVA SE', 'James Smith'),
(2, 'JavaDev', 'JAVA programming - JAVA JEE', 'James Smith'),
(3, 'JavaDev', 'JAVA programming - GIT', 'Robert Johnson'),
(4, 'JavaDev', 'JAVA programming - databases', 'Robert Johnson'),
(5, 'JavaDev', 'JAVA programming - Spring', 'Robert Johnson'),
(6, 'JavaDev', 'JAVA programming - REST API', 'David Garcia');


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