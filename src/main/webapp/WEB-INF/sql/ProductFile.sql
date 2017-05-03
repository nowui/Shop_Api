#namespace("product_file")

  #sql("list")
    SELECT
    product_file_id,
    product_file_path,
    product_file_original_path
    FROM table_product_file
    WHERE system_status = 1
    AND product_id = #p(product_id)
    ORDER BY system_create_time DESC
  #end

  #sql("find")
    SELECT
    *
    FROM table_product_file
    WHERE system_status = 1
    AND product_file_id = #p(product_file_id)
  #end

  #sql("save")
    INSERT INTO table_product_file (
      product_file_id,
      product_id,
      product_file_type,
      product_file_name,
      product_file_path,
      product_file_thumbnail_path,
      product_file_original_path,
      product_file_remark,
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
      ?
    )
  #end

  #sql("delete")
    UPDATE table_product_file SET
    system_update_user_id = ?,
    system_update_time = ?,
    system_status = 0
    WHERE product_file_id = ?
  #end

#end