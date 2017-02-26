#namespace("user")

  #sql("countByObject_idAndUser_account")
    SELECT COUNT(*) FROM table_user
    WHERE system_status = 1
    #if(object_id)
      AND object_id != #p(object_id)
    #end
    AND user_account = #p(user_account)
  #end

  #sql("findByUser_accountAndUser_passwordAndUser_type")
    SELECT
    *
    FROM table_user
    WHERE table_user.system_status = 1
    AND user_account = #p(user_account)
    AND user_password = #p(user_password)
    AND user_type = #p(user_type)
  #end

  #sql("updateByUser_password")
    UPDATE table_user SET
    user_password = #p(user_password),
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time)
    WHERE user_id = #p(user_id)
  #end

  #sql("updateByObject_idAndUser_accountAndUser_type")
    UPDATE table_user SET
    user_account = #p(user_account),
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time)
    WHERE object_id = #p(object_id)
    AND user_type = #p(user_type)
  #end

  #sql("updateByObject_idAndUser_passwordAndUser_type")
    UPDATE table_user SET
    user_password = #p(user_password),
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time)
    WHERE object_id = #p(object_id)
    AND user_type = #p(user_type)
  #end

  #sql("deleteByObject_idAndUser_type")
    UPDATE table_user SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE object_id = #p(object_id)
    AND user_type = #p(user_type)
  #end

  #sql("deleteByUser_type")
    UPDATE table_user SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE user_type = #p(user_type)
  #end

#end