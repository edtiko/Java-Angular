/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     03/08/2016 10:30:40 a. m.                    */
/*==============================================================*/


drop table activity;

drop table brand;

drop table city;

drop table country;

drop table data_type;

drop table dcf;

drop table dcf_detail;

drop table discipline;

drop table discipline_user;

drop table equipment_user_profile;

drop table federal_state;

drop table injury;

drop table membership;

drop table membership_discount;

drop table membership_price;

drop table membership_promotion;

drop table membership_user;

drop table modality;

drop table objective;

drop table option_type;

drop table payment_method;

drop table physiological_capacity;

drop table question;

drop table questionnaire;

drop table questionnaire_category;

drop table questionnaire_question;

drop table questionnaire_response;

drop table questionnaire_resp_history;

drop table questionnaire_user;

drop table question_option;

drop table replace_activity;

drop table response_option;

drop table response_type;

drop table role;

drop table role_user;

drop table sport;

drop table sport_equipment;

drop table sport_equipment_type;

drop table state;

drop table training_plan;

drop table training_plan_user;

drop table training_plan_workout;

drop table user_availability;

drop table user_profile;

drop table user_sport;

drop table user_training;

drop table video_user;

/*==============================================================*/
/* Table: activity                                              */
/*==============================================================*/
create table activity (
   activity_id          serial              not null,
   physiological_capacity_id integer              null,
   modality_id          integer              null,
   objective_id         integer              null,
   name                 varchar(800)         not null,
   description          varchar(1000)        null,
   constraint pk_activity primary key (activity_id)
);

/*==============================================================*/
/* Table: brand                                                 */
/*==============================================================*/
create table brand (
   brand_id             serial              not null,
   name                 varchar(500)         not null,
   constraint pk_brand primary key (brand_id)
);

/*==============================================================*/
/* Table: city                                                  */
/*==============================================================*/
create table city (
   city_id              serial              not null,
   federal_state_id     integer              null,
   name                 varchar(200)         null,
   constraint pk_city primary key (city_id)
);

/*==============================================================*/
/* Table: country                                               */
/*==============================================================*/
create table country (
   country_id           serial              not null,
   name                 varchar(200)         null,
   constraint pk_country primary key (country_id)
);

/*==============================================================*/
/* Table: data_type                                             */
/*==============================================================*/
create table data_type (
   data_type_id         serial              not null,
   name                 varchar(200)         not null,
   constraint pk_data_type primary key (data_type_id)
);

/*==============================================================*/
/* Table: dcf                                                   */
/*==============================================================*/
create table dcf (
   dcf_id               serial              not null,
   objective_id         integer              null,
   modality_id          integer              null,
   pattern              text                 not null,
   sessions             integer              not null,
   constraint pk_dcf primary key (dcf_id)
);

comment on table dcf is
'Esta tabla guardará la Distribución porcentual de las Capacidades Fisiológicas para la generación de los planes';

/*==============================================================*/
/* Table: dcf_detail                                            */
/*==============================================================*/
create table dcf_detail (
   dcf_detail_id        serial              not null,
   physiological_capacity_id integer              null,
   dcf_id               integer              null,
   percentage           integer              not null,
   constraint pk_dcf_detail primary key (dcf_detail_id)
);

/*==============================================================*/
/* Table: discipline                                            */
/*==============================================================*/
create table discipline (
   discipline_id        serial              not null,
   name                 varchar(100)         null,
   description          varchar(200)         null,
   constraint pk_discipline primary key (discipline_id)
);

/*==============================================================*/
/* Table: discipline_user                                       */
/*==============================================================*/
create table discipline_user (
   discipline_user_id   serial               not null,
   discipline           integer              null,
   user_id              integer              null,
   constraint pk_discipline_user primary key (discipline_user_id)
);

/*==============================================================*/
/* Table: equipment_user_profile                                */
/*==============================================================*/
create table equipment_user_profile (
   equipment_user_profile_id serial               not null,
   user_profile_id      integer              null,
   sport_equipment_id   integer              null,
   model_equipment_id   integer              null,
   constraint pk_equipment_user_profile primary key (equipment_user_profile_id)
);

/*==============================================================*/
/* Table: federal_state                                         */
/*==============================================================*/
create table federal_state (
   federal_state_id     serial              not null,
   country_id           integer              null,
   name                 varchar(200)         null,
   constraint pk_federal_state primary key (federal_state_id)
);

/*==============================================================*/
/* Table: injury                                                */
/*==============================================================*/
create table injury (
   injury_id            serial              not null,
   injury_parent_id     integer              null,
   name                 varchar(200)         not null,
   constraint pk_injury primary key (injury_id)
);

