#namespace("member")

  #sql("count")
    SELECT COUNT(*) FROM table_member
    WHERE system_status = 1
    #if(member_name)
      #set(member_name = "%" + member_name + "%")
      AND member_name LIKE #p(member_name)
    #end
  #end

  #sql("list")
    SELECT
    member_id,
    member_name
    FROM table_member
    WHERE system_status = 1
    #if(member_name)
      #set(member_name = "%" + member_name + "%")
      AND member_name LIKE #p(member_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    table_member.*,
    table_user.user_phone
    FROM table_member
    LEFT JOIN table_user ON table_user.user_id = table_member.user_id
    WHERE table_member.system_status = 1
    AND table_member.member_id = #p(member_id)
  #end

  #sql("delete")
    UPDATE table_member SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE member_id = #p(member_id)
  #end

#end