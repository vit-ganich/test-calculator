package com.calc.datareader;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;

/** Class for reading test data from Excel spreadsheets
 *
 * @author VHanich & StackOwerflow (with improvements)
 */
public class DataReader {
    private Sheet excelWSheet;

    public DataReader(String sheetPath, String sheetName) throws IOException {
        this.excelWSheet = createExcelSheet(sheetPath, sheetName);
    }

    private Sheet createExcelSheet(String sheetPath, String sheetName) throws IOException {
        FileInputStream excelFile = new FileInputStream(sheetPath);
        Workbook excelWBook = WorkbookFactory.create(excelFile);
        return excelWBook.getSheet(sheetName);
    }

    public Object[][] readExcel() {
        int rowsCount = excelWSheet.getLastRowNum();
        int cellsCount = excelWSheet.getRow(0).getLastCellNum();
        Object[][] data = new Object[rowsCount][cellsCount];

        for (int rowNum = 1; rowNum <= rowsCount; rowNum++) {
            Row row = excelWSheet.getRow(rowNum);

            for (int cellNum = 0; cellNum <= cellsCount; cellNum++) {
                Cell cell = row.getCell(cellNum);
                try {
                    if (cell.getCellType() == CellType.STRING) {
                        data[rowNum - 1][cellNum] = cell.getStringCellValue();
                    } else {
                        data[rowNum - 1][cellNum] = cell.getNumericCellValue();
                    }
                } catch (Exception ignored) { }
            }
        }
        return data;
    }
}
