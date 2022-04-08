drop table test_member cascade constraints;
drop table hwagae_talk cascade constraints;
drop sequence talk_seq;
create table test_member(
	store_id varchar2(200),
	item_id varchar2(200),
	store_name varchar2(200),
	item_name varchar2(200));
	
insert into TEST_MEMBER
values('005', '005', '상점5', '상품 : 5');

insert into TEST_MEMBER
values('006', '006', '상점6', '상품 : 6');

select *from test_member;

select*from HWAGAE_TALK;

create table HWAGAE_TALK(
	TALK_SEQ number(10),
	ITEM_ID number(10),
	SENDER_STORE_ID number(10),
	RECEIVER_STORE_ID number(10),
	TALK_INFO varchar2(1000),
	CONFIRM_YN varchar2(1) default 'N',
	TALK_DATE date default sysdate);
CREATE SEQUENCE TALK_SEQ INCREMENT BY 1 START WITH 1;
select * from test_member where store_id='001'
select *from user_tables;
select *from item;
select *from store;
select *from hwagae_talk;
insert into hwagae_talk
values(talk_seq.nextval, 3, 7, 1, 'test', 'Y', sysdate);
select item_id, sender_store_id, receiver_store_id, talk_info from HWAGAE_TALK where sender_store_id=1 or receiver_store_id=1
group by item_id, sender_store_id, receiver_store_id, talk_info;
select item_id,store_id, talk_info from (select *from HWAGAE_TALK where store_id!=1) 
group by item_id, store_id, talk_info;

SELECT * FROM 
(SELECT sender_store_id, receiver_store_id, item_id, TALK_INFO, TALK_DATE,RANK() OVER 
(PARTITION BY item_id ORDER BY TO_CHAR(TALK_DATE, 'YYYY-MM-DD HH24:MI:SS') DESC) 
AS RNK FROM hwagae_talk where sender_store_id =1 or receiver_store_id =1)

SELECT * FROM 
(SELECT TALK_SEQ, item_id, sender_store_id, receiver_store_id, TALK_INFO, confirm_yn, TALK_DATE,RANK() OVER (PARTITION BY item_id ORDER BY TO_CHAR(TALK_DATE, 'YYYY-MM-DD HH24:MI:SS') asc) AS RNK FROM hwagae_talk)
WHERE sender_store_id = 1 or receiver_store_id=1;	

update hwagae_talk set confirm_yn='Y' where sender_store_id=2 and receiver_store_id=1 and confirm_yn='N'
select *from store
select *from hwagae_talk where item_id=1 and ((sender_store_id=1 and receiver_store_id=2) or (sender_store_id=2 and receiver_store_id=1))
select *from item

INSERT INTO STORE (
	STORE_ID
	,STORE_NAME
	,STORE_INFO
	,SNSID 
	,SNSTYPE
	,MOBILE
	,MOBILE_OPEN_YN
	,CONTACT_START_TIME
	,CONTACT_END_TIME
	,AUTHENTICATION_MOBILE
	,AUTHENTICATION_YN
	,AREA
	,ZIP_CODE
	,BASIC_ADDRESS
	,DTAIL_ADDRESS
	,NUM_VISITORS 
	,NUM_SALES 
	,NUM_DELIVERY 
	,AVERAGE_POINT 
	,LAST_LOGIN_TIME
	,ACCOUNT_CREATION_DATE
	,LEAVE_DATE
) VALUES(
    STORE_SEQ.NEXTVAL
    ,'test5'
    ,'test5 상점입니다.'
    ,'5'
    ,'NAVER'
    ,'01000000005'
    ,'Y'
    ,'0000'
    ,'0000'
    ,''
    ,'N'
    ,'충장로'
    ,'61474'
    ,'광주광역시 동구'
    ,'예술길 31-15 (대의동)'
    ,0
    ,0
    ,0
    ,0
    ,NULL
    ,SYSDATE
    ,NULL
);