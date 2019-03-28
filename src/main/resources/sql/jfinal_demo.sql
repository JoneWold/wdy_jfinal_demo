-- #namespace 指令为 sql 语句指定命名空间，不同的命名空间可以让#sql指令使用相同的key值去定义sql，有利于模块化管理
-- 在使用的时候，只需要在key前面添加namesapce值前缀
#namespace("mysql")
    #sql("findBlog")
      select * from blog where id > ?
    #end

    #sql("findBlog2")
      select * from blog where id > #para(0)
    #end

    #sql("findBlog3")
      select * from blog where id > #para(id) and title like concat('%', #para(title), '%')
    #end

    #sql("getUserPage")
      select id,name from sys_user
    #end

#end
-- --------------------------------------------
#namespace("pg")
    #sql("findCodeTable")
      select * from code_table
    #end
#end