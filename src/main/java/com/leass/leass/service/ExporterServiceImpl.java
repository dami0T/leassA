package com.leass.leass.service;

import com.leass.leass.model.Agreement;
import com.leass.leass.model.Client;
import com.leass.leass.model.Invoice;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ExporterServiceImpl implements ExporterService {
    @Override
    public void write(String fileName, Invoice invoice) throws IOException {
        StringBuffer str = generateInvoiceString(invoice);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.append(' ');
        writer.append(str);

        writer.close();
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

    public StringBuffer generateInvoiceString(Invoice invoice){
        StringBuffer str = new StringBuffer();
        Client client = invoice.getAgreement().getClient();
        str.append("Sprzedawca:                  ").append("                            ").append("Nabywca:\n");
        str.append("Nazwa:                       ").append("                            ").append(client.getName()).append(" ").append(client.getForename()).append("\n");
        str.append("Miasto i kod                 ").append("                            ").append(client.getPostCode()).append(" ").append(client.getPost()).append(",\n");
        str.append("Ulica                        ").append("                            ").append(client.getStreet()).append(" ").append(client.getHouse()).append("\n\n").append("                          ");
        str.append("Fa-Vat").append("\n                      ").append(invoice.getIdentifier());
        str.append("\n\n").append("\n          ").append("Kwota netto: ").append(invoice.getNetValue()).append("   ").append("Kwota Vat: ").append(invoice.getVatValue());
        str.append("\n          ").append("Kwota Brutto: ").append(invoice.getGrossValue());
        return str;
    }
}
