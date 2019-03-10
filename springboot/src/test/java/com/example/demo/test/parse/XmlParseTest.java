package com.example.demo.test.parse;

import org.assertj.core.util.Sets;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class XmlParseTest {


    private static void test1() throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("C:\\Users\\yafeiyf.wang\\Desktop\\a\\twell_case.txt");

        NodeList cityurl = document.getElementsByTagName("cityurl");

        int length = cityurl.getLength();
        for(int i = 0 ; i < length ; i++){
            System.out.println(cityurl.item(i).getNodeValue());
        }

    }

    private static Set<String> test2(String url) throws Exception {

        Set<String> cityurls = Sets.newHashSet();

        SAXReader reader = new SAXReader();
        InputStream in = new FileInputStream(url);
        org.dom4j.Document dom = reader.read(in);
        Element rootElement = dom.getRootElement();
        List<Element> elements = rootElement.elements();
        Element element = elements.get(0);
        List<Element> elements1 = element.elements();
        IntStream.range(0, elements1.size()).forEach(i ->{
            Element element1 = elements1.get(i);
            Attribute cityurl = element1.attribute("cityurl");
            String value = cityurl.getValue();
            cityurls.add(value);
        });

        return  cityurls;

    }

    public static void main(String[] args) throws Exception {
        Set<String> diff = Sets.newHashSet();

        //Set<String> twell = test2("C:\\Users\\yafeiyf.wang\\Desktop\\a\\twell_case.txt");
        Set<String> hslist = test2("C:\\Users\\yafeiyf.wang\\Desktop\\a\\hslist_case.txt");
        Set<String> twell2 = test2("C:\\Users\\yafeiyf.wang\\Desktop\\a\\twell2.txt");

        diff.addAll(twell2);
        diff.addAll(hslist);

        diff.removeAll(twell2);

        diff.stream().forEach(dif ->{
            System.out.println(dif);
        });

    }


}
