-- postgreSQL 数据库自带字段用双引号，字符串类型的值用单引号
SELECT * from "SysRole" where id='1'
select "userID",count(*) count from "SysUserRolePermission" group by "userID" 

select id,"userID","roleID" from "SysUserRolePermission" where "roleID" !='1' and "userID"='2'

select id from "SysUser" where isdelete=0 and "currentuserroleid"!='1'

select "userID",count(*) count from "SysUserRolePermission" where "roleID"='1' group by "userID" 

select id,"roleID" from "SysUserRolePermission" where "roleID"!='1' and "userID"='1' 

select "B0121",count(*) from "b01" where "B0121" like '001.001.002.046.002%' GROUP BY "B0121" ORDER BY "B0121" desc

SELECT id,"B0111","B0121" from "b01" where "B0121" = '001.001.002.046' ORDER BY "B0111" desc
SELECT id,"B0101","B0111","B0121" from "b01" where "B0111" like '110%' ORDER BY "B0111"

SELECT id,"B0111","B0121" from "b01" where "B0121" = '110.110.111'  ORDER BY "B0111" desc
update "b01" set "B0111" = '1' ,"B0121"='2' where "id"='1'
SELECT "id","name","permission","isBuiltIn","validTime","createTime","updateTime","code","create" from "SysRole" where ("validTime" > now() or "validTime" is null) 

select * from a08 where "A0000"=?
ZB64学历代码
GB6864学位代码
GB16835专业代码
SELECT * from code_value where "CODE_TYPE"='ZB64'
SELECT * from code_value where "CODE_TYPE"='GB6864'
SELECT * from code_value where "CODE_TYPE"='GB16835'

 select "CODE_NAME","CODE_NAME2","CODE_NAME3" from "code_value" where "CODE_TYPE"='ZB64' and "CODE_VALUE"='1'
 select "CODE_VALUE_SEQ","CODE_TYPE","CODE_COLUMN_NAME","CODE_LEVEL","CODE_VALUE","ININO","SUB_CODE_VALUE","CODE_NAME","CODE_NAME2","CODE_NAME3","CODE_REMARK","CODE_SPELLING","ISCUSTOMIZE","CODE_ASSIST","CODE_STATUS","CODE_LEAF","START_DATE","STOP_DATE" FROM "code_value" WHERE "CODE_TYPE" = 'ZB64'
 
 SELECT "A0804","A0898","A0837","A0801B","A0901B","A0814","A0827" FROM "a08"
 SELECT "CODE_TYPE","CODE_COLUMN_NAME" from code_value where "CODE_COLUMN_NAME" is not null GROUP BY "CODE_COLUMN_NAME","CODE_TYPE";

SELECT * from "b01" where LENGTH(replace("B0111",'.',''))%3!=0
SELECT LENGTH(REPLACE("B0111",'.','')) from "b01" where LENGTH(REPLACE("B0111",'.',''))%3!=0
select id,code from "SysRole" where code like '10__' order by cast(code as INTEGER) desc
SELECT * FROM "b01" where id='0ebdcd6d7a914478b77d1a7fe6f8d8c6'

SELECT * from  replace(((uuid_generate_v4())::character varying)::text, '-'::text, ''::text)
SELECT "jobInfo".* from "jobInfo" LEFT JOIN "b01" on "b01"."id"="jobInfo"."orgId" where "B0111" like '%' and "jobInfo"."isDelete"=0 ORDER BY "sortId"

SELECT * from code_value where "CODE_COLUMN_NAME"='A0104'
SELECT * from code_value where "CODE_TYPE"='ZB127'

	UPDATE "jobInfo" set "isDelete"=1 where "id"=''
SELECT * from "jobInfo" where "id" in ('','')

select * from "b01" where id = '12' and "isDelete"='0' 
select * from "b01" where id = ? and "isDelete"='0' 

SELECT * from "code_value" where "CODE_TYPE"='ZB64'
	UPDATE "TempJob" set "isDelete"=1 where "id"=''

-- 学历学位
SELECT * from code_value where "CODE_TYPE"='ZB64'
-- 统计最高学历人数
SELECT a."A0801B",count(*)  from 
(SELECT "A0000","A0801B",to_number("A0801B",'99') from a08 GROUP BY "A0000","A0801B" ORDER BY to_number("A0801B",'99') LIMIT 1) a 
LEFT JOIN a02 on a02."A0000"=a."A0000" LEFT JOIN b01 on b01.id=a02."A0201B" 
where (b01."B0111" LIKE '100.100%' or b01."B0111" LIKE '100.100%') GROUP BY a."A0801B"


SELECT "CODE_TYPE",to_number("CODE_VALUE",'99') from "code_value" where "CODE_TYPE"='ZB64' GROUP BY "CODE_TYPE","CODE_VALUE" ORDER BY to_number("CODE_VALUE",'99') LIMIT 100  

SELECT "CODE_TYPE","CODE_VALUE" from "code_value" where "CODE_TYPE"='ZB64' GROUP BY "CODE_TYPE","CODE_VALUE" ORDER BY "CODE_VALUE" LIMIT 100  

-- 学历专业 
SELECT * from code_value where "CODE_TYPE"='GB16835'
SELECT * from code_value where "CODE_TYPE"='GB16835' and "SUB_CODE_VALUE"='-1'
SELECT * from code_value where "CODE_TYPE"='GB16835' and "CODE_VALUE" LIKE '2%'
SELECT * from code_value where "CODE_TYPE"='GB16835' and "CODE_VALUE" LIKE '10%' and LENGTH("CODE_VALUE")%2=0
-- 统计每个专业的人数 
SELECT SUBSTRING(a."A0827" FROM 1 FOR 1) sub,count(*) from
(SELECT "A0000","A0827" from a08 where "A0827" LIKE '%' and LENGTH("A0827")%2!=0 ) a  
LEFT JOIN a02 on a02."A0000"=a."A0000" LEFT JOIN b01 on b01.id=a02."A0201B"
where (b01."B0111" LIKE '100.100%' or b01."B0111" LIKE '100.100%') GROUP BY SUBSTRING(a."A0827" FROM 1 FOR 1)

