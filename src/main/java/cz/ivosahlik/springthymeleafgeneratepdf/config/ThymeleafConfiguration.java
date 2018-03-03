package cz.ivosahlik.springthymeleafgeneratepdf.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * Intellij Idea
 * Created by ivosahlik on 27/02/2018
 */
@Configuration
public class ThymeleafConfiguration {

//    @Bean
//    public ClassLoaderTemplateResolver templateResolver(){
//        ClassLoaderTemplateResolver templateResolver=new ClassLoaderTemplateResolver();
//        templateResolver.setPrefix("templates/");
//        templateResolver.setPrefix("css/css/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode("HTML5");
//        templateResolver.setTemplateMode("XHTML");
//        templateResolver.setCharacterEncoding("UTF-8");
//        templateResolver.setOrder(1);
//        templateResolver.setCacheable(false);
//
//        return templateResolver;
//    }

}