/*==============================================================*/
/* Table: membership                                            */
/*==============================================================*/
create table membership (
   membership_id        serial              not null,
   state_id             integer              null,
   membership_promo_id  integer              null,
   name                 varchar(200)         not null,
   description          varchar(5000)        null,
   initial_date         date                 not null,
   creation_date        date                 not null,
   constraint pk_membership primary key (membership_id)
);

/*==============================================================*/
/* Table: membership_discount                                   */
/*==============================================================*/
create table membership_discount (
   membership_discount_id serial              not null,
   discount             decimal(10,2)        not null,
   percentage           decimal(10,2)        null,
   star_date           date                 null,
   end_date             date                 null,
   creation_date        date                 null,
   constraint pk_membership_discount primary key (membership_discount_id)
);

/*==============================================================*/
/* Table: membership_price                                      */
/*==============================================================*/
create table membership_price (
   membership_price_id  serial              not null,
   membership_id        integer              not null,
   membership_discount_id integer              null,
   price                decimal(10,2)        not null,
   duration             decimal(10,2)        null,
   star_date           date                 not null,
   end_date             date                 null,
   creation_date        date                 null,
   constraint pk_membership_price primary key (membership_price_id)
);

comment on column membership_price.star_date is
'Indica si la fecha es NULL el precio va a estar vigente';

/*==============================================================*/
/* Table: membership_promotion                                  */
/*==============================================================*/
create table membership_promotion (
   membership_promo_id  serial              not null,
   code                 varchar(200)         not null,
   percentage           decimal(10,1)        null,
   type                 varchar(200)         null,
   value                varchar(200)         not null,
   star_date           date                 null,
   end_date             date                 null,
   used_date            date                 null,
   published_date       date                 null,
   creation_date        date                 null,
   constraint pk_membership_promotion primary key (membership_promo_id)
);

/*==============================================================*/
/* Table: membership_user                                       */
/*==============================================================*/
create table membership_user (
   membership_user_id   serial             not null,
   user_id              integer              null,
   membership_id        integer              null,
   payment_method_id    integer              null,
   state_id             integer              null,
   constraint pk_membership_user primary key (membership_user_id)
);

/*==============================================================*/
/* Table: modality                                              */
/*==============================================================*/
create table modality (
   modality_id          serial              not null,
   discipline_id        integer              null,
   name                 varchar(200)         not null,
   constraint pk_modality primary key (modality_id)
);

/*==============================================================*/
/* Table: objective                                             */
/*==============================================================*/
create table objective (
   objective_id         serial              not null,
   name                 varchar(200)         not null,
   level                short                not null,
   constraint pk_objective primary key (objective_id)
);

/*==============================================================*/
/* Table: option_type                                           */
/*==============================================================*/
create table option_type (
   option_type_id       serial              not null,
   name                 varchar(100)         not null,
   constraint pk_option_type primary key (option_type_id)
);

comment on table option_type is
'options like (conditional, color)';

/*==============================================================*/
/* Table: payment_method                                        */
/*==============================================================*/
create table payment_method (
   payment_metod_id     serial              not null,
   country_id           integer              not null,
   name                 varchar(100)         not null,
   description          varchar(200)         null,
   card_number          varchar(200)         null,
   expiration_date      date                 null,
   cvv_code             varchar(10)          null,
   franchise            varchar(100)         null,
   creation_date        date                 null,
   constraint pk_payment_method primary key (payment_metod_id)
);

/*==============================================================*/
/* Table: physiological_capacity                                */
/*==============================================================*/
create table physiological_capacity (
   physiological_capacity_id serial              not null,
   name                 varchar(200)         not null,
   code                 varchar(5)           null,
   constraint pk_physiological_capacity primary key (physiological_capacity_id)
);

/*==============================================================*/
/* Table: question                                              */
/*==============================================================*/
create table question (
   question_id          serial              not null,
   data_type_id         integer              null,
   state_id             integer              null,
   name                 varchar(2000)        not null,
   description          varchar(100)         null,
   unit                 varchar(100)         null,
   ind_additional       integer              not null,
   question_type        varchar(1)           not null,
   creation_date        date                 not null,
   constraint pk_question primary key (question_id)
);

comment on column question.ind_additional is
'0 Standard, 1 Additional';

comment on column question.question_type is
'M - Mandatory
O - Optional
A - Automated';

/*==============================================================*/
/* Table: questionnaire                                         */
/*==============================================================*/
create table questionnaire (
   questionnaire_id     serial              not null,
   state_id             integer              null,
   discipline_id        integer              null,
   name                 varchar(500)         not null,
   creation_date        date                 not null,
   constraint pk_questionnaire primary key (questionnaire_id)
);

/*==============================================================*/
/* Table: questionnaire_category                                */
/*==============================================================*/
create table questionnaire_category (
   questionnaire_category_id serial              not null,
   state_id             integer              null,
   parent_questionnaire_category_i integer              null,
   name                 varchar(500)         not null,
   description          varchar(500)         not null,
   creation_date        date                 null,
   constraint pk_questionnaire_category primary key (questionnaire_category_id)
);

