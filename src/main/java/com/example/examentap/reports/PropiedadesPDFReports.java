package com.example.examentap.reports;

import com.example.examentap.databases.dao.PropiedadesDao;
import com.example.examentap.models.Propiedades;
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


public class PropiedadesPDFReports {
    PropiedadesDao propiedadesDao = new PropiedadesDao();

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
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 3, 2, 2, 2, 2, 1, 2, 2, 3}))
                .useAllAvailableWidth();
        process(table, null, bold, true);

        for(Propiedades p : propiedadesDao.findAll()){
            process(table, p, font, false);
        }
        //table.addCell(new Cell().add(new Paragraph(" ")));

        document.add(table);
        document.close();
    }

    public void process(Table table, Propiedades p, PdfFont font, boolean isHeader) {
        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("ID").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("ADDRESS").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("PRICE").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("DESCRIPTION").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("NUM. ROOMS").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("NUM. BATHROOMS").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("MTS").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("TYPE").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("STATUS").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Y. CONSTRUCTION").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("IMAGE").setFont(font)));

        } else {

            table.addCell(new Cell().add(new Paragraph(p.getId_propiedad()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getDireccion()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getPrecio()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getDescripcion()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getNum_cuartos()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getNum_bayos()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getMetros_cuadrados()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getTipo_propiedad()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getStatus()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(p.getAyo_construccion()+"").setFont(font)));
            //table.addCell(new Cell().add(new Paragraph(p.getImagen()+"").setFont(font)));


            Image imgPropiedad = new Image(ImageDataFactory.create(getClass().getResource("/com/example/examentap/images/" + p.getImagen())));
            imgPropiedad.setWidth(80);
            imgPropiedad.setHeight(90);
            table.addCell(new Cell().add(new Paragraph().add(imgPropiedad)));


        }
    }
}

