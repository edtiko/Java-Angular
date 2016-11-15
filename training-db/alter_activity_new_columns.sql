
ALTER TABLE activity ADD COLUMN state_id integer, ADD COLUMN creation_date timestamp without time zone, ADD COLUMN last_update timestamp without time zone,
ADD COLUMN user_create integer, ADD COLUMN user_update integer;
ALTER TABLE physiological_capacity ADD COLUMN state_id integer, ADD COLUMN creation_date timestamp without time zone, ADD COLUMN last_update timestamp without time zone,
ADD COLUMN user_create integer, ADD COLUMN user_update integer;

ALTER TABLE plan_message ADD COLUMN to_star boolean;

ALTER TABLE plan_video ADD COLUMN to_star boolean;

ALTER TABLE plan_audio ADD COLUMN to_star boolean;

ALTER TABLE mail_communication ADD COLUMN to_star boolean;