-- 统计各个年龄段人数
SELECT CASE WHEN a.age<30 THEN '30岁以下' WHEN a.age BETWEEN 30 and 35 THEN '30岁~35岁' WHEN a.age BETWEEN 36 and 40 THEN '36岁~40岁'  
WHEN a.age BETWEEN 41 and 45 THEN '41岁~45岁' WHEN a.age BETWEEN 46 and 50 THEN '46岁~50岁' WHEN a.age BETWEEN 51 and 55 THEN '51岁~55岁' WHEN a.age BETWEEN 56 and 59 THEN '56岁~59岁' 
ELSE '60岁及以上' END as "name",count("A0000") count
FROM (
SELECT tab1.*,b01."B0111" from (select extract(YEAR FROM age("A0107")) as age,a01."A0000",a02."A0201B" from a01 LEFT JOIN a02 on (a01."A0000"=a02."A0000") where a02."A0000" is NULL ) tab1
LEFT JOIN b01 on (b01."id"=tab1."A0201B") 
WHERE (b01."B0111" LIKE '001%' or b01."B0111" LIKE '001%')) a GROUP BY name 
-- 年龄
select extract(YEAR FROM age("A0107")) as age  from a01 

SELECT * FROM a08 GROUP BY  "CODE_VALUE" 

-- 统计各个职务层次进行统计,默认是公务员综合类,类型是可以选择的
select * from code_value where "CODE_TYPE"='ZB09'
select * from code_value where "CODE_TYPE"='ZB148'
select * from code_value where "CODE_TYPE"='ZB09' and "SUB_CODE_VALUE"='-1' 
select * from code_value where "CODE_TYPE"='ZB09' and "CODE_VALUE" LIKE '1%' and "SUB_CODE_VALUE"='1' 
select * from code_value where "CODE_TYPE"='ZB09' and "CODE_VALUE" LIKE '1A%' 

SELECT a01."A0221",count(*) from a01 LEFT JOIN a02 on a01."A0000"=a02."A0000" 
LEFT JOIN b01 on b01."B0111"=a02."A0201B" 
where a01."A0221" like '1A%' and (b01."B0111" LIKE '%' or b01."B0111" LIKE '%') group by a01."A0221"


SELECT a01."A0221",count(*) from a01 LEFT JOIN a02 on a01."A0000"=a02."A0000" 
LEFT JOIN b01 on b01."B0111"=a02."A0201B" 
where a01."A0221" like '1A%' and b01."B0111" like '%' group by a01."A0221"

SELECT * from code_value where "CODE_TYPE"='ZB64'
SELECT * from code_value where "CODE_TYPE"='GB6864'

SELECT extract(YEAR FROM age("A0107")) from a01
-- 全日制最高学历
select * from a08 where "A0000"='30630394ad9a4aa8afc86d01e8a391dc' and "A0898"='1' and "A0837"='1' ORDER BY "A0801B" limit 1
-- 全日制学位
select "A0901B","A0901A","A0814","A0824","A0800" from a08 where "A0000"='30630394ad9a4aa8afc86d01e8a391dc' and "A0898"='1' and "A0837"='1' GROUP BY "A0901B","A0901A","A0814","A0824","A0800"
-- 在职学位
select "A0901B","A0901A" from a08 where "A0000"='30630394ad9a4aa8afc86d01e8a391dc' and "A0898"='1' and "A0837"='0' GROUP BY "A0901B","A0901A"

SELECT "CODE_VALUE","CODE_NAME" from code_value where "CODE_TYPE"='ZB09' and "SUB_CODE_VALUE" LIKE '1A%'

SELECT a01."A0221",count(*) from a01 LEFT JOIN a02 on a01."A0000"=a02."A0000" 
LEFT JOIN b01 on b01."B0111"=a02."A0201B" 
where a01."A0221" like '1A%' and b01."B0111" like '%' and a02."A0255"='0'  group by a01."A0221"

SELECT "CODE_VALUE","CODE_NAME" from code_value where "CODE_TYPE"='ZB09' and "SUB_CODE_VALUE" LIKE '1A%'

SELECT "M0100","M0111","M0105","M0101" from "m01" where "M0105" = '001' ORDER BY "M0111" desc
select "M0100","M0111","M0105","M0101","isUnit" from m01 where "M0111"='001'
SELECT "M0200","M0100","B0100","B0101","B0104","M0201" from m02 where "M0100"=? and "B0100"=?

SELECT m02.* from m02 LEFT join m01 on m01."M0100"=m02."M0100" where m01."M0111" like '001%'
SELECT m02.* from m02 LEFT join m01 on m01."M0100"=m02."M0100" where m01."M0111" like '001____%'

SELECT "M0100","M0111","M0101","M0102","M0104","M0105","operRen","isUnit"FROM m01 WHERE "operRen"='1' order by  length("M0111"),"M0104"

-- 获取人员列表
select a."A0000",a."A0101","A0192" as "A0215A" ,string_agg(b."A0215B",'、') AS "A0215B",
string_agg(to_char(b."A0243", 'YYYY.MM' ),'、') AS "A0243"  ,
a."A0192C",a."A0104",a."A0117",to_char(a."A0107",'YYYY.MM') as "A0107",
a."A0111A",a."QRZZS",a."ZZZS",a."A0196",to_char(a."A0134",'YYYY.MM') as "A0134",
to_char(a."A0144",'YYYY.MM') as "A0140",string_agg(b."mark",'、') as "mark",concat(a."XGR",to_char(a."XGSJ",'YYYY.MM')) as "XGRANDXGSJ","A0198",min(c."id") as B0100
from "a01" a left join "a02" b on a."A0000" = b."A0000" left join "b01" c on b."A0201B" = c."id" where c."B0111" like '%' group by a."A0000"

