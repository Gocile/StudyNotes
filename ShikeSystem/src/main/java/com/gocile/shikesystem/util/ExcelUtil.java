package com.gocile.shikesystem.util;

import com.gocile.shikesystem.model.Course;
import com.gocile.shikesystem.model.ExcelData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    public static List<ExcelData> parseUserExcel(MultipartFile file) throws IOException {
        List<ExcelData> list = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            ExcelData excelData = new ExcelData();
            excelData.setIdentity((int) row.getCell(0).getNumericCellValue());
            excelData.setName(row.getCell(1).getStringCellValue());
            excelData.setGender(row.getCell(2).getStringCellValue());
            excelData.setId(row.getCell(3).getStringCellValue());
            excelData.setPhoneNum(row.getCell(4).getStringCellValue());
            excelData.setEmail(row.getCell(5).getStringCellValue());
            excelData.setCollege(row.getCell(6).getStringCellValue());
            if(row.getCell(7)==null){
                list.add(excelData);
                continue;
            }
            excelData.setMajor(row.getCell(7).getStringCellValue());
            excelData.setGrade((int) row.getCell(8).getNumericCellValue());
            list.add(excelData);
        }
        workbook.close();
        return list;
    }

    public static List<Course> parseCourseExcel(String adminId,String adminName,MultipartFile file) throws IOException {
        List<Course> list = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Course course = new Course();
            course.setTitle(row.getCell(0).getStringCellValue());
            course.setGrade((int)row.getCell(1).getNumericCellValue());
            course.setCollege(row.getCell(2).getStringCellValue());
            course.setMajor(row.getCell(3).getStringCellValue());
            course.setCapacity((int) row.getCell(4).getNumericCellValue());
            course.setOptionality(row.getCell(5).getStringCellValue());
            course.setCategory(row.getCell(6).getStringCellValue());
            course.setSemester(row.getCell(7).getStringCellValue());
            course.setAdminId(adminId);
            course.setAdminName(adminName);
            list.add(course);
        }
        workbook.close();
        return list;
    }
}
