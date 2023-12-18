show tables;

create table member2 (
	idx int not null auto_increment , 			/* 회원 고유번호 */
	mid varchar(30) not null , 					/* 회원 아이디(중복불호) */
	pwd varchar(100) not null, 					/* 회원 비밀번호(SHA256 암호화 처리) */
	nickName varchar(20) not null, 				/* 회원 닉네임(중복불호/수정가능) */
	name varchar(20) not null,  				/* 회원 성명 */
	gender char(2) not null default '여자', 	/* 회원 성별 */
	birthday datetime default now(),			/* 회원 생일 (기입 안하면, 가입한 날짜를 생일로 친다) */
	tel varchar(15), 							/* 전화면호 : 010-1234-5678 */
	address varchar(100), 						/* 주소(다음 API 활용) */
	email varchar(60) not null,					/* 이메일(아이디/비밀번호 분실시 사용하기 위해서 필수입력)-형식체크필수 */
	homePage varchar(60), 						/* 홈페이지(블로그주소 등) */
	job varchar(20),							/* 직업 */
	hobby varchar(100), 						/* 취미 */
	photo varchar(100) default 'noimage.jpg',	/* 회원 사진(사진 등록 안할시 noimage.jpg가 뜬다) */
	content text,								/* 회원 소개 */
	userInfor char(3) default '공개',				/* 회원 정보 공개여부(공개/비공개) */
	userDel char(2) default 'NO', 				/* 회원 탈퇴신청(NO:현재 활동중, OK:탈퇴신청) */
	point int default 100, 						/* 회원 누적포인트(가입시 기본 100포인트 지급, 1회방문시 10포인트 , 1일 최대 50포인트까지 허용, 물건구매시 100원당 1포인트 증가) */
	level int default 1, 						/* 회원등급(0:관리자 1:준회원 2:정회원 3:우수회원 4:운영자) */
	visitCnt int default 0,						/* 총 방문횟수 */
	startDate datetime default now(), 			/* 최초 가입일 */
	lastDate datetime default now(), 			/* 마지막 접속일 */
	todayCnt int default 0, 					/* 오늘 방문한 횟수 */
--	salt char(8) not null, 						/* 비밀번호 보안을 위한 salt */

	primary key(idx), /* primary key는 외래키 설정이 가능하다. */
	unique key(mid)  /* unique key도 중복방지가 가능하기 때문에 외래키 설정이 사용가능하다. */
--	primary key (idx,mid)  /* 이런식으로 뒤로 빼서 사용 가능하다. / 이렇게 2개 적을 시, 그룹으로 지정됨. */
);

drop table member2;

desc member2;

select * from member2;

insert into member2 values(default,'admin','1234','관리맨','관리자','남자',default,'010-1234-1264','서울 / / /','axdc1234@naver.com','','학생','등산',default,'',default,default,default,default,default,default,default,default);