/*==============================================================*/
/* Table: questionnaire_question                                */
/*==============================================================*/
create table questionnaire_question (
   questionnaire_question_id serial              not null,
   questionnaire_id     integer              null,
   questionnaire_category_id integer              null,
   question_id          integer              null,
   state_id             integer              null,
   question_order       integer              not null,
   creation_date        date                 not null,
   constraint pk_questionnaire_question primary key (questionnaire_question_id)
);

/*==============================================================*/
/* Table: questionnaire_response                                */
/*==============================================================*/
create table questionnaire_response (
   questionnaire_response_id serial              not null,
   questionnaire_question_id integer              null,
   response_type_id     integer              null,
   question_option_id   integer              null,
   user_id              integer              null,
   response             varchar(2000)        null,
   creation_date        date                 null,
   constraint pk_questionnaire_response primary key (questionnaire_response_id)
);

/*==============================================================*/
/* Table: questionnaire_resp_history                            */
/*==============================================================*/
create table questionnaire_resp_history (
   questionnaire_resp_history_id serial              not null,
   questionnaire_response_id integer              null,
   question_option_id   integer              null,
   user_questionnaire_id integer              null,
   creation_date        date                 null,
   constraint pk_questionnaire_resp_history primary key (questionnaire_resp_history_id)
);

/*==============================================================*/
/* Table: questionnaire_user                                    */
/*==============================================================*/
create table questionnaire_user (
   questionnaire_user_id serial              not null,
   questionnaire_id     integer              null,
   user_training_id     integer              null,
   constraint pk_questionnaire_user primary key (questionnaire_user_id)
);

/*==============================================================*/
/* Table: question_option                                       */
/*==============================================================*/
create table question_option (
   question_option_id   serial              not null,
   question_id          integer              null,
   option_type_id       integer              not null,
   state_id             integer              null,
   parent_question_option_id integer              null,
   name                 varchar(2000)        not null,
   description          varchar(2000)        null,
   constraint pk_question_option primary key (question_option_id)
);

/*==============================================================*/
/* Table: replace_activity                                      */
/*==============================================================*/
create table replace_activity (
   replace_activity_id  serial not null,
   activity_id          integer              null,
   name                 varchar(800)         not null,
   constraint pk_replace_activity primary key (replace_activity_id)
);

/*==============================================================*/
/* Table: response_option                                       */
/*==============================================================*/
create table response_option (
   response_option_id   serial              not null,
   questionnaire_response_id integer              null,
   question_option_id   integer              null,
   constraint pk_response_option primary key (response_option_id)
);

/*==============================================================*/
/* Table: response_type                                         */
/*==============================================================*/
create table response_type (
   response_type_id     serial              not null,
   name                 varchar(100)         not null,
   constraint pk_response_type primary key (response_type_id)
);

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role (
   role_id              serial              not null,
   name                 varchar(100)         not null,
   constraint pk_role primary key (role_id)
);

/*==============================================================*/
/* Table: role_user                                             */
/*==============================================================*/
create table role_user (
   role_user_id         serial              not null,
   user_id              integer              null,
   role_id              integer              null,
   constraint pk_role_user primary key (role_user_id)
);

/*==============================================================*/
/* Table: sport                                                 */
/*==============================================================*/
create table sport (
   sport_id             serial              not null,
   name                 varchar(200)         not null,
   constraint pk_sport primary key (sport_id)
);

/*==============================================================*/
/* Table: sport_equipment                                       */
/*==============================================================*/
create table sport_equipment (
   sport_equipment_id   serial              not null,
   sport_equipment_type_id integer              null,
   brand_id             integer              null,
   name                 varchar(200)         not null,
   constraint pk_sport_equipment primary key (sport_equipment_id)
);

/*==============================================================*/
/* Table: sport_equipment_type                                  */
/*==============================================================*/
create table sport_equipment_type (
   sport_equipment_type_id serial              not null,
   name                 varchar(200)         not null,
   constraint pk_sport_equipment_type primary key (sport_equipment_type_id)
);

/*==============================================================*/
/* Table: state                                                 */
/*==============================================================*/
create table state (
   state_id             serial              not null,
   name                 varchar(100)         not null,
   description          varchar(200)         null,
   constraint pk_state primary key (state_id)
);

