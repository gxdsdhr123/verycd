package download;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.List;

public class FileLoad {

    public static void fileload(FileOutputStream fos, String url) {

        System.out.println("analysis two url start");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(url);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("analysis two url end");

        String result = HTTPClient.postJson("http://jrb.nmgcredit.com/" + url, "{}");

        if (!result.isEmpty()) {
            System.out.println("analysis two url successful");
        }

        try {

            String[] context = result.split("<table class=\"xdcparticle_table\">");

            if (context.length > 0) {

                String[] contents = context[1].split("</table>");

                if (contents.length > 0) {

                    String content = contents[0];
                    content = content.replaceAll("&nbsp;", "");
                    content = content.replaceAll("\r\n", "");
                    InputStream is = Link.getStringStream("<table>" + content + "</table>");
                    SAXReader reader = new SAXReader();
                    Document document = reader.read(is);

                    List<Element> trList = document.getRootElement().elements();

                    System.out.println("write file start");
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

                    for (int j = 0; j < trList.size(); j++) {
                        Element tr = trList.get(j);
                        List<Element> tdList = tr.elements();
                        for (int i = 0; i < tdList.size(); i++) {
                            if ((i % 2 == 1) || (j == trList.size() - 1)) {
                                Element td = tdList.get(i);
                                String text = td.getTextTrim();
                                text = text + ",";
                                System.out.println(text);
                                fos.write(text.getBytes());
                            }
                        }
                    }
                    fos.write("\r\n".getBytes());
                    /*String edit = rootElement.attributeValue("href") + "\r\n";
                    System.out.println(edit);*/

                    //fos.write((edit).getBytes());

                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    System.out.println("write file end");
                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            System.err.println("解析异常");
        }
    }
}
