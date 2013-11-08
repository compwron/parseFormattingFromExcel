import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

public class FormattedValues {
    @Getter
    private List<FormattedString> stringsWithFormatType = Lists.newArrayList();

    public FormattedValues(List<FormattedString> charactersWithFonts) {
        parse(charactersWithFonts);
    }

    private void parse(List<FormattedString> charactersWithFonts) {
        List<FormattedString> charactersInProgramNumber = Lists.newArrayList();

        for (FormattedString FormattedString : charactersWithFonts) {
            if (FormattedString.isLinebreak()) {
                addWordToListAccordingToFont(charactersInProgramNumber);
                charactersInProgramNumber = Lists.newArrayList();
            } else {
                charactersInProgramNumber.add(FormattedString);
            }
        }
        addWordToListAccordingToFont(charactersInProgramNumber);
    }

    private void addWordToListAccordingToFont(List<FormattedString> wordCharacters) {
        if (wordIsEmptyOrOnlyLineBreak(wordCharacters)) {
            //  do not add word
        } else if (anyLetterInWordHasStrikeout(wordCharacters)) {
            stringsWithFormatType.add(new FormattedString(makeWordFrom(wordCharacters), FooStatus.Discontinued));
        } else if (anyLetterInWordHasBold(wordCharacters)) {
            stringsWithFormatType.add(new FormattedString(makeWordFrom(wordCharacters), FooStatus.New));
        } else {
            stringsWithFormatType.add(new FormattedString(makeWordFrom(wordCharacters), FooStatus.Continuing));
        }
    }

    private boolean wordIsEmptyOrOnlyLineBreak(List<FormattedString> wordCharacters) {
        return wordCharacters.isEmpty() || allAreLinebreaks(wordCharacters);
    }

    private boolean allAreLinebreaks(List<FormattedString> wordCharacters) {
        for (FormattedString FormattedString : wordCharacters) {
            if (!FormattedString.isLinebreak()) {
                return false;
            }
        }
        return true;
    }

    //    TODO: use Guava map/predicates instead of for loop?
    private String makeWordFrom(List<FormattedString> wordCharacters) {
        String word = "";
        for (FormattedString FormattedString : wordCharacters) {
            word += FormattedString.getText();
        }
        return word;
    }

    //    TODO: use Guava predicates instead of for/if loop?
    private boolean anyLetterInWordHasBold(List<FormattedString> wordCharacters) {
        for (FormattedString FormattedString : wordCharacters) {
            if (FormattedString.isBold()) {
                return true;
            }
        }
        return false;
    }

    //    TODO: use Guava predicates instead of for/if loop?
    private boolean anyLetterInWordHasStrikeout(List<FormattedString> wordCharacters) {
        for (FormattedString FormattedString : wordCharacters) {
            if (FormattedString.isStrikeout()) {
                return true;
            }
        }
        return false;
    }
}
