#namespace("log")

  #sql("count")
    SELECT COUNT(*) FROM table_log
    WHERE system_status = 1
    #if(log_url)
      #set(log_url = "%" + log_url + "%")
      AND log_url LIKE #p(log_url)
    #end
  #end

  #sql("list")
    SELECT
    log_id,
    log_url,
    log_code,
    log_platform,
    log_version,
    log_create_time,
    log_run_time
    FROM table_log
    WHERE system_status = 1
    #if(log_url)
      #set(log_url = "%" + log_url + "%")
      AND log_url LIKE #p(log_url)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    *
    FROM table_log
    WHERE system_status = 1
    AND log_id = #p(log_id)
  #end

#end