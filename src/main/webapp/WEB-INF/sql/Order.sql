#namespace("order")

  #sql("count")
    SELECT COUNT(*) FROM table_order
    WHERE system_status = 1
    #if(order_number)
      #set(order_number = "%" + order_number + "%")
      AND order_number LIKE #p(order_number)
    #end
  #end

  #sql("list")
    SELECT
    order_id,
    order_number
    FROM table_order
    WHERE system_status = 1
    #if(order_number)
      #set(order_number = "%" + order_number + "%")
      AND order_number LIKE #p(order_number)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("listByUser_id")
    SELECT
    order_id,
    order_number,
    order_receivable_amount,
    order_status
    FROM table_order
    WHERE system_status = 1
    AND user_id LIKE #p(user_id)
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("listOrderNumber")
    SELECT
    order_number
    FROM table_order
    #set(order_number = "%" + order_number + "%")
    WHERE order_number LIKE #p(order_number)
  #end

  #sql("find")
    SELECT
    *
    FROM table_order
    WHERE system_status = 1
    AND order_id = #p(order_id)
  #end

  #sql("delete")
    UPDATE table_order SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE order_id = #p(order_id)
  #end

#end