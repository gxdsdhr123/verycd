package download;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class Link {

    public static void dealLink(String url, FileOutputStream fos) {

        System.out.println("analysis one url start");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(url);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("analysis one url end");

        String result = HTTPClient.postJson(url, "{}");

        if(!result.isEmpty()) {
            System.out.println("analysis one url successful");
        }

        try{

            String[] context = result.split("<table id=\"emuleFile\">");

            if(context.length>0) {

                String[] content = context[1].split("</table>");

                InputStream is = getStringStream(content[0]);
                SAXReader reader = new SAXReader();
                Document document = reader.read(is);

                Element rootElement = document.getRootElement();
                List<Element> list = rootElement.selectNodes("//tbody//tr//font//a");

                System.out.println("link start");
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                System.out.println(list.toString());
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println("link end");

                for(Element element : list) {

                    String href = element.attributeValue("href");

                    FileLoad.fileload(fos, href);
                }

            }

        } catch (DocumentException e) {

            System.err.println("解析异常");
        }
    }

    public static InputStream getStringStream(String sInputString){
        if (sInputString != null && !sInputString.trim().equals("")){
            try{
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
}
