package download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Run {

    public static void main(String[] args) throws IOException {

        if(args.length>0) {

            File file = new File("download.txt");
            FileOutputStream fos = new FileOutputStream(file);

            String[] param = args[0].split(",");
            for(String arg : param) {
                arg = arg.replaceAll("verycd.com", "verycd.gdajie.com");
                Link.dealLink(arg, fos);

            }
            fos.flush();
            fos.close();
        }

    }
}
