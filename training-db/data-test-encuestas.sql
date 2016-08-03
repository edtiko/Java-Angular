
--
-- Dumping data for table `option_type`
--
INSERT INTO option_type VALUES (1,'NONE'),(2,'CONDITIONAL');

--
-- Dumping data for table `response_type`
--

INSERT INTO `response_type` VALUES (1,'TEXT'),(2,'FILE');

insert into data_type (data_type_id, name) values (1,'text');
insert into data_type (data_type_id, name) values (2,'checkbox');
insert into data_type (data_type_id, name) values (3,'radio');
insert into data_type (data_type_id, name) values (4,'number');
insert into data_type (data_type_id, name) values (5,'select');
insert into data_type (data_type_id, name) values (6,'date');

insert into state (state_id, name, description)values (1,'Activo','Activo');
insert into state (state_id, name, description)values (2,'Inactivo','Inactivo');
insert into state (state_id, name, description)values (3,'Eliminado','Eliminado');

insert into discipline (discipline_id, name, description)values (1,'Natación',null);
insert into discipline (discipline_id, name, description)values (2,'Ciclismo',null);
insert into discipline (discipline_id, name, description)values (3,'Running',null);

insert into question (question_id, data_type_id, state_id, name, description, unit, ind_additional, question_type, creation_date)values (1,3,1,'¿Son frecuentes alergias. Gripas. Diarrea. Gastritis. Dolores lumbares y musculares?',null,null,0,'O',now ());
insert into question (question_id, data_type_id, state_id, name, description, unit, ind_additional, question_type, creation_date)values (2,1,1,'¿Qué comes en un día como hoy desde el desayuno hasta la cena?',null,null,0,'O',now ());
insert into question (question_id, data_type_id, state_id, name, description, unit, ind_additional, question_type, creation_date)values (3,1,1,'Principales logros deportivos MTB u otros',null,null,0,'O',now ());
insert into question (question_id, data_type_id, state_id, name, description, unit, ind_additional, question_type, creation_date)values (4,4,1,'Pulso recién levantado',null,null,0,'O',now ());
insert into question (question_id, data_type_id, state_id, name, description, unit, ind_additional, question_type, creation_date)values (5,2,1,'Si en una carrera sales mal ubicado logras remontar en la primera vuelta',null,null,0,'O',now ());
insert into question (question_id, data_type_id, state_id, name, description, unit, ind_additional, question_type, creation_date)values (6,5,1,'Sabes si giras al mismo tiempo todas las vueltas',null,null,0,'O',now ());
insert into question (question_id, data_type_id, state_id, name, description, unit, ind_additional, question_type, creation_date)values (7,1,1,'Que obstáculos técnicos te gustan en la pista y el que menos te gusta',null,null,0,'O',now ());
insert into question (question_id, data_type_id, state_id, name, description, unit, ind_additional, question_type, creation_date)values (8,1,1,'Has corrido ruta y como te va en la CRI',null,null,0,'O',now ());
insert into question (question_id, data_type_id, state_id, name, description, unit, ind_additional, question_type, creation_date)values (9,1,1,'Cuales consideras que son tus fortalezas y debilidades en MTB',null,null,0,'O',now ());

insert into questionnaire (questionnaire_id, discipline_id, state_id, name, creation_date)values (1,1,1,'Natación',now ());
insert into questionnaire (questionnaire_id, discipline_id, state_id, name, creation_date)values (2,2,1,'Ciclismo',now ());
insert into questionnaire (questionnaire_id, discipline_id, state_id, name, creation_date)values (3,3,1,'Running',now ());

insert into questionnaire_category (questionnaire_category_id, state_id, parent_questionnaire_category_i, name, description, creation_date)values (1,1,null,'Datos Adicionales','Datos Adicionales',now ());

insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (1,1,1,1,1,3,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (2,1,1,2,1,1,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (3,1,1,4,1,2,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (4,2,1,1,1,9,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (5,2,1,2,1,5,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (6,2,1,3,1,3,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (7,2,1,4,1,7,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (8,2,1,5,1,2,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (9,2,1,6,1,4,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (10,2,1,7,1,1,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (11,2,1,8,1,6,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (12,2,1,9,1,8,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (13,3,1,1,1,3,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (14,3,1,2,1,1,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (15,3,1,4,1,4,now ());
insert into questionnaire_question (questionnaire_question_id, questionnaire_id, questionnaire_category_id, question_id, state_id, question_order, creation_date)values (16,3,1,5,1,2,now ());

insert into question_option (question_option_id, question_id, option_type_id, state_id, parent_question_option_id, name, description)values (1,1,1,1,null,'Si',null);
insert into question_option (question_option_id, question_id, option_type_id, state_id, parent_question_option_id, name, description)values (2,1,1,1,null,'No',null);
insert into question_option (question_option_id, question_id, option_type_id, state_id, parent_question_option_id, name, description)values (3,1,1,1,null,'A veces',null);
insert into question_option (question_option_id, question_id, option_type_id, state_id, parent_question_option_id, name, description)values (4,5,1,1,null,'Si logro remontar',null);
insert into question_option (question_option_id, question_id, option_type_id, state_id, parent_question_option_id, name, description)values (5,5,1,1,null,'No lo logro',null);
insert into question_option (question_option_id, question_id, option_type_id, state_id, parent_question_option_id, name, description)values (6,6,1,1,null,'Algunas veces',null);
insert into question_option (question_option_id, question_id, option_type_id, state_id, parent_question_option_id, name, description)values (7,6,1,1,null,'Muy pocas veces',null);
insert into question_option (question_option_id, question_id, option_type_id, state_id, parent_question_option_id, name, description)values (8,6,1,1,null,'Siempre ',null);

