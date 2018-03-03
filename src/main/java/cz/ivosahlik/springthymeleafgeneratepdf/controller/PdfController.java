package cz.ivosahlik.springthymeleafgeneratepdf.controller;

import com.lowagie.text.DocumentException;
import cz.ivosahlik.springthymeleafgeneratepdf.util.PdfGenaratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Intellij Idea
 * Created by ivosahlik on 27/02/2018
 */
@Slf4j
@Controller
public class PdfController {

    @Autowired
    PdfGenaratorUtil pdfGenaratorUtil;


    /**
     * This method render of pdf with data from html (thymeleaf) template
     *
     * @param templateName
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(name = "/generate/pdf", produces = "application/pdf; charset=utf-8")
    public ResponseEntity<byte[]> pdf(@RequestParam("template") String templateName) throws Exception {

        Map<String,String> data = new HashMap<>();
        data.put("firstname","Ivo");
        data.put("lastname", "Vošahlík");
        data.put("work", "false");
        data.put("price", "100 000");
        data.put("mena", "Kč");

        log.info("Template Name: " + templateName);

        Context context = new Context();

        if (data != null) {
            Iterator itMap = data.entrySet().iterator();
            while (itMap.hasNext()) {
                Map.Entry pair = (Map.Entry) itMap.next();
                log.info("Key: " + pair.getKey().toString() + ", Value: " + pair.getValue());
                context.setVariable(pair.getKey().toString(), pair.getValue());
            }
        }

        String processedHtml = pdfGenaratorUtil.templateEngine(templateName, context);

        log.info("Content: \n\n" + processedHtml);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(pdfGenaratorUtil.render(processedHtml).toByteArray());
    }







    @GetMapping("/download/pdf")
    public ResponseEntity<byte[]> downloadPdf(HttpServletResponse response) throws DocumentException {

        String htmlContent ="<h1>Hello +ěščřžýáíéťďň</h1>";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(bos, false);
        renderer.finishPDF();

//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename="+"MY_PDF_FILE.pdf");
//        return bos.toByteArray();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bos.toByteArray());

    }


}
