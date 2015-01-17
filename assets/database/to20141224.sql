/**
 * GO in a single line or any line end with ';GO' is treated as ending of statement.
 */
create table location(
	_ID integer primary key autoincrement,
	latitude float,
	longitude float,
	altitude float,
	speed float,
	unix_time integer,
	street nvarchar(200),
	create_datetime datetime not null,
	update_datetime datetime 
);
GO

create table issue(
	_ID integer primary key autoincrement,
	title nvarchar(50) not null,
	description nvarchar(2000),
	create_date_time datetime not null,
	update_date_time datetime,
	plan_start_date_time datetime,
	plan_end_date_time datetime, 
	actual_start_date_time datetime,
	actual_end_date_time datetime, 
	important_level int not null 
);
GO
create table issue_slim_type(_ID int primary key, name nvarchar(100));GO
insert into issue_slim_type (_ID, name) values (1, 'text');GO
insert into issue_slim_type (_ID, name) values (2, 'image');GO
insert into issue_slim_type (_ID, name) values (3, 'audio');GO
insert into issue_slim_type (_ID, name) values (4, 'vedio');GO


	