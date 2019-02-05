package com.leass.leass.service;

import com.leass.leass.model.Agreement;
import com.leass.leass.model.Client;
import com.leass.leass.model.Invoice;
import com.leass.leass.repository.ClientRepository;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.util.Date;

@Service
public class ExporterServiceImpl implements ExporterService {

    @Autowired
    ClientRepository clientRepository;
    @Override
    public void write(String fileName, Invoice invoice) throws IOException {
//        StringBuffer str = generateInvoiceString(invoice);
////        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
////        writer.append(' ');
////        writer.append(str);
////
////        writer.close();

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph tmpParagraph = document.createParagraph();
        XWPFRun tmpRun = tmpParagraph.createRun();
        tmpRun = generateInvoiceString(invoice, tmpRun);
        tmpRun.setFontSize(12);
        XWPFTable table = generateInvoiceTable(invoice, document);
        XWPFRun tmpRun2 = tmpParagraph.createRun();
        tmpRun = generateInvoice(invoice, tmpRun2);
        tmpRun.setFontSize(12);
        XWPFRun tmpRun3 = tmpParagraph.createRun();
        tmpRun3 = generateDate(tmpRun3);
        tmpRun3.setFontSize(12);


        document.write(new FileOutputStream(new File(fileName)));
    }

    @Override
    public void createDirectory(String clientId) throws IOException {
        String path = "/home/damian/Pulpit/";
        String pathName = path + clientId;
        File directory = new File(String.valueOf(pathName));

        if(!directory.exists()){

            directory.mkdir();
        }

    }

    public XWPFRun generateInvoiceString(Invoice invoice,XWPFRun tmpRun){
        StringBuffer str = new StringBuffer();
        Client client = clientRepository.getOne(invoice.getAgreement().getClient().getId());
        tmpRun.setText("Sprzedawca:"+"                                           "+"Nabywca:");
        tmpRun.addCarriageReturn();
        tmpRun.setText("Leass"+"                                                      "+client.getShortName());
        tmpRun.addCarriageReturn();
        tmpRun.setText("43-300 Bielsko-Biała"+"                              "+client.getPostCode()+", "+ client.getLocality());
        tmpRun.addCarriageReturn();
        tmpRun.setText("ul.Wyzwolenia 9"+"                                    "+client.getStreet()+" "+client.getHouse());
        tmpRun.addCarriageReturn();
        tmpRun.addCarriageReturn();
        tmpRun.addCarriageReturn();
        tmpRun.addCarriageReturn();
        return tmpRun;
    }

    public XWPFRun generateInvoice(Invoice invoice,XWPFRun tmpRun){
        StringBuffer str = new StringBuffer();
        tmpRun.setBold(true);
        tmpRun.setText(str.append("                         Fa-Vat").append(" ").append(invoice.getIdentifier()).toString());
        str.append("\n\n").append("         ").append("Kwota netto: ").append(invoice.getNetValue()).append("   ").append("Kwota Vat: ").append(invoice.getVatValue());
        str.append("\n          ").append("Kwota Brutto: ").append(invoice.getGrossValue());
        return tmpRun;
    }

    public XWPFRun generateDate(XWPFRun tmpRun){
        tmpRun.setBold(true);
        tmpRun.setText("                         Data wystawienia: "+ new Date());
        tmpRun.addCarriageReturn();
        tmpRun.setText("                         Termin płatności: "+ plusDays(new Date(), 15));
        tmpRun.addCarriageReturn();
        tmpRun.addCarriageReturn();
        return tmpRun;
    }

    public XWPFTable generateInvoiceTable(Invoice invoice, XWPFDocument document){
//        StringBuffer str = new StringBuffer();
//        tmpRun.setBold(true);
//        tmpRun.setText(str.append("                         Fa-Vat").append(" ").append(invoice.getIdentifier()).toString());
//        str.append("\n\n").append("         ").append("Kwota netto: ").append(invoice.getNetValue()).append("   ").append("Kwota Vat: ").append(invoice.getVatValue());
//        str.append("\n          ").append("Kwota Brutto: ").append(invoice.getGrossValue());
//        return tmpRun;
        XWPFTable table = document.createTable();

        //create first row
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("Nazwa usługi");
        tableRowOne.addNewTableCell().setText("col two, row one");


        //create second row
        XWPFTableRow tableRowTwo = table.createRow();
        tableRowTwo.getCell(0).setText("Stawka Vat");
        tableRowTwo.getCell(1).setText("23");

        //create third row
        XWPFTableRow tableRowThree = table.createRow();
        tableRowThree.getCell(0).setText("Wartość netto");
        tableRowThree.getCell(1).setText("col three, row three");

        XWPFTableRow tableRowFour = table.createRow();
        tableRowFour.getCell(0).setText("Wartość Vat");
        tableRowFour.getCell(1).setText("col three, row three");

        XWPFTableRow tableRowFive = table.createRow();
        tableRowFive.getCell(0).setText("Wartość Brutto");
        tableRowFive.getCell(1).setText("col three, row three");

        return table;
    }

    public static Date plusDays(Date startDate, Integer numberOfDays) {
        if (startDate == null) return null;
        DateTime date = new DateTime(startDate.getTime());
        return date.plusDays(numberOfDays).toDate();
    }
}