/*==============================================================*/
/* Table: training_plan                                         */
/*==============================================================*/
create table training_plan (
   training_plan_id     serial               not null,
   name                 varchar(500)         not null,
   description          varchar(5000)        null,
   duration             decimal(10,2)        null,
   video_count          integer              not null default 0,
   video_emergency      integer              not null default 0,      
   video_duration       integer              not null default 0,
   message_count        integer              not null default 0,
   message_emergency    integer              not null default 0,
   email_count          integer              not null default 0,
   email_emergency      integer              not null default 0,
   audio_count          integer              not null default 0,
   audio_emergency      integer              not null default 0,
   audio_duration       integer              not null default 0,    
   creation_date        date                 not null,
   end_date             date                 not null,
   constraint pk_training_plan primary key (training_plan_id)
);

/*==============================================================*/
/* Table: training_plan_user                                    */
/*==============================================================*/
create table training_plan_user (
   training_plan_user_id serial               not null,
   user_id              integer              null,
   training_plan_id     integer              null,
   state_id             integer              null,
   constraint pk_training_plan_user primary key (training_plan_user_id)
);

/*==============================================================*/
/* Table: training_plan_workout                                 */
/*==============================================================*/
create table training_plan_workout (
   training_plan_workout_id serial               not null,
   training_plan_id     integer              not null,
   activity_id          integer              null,
   manual_activity_id   integer              null,
   workout_date         date                 not null,
   constraint pk_training_plan_workout primary key (training_plan_workout_id)
);

/*==============================================================*/
/* Table: user_availability                                     */
/*==============================================================*/
create table user_availability (
   user_availability_id serial               not null,
   user_profile_id      integer              null,
   monday               boolean              not null,
   tuesday              boolean              not null,
   wednesday            boolean              not null,
   thursday             boolean              not null,
   friday               boolean              not null,
   saturday             boolean              not null,
   sunday               boolean              not null,
   constraint pk_user_availability primary key (user_availability_id)
);

/*==============================================================*/
/* Table: user_profile                                          */
/*==============================================================*/
create table user_profile (
   user_profile_id      serial               not null,
   user_id              integer              null,
   objective_id         integer              null,
   modality_id          integer              null,
   ind_pulsometer       varchar(1)           null,
   ind_potentiometer    varchar(1)           null,
   age_sport            integer              null,
   ppm                  decimal              null,
   vo2_running          integer   null,
   vo2_ciclismo          integer   null,
   ind_power                decimal              null,
   sports_achievements  varchar(1000)        null,
   about_me             varchar(1000)        null,
   environment_id       integer              not null,
   weather_id           integer              not null,
   constraint pk_user_profile primary key (user_profile_id)
);

comment on column user_profile.ind_pulsometer is
'(0) NO (1) SI';

comment on column user_profile.ind_potentiometer is
'(0) NO (1) SI';

comment on column user_profile.age_sport is
'(0) NO (1) SI';

comment on column user_profile.ppm is
'(0) NO (1) SI';

comment on column user_profile.power is
'(0) NO (1) SI';

comment on column user_profile.sports_achievements is
'(0) NO (1) SI';

comment on column user_profile.about_me is
'(0) NO (1) SI';

/*==============================================================*/
/* Table: user_sport                                            */
/*==============================================================*/
create table user_sport (
   user_sport_id        serial               not null,
   user_profile_id      integer              null,
   sport_id             integer              null,
   constraint pk_user_sport primary key (user_sport_id)
);

/*==============================================================*/
/* Table: user_training                                         */
/*==============================================================*/
create table user_training (
   user_id              serial not null,
   city_id              integer              null,
   state_id             integer              null,
   star_id              integer              null,
   login                varchar(100)         not null,
   password             varchar(100)         null,
   name                 varchar(200)         not null,
   second_name          varchar(200)         not null,
   last_name            varchar(200)         null,
   email                varchar(100)         null,
   birth_date           date                 null,
   sex                  varchar(1)           null,
   weight               decimal              null,
   phone                varchar(50)          null,
   cellphone            varchar(50)          null,
   address              varchar(100)         null,
   postal_code          varchar(50)          null,
   profile_photo        bytea                null,
   facebook_page        varchar(200)         null,
   twitter_page        varchar(200)         null,
   instagram_page        varchar(200)         null,
   web_page             varchar(200)         null,
   ind_metric_sys       varchar(1)           not null,
   creation_date        date                 not null,
   constraint pk_user_training primary key (user_id)
);

comment on column user_training.star_id is
'Es la estrella del deporte asociada al usuario';

comment on column user_training.ind_metric_sys is
'(0) Ingles (1) Metrico Decimal';

/*==============================================================*/
/* Table: video_user                                            */
/*==============================================================*/
create table video_user (
   video_user_id        serial              not null,
   user_id              integer              not null,
   state_id             integer              null,
   url                  varchar(200)         not null,
   creation_date        date                 null,
   constraint pk_video_user primary key (video_user_id)
);

