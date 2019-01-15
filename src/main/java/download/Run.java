package download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Run {

    public static void main(String[] args) throws IOException {

        if(args.length>0) {

            for(String arg : args) {

                arg = arg.replaceAll("verycd.com", "verycd.gdajie.com");
                File file = new File("download.txt");

                FileOutputStream fos = new FileOutputStream(file);
                Link.dealLink(arg, fos);
                fos.flush();
                fos.close();
            }

        }

    }
}
