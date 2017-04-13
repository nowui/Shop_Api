#namespace("guangfeng")

  #sql("count")
    SELECT COUNT(*) FROM table_guangfeng
    WHERE system_status = 1
    #if(guangfeng_name)
      #set(guangfeng_name = "%" + guangfeng_name + "%")
      AND guangfeng_name LIKE #p(guangfeng_name)
    #end
  #end

  #sql("list")
    SELECT
    guangfeng_id,
    guangfeng_name
    FROM table_guangfeng
    WHERE system_status = 1
    #if(guangfeng_name)
      #set(guangfeng_name = "%" + guangfeng_name + "%")
      AND guangfeng_name LIKE #p(guangfeng_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("resultList")
    SELECT
    guangfeng_number,
    COUNT(guangfeng_number) AS guangfeng_count
    FROM table_guangfeng
    WHERE system_status = 1
    GROUP BY guangfeng_number
    ORDER BY guangfeng_number ASC
  #end

  #sql("find")
    SELECT
    *
    FROM table_guangfeng
    WHERE system_status = 1
    AND guangfeng_id = #p(guangfeng_id)
  #end

  #sql("delete")
    UPDATE table_guangfeng SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE guangfeng_id = #p(guangfeng_id)
  #end

#end