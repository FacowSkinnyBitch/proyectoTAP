package com.example.examentap.reports;
import com.example.examentap.databases.dao.UsuarioDAO;
import com.example.examentap.models.Usuario;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class ExcelReports {
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
        XSSFCell cellHeader11 = row.createCell(10);
        cellHeader11.setCellValue("IMAGE");
        cellHeader11.setCellStyle(cellTitleStyle);

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
            // Resto del código

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



            XSSFCell cell10 = newRow.createCell(9);
            //cell7.setCellValue(product.getCategory().getName());
            //cell8.setCellStyle(cellContentStyle);
            //InputStream inputStream1 = getClass().getResourceAsStream("/img/" + product.getImage());

            /*
            try {
                //byte[] inputImageBytes1 = IOUtils.toByteArray(inputStream1);
                //int inputImagePictureID1 = workbook.addPicture(inputImageBytes1, workbook.PICTURE_TYPE_JPEG);
                XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                XSSFClientAnchor imageAnchor = new XSSFClientAnchor();
                imageAnchor.setCol1(7); // Sets the column (0 based) of the first cell.
                imageAnchor.setCol2(8); // Sets the column (0 based) of the Second cell.

                imageAnchor.setRow1(row_number-1); // Sets the row (0 based) of the first cell.
                imageAnchor.setRow2(row_number); // Sets the row (0 based) of the Second cell.
                XSSFPicture myPicture = drawing.createPicture(imageAnchor, inputImagePictureID1);
                //myPicture.resize();
                //myPicture.getImageDimension().setSize(100, 100);
                //newRow.setHeight((short)-1);
                newRow.setHeightInPoints(60);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

             */


        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);



        //SHEET 2 PRODUCTS BY CATEGORY
        /*
        XSSFRow rowSheet2 = sheet2.createRow(0);

        XSSFCell cellHeaderDepartment = rowSheet2.createCell(0);
        cellHeaderDepartment.setCellValue("Category");
        cellHeaderDepartment.setCellStyle(cellTitleStyle);
        XSSFCell cellHeaderTotalEmployees = rowSheet2.createCell(1);
        cellHeaderTotalEmployees.setCellValue("Total Products");
        cellHeaderTotalEmployees.setCellStyle(cellTitleStyle);

        //row_number = 1;
        Map<String, Integer> prodList = productDao.totalProductsByCategory();
        AtomicInteger rowNum = new AtomicInteger(1);
        prodList.forEach((key, value) -> {
            XSSFRow _row = sheet2.createRow(rowNum.getAndIncrement());
            XSSFCell cellDept = _row.createCell(0);
            cellDept.setCellValue(key);
            XSSFCell cellTotal = _row.createCell(1);
            cellTotal.setCellValue(value);
        });

        sheet2.autoSizeColumn(0);
        sheet2.autoSizeColumn(1);

         */

        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

