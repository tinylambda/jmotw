package keep.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class IODirListInnerClass {
    public static void main(final String[] args) {
        File path = new File(".");
        String[] list;
        if(args.length == 0 ) {
            list = path.list();
        } else {
            list = path.list(new FilenameFilter() {
                private final Pattern pattern = Pattern.compile(args[0]);
                @Override
                public boolean accept(File dir, String name) {
                    return pattern.matcher(name).matches();
                }
            });
        }

        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for (String pathItem: list) {
            System.out.println(pathItem);
        }
    }
}
