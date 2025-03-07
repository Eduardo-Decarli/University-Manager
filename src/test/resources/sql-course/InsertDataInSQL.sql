-- Discipline

INSERT INTO discipline (description, name) VALUES ('Introduction to Computer Science','CS101');
INSERT INTO discipline (description, name) VALUES ('Introduction to Computer Science','CS102');
INSERT INTO discipline (description, name) VALUES ('Introduction to Computer Science','CS103');
INSERT INTO discipline (description, name) VALUES ('Introduction to Computer Science','CS104');
INSERT INTO discipline (description, name) VALUES ('Introduction to Computer Science','CS105');

-- Coordinator

INSERT INTO coordinator (birth_date, email, first_name, last_name, password, role, main_teacher_id, subs_teacher_id) VALUES
    ('2000-01-15', 'joe@example.com', 'Joe', 'Fontes', '$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm', 'ROLE_COORDINATOR', 1, 2);
INSERT INTO `coordinator` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `main_teacher_id`) VALUES
    ('2000-01-15', 'binto@example.com', 'Joe', 'Fontes', '$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm', 'ROLE_COORDINATOR', 3);

INSERT INTO `coordinator` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `main_teacher_id`) VALUES
    ('2000-01-15', 'bagnto@example.com', 'Joe', 'Fontes', '$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm', 'ROLE_COORDINATOR', 5);

-- Course

INSERT INTO course (description, name, coordinator_id) VALUES ('Course_description_1','Computer_Science', 1);
INSERT INTO course (description, name, coordinator_id) VALUES ('Course_description_2','Biology', 2);

-- course_disciplines

INSERT INTO course_disciplines (course_id, disciplines_id) VALUES ( 1, 1);
INSERT INTO course_disciplines (course_id, disciplines_id) VALUES ( 1, 2);
INSERT INTO course_disciplines (course_id, disciplines_id) VALUES ( 2, 5);
