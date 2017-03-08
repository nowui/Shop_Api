#namespace("sku")

  #sql("list")
    SELECT
    sku_id,
    product_attribute,
    product_price,
    product_stock
    FROM table_sku
    WHERE system_status = 1
    AND product_id = #p(product_id)
    ORDER BY system_create_time DESC
  #end

  #sql("find")
    SELECT
    *
    FROM table_sku
    WHERE system_status = 1
    AND sku_id = #p(sku_id)
  #end

  #sql("save")
    INSERT INTO table_sku (
      sku_id,
      product_id,
      product_attribute,
      product_price,
      product_stock,
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
      ?
    )
  #end

  #sql("updateProduct_stock")
    UPDATE table_sku SET
    product_stock = #p(product_stock),
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time)
    WHERE sku_id = #p(sku_id)
  #end

  #sql("delete")
    UPDATE table_sku SET
    system_update_user_id = ?,
    system_update_time = ?,
    system_status = 0
    WHERE sku_id = ?
  #end

#end