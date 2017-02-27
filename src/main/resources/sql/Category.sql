#namespace("category")

  #sql("count")
    SELECT COUNT(*) FROM table_category
    WHERE system_status = 1
    #if(category_name)
      #set(category_name = "%" + category_name + "%")
      AND category_name LIKE #p(category_name)
    #end
  #end

  #sql("countByCategory_idAndCategory_key")
    SELECT COUNT(*) FROM table_category
    WHERE system_status = 1
    #if(category_id)
      AND category_id != #p(category_id)
    #end
    AND category_key = #p(category_key)
  #end

  #sql("list")
    SELECT
    category_id,
    category_name,
    category_key,
    category_sort
    FROM table_category
    WHERE system_status = 1
    #if(category_name)
      #set(category_name = "%" + category_name + "%")
      AND category_name LIKE #p(category_name)
    #end
    AND parent_id = ''
    ORDER BY category_sort, system_create_time ASC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("treeListByCategory_path")
    SELECT
    category_id,
    parent_id,
    category_name,
    category_key,
    category_value,
    category_remark,
    category_sort
    FROM table_category
    WHERE system_status = 1
    AND category_path LIKE #p(category_path)
    ORDER BY category_sort, system_create_time ASC
  #end

  #sql("treeListByCategory_key")
    SELECT
    category_id,
    category_name
    FROM table_category
    WHERE system_status = 1
    AND category_path LIKE (SELECT CONCAT('%', category_id, '%') FROM table_category WHERE category_key = #p(category_key) )
    ORDER BY category_sort, system_create_time ASC
  #end

  #sql("find")
    SELECT
    *
    FROM table_category
    WHERE system_status = 1
    AND category_id = #p(category_id)
  #end

  #sql("findByCategory_key")
    SELECT
    *
    FROM table_category
    WHERE table_category.system_status = 1
    AND category_key = #p(category_key)
  #end

  #sql("delete")
    UPDATE table_category SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE category_id = #p(category_id)
    #set(category_id = "%" + category_id + "%")
    OR category_path LIKE #p(category_id)
  #end

#end