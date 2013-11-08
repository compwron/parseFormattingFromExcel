import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ExcelParserTest {

    @Test
    public void shouldReadProgramNumbersWithFormatFromSampleExcelFile() throws Exception {
        ExcelParser parser = new ExcelParser("test/resources/cell_with_formatting.xlsx");

        FormattedValues formattedProgramNumbers = new FormattedValues(parser.fontPairsFromCellAt(0, 0));

        List<FormattedString> programs = formattedProgramNumbers.getStringsWithFormatType();
        assertThat(programs.size(), is(5));
        assertThat(programs.get(0), is(new FormattedString("382694", FooStatus.Discontinued)));
        assertThat(programs.get(1), is(new FormattedString("382702", FooStatus.Discontinued)));
        assertThat(programs.get(2), is(new FormattedString("382705", FooStatus.Discontinued)));
        assertThat(programs.get(3), is(new FormattedString("382710", FooStatus.New)));
        assertThat(programs.get(4), is(new FormattedString("382713", FooStatus.Continuing)));

    }
}
