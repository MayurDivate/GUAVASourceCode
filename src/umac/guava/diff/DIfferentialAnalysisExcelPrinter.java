/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava.diff;

import umac.guava.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

/**
 *
 * @author mayurdivate
 */
public class DIfferentialAnalysisExcelPrinter {

    private File excelWorkBook;
    private String sheetName;

    public DIfferentialAnalysisExcelPrinter(File excelWorkBook, String sheetName) {
        this.excelWorkBook = excelWorkBook;
        this.sheetName = sheetName;
    }

    public void createExcelWoorkBook() {

        try {
            XSSFWorkbook xssfw = new XSSFWorkbook();
            FileOutputStream xssfwOutputStream = new FileOutputStream(this.getExcelWorkBook());
            xssfw.write(xssfwOutputStream);
            xssfwOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void createSheet(){
        try {
            FileInputStream fileInputStream = new FileInputStream(this.getExcelWorkBook());
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            if (this.getExcelWorkBook().isFile()){
                workbook.createSheet(this.getSheetName());
                FileOutputStream out = new FileOutputStream(this.getExcelWorkBook());
                workbook.write(out);
                out.close();
                fileInputStream.close();
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createSheet(String sheetName){
        try {
            FileInputStream fileInputStream = new FileInputStream(this.getExcelWorkBook());
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            if (this.getExcelWorkBook().isFile()){
                workbook.createSheet(sheetName);
                FileOutputStream out = new FileOutputStream(this.getExcelWorkBook());
                workbook.write(out);
                out.close();
                fileInputStream.close();
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void printTableHeader(String[] header, int rowid, int cellid){
        
        try {
            
            FileInputStream fileInputStream = new FileInputStream(this.getExcelWorkBook());
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet spreadsheet = workbook.getSheet(this.getSheetName()); 
            
            //header
            CellStyle headerStyle = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeight(14.0);
            headerStyle.setFont(font);
            headerStyle = getStyle(headerStyle, 1);

            
            XSSFRow row = spreadsheet.createRow(rowid);
            Cell cell;
            
            for(String text: header){
                cell = row.createCell(cellid++);
                cell.setCellValue(text);
                cell.setCellStyle(headerStyle);
            }
            
            FileOutputStream out = new FileOutputStream(this.getExcelWorkBook());
            workbook.write(out);
            out.close();
            fileInputStream.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printTableRecord(String[] header, int rowid, int cellid){
        
        try {
            
            FileInputStream fileInputStream = new FileInputStream(this.getExcelWorkBook());
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet spreadsheet = workbook.getSheet(this.getSheetName()); 
            
            //regular Style
            CellStyle regularStyle = workbook.createCellStyle();
            regularStyle = getStyle(regularStyle, 2);
            
            XSSFRow row = spreadsheet.createRow(rowid);
            Cell cell;
            
            for(String text: header){
                cell = row.createCell(cellid++);
                cell.setCellValue(text);
                cell.setCellStyle(regularStyle);
            }
            
            FileOutputStream out = new FileOutputStream(this.getExcelWorkBook());
            workbook.write(out);
            out.close();
            fileInputStream.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printSummary(GdiffInput input) {
        
        this.createExcelWoorkBook();
        this.createSheet();
        
        String rowData[] = new String[2];
        
        int rowid=0;
        
        rowData[0] = "Parameter";
        rowData[1] = "Value";
        this.printTableHeader(rowData, rowid++, 0);

        rowData[0] = "Project Name";
        rowData[1] = ""+input.getProjectName();
        this.printTableRecord(rowData, rowid++, 0);

        rowData[0] = "Genome build";
        rowData[1] = ""+input.getGenome().getGenomeName();
        this.printTableRecord(rowData, rowid++, 0);
        
        rowData[0] = "Analysis Method";
        rowData[1] = "DESeq2";
        this.printTableRecord(rowData, rowid++, 0);
        
        rowData[0] = "P value";
        rowData[1] = ""+input.getPvalue();
        this.printTableRecord(rowData, rowid++, 0);
        
        rowData[0] = "Fold change";
        rowData[1] = ""+input.getFoldChange();
        this.printTableRecord(rowData, rowid++, 0);
        
        rowData[0] = "Upstream";
        rowData[1] = ""+input.getUpstream();
        this.printTableRecord(rowData, rowid++, 0);
        
        rowData[0] = "Downstream";
        rowData[1] = ""+input.getDownstream();
        this.printTableRecord(rowData, rowid++, 0);
        
        for(int index=0; index < input.getDiffInputfiles().size(); index++){
            rowData[0] = ""+input.getDiffInputfiles().get(index).getCondition()+"_REP_"+input.getDiffInputfiles().get(index).getReplicateNumber();
            rowData[1] = ""+input.getDiffInputfiles().get(index).getDiifInputFile().getName();
            this.printTableRecord(rowData, rowid++, 0);
        }
    }
    
    public void printPeakTable(File peakAnnotationFile, int sheetNumber) {

        try {
            FileInputStream inputStream = new FileInputStream(this.getExcelWorkBook());
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook, 500, true);
            Sheet sxSheet = sxssfWorkbook.createSheet(this.getSheetName());
            
            sxssfWorkbook.setSheetOrder(sxSheet.getSheetName(), sheetNumber);
            if (this.getExcelWorkBook().isFile()) {
                FileReader peakFileReader = new FileReader(peakAnnotationFile);
                BufferedReader peakBufferedReader = new BufferedReader(peakFileReader);
                String line;
                int rowid = 0;

                // Header style
                CellStyle headerStyle = sxssfWorkbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                font.setColor(IndexedColors.WHITE.getIndex());
                short fontSize = 273;
                font.setFontHeight(fontSize);
                headerStyle.setFont(font);
                headerStyle = getStyle(headerStyle, 1);

                if ((line = peakBufferedReader.readLine()) != null) {
                    Row row = sxSheet.createRow(rowid++);
                    String[] headerData = line.split("\\t");
                    int cellid = 0;
                    for (String item : headerData) {
                        sxSheet.setColumnWidth(cellid, 3500);
                        Cell cell = row.createCell(cellid++);
                        cell.setCellValue(item);
                        cell.setCellStyle(headerStyle);
                    }
                }

                // Regular style
                CellStyle regularStyle = sxssfWorkbook.createCellStyle();
                regularStyle = getStyle(regularStyle, 2);

                while ((line = peakBufferedReader.readLine()) != null) {
                    Row row = sxSheet.createRow(rowid++);
                    String[] lineData = line.split("\\t");
                    int cellid = 0;
                    for (int i = 1; i < lineData.length; i++) {
                        Cell cell = row.createCell(cellid++);
                        cell.setCellValue(lineData[i]);
                        cell.setCellStyle(regularStyle);
                    }

                }

                // to write in chuncks of 500
                if (rowid > 500) {
                    for (int rownum = 0; rownum < (rowid - 500); rownum++) {
                        Assert.assertNull(sxSheet.getRow(rownum));
                    }

                    for (int rownum = (rowid - 500); rownum < rowid; rownum++) {
                        Assert.assertNotNull(sxSheet.getRow(rownum));
                    }
                }
                FileOutputStream out = new FileOutputStream(this.getExcelWorkBook());
                sxssfWorkbook.write(out);
                out.flush();
                out.close();
                sxssfWorkbook.dispose();
            }

            inputStream.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void printGeneOntologyTable(File gofile, int sheetNumber) {

        try {
            FileInputStream inputStream = new FileInputStream(this.getExcelWorkBook());
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook, 500, true);
            Sheet sxSheet = sxssfWorkbook.createSheet(this.getSheetName());
            sxssfWorkbook.setSheetOrder(sxSheet.getSheetName(), sheetNumber);

            sxSheet.setColumnWidth(0, 3500);
            sxSheet.setColumnWidth(1, 10500);
            sxSheet.setColumnWidth(3, 3500);
            sxSheet.setColumnWidth(4, 3500);
            
            
            if (this.excelWorkBook.isFile()) {
                HashMap<GeneOntology, GeneOntology> goHashMap = GeneOntology.parseGOAnalysisOutputFile(gofile);

                int rowid = 0;

                // Header style
                CellStyle headerStyle = sxssfWorkbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                font.setColor(IndexedColors.WHITE.getIndex());
                short fontSize = 273;
                font.setFontHeight(fontSize);
                headerStyle.setFont(font);
                headerStyle = getStyle(headerStyle, 1);

                Row row = sxSheet.createRow(rowid++);
                String[] headerData = {"GO ID", "GO Term", "GO Type", "P value", "adj. P value"};

                int cellid = 0;
                for (String item : headerData) {
                    Cell cell = row.createCell(cellid++);
                    sxSheet.setColumnWidth(cellid, 3500);
                    cell.setCellValue(item);
                    cell.setCellStyle(headerStyle);
                }

                // Regular style
                CellStyle regularStyle = sxssfWorkbook.createCellStyle();
                regularStyle = getStyle(regularStyle, 2);
                NumberFormat formatter = new DecimalFormat("0.00E00");

                for (GeneOntology go : goHashMap.keySet()) {

                    row = sxSheet.createRow(rowid++);
                    cellid = 0;

                    // GO ID
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue(go.getGoID());
                    cell.setCellStyle(regularStyle);
                    // GO TERM
                    cell = row.createCell(cellid++);
                    cell.setCellValue(go.getGoTerm());
                    cell.setCellStyle(regularStyle);
                    // GO Category
                    cell = row.createCell(cellid++);
                    cell.setCellValue(go.getGoCategory());
                    cell.setCellStyle(regularStyle);
                    // pvalue
                    cell = row.createCell(cellid++);
                    cell.setCellValue(formatter.format(go.getPvalue()));
                    cell.setCellStyle(regularStyle);
                    // adjusted pvalue
                    cell = row.createCell(cellid++);
                    cell.setCellValue(formatter.format(go.getAdjustedPvalue()));
                    cell.setCellStyle(regularStyle);

                }

                if (rowid > 500) {
                    for (int rownum = 0; rownum < (rowid - 500); rownum++) {
                        Assert.assertNull(sxSheet.getRow(rownum));
                    }

                    for (int rownum = (rowid - 500); rownum < rowid; rownum++) {
                        Assert.assertNotNull(sxSheet.getRow(rownum));
                    }
                }
                FileOutputStream out = new FileOutputStream(this.getExcelWorkBook());
                sxssfWorkbook.write(out);
                out.flush();
                out.close();
                sxssfWorkbook.dispose();
            }

            inputStream.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void printPathwayTable(File pathwayfile, int sheetNumber) {

        try {
            FileInputStream inputStream = new FileInputStream(this.getExcelWorkBook());
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook, 500, true);
            Sheet sxSheet = sxssfWorkbook.createSheet(this.getSheetName());
            sxssfWorkbook.setSheetOrder(sxSheet.getSheetName(), sheetNumber);
            sxSheet.setColumnWidth(0, 3500);
            sxSheet.setColumnWidth(1, 10500);
            sxSheet.setColumnWidth(3, 3500);
            sxSheet.setColumnWidth(4, 3500);
            
            if (this.getExcelWorkBook().isFile()) {
                HashMap<Pathway, Pathway> pathwayHashMap = Pathway.parsePathwayAnalysisOutputFile(pathwayfile);

                int rowid = 0;

                // Header style
                CellStyle headerStyle = sxssfWorkbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                font.setColor(IndexedColors.WHITE.getIndex());
                short fontSize = 273;
                font.setFontHeight(fontSize);
                headerStyle.setFont(font);
                headerStyle = getStyle(headerStyle, 1);

                Row row = sxSheet.createRow(rowid++);
                String[] headerData = {"KEGG ID", "Pathways Name", "P value", "adj. P value"};

                int cellid = 0;
                for (String item : headerData) {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue(item);
                    cell.setCellStyle(headerStyle);
                }

                // Regular style
                CellStyle regularStyle = sxssfWorkbook.createCellStyle();
                regularStyle = getStyle(regularStyle, 2);
                NumberFormat formatter = new DecimalFormat("0.00E00");

                for (Pathway pathway : pathwayHashMap.keySet()) {

                    row = sxSheet.createRow(rowid++);
                    cellid = 0;

                    // KEGG ID
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue(pathway.getKeggID());
                    cell.setCellStyle(regularStyle);
                    // Pathway Name
                    cell = row.createCell(cellid++);
                    cell.setCellValue(pathway.getPathwayname());
                    cell.setCellStyle(regularStyle);
                    cell.setCellStyle(regularStyle);
                    // pvalue
                    cell = row.createCell(cellid++);
                    cell.setCellValue(formatter.format(pathway.getPvalue()));
                    cell.setCellStyle(regularStyle);
                    // adjusted pvalue
                    cell = row.createCell(cellid++);
                    cell.setCellValue(formatter.format(pathway.getAdjPvalue()));
                    cell.setCellStyle(regularStyle);

                }

                // Write in chunks
                if (rowid > 500) {
                    for (int rownum = 0; rownum < (rowid - 500); rownum++) {
                        Assert.assertNull(sxSheet.getRow(rownum));
                    }

                    for (int rownum = (rowid - 500); rownum < rowid; rownum++) {
                        Assert.assertNotNull(sxSheet.getRow(rownum));
                    }
                }
                FileOutputStream out = new FileOutputStream(this.getExcelWorkBook());
                sxssfWorkbook.write(out);
                out.flush();
                out.close();
                sxssfWorkbook.dispose();
            }

            inputStream.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static CellStyle getStyle(CellStyle style, int index) {

        if (index == 1) {
            style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setBorderBottom(BorderStyle.THICK);
            style.setBorderLeft(BorderStyle.THICK);
            style.setBorderRight(BorderStyle.THICK);
            style.setBorderTop(BorderStyle.THICK);
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            style.setTopBorderColor(IndexedColors.BLACK.getIndex());
            style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            return style;
        } else if (index == 2) {
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            style.setTopBorderColor(IndexedColors.BLACK.getIndex());
            style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            return style;
        }

        return null;
    }

    public void printACRresults(File acrTXT) {

        try {
            FileInputStream fileInputStream = new FileInputStream(this.getExcelWorkBook());
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            //header
            CellStyle headerStyle = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeight(14.0);
            headerStyle.setFont(font);
            headerStyle = getStyle(headerStyle, 1);

            // Regular style
            CellStyle regularStyle = workbook.createCellStyle();
            regularStyle = getStyle(regularStyle, 2);

            if (this.getExcelWorkBook().isFile()) {
                XSSFSheet spreadsheet = workbook.getSheet(this.getSheetName());

                XSSFRow row;
                int rowid = 0;
                Cell cell;

                FileReader acrFileReader = new FileReader(acrTXT);
                BufferedReader acrBufferedReader = new BufferedReader(acrFileReader);
                String line = acrBufferedReader.readLine();

                while ((line = acrBufferedReader.readLine()) != null) {
                    String lineData[] = line.split("\t");
                    if (lineData.length == 3) {
                        row = spreadsheet.createRow(rowid++);

                        cell = row.createCell(0);
                        cell.setCellValue(lineData[1]);
                        cell.setCellStyle(headerStyle);

                        cell = row.createCell(1);
                        cell.setCellValue(lineData[2]);
                        cell.setCellStyle(regularStyle);
                    }
                }
            }

            FileOutputStream out = new FileOutputStream(this.getExcelWorkBook());
            workbook.write(out);
            out.close();
            fileInputStream.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void printImage(File imageFile, int sheetNumber, double nrows, double ncols) {
        this.printImage(imageFile, sheetNumber, nrows, ncols, 1, 1);
    }

    public void printImage(File imageFile, int sheetNumber, double nrows, double ncols, int rowNumber, int colNumber) {

        try {
            FileInputStream wbInputStream = new FileInputStream(this.getExcelWorkBook());
            XSSFWorkbook workbook = new XSSFWorkbook(wbInputStream);

            CreationHelper helper = workbook.getCreationHelper();
            InputStream imageInputStream = new FileInputStream(imageFile);
            byte[] imageBytes = IOUtils.toByteArray(imageInputStream);
            imageInputStream.close();
            int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);

            XSSFSheet sheet = workbook.createSheet(this.getSheetName());

            Drawing<?> drawing = sheet.createDrawingPatriarch();

            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(colNumber);
            anchor.setRow1(rowNumber);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize(ncols, nrows);

            OutputStream fileOutputStream = new FileOutputStream(this.getExcelWorkBook());
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            wbInputStream.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void printACRresults(ArrayList<ACRresult> acrResults) {

        try {
            FileInputStream fileInputStream = new FileInputStream(this.getExcelWorkBook());
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            //header
            CellStyle headerStyle = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeight(14.0);
            headerStyle.setFont(font);
            headerStyle = getStyle(headerStyle, 1);

            // Regular style
            CellStyle regularStyle = workbook.createCellStyle();
            regularStyle = getStyle(regularStyle, 2);

            if (this.getExcelWorkBook().isFile()) {
                XSSFSheet spreadsheet = workbook.getSheet(this.getSheetName());
                spreadsheet.setColumnWidth(1, 7000);
                XSSFRow row;
                int rowid = 1;
                Cell cell;
                for (ACRresult item : acrResults) {
                    row = spreadsheet.createRow(rowid++);
                    cell = row.createCell(0);
                    cell.setCellValue(item.getRegion());
                    cell.setCellStyle(headerStyle);
                    cell = row.createCell(1);
                    cell.setCellValue(item.getPercentage() + "%");
                    cell.setCellStyle(regularStyle);
                }

            }

            FileOutputStream out = new FileOutputStream(this.getExcelWorkBook());
            workbook.write(out);
            out.close();
            fileInputStream.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DIfferentialAnalysisExcelPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the excelWorkBook
     */
    public File getExcelWorkBook() {
        return excelWorkBook;
    }

    /**
     * @param excelWorkBook the excelWorkBook to set
     */
    public void setExcelWorkBook(File excelWorkBook) {
        this.excelWorkBook = excelWorkBook;
    }

    /**
     * @return the sheetName
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * @param sheetName the sheetName to set
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

}
