#namespace("order_product")

  #sql("find")
    SELECT
    *
    FROM table_order_product
    WHERE system_status = 1
    AND order_product_id = #p(order_product_id)
  #end

  #sql("delete")
    UPDATE table_order_product SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE order_product_id = #p(order_product_id)
  #end

#end