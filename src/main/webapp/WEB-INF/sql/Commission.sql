#namespace("commission")

  #sql("list")
    SELECT
    commission_id,
    product_attribute,
    product_commission
    FROM table_commission
    WHERE system_status = 1
    AND product_id = #p(product_id)
    ORDER BY system_create_time DESC
  #end

  #sql("find")
    SELECT
    *
    FROM table_commission
    WHERE system_status = 1
    AND commission_id = #p(commission_id)
  #end

  #sql("save")
    INSERT INTO table_commission (
      commission_id,
      product_id,
      product_attribute,
      product_commission,
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
      ?
    )
  #end

  #sql("delete")
    UPDATE table_commission SET
    system_update_user_id = ?,
    system_update_time = ?,
    system_status = 0
    WHERE commission_id = ?
  #end

  #sql("deleteByProduct_id")
    UPDATE table_commission SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE product_id = #p(product_id)
  #end

#end