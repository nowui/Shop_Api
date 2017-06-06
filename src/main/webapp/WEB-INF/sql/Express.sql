#namespace("express")

  #sql("count")
    SELECT COUNT(*) FROM table_express
    WHERE system_status = 1
    #if(express_number)
      AND express_number = #p(express_number)
    #end
  #end

  #sql("list")
    SELECT
    express_id,
    express_type,
    express_number,
    express_flow,
    express_status
    FROM table_express
    WHERE system_status = 1
    #if(express_number)
      AND express_number = #p(express_number)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("listByOrder_id")
    SELECT
    express_id,
    express_type,
    express_number,
    express_flow,
    express_status
    FROM table_express
    WHERE system_status = 1
    AND order_id = #p(order_id)
    ORDER BY system_create_time DESC
  #end

  #sql("find")
    SELECT
    *
    FROM table_express
    WHERE system_status = 1
    AND express_id = #p(express_id)
  #end

  #sql("updateBusiness")
    UPDATE table_express SET
    express_flow = ?,
    express_status = ?,
    express_trace = ?,
    system_update_time = ?
    WHERE express_id = ?
  #end

  #sql("delete")
    UPDATE table_express SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE express_id = #p(express_id)
  #end

#end