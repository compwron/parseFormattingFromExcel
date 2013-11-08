import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelParser {

    private final XSSFSheet sheet;

    public ExcelParser(String fileName) throws IOException {
        this.sheet = firstSheetInFile(fileName);
    }

    public List<FormattedString> fontPairsFromCellAt(int rowNumber, int columnNumber) {
        return characterFontPairsFromCell(cellAtPosition(rowNumber, columnNumber));
    }

    private List<FormattedString> characterFontPairsFromCell(XSSFCell cell) {
        List<FormattedString> charactersWithFonts = new ArrayList<FormattedString>();

        XSSFRichTextString rts = cell.getRichStringCellValue();
        String text = rts.toString();
        for (int i = 0; i < rts.length(); i++) {
            charactersWithFonts.add(new FormattedString(String.valueOf(text.charAt(i)), FooStatus.valueOf(rts.getFontAtIndex(i))));
        }
        return charactersWithFonts;
    }

    private XSSFSheet firstSheetInFile(String filePosition) throws IOException {
        InputStream fis = new FileInputStream(filePosition);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        return wb.getSheetAt(0);
    }

    private XSSFCell cellAtPosition(int rowNumber, int columnNumber) {
        return sheet.getRow(rowNumber).getCell(columnNumber);
    }
}
