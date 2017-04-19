#namespace("delivery")

  #sql("count")
    SELECT COUNT(*) FROM table_delivery
    WHERE system_status = 1
    #if(delivery_name)
      #set(delivery_name = "%" + delivery_name + "%")
      AND delivery_name LIKE #p(delivery_name)
    #end
    AND user_id = #p(user_id)
  #end

  #sql("list")
    SELECT
    delivery_id,
    delivery_name,
    delivery_phone,
    delivery_address
    FROM table_delivery
    WHERE system_status = 1
    #if(delivery_name)
      #set(delivery_name = "%" + delivery_name + "%")
      AND delivery_name LIKE #p(delivery_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("listByUser_id")
    SELECT
    delivery_id,
    delivery_name,
    delivery_phone,
    delivery_address
    FROM table_delivery
    WHERE system_status = 1
    AND user_id = #p(user_id)
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    *
    FROM table_delivery
    WHERE system_status = 1
    AND delivery_id = #p(delivery_id)
  #end

  #sql("findDefaultByUser_id")
    SELECT
    *
    FROM table_delivery
    WHERE system_status = 1
    AND delivery_is_default = 1
    AND user_id = #p(user_id)
  #end

  #sql("updateIsDefault")
    UPDATE table_delivery SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    delivery_is_default = 0
    WHERE user_id = #p(user_id)
    AND delivery_id != #p(delivery_id)
  #end

  #sql("delete")
    UPDATE table_delivery SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE delivery_id = #p(delivery_id)
  #end

#end