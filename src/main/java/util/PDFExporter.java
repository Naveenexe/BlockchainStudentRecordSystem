package util;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import model.Block;

import java.util.List;

public class PDFExporter {

    public static void export(List<Block> chain, String filename) {
        try {
            PdfWriter writer = new PdfWriter(filename);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Blockchain Exam Results").setBold().setFontSize(16));
            document.add(new Paragraph(" ")); // spacing

            for (Block block : chain) {
                document.add(new Paragraph("Name: " + block.getStudentName()));
                document.add(new Paragraph("Subject: " + block.getSubject()));
                document.add(new Paragraph("Marks: " + block.getMarks()));
                document.add(new Paragraph("Hash: " + block.getHash()));
                document.add(new Paragraph("Previous Hash: " + block.getPreviousHash()));
                document.add(new Paragraph("-----------------------------"));
            }

            document.close();
            System.out.println("PDF Exported to: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
