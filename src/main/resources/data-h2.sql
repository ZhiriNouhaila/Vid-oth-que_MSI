use VIDEOTHEQUE_SCHEMA;


insert into actor(id, name, bio) 
	values(1, 'actor1','bio1');
	
insert into category(id, name) 
	values(1, 'category1');

insert into film(id, title, "year", description, release_date, duration, actor_id,category_id ) 
	values(1, 'Title',2023,'Description','2015-05-04', 2000, 1, 1);