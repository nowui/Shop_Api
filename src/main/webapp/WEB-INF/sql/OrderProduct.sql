#namespace("order_product")

  #sql("list")
    SELECT
    product_id,
    product_name,
    product_image,
    product_price,
    product_quantity,
    commission_id,
    member_id,
    member_path,
    product_price,
    product_quantity
    FROM table_order_product
    WHERE system_status = 1
    AND order_id = #p(order_id)
    ORDER BY system_create_time DESC
  #end

  #sql("find")
    SELECT
    *
    FROM table_order_product
    WHERE system_status = 1
    AND order_product_id = #p(order_product_id)
  #end

  #sql("save")
    INSERT INTO table_order_product (
      order_product_id,
      order_id,
      order_status,
      product_id,
      category_id,
      category_name,
      brand_id,
      brand_name,
      product_name,
      product_image,
      product_image_list,
      product_is_new,
      product_is_recommend,
      product_is_bargain,
      product_is_hot,
      product_is_sale,
      product_content,
      sku_id,
      commission_id,
      member_id,
      member_path,
      product_attribute,
      product_market_price,
      product_price,
      product_stock,
      product_quantity,
      system_create_user_id,
      system_create_time,
      system_update_user_id,
      system_update_time,
      system_status
    ) VALUES (
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?,
      ?
    )
  #end

  #sql("updateByOrder_idAndOrder_status")
    UPDATE table_order_product SET
    order_status = #p(order_status),
    system_update_time = #p(system_update_time)
    WHERE order_id = #p(order_id)
  #end

  #sql("delete")
    UPDATE table_order_product SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE order_product_id = #p(order_product_id)
  #end

#end