#namespace("bill")

  #sql("count")
    SELECT COUNT(*) FROM table_bill
    WHERE system_status = 1
    #if(bill_name)
      #set(bill_name = "%" + bill_name + "%")
      AND bill_name LIKE #p(bill_name)
    #end
  #end

  #sql("findBill_AmountByUser_idAndBill_type")
    SELECT SUM(bill_amount) FROM table_bill
    WHERE system_status = 1
    AND user_id = #p(user_id)
    AND bill_type = #p(bill_type)
  #end

  #sql("list")
    SELECT
    bill_id,
    object_id,
    bill_type,
    bill_image,
    bill_name,
    bill_amount,
    bill_is_income,
    bill_time,
    bill_flow,
    bill_status
    FROM table_bill
    WHERE system_status = 1
    #if(bill_name)
      #set(bill_name = "%" + bill_name + "%")
      AND bill_name LIKE #p(bill_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("listByUser_id")
    SELECT
    bill_id,
    object_id,
    bill_type,
    bill_image,
    bill_name,
    bill_amount,
    bill_is_income,
    bill_time,
    bill_flow,
    bill_status
    FROM table_bill
    WHERE system_status = 1
    AND user_id = #p(user_id)
  #end

  #sql("find")
    SELECT
    *
    FROM table_bill
    WHERE system_status = 1
    AND bill_id = #p(bill_id)
  #end

  #sql("save")
    INSERT INTO table_bill (
      bill_id,
      user_id,
      object_id,
      bill_type,
      bill_image,
      bill_name,
      bill_amount,
      bill_is_income,
      bill_time,
      bill_flow,
      bill_status,
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
      ?
    )
  #end

  #sql("delete")
    UPDATE table_bill SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE bill_id = #p(bill_id)
  #end

#end