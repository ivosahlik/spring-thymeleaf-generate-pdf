package cz.ivosahlik.springthymeleafgeneratepdf.util;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfEncodings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Intellij Idea
 * Created by ivosahlik on 27/02/2018
 */
@Slf4j
@Component
public class PdfGenaratorUtil {

    @Autowired
    private TemplateEngine templateEngine;


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
     * This method return stream bytes
     *
     * @param processedHtml
     * @return
     * @throws DocumentException
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
            log.info("Renderer adding font " + fonts[i]);
            renderer.getFontResolver().addFont(fonts[i].getAbsolutePath(), "cp1250", true);
        }

        renderer.layout();
        renderer.createPDF(bos, false);
        renderer.finishPDF();

        return bos;

    }


}