/*==============================================================*/
/* Table: model_equipment                                            */
/*==============================================================*/
create table model_equipment (
   model_equipment_id        serial              not null,
   sport_equipment_id              integer not null,
   name                  varchar(500)              not null,
   state_id             integer              null,
   creation_date        date                 null,
   constraint pk_model_equipment primary key (model_equipment_id)
);

/*==============================================================*/
/* Table: environment                                           */
/*==============================================================*/
create table environment (
   environment_id        serial              not null,
   name                  varchar(500)              not null,
   state_id             integer              null,
   creation_date        date                 null,
   constraint pk_environment primary key (environment_id)
);

/*==============================================================*/
/* Table: weather                                               */
/*==============================================================*/
create table weather (
   weather_id        serial              not null,
   name                  varchar(500)              not null,
   state_id             integer              null,
   creation_date        date                 null,
   constraint pk_weather primary key (weather_id)
);

/*==============================================================*/
/* Table: star_team                                            */
/*==============================================================*/
create table star_team (
   star_team_id         serial  not null,
   star_user_id         integer not null,
   coach_user_id         integer not null,
   state_id              integer     null,
   creation_date         date        null,
   constraint pk_star_team primary key (star_team_id)
);

/*==============================================================*/
/* Table: coach_assigned_plan                                   */
/*==============================================================*/
create table coach_assigned_plan (
   coach_assigned_plan_id     serial             not null,
   star_team_id              integer            not null,
   training_plan_user_id      integer            not null,
   state_id                   integer            null,
   creation_date              date               null,
   constraint pk_coach_assigned_plan primary key (coach_assigned_plan_id)
);

/*==============================================================*/
/* Table: plan_message                                          */
/*==============================================================*/
create table plan_message (
   plan_message_id           serial  not null,
   coach_assigned_plan_id    integer,
   coach_ext_athlete_id      integer, 
   message                   varchar(5000),
   message_user_id           integer not null,
   receiving_user_id         integer null, 
   readed                    boolean  default false,
   state_id                  integer  null,
   creation_date             timestamp without time zone,
   constraint pk_plan_message primary key (plan_message_id)
);

/*==============================================================*/
/* Table: manual_activity                                          */
/*==============================================================*/
create table manual_activity (
   manual_activity_id           serial  not null,
   name                         varchar(800) not null,
   description                  varchar(1000),
   sport_id                integer not null,
   user_id                    integer not null,
   state_id                  integer  null,
   creation_date             date     null,
   constraint pk_manual_activity primary key (manual_activity_id)
);


/*==============================================================*/
/* Table: plan_video                                          */
/*==============================================================*/
create table plan_video (
   plan_video_id             serial  not null,
   name                      varchar(500) not null,
   duration                  integer,
   video_path                varchar(1000) not null,
   from_user_id              integer not null,
   to_user_id                integer not null,    
   coach_assigned_plan_id    integer null,   
   coach_ext_athlete_id      integer, 
   readed boolean DEFAULT false,
   creation_date             timestamp without time zone,
   constraint pk_plan_video primary key (plan_video_id)
);


/*==============================================================*/
/* Table: plan_audio                                         */
/*==============================================================*/
create table plan_audio (
   plan_audio_id             serial  not null,
   name                      varchar(1000) not null,
   from_user_id              integer not null,
   to_user_id                integer not null,    
   coach_assigned_plan_id    integer null,   
   coach_ext_athlete_id      integer, 
   readed boolean not null DEFAULT false,
   creation_date             timestamp without time zone,
   constraint pk_plan_audio primary key (plan_audio_id)
);

7436677 ext 310 cristian pelaez

create table coach_ext_athlete(
coach_ext_athlete_id  serial not null,
training_plan_user_id integer not null, --id plan coach externo
user_training_id integer not null, --id referencia usuario atleta
state_id integer not null, -- id estado ej: retirado, pendiente..
creation_date timestamp without time zone, 
constraint pk_coach_ext_athlete primary key (coach_ext_athlete_id)
);

alter table coach_ext_athlete
add constraint fk_coach_ext_athlete_reference_training_plan_user foreign key (training_plan_user_id)
references training_plan_user(training_plan_user_id)
on delete restrict on update restrict;

alter table coach_ext_athlete
add constraint fk_coach_ext_athlete_reference_user_training foreign key (user_training_id)
references user_training(user_id)
on delete restrict on update restrict;

alter table coach_ext_athlete
add constraint fk_coach_ext_athlete_reference_state foreign key (state_id)
references state(state_id)
on delete restrict on update restrict;

alter table plan_video
add constraint fk_plan_video_from_reference_user foreign key (from_user_id)
references user_training(user_id)
on delete restrict on update restrict;

alter table plan_video
add constraint fk_plan_video_to_reference_user foreign key (to_user_id)
references user_training(user_id)
on delete restrict on update restrict;

