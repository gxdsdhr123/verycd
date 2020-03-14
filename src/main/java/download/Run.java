package download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Run {

    public static void main(String[] args) throws IOException {

        args = new String[47];
        for(int i=1; i<=47; i++) {
            args[i-1] = "http://jrb.nmgcredit.com/yqzt_rzdtlist.html?page=" + i;
        }

        if(args.length>0) {

            File file = new File("download.txt");
            FileOutputStream fos = new FileOutputStream(file);

            for(String arg : args) {
                Link.dealLink(arg, fos);
            }
            fos.flush();
            fos.close();
        }

    }
}
