--Menú Asignar Atletas Coach Externo
insert into option(option_id, module_id, state_id, name, description, url, creation_date) values(nextval('option_option_id_seq'), 2, 1, 'Asignar Atletas', '', '#external-coach',now());

insert into state(state_id, name)values(3, 'RETIRE','')