alter table plan_video
add constraint fk_plan_video_ref_coach_assigned_plan foreign key (coach_assigned_plan_id)
references coach_assigned_plan(coach_assigned_plan_id)
on delete restrict on update restrict;

alter table plan_video
add constraint fk_plan_video_ref_coach_ext_athlete foreign key (coach_ext_athlete_id)
references coach_ext_athlete(coach_ext_athlete_id)
on delete restrict on update restrict;

alter table plan_audio
add constraint fk_plan_audio_from_reference_user foreign key (from_user_id)
references user_training(user_id)
on delete restrict on update restrict;

alter table plan_audio
add constraint fk_plan_video_to_reference_user foreign key (to_user_id)
references user_training(user_id)
on delete restrict on update restrict;

alter table plan_audio
add constraint fk_plan_audio_ref_coach_assigned_plan foreign key (coach_assigned_plan_id)
references coach_assigned_plan(coach_assigned_plan_id)
on delete restrict on update restrict;

alter table plan_audio
add constraint fk_plan_audio_ref_coach_ext_athlete foreign key (coach_ext_athlete_id)
references coach_ext_athlete(coach_ext_athlete_id)
on delete restrict on update restrict;

alter table star_team
add constraint fk_star_team_star_reference_user foreign key (star_user_id)
references user_training(user_id)
on delete restrict on update restrict;

alter table star_team
add constraint fk_star_team_coach_reference_user foreign key (coach_user_id)
references user_training(user_id)
on delete restrict on update restrict;

alter table coach_assigned_plan
add constraint fk_coach_assigned_plan_reference_star_team foreign key (star_team_id)
references star_team (star_team_id)
on delete restrict on update restrict;
	  
alter table coach_assigned_plan
add constraint fk_coach_assigned_plan_reference_plan_user foreign key (training_plan_user_id)
references training_plan_user (training_plan_user_id)
on delete restrict on update restrict;

alter table plan_message
add constraint fk_plan_message_reference_assigned_plan foreign key (coach_assigned_plan_id)
references coach_assigned_plan (coach_assigned_plan_id)
on delete restrict on update restrict;

alter table plan_message
add constraint fk_plan_message_reference_user foreign key (message_user_id)
references user_training (user_id)
on delete restrict on update restrict;

alter table plan_message
add constraint fk_plan_message_ref_coach_ext_athlete foreign key (coach_ext_athlete_id)
references coach_ext_athlete (coach_ext_athlete_id)
on delete restrict on update restrict;

alter table model_equipment
   add constraint fk_model_equipment_reference_sport_eq foreign key (sport_equipment_id)
      references sport_equipment (sport_equipment_id)
      on delete restrict on update restrict;

alter table activity
   add constraint fk_activity_reference_physiolo foreign key (physiological_capacity_id)
      references physiological_capacity (physiological_capacity_id)
      on delete restrict on update restrict;

alter table activity
   add constraint fk_activity_reference_modality foreign key (modality_id)
      references modality (modality_id)
      on delete restrict on update restrict;
	  
alter table manual_activity
   add constraint fk_manual_activity_reference_sport foreign key (sport_id)
      references sport (sport_id)
      on delete restrict on update restrict;

	  alter table manual_activity
   add constraint fk_manual_activity_reference_user foreign key (user_id)
      references user_training (user_id)
      on delete restrict on update restrict;

alter table activity
   add constraint fk_activity_reference_objectiv foreign key (objective_id)
      references objective (objective_id)
      on delete restrict on update restrict;

alter table city
   add constraint fk_city_fede_state foreign key (federal_state_id)
      references federal_state (federal_state_id)
      on delete restrict on update restrict;

alter table dcf
   add constraint fk_dcf_reference_objectiv foreign key (objective_id)
      references objective (objective_id)
      on delete restrict on update restrict;

alter table dcf
   add constraint fk_dcf_reference_modality foreign key (modality_id)
      references modality (modality_id)
      on delete restrict on update restrict;

alter table dcf_detail
   add constraint fk_dcf_deta_reference_physiolo foreign key (physiological_capacity_id)
      references physiological_capacity (physiological_capacity_id)
      on delete restrict on update restrict;

alter table dcf_detail
   add constraint fk_dcf_deta_reference_dcf foreign key (dcf_id)
      references dcf (dcf_id)
      on delete restrict on update restrict;

alter table discipline_user
   add constraint fk_disc_user_disc foreign key (discipline)
      references discipline (discipline_id)
      on delete restrict on update restrict;

alter table discipline_user
   add constraint fk_disc_user_user foreign key (user_id)
      references user_training (user_id)
      on delete restrict on update restrict;

alter table equipment_user_profile
   add constraint fk_equipmen_reference_user_pro foreign key (user_profile_id)
      references user_profile (user_profile_id)
      on delete restrict on update restrict;

