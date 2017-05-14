#namespace("scene")

  #sql("count")
    SELECT COUNT(*) FROM table_scene
    WHERE system_status = 1
    #if(scene_type)
      AND scene_type = #p(scene_type)
    #end
  #end

  #sql("list")
    SELECT
    scene_id,
    scene_type,
    scene_is_expire,
    scene_add,
    scene_cancel
    FROM table_scene
    WHERE system_status = 1
    #if(scene_type)
      AND scene_type = #p(scene_type)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    *
    FROM table_scene
    WHERE system_status = 1
    AND scene_id = #p(scene_id)
  #end

  #sql("updateScene_addByScene_id")
    UPDATE table_scene SET
    scene_add = scene_add + 1
    WHERE scene_id = #p(scene_id)
  #end

  #sql("updateScene_cancelByScene_id")
    UPDATE table_scene SET
    scene_cancel = scene_cancel + 1
    WHERE scene_id = #p(scene_id)
  #end

  #sql("updateScene_is_expireByScene_id")
    UPDATE table_scene SET
    scene_is_expire = 1
    WHERE scene_id = #p(scene_id)
  #end

  #sql("delete")
    UPDATE table_scene SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE scene_id = #p(scene_id)
  #end

#end