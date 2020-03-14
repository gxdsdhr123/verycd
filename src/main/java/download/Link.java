package download;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            String[] context = result.split("<ul class=\"yqzt2_list\">");

            if(context.length>0) {

                String[] content = context[1].split("</ul>");

                InputStream is = getStringStream(content[0]);
                SAXReader reader = new SAXReader();
                Document document = reader.read(is);

                Element rootElement = document.getRootElement();
                List<Element> list = rootElement.selectNodes("//li//div//a");

                System.out.println("link start");
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                System.out.println(list.toString());
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println("link end");

                Map<String, String> hasMap = new HashMap<>();
                for(Element element : list) {

                    String href = element.attributeValue("href");
                    if(!hasMap.containsKey(href) && !href.contains("page")) {
                        hasMap.put(href, "1");
                        FileLoad.fileload(fos, href);
                    }
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
