#namespace("attribute")

  #sql("count")
    SELECT COUNT(*) FROM table_attribute
    WHERE system_status = 1
    #if(attribute_name)
      #set(attribute_name = "%" + attribute_name + "%")
      AND attribute_name LIKE #p(attribute_name)
    #end
  #end

  #sql("list")
    SELECT
    attribute_id,
    attribute_name
    FROM table_attribute
    WHERE system_status = 1
    #if(attribute_name)
      #set(attribute_name = "%" + attribute_name + "%")
      AND attribute_name LIKE #p(attribute_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    *
    FROM table_attribute
    WHERE system_status = 1
    AND attribute_id = #p(attribute_id)
  #end

  #sql("delete")
    UPDATE table_attribute SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE attribute_id = #p(attribute_id)
  #end

#end