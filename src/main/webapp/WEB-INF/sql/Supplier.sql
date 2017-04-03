#namespace("supplier")

  #sql("count")
    SELECT COUNT(*) FROM table_supplier
    WHERE system_status = 1
    #if(supplier_name)
      #set(supplier_name = "%" + supplier_name + "%")
      AND supplier_name LIKE #p(supplier_name)
    #end
  #end

  #sql("list")
    SELECT
    supplier_id,
    supplier_name
    FROM table_supplier
    WHERE system_status = 1
    #if(supplier_name)
      #set(supplier_name = "%" + supplier_name + "%")
      AND supplier_name LIKE #p(supplier_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    table_supplier.*,
    table_user.user_account
    FROM table_supplier
    LEFT JOIN table_user ON table_user.user_id = table_supplier.user_id
    WHERE table_supplier.system_status = 1
    AND table_supplier.supplier_id = #p(supplier_id)
  #end

  #sql("delete")
    UPDATE table_supplier SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE supplier_id = #p(supplier_id)
  #end

#end