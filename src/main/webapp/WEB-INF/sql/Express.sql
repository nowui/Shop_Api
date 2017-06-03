#namespace("express")

  #sql("count")
    SELECT COUNT(*) FROM table_express
    WHERE system_status = 1
    #if(order_id)
      AND order_id = #p(order_id)
    #end
  #end

  #sql("list")
    SELECT
    express_id,
    express_type,
    express_number,
    express_flow
    FROM table_express
    WHERE system_status = 1
    #if(order_id)
      AND order_id = #p(order_id)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    *
    FROM table_express
    WHERE system_status = 1
    AND express_id = #p(express_id)
  #end

  #sql("delete")
    UPDATE table_express SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE express_id = #p(express_id)
  #end

#end