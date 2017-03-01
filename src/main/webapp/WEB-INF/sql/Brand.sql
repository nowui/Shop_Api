#namespace("brand")

  #sql("count")
    SELECT COUNT(*) FROM table_brand
    WHERE system_status = 1
    #if(brand_name)
      #set(brand_name = "%" + brand_name + "%")
      AND brand_name LIKE #p(brand_name)
    #end
  #end

  #sql("list")
    SELECT
    brand_id,
    brand_name
    FROM table_brand
    WHERE system_status = 1
    #if(brand_name)
      #set(brand_name = "%" + brand_name + "%")
      AND brand_name LIKE #p(brand_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    *
    FROM table_brand
    WHERE system_status = 1
    AND brand_id = #p(brand_id)
  #end

  #sql("delete")
    UPDATE table_brand SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE brand_id = #p(brand_id)
  #end

#end