import org.apache.poi.xssf.usermodel.XSSFFont;

public enum FooStatus {
    Discontinued, Continuing, New;

    public static FooStatus valueOf(XSSFFont font) {
        if (font.getStrikeout()) {
            return Discontinued;
        }
        if (font.getBold()) {
            return New;
        }
        return Continuing;
    }
}
