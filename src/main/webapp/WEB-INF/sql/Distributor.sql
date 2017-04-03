#namespace("distributor")

  #sql("count")
    SELECT COUNT(*) FROM table_distributor
    WHERE system_status = 1
    #if(distributor_name)
      #set(distributor_name = "%" + distributor_name + "%")
      AND distributor_name LIKE #p(distributor_name)
    #end
  #end

  #sql("list")
    SELECT
    distributor_id,
    distributor_name
    FROM table_distributor
    WHERE system_status = 1
    #if(distributor_name)
      #set(distributor_name = "%" + distributor_name + "%")
      AND distributor_name LIKE #p(distributor_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    table_distributor.*,
    table_user.user_account
    FROM table_distributor
    LEFT JOIN table_user ON table_user.user_id = table_distributor.user_id
    WHERE table_distributor.system_status = 1
    AND table_distributor.distributor_id = #p(distributor_id)
  #end

  #sql("delete")
    UPDATE table_distributor SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE distributor_id = #p(distributor_id)
  #end

#end