SELECT * from m03 where "B0100"=? and "A0000"=?

-- 统计最高学历人数
SELECT CASE WHEN substring(a."A0801B", 1, 1) = '1' THEN 1 END AS "研究生",
    COALESCE(CASE WHEN substring(a."A0801B", 1, 1) in ('4','5','6','7','8','9') THEN 1 END, 0, 
		CASE WHEN substring(a."A0801B", 1, 1) in ('4','5','6','7','8','9') THEN 1 END) AS "中专及以下",
    COALESCE(CASE WHEN "substring"(a."A0801B", 1, 1) = '3' THEN 1 END, 0,CASE WHEN "substring"(a."A0801B", 1, 1) = '3' THEN 1 END) AS "大学专科",
    COALESCE(CASE WHEN "substring"(a."A0801B", 1, 1) = '2' THEN 1 END, 0,CASE WHEN "substring"(a."A0801B", 1, 1) = '2' THEN 1 END) AS "大学本科"
FROM ( SELECT a08."A0000", a08."A0801B" FROM a08 GROUP BY a08."A0000", a08."A0801B" ORDER BY a08."A0801B" LIMIT 1) a
LEFT JOIN a02 ON a02."A0000" = a."A0000"     
LEFT JOIN b01 ON b01.id = a02."A0201B"
where (  b01."B0111" like '001.001%' )   
GROUP BY a."A0801B";


select count(*) from "a01" a left join "a02" b on a."A0000" = b."A0000" left join "b01" c on b."A0201B" = c."id" where c."B0111" like '001.001.014%' group by a."A0000"

select "M0400","M0401" from m04 where "M0406"=2



select a01."A0000",a01."A0101",a01."A0192" as "A0215A" ,(select string_agg(a02."A0215B",'、') from "a02" where "A0000" = a01."A0000" group by "A0000") as "A0215B",(select string_agg ( to_char ( a02."A0243", 'YYYY.MM' ), '、' ) from "a02" where "A0000" =a01."A0000" group by "A0000") AS "A0243",a01."A0192C",a01."A0104",a01."A0117",to_char(a01."A0107",'YYYY.MM') as "A0107",a01."A0111A",a01."QRZZS",a01."ZZZS",a01."A0196",to_char(a01."A0134",'YYYY.MM') as "A0134",to_char(a01."A0144",'YYYY.MM') as "A0140",string_agg(a02."mark",'、') as "mark",concat(a01."XGR",to_char(a01."XGSJ",'YYYY.MM')) as "XGRANDXGSJ",a01."A0198"	from "a01" left join "a02" on a01."A0000" = a02."A0000" left join "b01" on a02."A0201B" = b01."id" left join "a08" on a08."A0000" = a01."A0000" where  b01."B0111" like 'null%' group by a01."A0000" 
M0502  姓名  A0101
M0503  现任职务 	A0215A
M0504  原任职务 A0215B
M0505  任职时间 A0243
M0506  任现职级时间  A0192C
M0507  性别 A0104  A0104Name
M0508  民族   A0117  A0117Name
M0509  出生年月 A0107
M0510  籍贯 A0111A
M0511  全日制教育学历学位 QRZZS
M0512  在职教育学历学位  ZZZS
M0513  专业技术职务 A0196
M0514  参加工作时间 A0134
M0515  入党时间 A0140
M0516  备注 mark
SELECT "A0000","M0502" A0101,"M0503" A0215A,"M0504" A0215B, "M0505" A0243, "M0506" A0192C, "M0507" A0104Name, "M0508" A0117Name, "M0509" A0107,"M0510" A0111A,"M0511" QRZZS,"M0512" ZZZS,"M0513" A0196,"M0514" A0134,"M0515" A0140,"M0516" mark from m05  

SELECT SUBSTRING(a."A0827" FROM 1 FOR 1)  from (SELECT "A0000","A0827" from a08 where "A0827" LIKE '2%' and LENGTH("A0827")%2!=0 ) a 
LEFT JOIN a02 on a02."A0000"=a."A0000" LEFT JOIN b01 on b01.id=a02."A0201B" 
where a02."A0255"='0' GROUP BY b01."id" 

select a01."A0000",a01."A0101",a01."A0192" as "A0215A" ,(select string_agg(a02."A0215B",'、') from "a02" where "A0000" = a01."A0000" group by "A0000") as "A0215B",(select string_agg ( to_char ( a02."A0243", 'YYYY.MM' ), '、' ) from "a02" where "A0000" =a01."A0000" group by "A0000") AS "A0243",a01."A0192C",a01."A0104",a01."A0117",to_char(a01."A0107",'YYYY.MM') as "A0107",a01."A0111A",a01."QRZZS",a01."ZZZS",a01."A0196",to_char(a01."A0134",'YYYY.MM') as "A0134",to_char(a01."A0144",'YYYY.MM') as "A0140",string_agg(a02."mark",'、') as "mark",concat(a01."XGR",to_char(a01."XGSJ",'YYYY.MM')) as "XGRANDXGSJ",a01."A0198"	from "a01" left join "a02" on a01."A0000" = a02."A0000" left join "b01" on a02."A0201B" = b01."id" left join "a08" on a08."A0000" = a01."A0000" where  extract(YEAR FROM age(now()::date,a01."A0107")) > '30'	and	 a01."A0104" in ('1','2') 	and	 (a01."A0101" like '%123%' ) 	and	 b01."B0111" like '001.001%' group by a01."A0000" 

