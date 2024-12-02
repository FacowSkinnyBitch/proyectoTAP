package com.example.examentap.reports;
import com.example.examentap.databases.dao.UsuarioDAO;
import com.example.examentap.models.Usuario;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UsersExcelReports {
    UsuarioDAO usuarioDao = new UsuarioDAO();

    public void createExcel(String filename, int user_id){
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Users");
        XSSFSheet sheet2 = workbook.createSheet("Total Users by category");

        XSSFRow row = sheet.createRow(0);

        CellStyle cellTitleStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellTitleStyle.setFont(font);
        cellTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFCell cellHeader1 = row.createCell(0);
        cellHeader1.setCellValue("ID");
        cellHeader1.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader2 = row.createCell(1);
        cellHeader2.setCellValue("USER");
        cellHeader2.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader3 = row.createCell(2);
        cellHeader3.setCellValue("NAME");
        cellHeader3.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader4 = row.createCell(3);
        cellHeader4.setCellValue("FIRST SURNAME");
        cellHeader4.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader5 = row.createCell(4);
        cellHeader5.setCellValue("LAST NAME");
        cellHeader5.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader6 = row.createCell(5);
        cellHeader6.setCellValue("EMAIL");
        cellHeader6.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader7 = row.createCell(6);
        cellHeader7.setCellValue("TEL");
        cellHeader7.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader8 = row.createCell(7);
        cellHeader8.setCellValue("ADDRES");
        cellHeader8.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader9 = row.createCell(8);
        cellHeader9.setCellValue("GENDER");
        cellHeader9.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader10 = row.createCell(9);
        cellHeader10.setCellValue("PROPERTY ACQUIRED");
        cellHeader10.setCellStyle(cellTitleStyle);


        int row_number = 1;
        CellStyle cellStyleDateFormat = workbook.createCellStyle();
        CreationHelper helper = workbook.getCreationHelper();
        cellStyleDateFormat.setDataFormat(helper.createDataFormat().getFormat("dd/mm/yyyy"));
        cellStyleDateFormat.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleDateFormat.setAlignment(HorizontalAlignment.CENTER);
        CellStyle cellContentStyle = sheet.getWorkbook().createCellStyle();
        cellContentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellContentStyle.setAlignment(HorizontalAlignment.CENTER);

        List<Usuario> users;
        if (user_id == 0) {
            users = usuarioDao.findAll();
        } else {
            Optional<Usuario> user = usuarioDao.findById(user_id);
            users = user != null ? List.of() : List.of(); // Si el usuario no existe, la lista estará vacía
        }

        for (Usuario user : users) {

            XSSFRow newRow = sheet.createRow(row_number++);

            XSSFCell cell1 = newRow.createCell(0);
            cell1.setCellValue(user.getId());
            cell1.setCellStyle(cellContentStyle);
            XSSFCell cell2 = newRow.createCell(1);
            cell2.setCellValue(user.getUser());
            cell2.setCellStyle(cellContentStyle);
            XSSFCell cell3 = newRow.createCell(2);
            cell3.setCellValue(user.getNombre());
            cell3.setCellStyle(cellContentStyle);
            XSSFCell cell4 = newRow.createCell(3);
            cell4.setCellValue(user.getPrimer_apellido());
            cell4.setCellStyle(cellContentStyle);
            XSSFCell cell5 = newRow.createCell(4);
            cell5.setCellValue(user.getSegundo_apellido());
            cell5.setCellStyle(cellContentStyle);
            XSSFCell cell6 = newRow.createCell(5);
            cell6.setCellValue(user.getEmail());
            cell6.setCellStyle(cellStyleDateFormat);
            XSSFCell cell7 = newRow.createCell(6);
            cell7.setCellValue(user.getTelefono());
            cell7.setCellStyle(cellStyleDateFormat);

            XSSFCell cell8 = newRow.createCell(7);
            cell8.setCellValue(user.getDireccion());
            cell8.setCellStyle(cellStyleDateFormat);
            XSSFCell cell9 = newRow.createCell(8);
            cell9.setCellValue(user.getGenero());
            cell9.setCellStyle(cellStyleDateFormat);

        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);


        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

