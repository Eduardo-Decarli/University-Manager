# Students

INSERT INTO `university_system`.`student` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`, `course_id`)
VALUES ('2000-05-15', 'joao.silva@example.com', 'João', 'Silva', 'senha123', 'ROLE_STUDENT', 'Rua das Flores, 123', NULL);

INSERT INTO `university_system`.`student` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`, `course_id`)
VALUES ('1998-03-22', 'maria.souza@example.com', 'Maria', 'Souza', 'senha456', 'ROLE_STUDENT', 'Avenida Central, 456', NULL);

INSERT INTO `university_system`.`student` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`, `course_id`)
VALUES ('1995-10-30', 'lucas.oliveira@example.com', 'Lucas', 'Oliveira', 'senha789', 'ROLE_STUDENT', 'Rua da Praia, 789', NULL);

INSERT INTO `university_system`.`student` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`, `course_id`)
VALUES ('2001-08-12', 'ana.silva@example.com', 'Ana', 'Silva', 'senha321', 'ROLE_STUDENT', 'Rua do Campo, 321', NULL);

INSERT INTO `university_system`.`student` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`, `course_id`)
VALUES ('1999-12-05', 'carlos.santos@example.com', 'Carlos', 'Santos', 'senha654', 'ROLE_STUDENT', 'Travessa das Árvores, 654', NULL);

INSERT INTO `university_system`.`student` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `address`, `course_id`)
VALUES ('2002-07-18', 'fernanda.martins@example.com', 'Fernanda', 'Martins', 'senha987', 'ROLE_STUDENT', 'Alameda das Rosas, 987', NULL);

#Coordinator

INSERT INTO `university_system`.`coordinator` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`)
VALUES ('1985-02-10', 'ana.coordinator@example.com', 'Ana', 'Carla', 'teste123', 'ROLE_COORDINATOR');

INSERT INTO `university_system`.`coordinator` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `course_id`, `main_teacher_id`, `subs_teacher_id`)
VALUES ('1979-04-23', 'joao.coordinator@example.com', 'João', 'Almeida', 'senha1234', 'ROLE_COORDINATOR', 2, 4, 5);

INSERT INTO `university_system`.`coordinator` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `course_id`, `main_teacher_id`, `subs_teacher_id`)
VALUES ('1988-09-15', 'mariana.coordinator@example.com', 'Mariana', 'Pereira', 'senha5678', 'ROLE_COORDINATOR', 3, 6, 7);

INSERT INTO `university_system`.`coordinator` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `course_id`, `main_teacher_id`, `subs_teacher_id`)
VALUES ('1992-11-03', 'lucas.coordinator@example.com', 'Lucas', 'Santos', 'senha91011', 'ROLE_COORDINATOR', 4, 8, 9);

INSERT INTO `university_system`.`coordinator` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `course_id`, `main_teacher_id`, `subs_teacher_id`)
VALUES ('1980-06-18', 'carla.coordinator@example.com', 'Carla', 'Silva', 'senha121314', 'ROLE_COORDINATOR', 5, 10, 11);

INSERT INTO `university_system`.`coordinator` (`birth_date`, `email`, `first_name`, `last_name`, `password`, `role`, `course_id`, `main_teacher_id`, `subs_teacher_id`)
VALUES ('1975-03-30', 'pedro.coordinator@example.com', 'Pedro', 'Matos', 'senha151617', 'ROLE_COORDINATOR', 6, 12, 13);

#COURSE

INSERT INTO `university_system`.`course` (`name`, `description`, `coordinator_id`)
VALUES ('Ciência da Computação', 'Curso focado em fundamentos da computação e programação.', 1);

INSERT INTO `university_system`.`course` (`name`, `description`, `coordinator_id`)
VALUES ('Engenharia de Software', 'Curso voltado para o desenvolvimento de softwares em larga escala.', 2);

INSERT INTO `university_system`.`course` (`name`, `description`, `coordinator_id`)
VALUES ('Administração', 'Curso voltado para gestão e administração de negócios.', 3);

INSERT INTO `university_system`.`course` (`name`, `description`, `coordinator_id`)
VALUES ('Direito', 'Curso de formação jurídica com foco em leis e legislação.', 4);

INSERT INTO `university_system`.`course` (`name`, `description`, `coordinator_id`)
VALUES ('Medicina', 'Curso de formação médica com ênfase em saúde e bem-estar.', 5);

