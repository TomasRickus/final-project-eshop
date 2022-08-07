package com.example.demo.service;

import com.example.demo.model.Product;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ProductPdfService {
    public void exportProductsToPdf(final List<Product> products) {


        Document document = new Document();

        try {
            OutputStream outputStream = new FileOutputStream("products.pdf");
            PdfWriter.getInstance(document, outputStream);
            document.open();

            PdfPCell cell2 = new PdfPCell(new Paragraph("KAINA"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("PAVADINIMAS"));
            PdfPCell cell4 = new PdfPCell(new Paragraph("KIEKIS"));
            PdfPCell cell5 = new PdfPCell(new Paragraph("MEDŽIAGA"));

            PdfPTable pdfPTable = new PdfPTable(4);
            pdfPTable.addCell(cell3).setBorderColor(BaseColor.BLUE);
            pdfPTable.addCell(cell2).setBorderColor(BaseColor.BLUE);
            ;
            pdfPTable.addCell(cell4).setBorderColor(BaseColor.BLUE);
            ;
            pdfPTable.addCell(cell5).setBorderColor(BaseColor.BLUE);
            ;

            for (Product product : products) {

                PdfPCell cartCell2 = new PdfPCell(new Paragraph(product.getTitle()));
                pdfPTable.addCell(cartCell2);
                PdfPCell cartCell1 = new PdfPCell(new Paragraph(String.valueOf(product.getPrice())));
                pdfPTable.addCell(cartCell1);
                PdfPCell cartCell3 = new PdfPCell(new Paragraph(product.getQuantity().toString()));
                pdfPTable.addCell(cartCell3);
                PdfPCell cartCell4 = new PdfPCell(new Paragraph(product.getFabric()));
                pdfPTable.addCell(cartCell4);

            }

            Paragraph paragraph = new Paragraph();
            paragraph.add("");
            paragraph.setAlignment(150);

            document.add(pdfPTable);
            document.add(paragraph);
            document.close();
            outputStream.close();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

}

