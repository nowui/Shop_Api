#namespace("sku")

  #sql("list")
    SELECT
    sku_id,
    sku_name
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

  #sql("delete")
    UPDATE table_sku SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE sku_id = #p(sku_id)
  #end

#end