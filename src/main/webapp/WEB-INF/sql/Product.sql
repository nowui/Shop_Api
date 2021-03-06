#namespace("product")

  #sql("count")
    SELECT COUNT(*) FROM table_product
    WHERE system_status = 1
    #if(product_name)
      #set(product_name = "%" + product_name + "%")
      AND product_name LIKE #p(product_name)
    #end
    #if(category_id)
      AND category_id = #p(product_name)
    #end
    #if(brand_id)
      AND brand_id = #p(brand_id)
    #end
  #end

  #sql("list")
    SELECT
    product_id,
    product_name,
    product_image,
    product_price
    FROM table_product
    WHERE system_status = 1
    #if(product_name)
      #set(product_name = "%" + product_name + "%")
      AND product_name LIKE #p(product_name)
    #end
    #if(category_id)
      AND category_id = #p(product_name)
    #end
    #if(brand_id)
      AND brand_id = #p(brand_id)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("listAll")
    SELECT
    product_id,
    category_id,
    product_name,
    product_image,
    product_price
    FROM table_product
    WHERE system_status = 1
    ORDER BY product_is_hot DESC, system_create_time DESC
  #end

  #sql("listAllHot")
    SELECT
    product_id,
    product_name,
    product_image,
    product_price,
    system_create_time
    FROM table_product
    WHERE system_status = 1
    AND product_is_hot = 1
    ORDER BY system_create_time DESC
  #end

  #sql("find")
    SELECT
    *
    FROM table_product
    WHERE system_status = 1
    AND product_id = #p(product_id)
  #end

  #sql("delete")
    UPDATE table_product SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE product_id = #p(product_id)
  #end

#end