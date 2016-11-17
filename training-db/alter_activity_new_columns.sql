
ALTER TABLE activity ADD COLUMN state_id integer, ADD COLUMN creation_date timestamp without time zone, ADD COLUMN last_update timestamp without time zone,
ADD COLUMN user_create integer, ADD COLUMN user_update integer;
ALTER TABLE physiological_capacity ADD COLUMN state_id integer, ADD COLUMN creation_date timestamp without time zone, ADD COLUMN last_update timestamp without time zone,
ADD COLUMN user_create integer, ADD COLUMN user_update integer;