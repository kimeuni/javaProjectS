show tables;

create table qrCode(
	idx int not null auto_increment primary key,
	mid varchar(20) not null,
	name varchar(30) not null,
	email varchar(50) not null,
	movieName varchar(50) not null,	-- 영화 제목명
	movieDate varchar(50) not null,	-- 영화 상영일자
	movieTime varchar(50) not null,	-- 영화 상영시간
	movieAdult int not null,	-- 성인 티켓수
	movieChild int not null,	-- 어린이 티켓수
	publishNow varchar(50) not null, -- 발행일자
	qrCodeName varchar(50) not null	/* qr코드 이미지 파일명 */
);