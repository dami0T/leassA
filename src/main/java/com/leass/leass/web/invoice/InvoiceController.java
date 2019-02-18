package com.leass.leass.web.invoice;

import com.leass.leass.model.Invoice;
import com.leass.leass.service.ExporterService;
import com.leass.leass.service.UserService;
import com.leass.leass.service.invoice.InvoiceService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

@Controller
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    UserService userService;

    @Autowired
    ExporterService exporterService;

    ArrayList wrapper ;
    Boolean visible;


    @RequestMapping(value = "/listinvoice", method = RequestMethod.GET)
    public ModelAndView invoiceListPage() {
        ModelAndView model = new ModelAndView();
        visible =  userService.adminRole();
        wrapper = new ArrayList<>();
        wrapper.addAll(new ArrayList<Invoice>(invoiceService.findAll()));
        model.addObject("invoiceList", wrapper);
        model.addObject("visible", visible);
        model.setViewName("pages/invoice/invoiceListPage");
        return model;
    }

    @RequestMapping(value="/download", method=RequestMethod.GET)
    @ResponseBody
    public void downloadInvoice(@Param(value="id") Long id, HttpSession session, HttpServletResponse response) throws IOException {

        Invoice invoice = invoiceService.getOne(id);

        exporterService.downloadAttachment(invoice, response);

//
//        try {
//            String filePathToBeServed = invoice.getFileUrl();
//            File fileToDownload = new File(filePathToBeServed);
//            InputStream inputStream = new FileInputStream(fileToDownload);
//            response.setContentType("application/force-download");
//            response.setHeader("Content-Disposition", "attachment; filename="+invoice.getFileUrl());
//            IOUtils.copy(inputStream, response.getOutputStream());
//            response.flushBuffer();
//            inputStream.close();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }

}
