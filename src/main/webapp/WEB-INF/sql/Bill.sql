#namespace("bill")

  #sql("count")
    SELECT COUNT(*) FROM table_bill
    WHERE system_status = 1
    #if(bill_name)
      #set(bill_name = "%" + bill_name + "%")
      AND bill_name LIKE #p(bill_name)
    #end
  #end

  #sql("list")
    SELECT
    bill_id,
    bill_name
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

  #sql("find")
    SELECT
    *
    FROM table_bill
    WHERE system_status = 1
    AND bill_id = #p(bill_id)
  #end

  #sql("delete")
    UPDATE table_bill SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE bill_id = #p(bill_id)
  #end

#end