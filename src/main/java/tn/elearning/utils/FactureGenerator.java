package tn.elearning.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import tn.elearning.entities.Paiement;
import tn.elearning.entities.User;

import java.io.FileOutputStream;

public class FactureGenerator {

    public void generateFacture(Paiement paiement) {
        Document document = new Document();

        try {
            String fileName = "Facture_Paiement_" + paiement.getId() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            Image logo = Image.getInstance(getClass().getResource("/image/logoo.png"));
            logo.scaleToFit(120, 120); // Ajuste la taille du logo
            logo.setAlignment(Element.ALIGN_CENTER); // Centre le logo
            document.add(logo);
            document.add(new Paragraph("\n"));
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLUE);
            Paragraph title = new Paragraph("Facture de Paiement", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);
            User user = UserSession.getInstance().getUser();
            Paragraph userInfo = new Paragraph(
                    "Client : " + user.getNom() +
                            "\nEmail : " + user.getEmail() +
                            "\n\n"
            );
            System.out.println(user);
            userInfo.setSpacingAfter(20);
            document.add(userInfo);
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Font headFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            PdfPCell cell1 = new PdfPCell(new Phrase("Détail", headFont));
            PdfPCell cell2 = new PdfPCell(new Phrase("Valeur", headFont));
            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell1);
            table.addCell(cell2);

            table.addCell("ID Paiement");
            table.addCell(String.valueOf(paiement.getId()));

            table.addCell("Montant");
            table.addCell(String.format("%.2f TND", paiement.getMontant()));

            table.addCell("Date de Paiement");
            table.addCell(paiement.getDate().toLocalDate().toString());

            table.addCell("Type de l'abonnement");
            table.addCell(String.valueOf(paiement.getAbonnement().getType()));

            document.add(table);

            Paragraph footer = new Paragraph(
                    "\nMerci pour votre confiance !",
                    new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC)
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

            System.out.println("Facture générée : " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
