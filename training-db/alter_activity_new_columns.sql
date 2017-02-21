
ALTER TABLE activity ADD COLUMN state_id integer, ADD COLUMN creation_date timestamp without time zone, ADD COLUMN last_update timestamp without time zone,
ADD COLUMN user_create integer, ADD COLUMN user_update integer;
ALTER TABLE physiological_capacity ADD COLUMN state_id integer, ADD COLUMN creation_date timestamp without time zone, ADD COLUMN last_update timestamp without time zone,
ADD COLUMN user_create integer, ADD COLUMN user_update integer;

tipo de letra numeros
si el dashboard va a ocupar la resolución por defecto o va tener scroll
tamaños de fuente
como se muestra las notificaciones de la campanita (diseño)

select sum(cast(b.value as double precision)) val, a.label , a.activity_performance_metafield_id,
(select count(activity_performance_metafield_id) from user_activity_performance where user_id = 336 and activity_performance_metafield_id=1) activities
from activity_performance_metafield a,
     user_activity_performance b
where a.activity_performance_metafield_id = b.activity_performance_metafield_id
and b.user_id = 336
and a.activity_performance_metafield_id != 1
group by a.activity_performance_metafield_id