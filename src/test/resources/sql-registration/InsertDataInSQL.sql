-- Discipline

INSERT INTO discipline (description, name) VALUES ('Introduction to Computer Science','CS101');
INSERT INTO discipline (description, name) VALUES ('Introduction to Computer Science','CS102');
INSERT INTO discipline (description, name) VALUES ('Introduction to Computer Science','CS103');

-- Coordinator

INSERT INTO coordinator (birth_date, email, first_name, last_name, password, role, main_teacher_id, subs_teacher_id) VALUES
    ('2000-01-15', 'joe@example.com', 'Joe', 'Fontes', '$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm', 'ROLE_COORDINATOR', 1, 2);
INSERT INTO `coordinator` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `main_teacher_id`) VALUES
    ('2000-01-15', 'binto@example.com', 'Joe', 'Fontes', '$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm', 'ROLE_COORDINATOR', 3);

    INSERT INTO `coordinator` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`) VALUES
        ('2000-01-15', 'ash@example.com', 'Joe', 'Fontes', '$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm', 'ROLE_COORDINATOR');

-- Course

INSERT INTO course (description, name, coordinator_id) VALUES ('Course_description_1','Computer_Science', 1);
INSERT INTO `course` (`description`, `name`, `coordinator_id`) VALUES ('Course_description_2','Biology', 2);
INSERT INTO `course` (`description`, `name`, `coordinator_id`) VALUES ('Course_description_3','Math', 3);

-- course_disciplines

INSERT INTO course_disciplines (course_id, disciplines_id) VALUES ( 1, 1);
INSERT INTO course_disciplines (course_id, disciplines_id) VALUES ( 1, 2);

-- Students

INSERT INTO `student` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`) VALUES ('2000-05-15','john@email.com','John','Reis','$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm','ROLE_STUDENT','Santa Catarina/SC | Palhoça, Passa Vinte, Rua João Cândido da Rosa');
INSERT INTO `student` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`) VALUES ('2000-05-15','mario@email.com','Mario','Topen','$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm','ROLE_STUDENT','Santa Catarina/SC | Palhoça, Passa Vinte, Rua João Cândido da Rosa');

-- registration

INSERT INTO `registration` (`course_id`, `student_id`) VALUES (1, 1);