数据交换
select a01.* from "a01" left join "a02" on a01."A0000"=a02."A0000" LEFT JOIN b01 on b01."id"=a02."A0201B" where b01."B0111" like '%'
select a02.* from "a02" left join "a01" on a01."A0000" = a02."A0000" LEFT JOIN b01 on b01."id"=a02."A0201B" where b01."B0111" like '%'
select a05.* from "a05" left join "a02" on a05."A0000" = a02."A0000" LEFT JOIN b01 on b01."id"=a02."A0201B" where a05."A0524"='1' and b01."B0111" like '%'
select b01.* from b01 where "isDelete"=0 and b01."B0111" like ?
select a06.* from "a06" left join "a02" on a06."A0000"=a02."A0000" LEFT JOIN b01 on b01."id"=a02."A0201B" where b01."B0111" like '%'
select a08.* from "a08" left join "a02" on a08."A0000"=a02."A0000" LEFT JOIN b01 on b01."id"=a02."A0201B" where b01."B0111" like '%'
select a14.* from "a14" left join "a02" on a14."A0000"=a02."A0000" LEFT JOIN b01 on b01."id"=a02."A0201B" where b01."B0111" like '%'
select a15.* from "a15" left join "a02" on a15."A0000"=a02."A0000" LEFT JOIN b01 on b01."id"=a02."A0201B" where b01."B0111" like '%'
select a36.* from "a36" left join "a02" on a36."A0000"=a02."A0000" LEFT JOIN b01 on b01."id"=a02."A0201B" where b01."B0111" like '%'
select a57.* from "a57" left join "a02" on a57."A0000"=a02."A0000" LEFT JOIN b01 on b01."id"=a02."A0201B" where b01."B0111" like '%'
select a99z1.* from "a99z1" left join "a02" on a99z1."A0000"=a02."A0000" LEFT JOIN b01 on b01."id"=a02."A0201B" where b01."B0111" like '%'
select count(*) FROM "SysUser" where "currentOrgId"='1'
select * FROM "SysUser" where "currentOrgId"='1'

SELECT * from code_value where "CODE_TYPE"='ZB09' and "SUB_CODE_VALUE" LIKE '1A%' order by code_value
SELECT a01."A0221",min(b01."B0111"),count(*) from a01 LEFT JOIN a02 on a01."A0000"=a02."A0000" LEFT JOIN b01 on b01."id"=a02."A0201B" where a01."A0221" like '1A%'   and a02."A0255"='1' group by a01."A0221"


CREATE TABLE "public"."_temp" (

		"impId" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,

)

-- 管理类别
SELECT "CODE_VALUE","CODE_NAME" from code_value where "CODE_TYPE"='ZB130'
-- 人员类别
SELECT "CODE_VALUE","CODE_NAME" from code_value where "CODE_TYPE"='ZB125'
-- 人员列表
select a01."A0000" from a01 left join a02 on a02."A0000"=a01."A0000" left join b01 on b01."id"=a02."A0201B" where "A0160" in ('1','5','6') and "A0165" in ('01','02','03','04','09') and "A0255"='1' and b01."B0111" like '%' group by a01."A0000"

select a."id",a."fileName",a."expDate",a."expMem",a."expOrgCode",a."expMemCount",b01."id" "orgId",u."name" "expMemName" from "Expinfo" a 
LEFT JOIN b01 on b01."B0111"=a."expOrgCode" left join "SysUser" u on u."id"=a."expMem" where u."id"='1' order by "expDate" desc

select a.*,u."name" "impMemName"  from "Impinfo" a left join "SysUser" u on u."id"=a."impMem" where u."id"='1' order by "impDate" desc

SELECT id,"B0101","B0111","B0121" from "b01" where "isDelete" = 0 and "B0121" like '001.001.015%' ORDER BY "B0111" desc
SELECT * from code_value where "CODE_TYPE"='ZB09' and "SUB_CODE_VALUE" LIKE '1A%' order by "CODE_VALUE"

delete from a01_temp;
delete from a02_temp;
delete from a05_temp;
delete from a06_temp;
delete from a08_temp;
delete from a14_temp;
delete from a15_temp;
delete from a30_temp;
delete from a36_temp;
delete from a57_temp;
delete from a99z1_temp;
delete from b01_temp;


select a01."A0000" from a01 left join a02 on a02."A0000"=a01."A0000" left join b01 on b01."id"=a02."A0201B" where "A0160" in ('1','5','6') and "A0255"='1' and b01."B0111" like '%' group by a01."A0000"

select a."id",a."fileName",a."expDate",a."expMem",a."expOrgCode",a."expMemCount",b01."id" "orgId",b01."B0101"  from "Expinfo" a LEFT JOIN b01 on b01."B0111"=a."expOrgCode" where ( a."expOrgCode" like '001.001%') order by "expDate" desc limit 10 offset 0

select a.*,b01."id" "orgId",b01."B0101" from "Expinfo" a LEFT JOIN b01 on b01."B0111"=a."expOrgCode"
select "A0184",min("A0000") from a01 GROUP BY "A0184"

select * from b01_temp where "impId"='0ad312a736c44ed6a72962c836f12c08' ORDER BY "B0111"

select a01_temp."A0000" from a01_temp left join a02_temp on a02_temp."A0000"=a01_temp."A0000" where "A0165" in ('01','02','03','04','09') and "A0255"='1' and a01_temp."impId"='0ad312a736c44ed6a72962c836f12c08' group by a01_temp."A0000"
-- 退出单位
SELECT "CODE_VALUE","CODE_NAME" from code_value where "CODE_TYPE"='ZB78'
select * from a30 where "A3038" like '001.001%' and "A3001" not like '2%' and "A3001" not like '8%'

SELECT * from b01 where "B0114"='123.142.123'
select "id","B0101","B0111","B0114" from b01_temp where "impId"='0ad312a736c44ed6a72962c836f12c08' ORDER BY "B0111"
select b01."id","B0101","B0111","B0114" from b01 where "isDelete"=0 and b01."B0111" like '001.001.015%' ORDER BY "B0111"

