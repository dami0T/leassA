package com.leass.leass.service;

import com.leass.leass.model.Invoice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExporterService {

    void write(String fileName, Invoice invoice, HttpServletResponse response) throws IOException;

    void createDirectory(String clientId) throws IOException;

    void downloadAttachment(Invoice invoice, HttpServletResponse response);
}
