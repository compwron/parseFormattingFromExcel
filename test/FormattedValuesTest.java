import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FormattedValuesTest {

    public static final String lineBreak = "\n";

    @Test
    public void shouldHaveNoStrikethroughProgramsWhenGivenNoData() {
        List<FormattedString> fontsPerCharacter = Lists.newArrayList();
        List<FormattedString> programNumbers = new FormattedValues(fontsPerCharacter).getStringsWithFormatType();
        assertThat(programNumbers.size(), is(0));
    }

    @Test
    public void shouldHaveNoProgramsWhenThereAreOnlyLineBreaks() {
        FormattedString lineBreak = new FormattedString("\n", FooStatus.New);
        List<FormattedString> fontsPerCharacter = Arrays.asList(lineBreak, lineBreak);
        List<FormattedString> programNumbers = new FormattedValues(fontsPerCharacter).getStringsWithFormatType();
        assertThat(programNumbers.size(), is(0));
    }

    @Test
    public void shouldHaveOneStrikethroughProgramsWhenGivenOneStrikeThroughWordOfOneLetter() {

        List<FormattedString> fontsPerCharacter = Arrays.asList(new FormattedString("a", FooStatus.Discontinued));

        List<FormattedString> programNumbers = new FormattedValues(fontsPerCharacter).getStringsWithFormatType();

        assertThat(programNumbers.size(), is(1));
        assertThat(programNumbers.get(0).getText(), is("a"));
        assertThat(programNumbers.get(0).getFormatType(), is(FooStatus.Discontinued));
    }

    @Test
    public void shouldHaveOneWordWithOneWordAndTrailingLinebreak() {

        List<FormattedString> fontsPerCharacter = Lists.newArrayList();
        fontsPerCharacter.add(new FormattedString("a", FooStatus.Discontinued));
        fontsPerCharacter.add(new FormattedString(lineBreak, FooStatus.Discontinued));

        List<FormattedString> programNumbers = new FormattedValues(fontsPerCharacter).getStringsWithFormatType();

        assertThat(programNumbers.size(), is(1));
        assertThat(programNumbers.get(0), is(new FormattedString("a", FooStatus.Discontinued)));
    }

    @Test
    public void shouldHaveTwoStrikethroughProgramsWhenGivenStrikeThroughProgramsSeparatedByLineBreak() {

        List<FormattedString> fontsPerCharacter = Lists.newArrayList();
        fontsPerCharacter.add(new FormattedString("a", FooStatus.Discontinued));
        fontsPerCharacter.add(new FormattedString(lineBreak, FooStatus.Discontinued));
        fontsPerCharacter.add(new FormattedString("b", FooStatus.Discontinued));

        List<FormattedString> programNumbers = new FormattedValues(fontsPerCharacter).getStringsWithFormatType();

        assertThat(programNumbers.size(), is(2));
        assertThat(programNumbers.get(0), is(new FormattedString("a", FooStatus.Discontinued)));
        assertThat(programNumbers.get(1), is(new FormattedString("b", FooStatus.Discontinued)));
    }

    @Test
    public void shouldHaveOneNonStrikethroughProgramWhenGivenStrikeoutWordsFollowedByOneNonStrikeoutWord() {
        List<FormattedString> fontsPerCharacter = Lists.newArrayList();
        fontsPerCharacter.add(new FormattedString("a", FooStatus.Discontinued));
        fontsPerCharacter.add(new FormattedString(lineBreak, FooStatus.Discontinued));
        fontsPerCharacter.add(new FormattedString("b", FooStatus.Discontinued));
        fontsPerCharacter.add(new FormattedString(lineBreak, FooStatus.Discontinued));
        fontsPerCharacter.add(new FormattedString("c", FooStatus.Continuing));

        List<FormattedString> programNumbers = new FormattedValues(fontsPerCharacter).getStringsWithFormatType();

        assertThat(programNumbers.size(), is(3));
        assertThat(programNumbers.get(2), is(new FormattedString("c", FooStatus.Continuing)));
        assertThat(programNumbers.get(1), is(new FormattedString("b", FooStatus.Discontinued)));
        assertThat(programNumbers.get(0), is(new FormattedString("a", FooStatus.Discontinued)));
    }

    @Test
    public void shouldDetectMultiLetterStrikeoutStringWithSpaceWithoutTrailingLinebreak(){
        List<FormattedString> fontsPerCharacter = Lists.newArrayList();
        fontsPerCharacter.add(new FormattedString("a", FooStatus.Discontinued));
        fontsPerCharacter.add(new FormattedString(" ", FooStatus.Discontinued));
        fontsPerCharacter.add(new FormattedString("b", FooStatus.Discontinued));

        List<FormattedString> programNumbers = new FormattedValues(fontsPerCharacter).getStringsWithFormatType();

        assertThat(programNumbers.size(), is(1));
        assertThat(programNumbers.get(0), is(new FormattedString("a b", FooStatus.Discontinued)));
    }

    @Test
    public void shouldDetectBoldProgramNumberWhichMeansNewPrograms(){
        List<FormattedString> fontsPerCharacter = Lists.newArrayList();
        fontsPerCharacter.add(new FormattedString("a", FooStatus.New));

        List<FormattedString> programNumbers = new FormattedValues(fontsPerCharacter).getStringsWithFormatType();

        assertThat(programNumbers.size(), is(1));
        assertThat(programNumbers.get(0), is(new FormattedString("a", FooStatus.New)));
    }

}
