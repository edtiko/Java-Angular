
/*---------------------------------------Alter para desplegar nuevo dashboard atleta-----------------------------------------------*/

ALTER TABLE plan_message ADD COLUMN to_star boolean;

ALTER TABLE plan_video ADD COLUMN to_star boolean;

ALTER TABLE plan_audio ADD COLUMN to_star boolean;

ALTER TABLE mail_communication ADD COLUMN to_star boolean;

ALTER TABLE training_plan
  DROP COLUMN video_count;
ALTER TABLE training_plan
  DROP COLUMN message_count;
ALTER TABLE training_plan
  DROP COLUMN email_count;
ALTER TABLE training_plan
  DROP COLUMN audio_count;
ALTER TABLE training_plan
  DROP COLUMN video_emergency;
ALTER TABLE training_plan
  DROP COLUMN message_emergency;
ALTER TABLE training_plan
  DROP COLUMN audio_emergency;
ALTER TABLE training_plan
  DROP COLUMN video_duration;
ALTER TABLE training_plan
  DROP COLUMN audio_duration;
ALTER TABLE training_plan
  DROP COLUMN email_emergency;