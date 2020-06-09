package keep.javautil;

import static java.util.Objects.requireNonNull;


public class JavaUtilRequireNonNull {
    public static void main(String[] args) {
        String value = null;
        // Checks that the specified object reference is not {@code null} and
        // throws a customized {@link NullPointerException} if it is.
        requireNonNull(value, "value is null");
    }
}
