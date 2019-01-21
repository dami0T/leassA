package com.leass.leass.service;

import com.leass.leass.model.Invoice;

import java.io.IOException;

public interface ExporterService {

    void write(String fileName, Invoice invoice) throws IOException;

    void createDirectory(String clientId) throws IOException;
}