alter table equipment_user_profile
   add constraint fk_equipmen_reference_sport_eq foreign key (sport_equipment_id)
      references sport_equipment (sport_equipment_id)
      on delete restrict on update restrict;
	  
alter table equipment_user_profile
   add constraint fk_equipmen_reference_model_eq foreign key (model_equipment_id)
      references model_equipment (model_equipment_id)
      on delete restrict on update restrict;

alter table federal_state
   add constraint fk_fede_state_country foreign key (country_id)
      references country (country_id)
      on delete restrict on update restrict;

alter table injury
   add constraint fk_injury_reference_injury foreign key (injury_parent_id)
      references injury (injury_id)
      on delete restrict on update restrict;

alter table membership
   add constraint fk_membership_state foreign key (state_id)
      references state (state_id)
      on delete restrict on update restrict;

alter table membership
   add constraint fk_member_member_promo foreign key (membership_promo_id)
      references membership_promotion (membership_promo_id)
      on delete restrict on update restrict;

alter table membership_price
   add constraint fk_mber_price_mber_disc foreign key (membership_discount_id)
      references membership_discount (membership_discount_id)
      on delete restrict on update restrict;

alter table membership_price
   add constraint fk_member_price_member foreign key (membership_id)
      references membership (membership_id)
      on delete restrict on update restrict;

alter table membership_user
   add constraint fk_membersh_fk_member_membersh foreign key (membership_id)
      references membership (membership_id)
      on delete restrict on update restrict;

alter table membership_user
   add constraint fk_member_user_user foreign key (user_id)
      references user_training (user_id)
      on delete restrict on update restrict;

alter table membership_user
   add constraint fk_mmbr_user_pay_meth foreign key (payment_method_id)
      references payment_method (payment_metod_id)
      on delete restrict on update restrict;

alter table membership_user
   add constraint fk_mmbr_user_state foreign key (state_id)
      references state (state_id)
      on delete restrict on update restrict;

alter table modality
   add constraint fk_modality_reference_discipli foreign key (discipline_id)
      references discipline (discipline_id)
      on delete restrict on update restrict;

alter table payment_method
   add constraint fk_pay_meth_country foreign key (country_id)
      references country (country_id)
      on delete restrict on update restrict;

alter table question
   add constraint fk_question_data_type foreign key (data_type_id)
      references data_type (data_type_id)
      on delete restrict on update restrict;

alter table question
   add constraint fk_question_reference_state foreign key (state_id)
      references state (state_id)
      on delete restrict on update restrict;

alter table questionnaire
   add constraint fk_question_questionn_discipli foreign key (discipline_id)
      references discipline (discipline_id)
      on delete restrict on update restrict;

alter table questionnaire
   add constraint fk_question_reference_state foreign key (state_id)
      references state (state_id)
      on delete restrict on update restrict;

alter table questionnaire_category
   add constraint fk_question_reference_state foreign key (state_id)
      references state (state_id)
      on delete restrict on update restrict;

alter table questionnaire_category
   add constraint fk_question_reference_question foreign key (parent_questionnaire_category_i)
      references questionnaire_category (questionnaire_category_id)
      on delete restrict on update restrict;

alter table questionnaire_question
   add constraint fk_questna_qt_questna foreign key (questionnaire_id)
      references questionnaire (questionnaire_id)
      on delete restrict on update restrict;

alter table questionnaire_question
   add constraint fk_questna_qt_questna_categ foreign key (questionnaire_category_id)
      references questionnaire_category (questionnaire_category_id)
      on delete restrict on update restrict;

alter table questionnaire_question
   add constraint fk_questna_quest_quest foreign key (question_id)
      references question (question_id)
      on delete restrict on update restrict;

alter table questionnaire_question
   add constraint fk_question_reference_state foreign key (state_id)
      references state (state_id)
      on delete restrict on update restrict;

alter table questionnaire_response
   add constraint fk_questna_resp_questna_qt foreign key (questionnaire_question_id)
      references questionnaire_question (questionnaire_question_id)
      on delete restrict on update restrict;

alter table questionnaire_response
   add constraint fk_questna_resp_quest_opt foreign key (question_option_id)
      references question_option (question_option_id)
      on delete restrict on update restrict;

alter table questionnaire_response
   add constraint " fk_questna_resp_resp_type" foreign key (response_type_id)
      references response_type (response_type_id)
      on delete restrict on update restrict;

alter table questionnaire_response
   add constraint fk_question_reference_user_tra foreign key (user_id)
      references user_training (user_id)
      on delete restrict on update restrict;

alter table questionnaire_user
   add constraint fk_question_reference_question foreign key (questionnaire_id)
      references questionnaire (questionnaire_id)
      on delete restrict on update restrict;

alter table questionnaire_user
   add constraint fk_question_reference_user_tra foreign key (user_training_id)
      references user_training (user_id)
      on delete restrict on update restrict;

