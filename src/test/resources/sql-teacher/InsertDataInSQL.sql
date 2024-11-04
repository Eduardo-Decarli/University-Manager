
INSERT INTO `discipline` (`description`, `name`) VALUES ('Introduction to Computer Science','CS101');

INSERT INTO `discipline` (`description`, `name`) VALUES ('Introduction to Computer Science','CS102');

INSERT INTO coordinator (birth_date, email, first_name, last_name, password, role)
    VALUES ('2000-01-15', 'joe@example.com', 'Joe', 'Fontes', '$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm', 'ROLE_COORDINATOR');

INSERT INTO coordinator (birth_date, email, first_name, last_name, password, role)
    VALUES ('2000-01-15', 'di@example.com', 'Joe', 'Fontes', '$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm', 'ROLE_COORDINATOR');




INSERT INTO `course` (`description`, `name`, `coordinator_id`) VALUES ('Course_description_1','Computer_Science', 1);
INSERT INTO `course` (`description`, `name`, `coordinator_id`) VALUES ('Course_description_2','Computer', 2);

INSERT INTO `teacher` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `main_teacher_id`, `subs_teacher_id`, course_id) VALUES ('2000-01-15','doe@example.com','Doe','Dois','$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm','ROLE_TEACHER', 1, 2, 1);
INSERT INTO `teacher` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`) VALUES ('2000-01-15','serena@example.com','Doe','Dois','$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm','ROLE_TEACHER');


INSERT INTO `student` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`) VALUES ('2000-05-15','john@email.com','John','Reis','$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm','ROLE_STUDENT','Santa Catarina/SC | Palhoça, Passa Vinte, Rua João Cândido da Rosa');


INSERT INTO `student` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`) VALUES ('2000-05-15','mario@email.com','Mario','Topen','$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm','ROLE_STUDENT','Santa Catarina/SC | Palhoça, Passa Vinte, Rua João Cândido da Rosa');


INSERT INTO `discipline_students` (discipline_id, students_id) VALUES (1,1);
INSERT INTO `discipline_students` (discipline_id, students_id) VALUES (1,2);
INSERT INTO `discipline_students` (discipline_id, students_id) VALUES (2,1);
INSERT INTO `discipline_students` (discipline_id, students_id) VALUES (2,2);

INSERT INTO `registration` (`course_id`, `student_id`) VALUES (1,1);
INSERT INTO `registration` (`course_id`, `student_id`) VALUES (1,2);