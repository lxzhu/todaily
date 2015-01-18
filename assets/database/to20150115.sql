create table issue_slim
(
	_ID integer primary key autoincrement ,
	issue_id integer not null,
	location_id integer ,
	start_datetime datetime not null,
	end_datetime datetime,
	issue_slim_type_id int not null,
	Issue_slim_blob blob,
	create_datetime datetime not null,
	update_datetime datetime
);
GO