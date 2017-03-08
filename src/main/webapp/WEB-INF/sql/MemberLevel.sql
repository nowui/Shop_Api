#namespace("member_level")

  #sql("count")
    SELECT COUNT(*) FROM table_member_level
    WHERE system_status = 1
    #if(member_level_name)
      #set(member_level_name = "%" + member_level_name + "%")
      AND member_level_name LIKE #p(member_level_name)
    #end
  #end

  #sql("list")
    SELECT
    member_level_id,
    member_level_name
    FROM table_member_level
    WHERE system_status = 1
    #if(member_level_name)
      #set(member_level_name = "%" + member_level_name + "%")
      AND member_level_name LIKE #p(member_level_name)
    #end
    ORDER BY member_level_sort, system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    *
    FROM table_member_level
    WHERE system_status = 1
    AND member_level_id = #p(member_level_id)
  #end

  #sql("delete")
    UPDATE table_member_level SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE member_level_id = #p(member_level_id)
  #end

#end