#namespace("landwind")

  #sql("count")
    SELECT COUNT(*) FROM table_landwind
    WHERE system_status = 1
    #if(landwind_name)
      #set(landwind_name = "%" + landwind_name + "%")
      AND landwind_name LIKE #p(landwind_name)
    #end
  #end

  #sql("list")
    SELECT
    landwind_id,
    landwind_name,
    landwind_phone,
    landwind_car,
    landwind_sex,
    system_create_time
    FROM table_landwind
    WHERE system_status = 1
    #if(landwind_name)
      #set(landwind_name = "%" + landwind_name + "%")
      AND landwind_name LIKE #p(landwind_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    *
    FROM table_landwind
    WHERE system_status = 1
    AND landwind_id = #p(landwind_id)
  #end

  #sql("delete")
    UPDATE table_landwind SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE landwind_id = #p(landwind_id)
  #end

#end