import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@EqualsAndHashCode
public class FormattedString {

    private final String text;
    private final FooStatus formatType;

    public boolean isLinebreak() {
        return text.equals("\n");
    }

    public boolean isStrikeout() {
        return FooStatus.Discontinued.equals(formatType);
    }

    public boolean isBold() {
        return FooStatus.New.equals(formatType);
    }

    public String toString(){
        return "[" + text + " " + formatType + "]";
    }
}
