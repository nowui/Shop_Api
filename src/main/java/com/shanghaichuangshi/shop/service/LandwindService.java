package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.render.ExcelRender;
import com.shanghaichuangshi.shop.dao.LandwindDao;
import com.shanghaichuangshi.shop.model.Landwind;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.util.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.List;

public class LandwindService extends Service {

    private final LandwindDao landwindDao = new LandwindDao();

    public int count(Landwind landwind) {
        return landwindDao.count(landwind.getLandwind_name());
    }

    public List<Landwind> list(Landwind landwind, int m, int n) {
        return landwindDao.list(landwind.getLandwind_name(), m, n);
    }

    public Landwind find(String landwind_id) {
        return landwindDao.find(landwind_id);
    }

    public Landwind save(Landwind landwind, String request_user_id) {
        return landwindDao.save(landwind, request_user_id);
    }

    public boolean update(Landwind landwind, String request_user_id) {
        return landwindDao.update(landwind, request_user_id);
    }

    public boolean delete(Landwind landwind, String request_user_id) {
        return landwindDao.delete(landwind.getLandwind_id(), request_user_id);
    }



    public ExcelRender export() {
        List<Landwind> landwindList = landwindDao.list("", 0, 0);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFSheet sheet = wb.createSheet("数据汇总");

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("手机号码");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("意向车型");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("性别");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("填写时间");
        cell.setCellStyle(style);

        for (int i = 0; i < landwindList.size(); i++) {
            Landwind landwind = landwindList.get(i);

            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(landwind.getLandwind_name());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(landwind.getLandwind_phone());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(landwind.getLandwind_car());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(landwind.getLandwind_sex());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(DateUtil.getDateTimeString(landwind.getSystem_create_time()));
            cell.setCellStyle(style);
        }

        return new ExcelRender(wb, "用户信息");
    }

}