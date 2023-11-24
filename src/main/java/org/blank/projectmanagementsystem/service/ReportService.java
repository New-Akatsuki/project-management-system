package org.blank.projectmanagementsystem.service;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ReportService {
    void generatePdf(HttpServletResponse response, List<?> data, String jrxmlFileName, String exportFileName);
    void generateExcel(HttpServletResponse response, List<?> data, String jrxmlFileName, String exportFileName);
    String preview(List<?> data, String jrxmlFileName);
    public String saveHtml(List<?> data, String jrxmlFileName, String exportFileName);
}
