show tables;

create table user(
	idx int not null auto_increment primary key,
	mid varchar(20) not null,
	name varchar(20) not null,
	age int default 20,
	address varchar(10)
);

desc user;

select * from user order by mid;

delete from user where mid = '';

create table user2(
	mid varchar(20) not null,
	jab varchar(10)
);

desc user2;

drop table user2;