select a01.* from a01 left join a02 on a02."A0000"=a01."A0000" left join b01 on b01."id"=a02."A0201B" where b01."B0111" like '001.001%' GROUP BY a01."A0000"
A0101  A0184  A0215A  A0221  A0192D 
-- 人员对比
select a01."A0000",a01."A0184",a01."A0101",a01."A0192" as "A0215A",a01."A0221",a01."A0192D"	from "a01" left join "a02" on a01."A0000" = a02."A0000" left join "b01" on a02."A0201B" = b01."id" where b01."B0111" like '001.001%' group by a01."A0000" 

select * from a01_temp where "impId"='f7c2212e9852491cb070d56d7532a341'

select a01_temp."A0000",min(a01_temp."A0184") "A0184",min(a01_temp."A0101") "A0101",min(a01_temp."A0192") as "A0215A",min(a01_temp."A0221") "A0221",min(a01_temp."A0192D") "A0192D"	from "a01_temp" left join "a02_temp" on a01_temp."A0000" = a02_temp."A0000" left join "b01_temp" on a02_temp."A0201B" = b01_temp."id" where a01_temp."impId"='f7c2212e9852491cb070d56d7532a341' group by a01_temp."A0000" 

-- 人员校核
select a01.*,json_agg(json_build_object("A0000","A0101")) from a01_temp a01 where a01."impId" = 'dc00f8b06b71496b9a191d1494bae552' GROUP BY a01."A0000",a01."impId"

SELECT a01.*, json_agg(json_build_object('A0000',"a02"."A0000",'A0200',"a02"."A0200",'A0201A',"a02"."A0201A",'A0201B',"a02"."A0201B",'A0201D',"a02"."A0201D",'A0201E',"a02"."A0201E",'A0215A',"a02"."A0215A",'A0219',"a02"."A0219",'A0223',"a02"."A0223",'A0225',"a02"."A0225",'A0243',"a02"."A0243",'A0245',"a02"."A0245",'A0247',"a02"."A0247",'A0251B',"a02"."A0251B",'A0255',"a02"."A0255",'A0265',"a02"."A0265",'A0267',"a02"."A0267",'A0272',"a02"."A0272",'A0281',"a02"."A0281",'A0221T',"a02"."A0221T",'A0221A',"a02"."A0221A",'A4907',"a02"."A4907",'A4904',"a02"."A4904",'A4901',"a02"."A4901",'A0299',"a02"."A0299",'A0295',"a02"."A0295",'A0289',"a02"."A0289",'A0284',"a02"."A0284",'A0277',"a02"."A0277",'A0271',"a02"."A0271",'A0259',"a02"."A0259",'A0256C',"a02"."A0256C",'A0256B',"a02"."A0256B",'A0256A',"a02"."A0256A",'A0256',"a02"."A0256",'A0251',"a02"."A0251",'A0229',"a02"."A0229",'A0222',"a02"."A0222",'A0221W',"a02"."A0221W",'A0219W',"a02"."A0219W",'A0216A',"a02"."A0216A",'A0215B',"a02"."A0215B",'A0209',"a02"."A0209",'A0207',"a02"."A0207",'A0204',"a02"."A0204",'A0201C',"a02"."A0201C",'A0201',"a02"."A0201",'A0279',"a02"."A0279",'mark',"a02"."mark")) as "a02Info",
json_agg(json_build_object('A0000',"a02"."A0000",'A0800',"a08"."A0800",'A0801A',"a08"."A0801A",'A0801B',"a08"."A0801B",'A0901A',"a08"."A0901A",'A0901B',"a08"."A0901B",'A0804',"a08"."A0804",'A0807',"a08"."A0807",'A0904',"a08"."A0904",'A0814',"a08"."A0814",'A0824',"a08"."A0824",'A0827',"a08"."A0827",'A0837',"a08"."A0837",'A0811',"a08"."A0811",'A0898',"a08"."A0898",'A0831',"a08"."A0831",'A0832',"a08"."A0832",'A0834',"a08"."A0834",'A0835',"a08"."A0835",'A0838',"a08"."A0838",'A0839',"a08"."A0839",'A0899',"a08"."A0899")) as "a08Info",
json_agg(json_build_object('A0000',"a36"."A0000",'A3600',"a36"."A3600",'A3601',"a36"."A3601",'A3604A',"a36"."A3604A",'A3607',"a36"."A3607",'A3611',"a36"."A3611",'A3627',"a36"."A3627",'A3684',"a36"."A3684",'A3699',"a36"."A3699")) as "a36Info",
json_agg(json_build_object('A0000',"a15"."A0000",'A1500',"a15"."A1500",'A1517',"a15"."A1517",'A1521',"a15"."A1521",'A1527',"a15"."A1527",'A1599',"a15"."A1599")) as "a15Info",
json_agg(json_build_object('A0000',"a14"."A0000",'A1400',"a14"."A1400",'A1404A',"a14"."A1404A",'A1404B',"a14"."A1404B",'A1407',"a14"."A1407",'A1411A',"a14"."A1411A",'A1414',"a14"."A1414",'A1415',"a14"."A1415",'A1424',"a14"."A1424",'A1428"',"a14"."A1428",'A1500',"a14"."A1500")) as "a14Info",
json_agg(json_build_object('A0000',"a05"."A0000",'A0500',"a05"."A0500",'A0531',"a05"."A0531",'A0501B',"a05"."A0501B",'A0504',"a05"."A0504",'A0511',"a05"."A0511",'A0517',"a05"."A0517",'A0524',"a05"."A0524",'A0525',"a05"."A0525"))  as "a05Info", 
json_agg(json_build_object('A0600',"a06"."A0600",'A0000',"a06"."A0000",'A0601',"a06"."A0601",'A0602',"a06"."A0602",'A0604',"a06"."A0604",'A0607',"a06"."A0607",'A0611',"a06"."A0611",'A0614',"a06"."A0614",'A0699',"a06"."A0699",'A0615',"a06"."A0615")) as "a06Info" 
FROM "a01" LEFT JOIN "a02" ON a01."A0000" = a02."A0000" LEFT JOIN "b01" ON a02."A0201B" = b01."id"  LEFT JOIN "a08" on a01."A0000" = a08."A0000" LEFT JOIN "a36" ON a01."A0000" = a36."A0000" LEFT JOIN "a15" on a01."A0000" = a15."A0000" LEFT JOIN "a14" on a01."A0000" = a14."A0000" LEFT JOIN a05 on a01."A0000" = a05."A0000" LEFT JOIN a06 on a06."A0000" = a06."A0000" WHERE "b01"."B0111" like '001.001%' group by a01."A0000"


