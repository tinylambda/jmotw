package keep.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class IODirList {
    public static void main(String[] args) {
        File path = new File(".");
        String[] list;
        if (args.length == 0 ) {
            list = path.list();
        } else {
            list = path.list(new DirFilter(args[0]));
        }
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for(String pathItem: list) {
            System.out.println(pathItem);
        }
    }
}

class DirFilter implements FilenameFilter {
    private Pattern pattern;
    public DirFilter(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean accept(File dir, String name) {
        return this.pattern.matcher(name).matches();
    }
}