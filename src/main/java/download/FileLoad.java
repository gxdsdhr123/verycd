package download;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;

public class FileLoad {

    public static void fileload(FileOutputStream fos, String url) {

        String result = HTTPClient.postJson(url, "{}");

        System.out.println("resource start");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(result);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("resource end");

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

                    System.out.println("file start");
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    System.out.println(rootElement.toString());
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    System.out.println("file end");

                    String edit = rootElement.attributeValue("href") + "\r\n";
                    fos.write(edit.getBytes());
                }

            }

        } catch (DocumentException e) {

            System.err.println("解析异常");
        } catch (IOException e) {

            System.err.println("IO异常");
        }
    }
}
