#namespace("role")

  #sql("count")
    SELECT COUNT(*) FROM table_role
    WHERE system_status = 1
    #if(role_name)
      #set(role_name = "%" + role_name + "%")
      AND role_name LIKE #p(role_name)
    #end
  #end

  #sql("list")
    SELECT
    role_id,
    role_name
    FROM table_role
    WHERE system_status = 1
    #if(role_name)
      #set(role_name = "%" + role_name + "%")
      AND role_name LIKE #p(role_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    *
    FROM table_role
    WHERE system_status = 1
    AND role_id = #p(role_id)
  #end

  #sql("delete")
    UPDATE table_role SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE role_id = #p(role_id)
  #end

#end