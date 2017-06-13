#namespace("article")

  #sql("count")
    SELECT COUNT(*) FROM table_article
    WHERE system_status = 1
    #if(article_name)
      #set(article_name = "%" + article_name + "%")
      AND article_name LIKE #p(article_name)
    #end
  #end

  #sql("list")
    SELECT
    article_id,
    article_name
    FROM table_article
    WHERE system_status = 1
    #if(article_name)
      #set(article_name = "%" + article_name + "%")
      AND article_name LIKE #p(article_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("listByCategory_id")
    SELECT
    article_id,
    article_name,
    article_image,
    article_summary
    FROM table_article
    WHERE system_status = 1
    AND category_id = #p(category_id)
    ORDER BY system_create_time DESC
  #end

  #sql("find")
    SELECT
    *
    FROM table_article
    WHERE system_status = 1
    AND article_id = #p(article_id)
  #end

  #sql("delete")
    UPDATE table_article SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE article_id = #p(article_id)
  #end

#end