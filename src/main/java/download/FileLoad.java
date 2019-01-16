package download;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;

public class FileLoad {

    public static void fileload(FileOutputStream fos, String url) {

        System.out.println("analysis two url start");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(url);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("analysis two url end");

        String result = HTTPClient.postJson(url, "{}");

        if(!result.isEmpty()) {
            System.out.println("analysis two url successful");
        }

        try{

            String[] context = result.split("<div id=\"detail\" style=\"display: none;\"> ");

            if(context.length>0) {

                String[] contents = context[1].split("<center>");

                if(contents.length>0) {

                    String[] content = contents[1].split("</center>");

                    content[0] = content[0].replaceAll("<br>", "");
                    InputStream is = Link.getStringStream(content[0]);
                    SAXReader reader = new SAXReader();
                    Document document = reader.read(is);

                    Element rootElement = document.getRootElement();

                    System.out.println("write file start");
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

                    String edit = rootElement.attributeValue("href") + "\r\n";

                    System.out.println(edit);

                    fos.write(edit.getBytes());

                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    System.out.println("write file end");
                }

            }

        } catch (DocumentException e) {

            System.err.println("解析异常");
        } catch (IOException e) {

            System.err.println("IO异常");
        }
    }
}
