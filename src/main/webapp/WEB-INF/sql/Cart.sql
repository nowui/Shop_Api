#namespace("cart")

  #sql("count")
    SELECT COUNT(*) FROM table_cart
    WHERE system_status = 1
  #end

  #sql("list")
    SELECT
    cart_id,
    cart_name
    FROM table_cart
    WHERE system_status = 1
    ORDER BY system_create_time DESC
  #end

  #sql("listByUser_id")
    SELECT
    cart_id,
    cart_name
    FROM table_cart
    WHERE system_status = 1
    AND user_id = #p(user_id)
    ORDER BY system_create_time DESC
  #end

  #sql("find")
    SELECT
    *
    FROM table_cart
    WHERE system_status = 1
    AND cart_id = #p(cart_id)
  #end

  #sql("update")
    UPDATE table_cart SET
    cart_product_quantity = #p(cart_product_quantity),
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time)
    WHERE cart_id = #p(cart_id)
  #end

  #sql("delete")
    UPDATE table_cart SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE cart_id = #p(cart_id)
  #end

#end