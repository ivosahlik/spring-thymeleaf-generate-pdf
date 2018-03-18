package cz.ivosahlik.springthymeleafgeneratepdf.controller;

import com.lowagie.text.DocumentException;
import cz.ivosahlik.springthymeleafgeneratepdf.util.PdfGenaratorUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.pdf.ITextRenderer;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

        String processedHtml = pdfGenaratorUtil.templateEngine(templateName, pdfGenaratorUtil.contextMap());
//        log.info("Content: \n\n" + processedHtml);

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



    @RequestMapping("/handle")
    public ResponseEntity<String> handle(@RequestHeader(value="User-Agent") String userAgent) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();

//        File file = new File("src/main/resources/data/data.input.xml");

//        InputStream inputStream = new FileInputStream(file);

//        byte[] bytes = Base64.getEncoder().encode(Utils.readBytesFromStream(inputStream));

//        byte[] bytes = Utils.readBytesFromStream(inputStream);

        String content = "<d_numaut>208491133564896</d_numaut><d_x_typ>X</d_x_typ><d_typ>6</d_typ>";

        byte[] bytes = Base64.getEncoder().encode(content.getBytes());

        responseHeaders.set("Content-Type", "application/json");
        responseHeaders.set("MyResponseHeader", "MyValue");
        responseHeaders.set("Data", content);
        responseHeaders.set("DataBase64", bytes.toString());


        byte[] decode = Base64.getDecoder().decode(bytes);

        String decodeStr = new String(decode);

//        System.out.println("decode: " + decodeStr);

//        System.out.println(userAgent);




        List<String> list = Arrays.asList("aaaaaaa", "ccccccc", "ddddddd", "eee", "ttt", "aaaaaaa", "ccccccc", "ddddddd", "eee", "ttt");
        //System.out.println(list.stream().filter(a -> a.length() == 3).collect(Collectors.toList()));

//        list.forEach(a -> System.out.println(a));
//
//        list.forEach(System.out::println);
//
//        List<Integer> numbers = Arrays.asList(1,2,4,6,8);
//
//        numbers.stream().map(number -> number * 2).forEach(System.out::println);
//
//        list.parallelStream().forEach(System.out::println);

//        list.stream().forEach(System.out::println);

        String str = list.stream()
                .filter(a -> a.length() == 7)
                .limit(3)
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.joining(","));

        //System.out.println(str);


        List<Integer> listInt = Arrays.asList(1,2,3,4,5,6,1,2,3,4,5,6);

        IntSummaryStatistics intSummaryStatistics = listInt.stream().filter(a -> a == 2).mapToInt(x  -> x).summaryStatistics();

//        System.out.println(intSummaryStatistics.getCount());
//        System.out.println(intSummaryStatistics.getAverage());
//        System.out.println(intSummaryStatistics.getSum());
//        System.out.println(intSummaryStatistics.toString());

        OptionalInt optionalInt = listInt.stream().mapToInt(x->x).max();

//        System.out.println(optionalInt.getAsInt());

        Integer sum = listInt.stream().mapToInt(x -> x).sum();
//        System.out.println(sum);

        Integer sumColl = listInt.stream().collect(Collectors.summingInt(value -> value));

//        System.out.println(sumColl);

        Map<Integer, Set<String>> groupMap = list.stream().collect(Collectors.groupingBy(String::length, Collectors.toSet()));

//        groupMap.entrySet().forEach(System.out::println);


        List<List<Integer>> listOfList = Arrays.asList(Arrays.asList(1,2,3), Arrays.asList(4,5,6));

        List<Integer> listNum = listOfList.stream().flatMap(Collection::stream).collect(Collectors.toList());

//        listNum.forEach(System.out::println);

        String reduceStr = list.stream().reduce((a, b) -> a + " - " + b).get();

//        System.out.println(reduceStr.toString());

        String reduceSlash = list.stream().reduce((a, b ) -> a + ", " + b).get();

//        System.out.println(reduceSlash);


//        Assert.isTrue(list.stream().filter(a -> a.length() == 3).collect(Collectors.toList()).size() == 2, "OK");


        Flux<String> flux = Flux.just("aaaaaaa", "ccccccc", "ddddddd", "eee", "ttt", "aaaaaaa", "ccccccc", "ddddddd", "eee", "ttt");

//        flux.toStream().forEach(System.out::println);
//        flux.subscribe(System.out::println);

//        flux.doOnEach(System.out::println);

//        flux.doOnEach(a -> System.out.println(a.get()));

//        flux.doOnEach(b -> System.out.println(b.get())).subscribe();

//        flux.subscribe(s -> System.out.println(s), null, () -> System.out.println("WAU!"));

//        Consumer<String> println = System.out::println;
//        Consumer<Throwable> throwableConsumer = e -> System.out.println("Some Error Occured!!");
//        Runnable allDone = () -> System.out.println("WAU!!!");
//        flux.subscribe(println, throwableConsumer, allDone);

//        flux.filter(a -> a.length() == 7).subscribe(System.out::println);

//        flux.filter(x -> x.length() == 7)
//                .take(2)
//                .subscribe(System.out::println);

//        flux.filter(x -> x.length() == 7).take(3).sort().subscribe(System.out::println);

//        flux.filter(x -> x.length() == 7).take(2).sort().collect(Collectors.joining(", ")).subscribe(System.out::println);

//        Flux<List<List<Integer>>> fluxList = Flux.just(Arrays.asList(Arrays.asList(1,2,3), Arrays.asList(4,5,6)));
//        fluxList.flatMap(lists -> Flux.fromIterable(lists))
//                .subscribe(System.out::println);
//
//        Flux<List<List<Integer>>> fluxList = Flux.just(Arrays.asList(Arrays.asList(1,2,3), Arrays.asList(4,5,6)));
//        fluxList.flatMap(lists -> Flux.fromIterable(lists))
//                .flatMap(integers -> Flux.fromIterable(integers))
//                .subscribe(System.out::println);

//        Flux<List<List<Integer>>> fluxList = Flux.just(Arrays.asList(Arrays.asList(1,2,3), Arrays.asList(4,5,6)));
//
//        fluxList.flatMap(lists -> Flux.fromIterable(
//                    lists.stream().flatMap(Collection::stream)
//                .collect(Collectors.toList())))
//                .subscribe(System.out::println);


        Flux<String> listFlux = Flux.just("aaaaaaa", "ccccccc", "ddddddd", "eee", "ttt", "aaaaaaa", "ccccccc", "ddddddd", "eee", "ttt");

        listFlux.reduce((a, b) -> (a + " - " + b))
                .subscribe(System.out::println);








        return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
    }


}
