-- Coordinator

INSERT INTO `coordinator` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `main_teacher_id`)
VALUES ('2000-01-15', 'joe@example.com', 'Joe', 'Fontes', '$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm', 'ROLE_COORDINATOR', null);

-- Student

INSERT INTO `student` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`) VALUES ('2000-05-15','john@email.com','John','Reis','$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm','ROLE_STUDENT','Santa Catarina/SC | Palhoça, Passa Vinte, Rua João Cândido da Rosa');

INSERT INTO `student` (`id`, `birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`) VALUES (40, '2000-05-15','mario@email.com','Mario','Topen','$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm','ROLE_STUDENT','Santa Catarina/SC | Palhoça, Passa Vinte, Rua João Cândido da Rosa');

-- Course

INSERT INTO `course` (`description`, `name`, `coordinator_id`) VALUES ('Course_description_1', 'Computer_Science', 1);

-- Disciplina

INSERT INTO `discipline` (`description`, `name`) VALUES ('Introduction to Computer Science','CS101');

-- Course_Disciplines

INSERT INTO `course_disciplines` (`course_id`, `disciplines_id`) VALUES (1, 1);


-- registration

INSERT INTO `registration` (`course_id`, `student_id`) VALUES (1, 1);