alter table question_option
   add constraint fk_quest_option_option_type foreign key (option_type_id)
      references option_type (option_type_id)
      on delete restrict on update restrict;

alter table question_option
   add constraint fk_quest_option_quest foreign key (question_id)
      references question (question_id)
      on delete restrict on update restrict;

alter table question_option
   add constraint fk_question_reference_state foreign key (state_id)
      references state (state_id)
      on delete restrict on update restrict;

alter table question_option
   add constraint fk_question_reference_question foreign key (parent_question_option_id)
      references question_option (question_option_id)
      on delete restrict on update restrict;

alter table replace_activity
   add constraint fk_replace__reference_activity foreign key (activity_id)
      references activity (activity_id)
      on delete restrict on update restrict;

alter table response_option
   add constraint fk_resp_option_quest_opt foreign key (question_option_id)
      references question_option (question_option_id)
      on delete restrict on update restrict;

alter table response_option
   add constraint fk_resp_opt_questna_resp foreign key (questionnaire_response_id)
      references questionnaire_response (questionnaire_response_id)
      on delete restrict on update restrict;

alter table role_user
   add constraint fk_role_user_role foreign key (role_id)
      references role (role_id)
      on delete restrict on update restrict;

alter table role_user
   add constraint fk_role_user_user foreign key (user_id)
      references user_training (user_id)
      on delete restrict on update restrict;

alter table sport_equipment
   add constraint fk_sport_eq_reference_sport_eq foreign key (sport_equipment_type_id)
      references sport_equipment_type (sport_equipment_type_id)
      on delete restrict on update restrict;

alter table sport_equipment
   add constraint fk_sport_eq_reference_brand foreign key (brand_id)
      references brand (brand_id)
      on delete restrict on update restrict;
	 

alter table training_plan_user
   add constraint fk_train_plan_user_user foreign key (user_id)
      references user_training (user_id)
      on delete restrict on update restrict;

alter table training_plan_user
   add constraint fk_train_pln_user_train_pln foreign key (training_plan_id)
      references training_plan (training_plan_id)
      on delete restrict on update restrict;

alter table training_plan_user
   add constraint fk_training_reference_state foreign key (state_id)
      references state (state_id)
      on delete restrict on update restrict;

alter table training_plan_workout
   add constraint fk_train_plan_wkout_train foreign key (training_plan_id)
      references training_plan (training_plan_id)
      on delete restrict on update restrict;

alter table training_plan_workout
   add constraint fk_training_reference_activity foreign key (activity_id)
      references activity (activity_id)
      on delete restrict on update restrict;
	  
	  alter table training_plan_workout
   add constraint fk_training_reference_manual_activity foreign key (manual_activity_id)
      references manual_activity (manual_activity_id)
      on delete restrict on update restrict;

alter table user_availability
   add constraint fk_user_ava_reference_user_pro foreign key (user_profile_id)
      references user_profile (user_profile_id)
      on delete restrict on update restrict;

alter table user_profile
   add constraint fk_user_pro_reference_user_tra foreign key (user_id)
      references user_training (user_id)
      on delete restrict on update restrict;

alter table user_profile
   add constraint fk_user_pro_reference_objectiv foreign key (objective_id)
      references objective (objective_id)
      on delete restrict on update restrict;

alter table user_profile
   add constraint fk_user_pro_reference_modality foreign key (modality_id)
      references modality (modality_id)
      on delete restrict on update restrict;
	  
alter table user_profile
   add constraint fk_user_pro_reference_environment foreign key (environment_id)
      references environment (environment_id)
      on delete restrict on update restrict;
	  
alter table user_profile
   add constraint fk_user_pro_reference_weather foreign key (weather_id)
      references weather (weather_id)
      on delete restrict on update restrict;	  

alter table user_sport
   add constraint fk_user_spo_reference_user_pro foreign key (user_profile_id)
      references user_profile (user_profile_id)
      on delete restrict on update restrict;

alter table user_sport
   add constraint fk_user_spo_reference_sport foreign key (sport_id)
      references sport (sport_id)
      on delete restrict on update restrict;

alter table user_training
   add constraint fk_user_city foreign key (city_id)
      references city (city_id)
      on delete restrict on update restrict;

alter table user_training
   add constraint fk_user_state foreign key (state_id)
      references state (state_id)
      on delete restrict on update restrict;

alter table user_training
   add constraint fk_user_tra_reference_user_tra foreign key (star_id)
      references user_training (user_id)
      on delete restrict on update restrict;

alter table video_user
   add constraint fk_video_user_user foreign key (user_id)
      references user_training (user_id)
      on delete restrict on update restrict;

alter table video_user
   add constraint fk_video_us_reference_state foreign key (state_id)
      references state (state_id)
      on delete restrict on update restrict;

