show tables;

create table user(
	idx int not null auto_increment primary key,
	mid varchar(20) not null,
	name varchar(20) not null,
	age int default 20,
	address varchar(10)
);

desc user;

select * from user;

insert into user values (default,'aaaa','에에에에',22,'서울');
insert into user values (default,'bbbb','비비비비',32,'청주');
insert into user values (default,'cccc','씨씨씨씨',25,'제주');

delete from user where idx = 8;

select * from user where name like '%에%' order by idx desc;
select * from user where name like concat('%','에','%') order by idx desc;