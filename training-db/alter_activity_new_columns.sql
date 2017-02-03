
ALTER TABLE activity ADD COLUMN state_id integer, ADD COLUMN creation_date timestamp without time zone, ADD COLUMN last_update timestamp without time zone,
ADD COLUMN user_create integer, ADD COLUMN user_update integer;
ALTER TABLE physiological_capacity ADD COLUMN state_id integer, ADD COLUMN creation_date timestamp without time zone, ADD COLUMN last_update timestamp without time zone,
ADD COLUMN user_create integer, ADD COLUMN user_update integer;

tipo de letra numeros
si el dashboard va a ocupar la resolución por defecto o va tener scroll
tamaños de fuente
como se muestra las notificaciones de la campanita (diseño)