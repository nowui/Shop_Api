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

  #sql("teamList")
    SELECT
    member_id,
    member_name,
    member_status
    FROM table_member
    WHERE system_status = 1
    AND parent_id = #p(parent_id)
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

  #sql("updateByMember_idAndScene_idAndScene_qrcode")
    UPDATE table_member SET
    scene_id = #p(scene_id),
    scene_qrcode = #p(scene_qrcode),
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time)
    WHERE member_id = #p(member_id)
  #end

  #sql("updateByMember_idAndParent_idAndParent_pathAndMember_level_id")
    UPDATE table_member SET
    parent_id = #p(parent_id),
    parent_path = #p(parent_path),
    member_level_id = #p(member_level_id)
    WHERE member_id = #p(member_id)
  #end

  #sql("updateByMember_total_amountAndMember_withdrawal_amount")
    UPDATE table_member SET
    member_total_amount = #p(member_total_amount),
    member_withdrawal_amount = #p(member_withdrawal_amount)
    WHERE member_id = #p(member_id)
  #end

  #sql("delete")
    UPDATE table_member SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE member_id = #p(member_id)
  #end

#end