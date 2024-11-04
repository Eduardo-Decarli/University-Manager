INSERT INTO `coordinator` (`id`, `birth_date`, `email`, `first_name`, `last_name`, `password`, `role`)
VALUES (1, '2000-01-15', 'joe@example.com', 'Joe', 'Fontes', '$2a$10$zGp1XxUHnH9fsKMJlw8YLe5RGUxqjgMXcW4VYHZxvahgJgRNKVavm', 'ROLE_COORDINATOR');

INSERT INTO `course` (`id`, `description`, `name`, `coordinator_id`) VALUES (1,'Course description 1','Computer Science',1);

INSERT INTO `discipline` (`id`, `description`, `name`) VALUES (1,'Introduction to Computer Science','CS101');

INSERT INTO `student` (`id`, `birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`, `course_id`) VALUES (1,'2000-05-15','john@email.com','John','Reis','$2a$10$0X8RBwIqdBWLIJ/jSjF4puDAZuqOVSK2QDOIXNeU8.XoS4fehIRTy','ROLE_STUDENT','Santa Catarina/SC | Palhoça, Passa Vinte, Rua João Cândido da Rosa',1);

INSERT INTO `registration` (`id`, `course_id`, `student_id`) VALUES (1,1,1);

INSERT INTO `teacher` (`id`, `birth_date`, `email`, `first_name`, `last_name`, `password`, `role`) VALUES (1,'2000-01-15','doe@example.com','Doe','Dois','$2a$10$mt6JSlRr886RbK6bEgAQU.8.Qs.uv9nPRgaxwKrMtJNkZhZBcaYo6','ROLE_TEACHER');

