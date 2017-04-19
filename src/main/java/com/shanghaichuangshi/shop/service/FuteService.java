package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.render.ExcelRender;
import com.shanghaichuangshi.shop.dao.FuteDao;
import com.shanghaichuangshi.shop.model.Fute;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.model.Landwind;
import com.shanghaichuangshi.util.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.List;

public class FuteService extends Service {

    private final FuteDao futeDao = new FuteDao();

    public int count(Fute fute) {
        return futeDao.count(fute.getFute_name());
    }

    public List<Fute> list(Fute fute, int m, int n) {
        return futeDao.list(fute.getFute_name(), m, n);
    }

    public Fute find(String fute_id) {
        return futeDao.find(fute_id);
    }

    public Fute save(Fute fute, String request_user_id) {
        return futeDao.save(fute, request_user_id);
    }

    public boolean update(Fute fute, String request_user_id) {
        return futeDao.update(fute, request_user_id);
    }

    public boolean delete(Fute fute, String request_user_id) {
        return futeDao.delete(fute.getFute_id(), request_user_id);
    }

    public ExcelRender export() {
        List<Fute> futeList = futeDao.list("", 0, 0);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFSheet sheet = wb.createSheet("数据汇总");

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("性别");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("手机号码");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("省份");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("城市");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("经销商");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("填写时间");
        cell.setCellStyle(style);

        for (int i = 0; i < futeList.size(); i++) {
            Fute fute = futeList.get(i);

            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(fute.getFute_name());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(fute.getFute_sex());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(fute.getFute_phone());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(fute.getFute_province());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(fute.getFute_city());
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue(fute.getFute_distributor());
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue(DateUtil.getDateTimeString(fute.getSystem_create_time()));
            cell.setCellStyle(style);
        }

        return new ExcelRender(wb, "用户信息");
    }

}