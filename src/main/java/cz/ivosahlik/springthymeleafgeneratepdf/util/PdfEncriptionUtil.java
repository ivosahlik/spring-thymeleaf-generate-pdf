package cz.ivosahlik.springthymeleafgeneratepdf.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.PDFEncryption;

import static cz.ivosahlik.springthymeleafgeneratepdf.config.Constants.PASSWORD;


/**
 * Intellij Idea
 * Created by ivosahlik on 18/03/2018
 */
@Slf4j
@Component
public class PdfEncriptionUtil {

    /**
     *  This method return pdf encription
     *
     * @return PDFEncryption
     */
    public PDFEncryption getPdfEncryption() {
        PDFEncryption pdfEncryption = new PDFEncryption();
        pdfEncryption.setUserPassword(getPasswordByte());
        return pdfEncryption;
    }


    /**
     * This method return password like byte[]
     *
     * @return byte[]
     */
    private byte[] getPasswordByte() {
        return PASSWORD.getBytes();
    }


}