-- temp 人员校核
select a01.* ,json_agg(json_build_object('A0000',"a02"."A0000",'A0200',"a02"."A0200",'A0201A',"a02"."A0201A",'A0201B',"a02"."A0201B",'A0201D',"a02"."A0201D",'A0201E',"a02"."A0201E",'A0215A',"a02"."A0215A",'A0219',"a02"."A0219",'A0223',"a02"."A0223",'A0225',"a02"."A0225",'A0243',"a02"."A0243",'A0245',"a02"."A0245",'A0247',"a02"."A0247",'A0251B',"a02"."A0251B",'A0255',"a02"."A0255",'A0265',"a02"."A0265",'A0267',"a02"."A0267",'A0272',"a02"."A0272",'A0281',"a02"."A0281",'A0221T',"a02"."A0221T",'A0221A',"a02"."A0221A",'A4907',"a02"."A4907",'A4904',"a02"."A4904",'A4901',"a02"."A4901",'A0299',"a02"."A0299",'A0295',"a02"."A0295",'A0289',"a02"."A0289",'A0284',"a02"."A0284",'A0277',"a02"."A0277",'A0271',"a02"."A0271",'A0259',"a02"."A0259",'A0256C',"a02"."A0256C",'A0256B',"a02"."A0256B",'A0256A',"a02"."A0256A",'A0256',"a02"."A0256",'A0251',"a02"."A0251",'A0229',"a02"."A0229",'A0222',"a02"."A0222",'A0221W',"a02"."A0221W",'A0219W',"a02"."A0219W",'A0216A',"a02"."A0216A",'A0215B',"a02"."A0215B",'A0209',"a02"."A0209",'A0207',"a02"."A0207",'A0204',"a02"."A0204",'A0201C',"a02"."A0201C",'A0201',"a02"."A0201",'A0279',"a02"."A0279",'mark',"a02"."mark")) as "a02Info",
json_agg(json_build_object('A0000',"a05"."A0000",'A0500',"a05"."A0500",'A0531',"a05"."A0531",'A0501B',"a05"."A0501B",'A0504',"a05"."A0504",'A0511',"a05"."A0511",'A0517',"a05"."A0517",'A0524',"a05"."A0524",'A0525',"a05"."A0525"))  as "a05Info", 
json_agg(json_build_object('A0600',"a06"."A0600",'A0000',"a06"."A0000",'A0601',"a06"."A0601",'A0602',"a06"."A0602",'A0604',"a06"."A0604",'A0607',"a06"."A0607",'A0611',"a06"."A0611",'A0614',"a06"."A0614",'A0699',"a06"."A0699",'A0615',"a06"."A0615")) as "a06Info" ,
json_agg(json_build_object('A0000',"a02"."A0000",'A0800',"a08"."A0800",'A0801A',"a08"."A0801A",'A0801B',"a08"."A0801B",'A0901A',"a08"."A0901A",'A0901B',"a08"."A0901B",'A0804',"a08"."A0804",'A0807',"a08"."A0807",'A0904',"a08"."A0904",'A0814',"a08"."A0814",'A0824',"a08"."A0824",'A0827',"a08"."A0827",'A0837',"a08"."A0837",'A0811',"a08"."A0811",'A0898',"a08"."A0898",'A0831',"a08"."A0831",'A0832',"a08"."A0832",'A0834',"a08"."A0834",'A0835',"a08"."A0835",'A0838',"a08"."A0838",'A0839',"a08"."A0839",'A0899',"a08"."A0899")) as "a08Info"
from "a01_temp" a01
LEFT JOIN "a02_temp" a02 on a01."A0000"=a02."A0000" 
LEFT JOIN "a05_temp" a05 on a01."A0000" = a05."A0000" 
LEFT JOIN "a06_temp" a06 on a01."A0000" = a06."A0000"
LEFT JOIN "a08_temp" a08 on a01."A0000" = a08."A0000" 
where a01."impId"='dc00f8b06b71496b9a191d1494bae552' GROUP BY a01."A0000",a01."impId"


select a01."A0000" ,json_agg(json_build_object('A0000',"a14"."A0000",'A1400',"a14"."A1400",'A1404A',"a14"."A1404A",'A1404B',"a14"."A1404B",'A1407',"a14"."A1407",'A1411A',"a14"."A1411A",'A1414',"a14"."A1414",'A1415',"a14"."A1415",'A1424',"a14"."A1424",'A1428"',"a14"."A1428",'A1500',"a14"."A1500")) as "a14Info",
json_agg(json_build_object('A0000',"a15"."A0000",'A1500',"a15"."A1500",'A1517',"a15"."A1517",'A1521',"a15"."A1521",'A1527',"a15"."A1527",'A1599',"a15"."A1599")) as "a15Info",
json_agg(json_build_object('A0000',"a36"."A0000",'A3600',"a36"."A3600",'A3601',"a36"."A3601",'A3604A',"a36"."A3604A",'A3607',"a36"."A3607",'A3611',"a36"."A3611",'A3627',"a36"."A3627",'A3684',"a36"."A3684",'A3699',"a36"."A3699")) as "a36Info"
from "a01_temp" a01
LEFT JOIN "a14_temp" a14 on a01."A0000" = a14."A0000" 
LEFT JOIN "a15_temp" a15 on a01."A0000" = a15."A0000"
LEFT JOIN "a36_temp" a36 ON a01."A0000" = a36."A0000" 
where a01."impId"='dc00f8b06b71496b9a191d1494bae552' GROUP BY a01."A0000",a01."impId"




