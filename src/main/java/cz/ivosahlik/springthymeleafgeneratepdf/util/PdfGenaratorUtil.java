package cz.ivosahlik.springthymeleafgeneratepdf.util;

import com.lowagie.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Intellij Idea
 * Created by ivosahlik on 27/02/2018
 */
@Slf4j
@Component
public class PdfGenaratorUtil {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private PdfEncriptionUtil pdfEncriptionUtil;


    /**
     * This method return template engine, html + data
     *
     * @param templateName
     * @param context
     * @return
     */
    public String templateEngine(String templateName, Context context) {

        return templateEngine.process(templateName, context);

    }


    /**
     * This method return stream bytes, add fonts
     *
     * @param processedHtml
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public ByteArrayOutputStream render(String processedHtml) throws DocumentException, IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(processedHtml);

        String fontsDir = new File("src/main/resources/ttf").getAbsolutePath();

        File[] fonts = new File(fontsDir).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().endsWith(".ttf");
            }
        });

        for (int i = 0; i < fonts.length; i++) {
//            log.info("Renderer adding font " + fonts[i]);
            renderer.getFontResolver().addFont(fonts[i].getAbsolutePath(), "cp1250", true);
        }

        renderer.setPDFEncryption(pdfEncriptionUtil.getPdfEncryption());
        renderer.layout();
        renderer.createPDF(bos, false);
        renderer.finishPDF();

        return bos;

    }


    /**
     *
     * This method return of load document
     *
     * @return Document
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public Document document() throws IOException, SAXException, ParserConfigurationException {

        File inputXmlFile = new File("src/main/resources/data/data.input.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Document doc = dBuilder.parse(inputXmlFile);
        doc.getDocumentElement().normalize();

        return doc;

    }


    /**
     *
     * This method parse xml, data put to hash map
     *
     * @param nodes
     * @param prefix
     * @param data
     * @return Map
     */
    public Map<String,String> parseXml(Node nodes, String prefix, Map<String,String> data){

        if(nodes.hasChildNodes()){
            NodeList nodeList = nodes.getChildNodes();
            int length = nodeList.getLength();
            String key = nodes.getNodeName();
            String value = nodes.getTextContent();

            if(prefix != "") {
//                log.info(prefix + "_" + key + " : " + value);
                data.put(prefix + "_" + key, value);
            } else {
                if(length <= 1) {
//                    log.info(key + " : " + value);
                    data.put(key, value);
                }
            }

            if(length > 1) {
                for(int j = 0; j < length; j++) {
                    parseXml(nodeList.item(j), key, data);
                }

            }
        }

        return data;

    }


    /**
     *
     * This method prepair data to context as key and value
     *
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public Context contextMap() throws ParserConfigurationException, SAXException, IOException {

        Map<String,String> data = new HashMap<>();
        NodeList nodeList = document().getDocumentElement().getChildNodes();

        for(int k=0;k<nodeList.getLength();k++){
            data = parseXml((Node)nodeList.item(k), "", data);
        }

        Context context = new Context();

        if (data != null) {
            Iterator itMap = data.entrySet().iterator();
            while (itMap.hasNext()) {
                Map.Entry pair = (Map.Entry) itMap.next();
//                log.info("Key: " + pair.getKey().toString() + ", Value: " + pair.getValue());
                context.setVariable(pair.getKey().toString(), pair.getValue());
            }
        }

        return context;

    }


}