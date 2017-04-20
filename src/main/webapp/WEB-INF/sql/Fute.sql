#namespace("fute")

  #sql("count")
    SELECT COUNT(*) FROM table_fute
    WHERE system_status = 1
    #if(fute_name)
      #set(fute_name = "%" + fute_name + "%")
      AND fute_name LIKE #p(fute_name)
    #end
  #end

  #sql("list")
    SELECT
    fute_id,
    fute_name,
    fute_sex,
    fute_phone,
    fute_province,
    fute_city,
    fute_distributor,
    fute_platform,
    system_create_time
    FROM table_fute
    WHERE system_status = 1
    #if(fute_name)
      #set(fute_name = "%" + fute_name + "%")
      AND fute_name LIKE #p(fute_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    *
    FROM table_fute
    WHERE system_status = 1
    AND fute_id = #p(fute_id)
  #end

  #sql("delete")
    UPDATE table_fute SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE fute_id = #p(fute_id)
  #end

#end