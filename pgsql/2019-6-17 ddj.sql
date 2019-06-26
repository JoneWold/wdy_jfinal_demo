-- 按月统计已缴金额
SELECT MONTH,	sum( pay_money ) payed FROM	ccp_fee WHERE	"year" = '2019' -- 	AND "month" = '5'	and "pay_money" is not null
AND "delete_time" IS NULL AND "mem_org_org_code" LIKE '500%' GROUP BY MONTH;
-- 按月统计支出金额
select extract(month from record_time) as "month", sum("money") as "count" from "ccp_fee_disburse" where ("disburse_org_code" ='500' and "delete_time" is null)  GROUP BY month;
-- 按录入类型统计支出金额
select CASE WHEN record_type='1' THEN	'账单录入'	ELSE '手动录入'	END AS record_type,  sum("money") as "count"
from "ccp_fee_disburse" where ("disburse_org_code" = '500'  and "money" is not null  and "delete_time" is null) group by "record_type";

select CASE WHEN d49_code='1' THEN '按标准交纳' WHEN d49_code='2' THEN '按工资比例' WHEN d49_code='3' THEN '少交党费' WHEN d49_code='4' THEN '免交党费' END AS name, sum("pay_money") as "count" from "ccp_fee" where ("mem_org_org_code" like '500%'  and "pay_money" is not null  and "delete_time" is null) group by "d49_code";

-- 统计下拨党委，下拨党支部
SELECT count(1),CASE WHEN org.org_type='1' THEN '党委' WHEN org.org_type='3' THEN '党支部' END as name from ccp_fee_allocate as fee LEFT JOIN ccp_org org on org.org_code=fee.allocate_org_code and org.delete_time is null
where fee.delete_time is null and allocate_org_code like '500___' GROUP BY org.org_type

-- 管理组织
SELECT user_id,org_code,org_id from sys_user_role_permission where user_id in (SELECT id from sys_user where is_delete=0 and mem_code='9af047f6-9938-4ea8-9cd4-2fe102ee24ff')

-- 最新届次
SELECT code from ccp_org_committee_elect elect WHERE elect.org_code=(SELECT org_code from ccp_mem where code='63c6f340e5c147d9b2dc0a388e40ea15') ORDER BY tenure_end_date desc LIMIT 1





























