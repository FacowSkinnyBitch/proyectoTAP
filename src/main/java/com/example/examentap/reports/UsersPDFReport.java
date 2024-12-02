package com.example.examentap.reports;

import com.example.examentap.databases.dao.UsuarioDAO;
import com.example.examentap.models.Usuario;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;

public class UsersPDFReport {
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void createPdf(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 3, 2, 2, 2, 2, 1, 2, 2}))
                .useAllAvailableWidth();
        process(table, null, bold, true);

        for(Usuario p : usuarioDAO.findAll()){
            process(table, p, font, false);
        }
        //table.addCell(new Cell().add(new Paragraph(" ")));

        document.add(table);
        document.close();
    }

    public void process(Table table, Usuario p, PdfFont font, boolean isHeader) {
        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("ID").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("USER").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("NAME").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("FIRST SURNAME").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("lAST NAME").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("EMAIL").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("TEL").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("ADDRESS").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("GENDER").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("ROLE").setFont(font)));


        } else {

            table.addCell(new Cell().add(new Paragraph(p.getId()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getUser()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getNombre()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getPrimer_apellido()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getSegundo_apellido()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getEmail()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getTelefono()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getDireccion()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getGenero()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getRole()+"").setFont(font)));
            //table.addCell(new Cell().add(new Paragraph(p.getImagen()+"").setFont(font)));




        }
    }
}

