package org.blank.projectmanagementsystem.service;

import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ReportService {
    byte[] convertHtmlToPdf(String html) throws IOException, DocumentException;
    byte[] generatePdf(Map<String, Object> dataBeans, String jrxmlFileName);
    void generatePdf(HttpServletResponse response, Map<String, Object> dataBeans, String jrxmlFileName, String exportFileName);
    byte[] generateExcel(Map<String, Object> dataBeans, String jrxmlFileName);
    String preview(List<?> data, String jrxmlFileName);
    public String saveHtml(List<?> data, String jrxmlFileName, String exportFileName);
}
