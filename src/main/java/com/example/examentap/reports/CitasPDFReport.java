package com.example.examentap.reports;

import com.example.examentap.databases.dao.CitasDao;
import com.example.examentap.models.Datos_Cita;

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
import java.util.List;

public class CitasPDFReport {
    CitasDao citasDao = new CitasDao();

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
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 3, 2, 2, 2, 2, 1, 2}))
                .useAllAvailableWidth();
        process(table, null, bold, true);

        for(Datos_Cita p : citasDao.findAll()){
            process(table, p, font, false);
        }
        //table.addCell(new Cell().add(new Paragraph(" ")));

        document.add(table);
        document.close();
    }

    public void process(Table table, Datos_Cita p, PdfFont font, boolean isHeader) {
        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("ID").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("NAME").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("MAIL").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("TEL").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("DATE").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("TIME").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("ID PROPIEDAD").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("ID USUARIO").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("STATUS").setFont(font)));


        } else {

            table.addCell(new Cell().add(new Paragraph(p.getId_cita()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getNombre_completo()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getCorreo()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getTelefono()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getFecha_cita()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getHora_cita()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getId_propiedad()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getId_usuario()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getId_cita()+"").setFont(font)));
            //table.addCell(new Cell().add(new Paragraph(p.getImagen()+"").setFont(font)));


        }
    }
}
