INSERT INTO ITEM (
	ITEM_ID  
	,STORE_ID  
	,ITEM_TITLE
	,ITEM_INFO
	,ITEM_CATEGORY  
	,ITEM_STATUS
	,EXCHANGE_YN
	,PRICE
	,DELIVERY_PRICE
	,INCLUDE_DELIVERY_PRICE_YN
	,TRADE_AREA 
	,RELATION_TAG 
	,STOCK
	,SAFETY_TRADE_YN
	,IMG_PATH
	,NUM_LIKE
	,REGISTRATION_DATE
) VALUES(
    ITEM_SEQ.NEXTVAL
    ,'10000000'
    ,'남자바지 판매합니다!'
    ,'테스트 내용입니다!.'
    ,'105'
    ,'N'
    ,'N'
    ,50000
    ,0
    ,'N'
    ,'충장로'
    ,''
    ,1
    ,'N'
    ,'TEMP2.png'
    ,0
    ,SYSDATE
);