package org.blank.projectmanagementsystem.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.blank.projectmanagementsystem.service.ReportService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public void generatePdf(HttpServletResponse response, Map<String, Object> dataBeans, String jrxmlFileName, String exportFileName) {
        try (InputStream reportTemplate = getClass().getResourceAsStream(String.format("/%s.jrxml", jrxmlFileName))) {

            // Compile the Jasper report from .jrxml to .jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplate);

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();

            // Add all beans to the parameters map
            parameters.putAll(dataBeans);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // Set response content type and headers for PDF download
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", String.format("attachment; filename=%s.pdf", exportFileName));

            try (OutputStream outputStream = response.getOutputStream()) {
                // Export the report to the response output stream
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions here
        }
    }

    @Override
    public void generateExcel(HttpServletResponse response, List<?> data, String jrxmlFileName, String exportFileName) {
        // Load the Jasper report template
        try (InputStream reportTemplate = getClass().getResourceAsStream(String.format("/%s.jrxml", jrxmlFileName))) {

            // Compile the Jasper report from .jrxml to .jasper (if needed)
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplate);

            // Create a JRBeanCollectionDataSource with your data
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(data);

            // Add parameters if needed
            Map<String, Object> parameters = new HashMap<>();

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);

            // Create the XLSX exporter
            JRXlsxExporter exporter = new JRXlsxExporter();

            // Set the XLSX export configuration
            SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
            reportConfigXLS.setSheetNames(new String[]{"Sheet1"}); // Set sheet name(s) as needed
            exporter.setConfiguration(reportConfigXLS);

            // Set response content type and headers for XLSX download
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", String.format("attachment; filename=%s.xlsx", exportFileName));

            try (OutputStream outputStream = response.getOutputStream()) {
                // Set the exporter input and output
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

                // Export the report to XLSX
                exporter.exportReport();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions here
        }
    }

    @Override
    public String preview(List<?> data, String jrxmlFileName) {
        try {
            // Load the Jasper report template
            try (InputStream reportTemplate = getClass().getResourceAsStream(String.format("/%s.jrxml", jrxmlFileName))) {
                JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplate);
                JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(data);
                Map<String, Object> parameters = new HashMap<>();
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);

                // Export the report to a ByteArrayOutputStream
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, pdfStream);

                // Convert the PDF content to a Base64-encoded string

                return Base64.getEncoder().encodeToString(pdfStream.toByteArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions here
            return null;
        }
    }

    public String saveHtml(List<?> data, String jrxmlFileName, String exportFileName) {
        String filePath = null;
        try {
            // Load the JRXML template
            try (InputStream reportTemplate = getClass().getResourceAsStream(String.format("/%s.jrxml", jrxmlFileName))) {
                if (reportTemplate == null) {
                    throw new FileNotFoundException("JRXML template not found: " + jrxmlFileName);
                }

                // Create directories if they don't exist
                Path directoryPath = Paths.get("src/main/resources/static/report-file");
                Files.createDirectories(directoryPath);

                // Compile the JRXML template
                JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplate);

                JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(data);
                Map<String, Object> parameters = new HashMap<>();

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);

                // Specify an absolute path to save the HTML file
                String absoluteFilePath = Paths.get(((Path) directoryPath).toString(), exportFileName + ".html").toString();

                // Export to HTML
                JasperExportManager.exportReportToHtmlFile(jasperPrint, absoluteFilePath);

                filePath = absoluteFilePath;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Handle the file not found exception
        } catch (Exception e) {
            e.printStackTrace();
            // Handle other exceptions here
        }
        return filePath;
    }

}
