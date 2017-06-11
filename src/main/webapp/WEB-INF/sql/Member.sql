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
    member_name,
    system_create_time
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

  #sql("treeList")
    SELECT
    member_id,
    member_name,
    parent_id,
    user_id,
    member_level_id
    FROM table_member
    WHERE system_status = 1
  #end

  #sql("teamList")
    SELECT
    member_id,
    member_name,
    user_id,
    member_level_id,
    member_status
    FROM table_member
    WHERE system_status = 1
    AND parent_id = #p(parent_id)
    ORDER BY system_create_time DESC
  #end

  #sql("find")
    SELECT
    *
    FROM table_member
    WHERE system_status = 1
    AND member_id = #p(member_id)
  #end

  #sql("updateByMember_idAndScene_idAndScene_qrcode")
    UPDATE table_member SET
    scene_id = #p(scene_id),
    scene_qrcode = #p(scene_qrcode),
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time)
    WHERE member_id = #p(member_id)
  #end

  #sql("childrenUpdate")
    UPDATE table_member SET
    member_level_id = #p(member_level_id),
    member_status = 1,
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

  #sql("updateByMember_idAndMember_name")
    UPDATE table_member SET
    member_name = #p(member_name),
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time)
    WHERE member_id = #p(member_id)
  #end

  #sql("updateByMember_idAndMember_level_id")
    UPDATE table_member SET
    member_level_id = #p(member_level_id),
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time)
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