SELECT count(*),"B0114" from "b01" group by "B0114" having count(*)  > 1 
select id,count(*) count from b01 GROUP BY id ORDER BY count desc

select "id","B0111","B0114","B0121" FROM "b01" where "B0114"='123.142.123'

select * from a08 where "A0000"='79eb76b314df476f8f9a895909456342' ORDER BY "A0804", "A0904"
select * from a08 where 1=1 ORDER BY "A0804", "A0904"

SELECT "A0000","A0267","A0251B","mark","A0245","A0215B","A0200","A0281","A0201A","A0201B","A0215A","A0243","A0219","A0201D","A0201E","A0247","A0279","A0255","A0265","A0223","mark" from a02 where "A0000"= '79eb76b314df476f8f9a895909456342' and "A0255" = '1' ORDER BY "A0255" desc,"A0223","A0201E","A0243"

-- 职务层次
select * from code_value where "CODE_TYPE"='ZB09'
-- 管理层次
select * from code_value where "CODE_TYPE"='ZB130'

select "CODE_VALUE","CODE_NAME" from code_value where "CODE_TYPE"='ZB09' and ("SUB_CODE_VALUE" ='-1' or "SUB_CODE_VALUE"='1' or "SUB_CODE_VALUE"='7') and "CODE_VALUE" !='1' ORDER BY "CODE_VALUE"
SELECT "CODE_VALUE","CODE_NAME" from code_value where "CODE_TYPE"='ZB09' and ("CODE_VALUE" like '1A' or "CODE_VALUE" like '1B' )  order by "CODE_TYPE","ININO","CODE_VALUE"

select "userID","roleID","permission","orgArray" from "SysUserRolePermission" where "userID" = '1' and "roleID" = '9b6e97087eff4e5fbcb418c9fcc4450e'

-- 学历学位
SELECT * from code_value where "CODE_TYPE"='ZB64' 
select * from a08 where "A0000"='9D491E03-3095-43C0-9604-70480BD11850' ORDER BY "A0801B" limit 1
select * from a08 where "A0000"='70300223-d301-40d1-ae6b-bb7c8472cf46' and "A0801B" is not null and "A0801B"!='' ORDER BY "A0801B" limit 1

select * from a08 where "A0801B" is null or "A0801B"=''
-- temp身份证重复列表
select min("A0000") "A0000",min("A0184") "A0184",min("A0101") "A0101",min("A0192") as "A0215A"	from "a01_temp" where "impId"='e63272518dbe4c908ba72af58d951e65' group by "A0184" having count("A0184") > 1

SELECT concat("A0101",',',"A0111A") from a01
--  原人员数据信息，例如：[{"A0000":"123","A0184":"身份证","A0192":"现任职务","A0107":"出生日期","A0101":"姓名","A0255":"是否在任","result":"对比结果"}]
-- 最高学历
SELECT concat("A0000") from a08 GROUP BY "A0000"
select * from a08 where "A0000"='E1A6E081-794A-420E-8571-B48390477A77'
select * from a08 where "A0000"='A5E71F5B-D36E-4F28-9FF9-221F55DC347B'

SELECT * from a08
select min("A0000") as "A0000", min("A0801B") "A0801B" from a08 GROUP BY "A0000"

select * from a08 where "A0000"=(select "A0000" from (select min("A0000") as "A0000", min("A0801B") "A0801B" from a08 GROUP BY "A0000") a) and "A0801B"=(select "A0801B" from 
(select min("A0000") as "A0000", min("A0801B") "A0801B" from a08 GROUP BY "A0000")b)

select "A0000" from a08 GROUP BY "A0000"
select "A0000","A0184","A0192","A0107","A0101" from a01 GROUP BY "A0000"   -- 123

select * from a01_temp where "impId"='aaaaaaa' and "A0000"='960e10af21544d9ea14c6aca67553475'

SELECT "A0000","A0184","A0192","A0107","A0101" FROM a01 GROUP BY "A0101","A0107"

-- 分组
SELECT DISTINCT ON("A0101","A0107")"A0101","A0192"  FROM a01

select "A0000","A0184","A0192","A0107","A0101","oldDataArray","toA0000","result" from a01_temp where "impId"='asgdfjhtrjv'
select "A0000" from a01_temp where "impId"='asgdfjhtrjv' group by "A0000"

-- 学历学位拼接
select "A0000",json_agg(json_build_object('A0898',"A0898",'A0804',"A0804",'A0807',"A0807",'A0801A',"A0801A",'A0801B',"A0801B",'A0901B',"A0901B",'A0901A',"A0901A",'A0814',"A0814",
'A0824',"A0824",'A0800',"A0800",'A0837',"A0837")) a08Info from "a08" 
where "A0000" in ('00017421-1474-4E7C-B975-5378A455F0FB','003FFC9A-0BAC-4ACE-9D5F-8FAAEC0D3EC3','005FA269-51CA-47D1-9DFD-32E198D94DFE') 
group by "A0000"

-- A0898  输出标识   等（1是，0否）	
-- A0837  教育类别   等（1全日制，0在职）
-- 全日制最高学历 ZB64
select * from a08 where "A0000"='00017421-1474-4E7C-B975-5378A455F0FB' and "A0898"='1' and "A0837"='1' ORDER BY "A0801B" limit 1
-- 全日制学位
select "A0901B","A0901A","A0814","A0824","A0800" from a08 where "A0000"='00017421-1474-4E7C-B975-5378A455F0FB' and "A0898"='1' and "A0837"='1' GROUP BY "A0901B","A0901A","A0814","A0824","A0800"

-- 在职最高学历
select * from a08 where "A0000"='00017421-1474-4E7C-B975-5378A455F0FB' and "A0898"='1' and "A0837"='0' ORDER BY "A0801B" limit 1
-- 在职学位
select "A0901B","A0901A","A0814","A0824","A0800" from a08 where "A0000"='00017421-1474-4E7C-B975-5378A455F0FB' and "A0898"='1' and "A0837"='0' GROUP BY "A0901B","A0901A","A0814","A0824","A0800"

-- 角色列表
SELECT "id","name","permission","isBuiltIn","validTime","createTime","updateTime","code","create"  from "SysRole" where ("validTime" > now() or "validTime" is null) and "code" like '1015%' ORDER BY "createTime" desc

-- 全息2019-05-26
-- 机构级别
select * from code_value where "CODE_TYPE"='ZB03'
-- 机构性质类别
select * from code_value where "CODE_TYPE"='ZB04'
select * from code_value where "CODE_TYPE"='QX_JGXZLB'

select "B0101","B0111","id" from b01 where "system"='0'
select * from b01 where "system"='0' and id = 'e814f3c192b849038a3fed1c3890a93e'

SELECT id,"B0101","B0114","B0194","B0104","B0111","B0121","B0127","B01Z101","B01Z102","B01Z103","B01Z104","B01Z105","B01Z106","B01Z107","isDelete","SORTID" from "b01" where "isDelete" = 0 and ("B0111" like '001.001%') ORDER BY length("B0111"),"SORTID","CREATE_DATE","B0111" 

SELECT id,"B0101","B0104","B0194","B0111","B0121","B0114" from "b01" where "isDelete" = 0 and "B0111" like '001.001%' and "B01Z102"='1' and ("system" = '1' or "system" = '0')  order by  length("B0111"),"SORTID","CREATE_DATE","B0111"
-- 人员列表
select (select min("A0801B") from "a08" where "A0000" = a."A0000") as "A0801B",a."A0192A" as "A0192",a."QRZXW",a."A0000",to_char(a."A0144",'YYYY.MM') as "A0144",a."A0141",a."A0165",a."A0184",a."A0101","A0192A" as "A0215A" ,string_agg(b."A0215B",',') AS "A0215B",string_agg(to_char(b."A0243", 'YYYY.MM' ),',') AS "A0243"  ,a."A0288" as "A0192C",a."A0104",a."A0117",to_char(a."A0107",'YYYY.MM') as "A0107",a."A0111A",a."QRZZS",a."ZZZS",a."A0196",to_char(a."A0134",'YYYY.MM') as "A0134","A0140",string_agg(b."mark",',') as "mark",concat("XGR",',','(',to_char("XGSJ",'YYYY.MM.dd HH:mm'),')') as "XGRANDXGSJ","A0198" from "a01" a left join "a02" b on a."A0000" = b."A0000" and "b"."A0255" = '1'  left join "b01" c on b."A0201B" = c."id" and c."isDelete" = '0'  where (c."B0121"='001.001' or c."B0111"='001.001')  group by a."A0000" order by min(c."B0111"),a."SORTID",min(b."A0225"),a."A0000" limit 10 offset 0

-- 专业结构
select "A0827" from "a08" where "A0000" in (select a01."A0000" from "a01" inner join "a02" on "a01"."A0000" = "a02"."A0000" and "A0255" = '1' and a02."A0279" = '1' left join "b01" on "a02"."A0201B" = "b01"."id" and "b01"."isDelete" = '0' where ("b01"."B0111"='001.001' or "b01"."B0121"='001.001')    group by a01."A0000")
-- 政治面貌
select * from code_value where "CODE_TYPE"='GB4762'
select * from code_value where "CODE_TYPE"='GB3304'
select * from code_value where "CODE_TYPE"='GB2261'
-- 女少非，年龄结构，任职时间，任职务层次时间
SELECT a."A0000",min( a."A0104" ) AS "A0104",min( a."A0117" ) AS "A0117",min(a."A0141") as "A0141", 
min(extract(YEAR FROM age("A0243"))) as "A0243",min(extract(YEAR FROM age(a."A0288"))) as "A0288",
MIN(extract( YEAR FROM age ( "A0107" ))) as age  FROM "a01" AS a 
LEFT JOIN "a02" AS b ON a."A0000" = b."A0000" and b."A0255" = '1' -- and b."A0279" = '1'
LEFT JOIN "b01" AS c ON c."id" = b."A0201B" and c."isDelete" = '0'
where  (c."B0111"='001.001' or c."B0121"='001.001') GROUP BY a."A0000"

select * from code_value where "CODE_TYPE"='ZB148'
-- 考核评价
SELECT id,"orgId",to_char("checkYear",'YYYY') "checkYear",advice,pic,conclude,"rank","fileName","fileUrl" from "OrgCheck" where "orgId"='e814f3c192b849038a3fed1c3890a93e' ORDER BY "checkYear"

-- pgsql id 自增
ALTER TABLE "OrgCheck"
DROP CONSTRAINT "OrgCheck_pkey" ,
DROP COLUMN "id",
ADD COLUMN "id" serial8 NOT NULL,
ADD CONSTRAINT "OrgCheck_pkey" PRIMARY KEY ("id");




alter table "a01" add column "A0123" varchar(8);

-- 如果A0223不是int类型就执行这条
-- UPDATE a02 set "A0223"=null where "A0223"='';
alter table "a02" alter COLUMN "A0223" type int2 using "A0223"::int2;

alter table a05 add column "A0528B" varchar(8);
alter table a05 add column "A0530" varchar(8);
alter table a05 add column "A0532" varchar(8);

alter table b01 add column "B0164" TIMESTAMP;
alter table b01 add column "B0167" varchar(255);
alter table b01 add column "B0268" TIMESTAMP;
alter table b01 add column "B0269" varchar(255);

alter table a99z1 add column "A99Z195" varchar(24);







