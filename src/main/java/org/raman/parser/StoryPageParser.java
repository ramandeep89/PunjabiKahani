package org.raman.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.raman.encoder.PunjabiURLEncoder;
import org.raman.exception.ContainsPDFException;

import java.io.IOException;
import java.net.URL;

public class StoryPageParser {
    public static String parse(final String storyUrl) throws IOException, ContainsPDFException {
        System.out.println("storyUrl = " + storyUrl);
        //        Document document = Jsoup.connect(storyUrl).get();
        Document document = Jsoup.parse(new URL(storyUrl).openStream(), "ISO-8859-1", storyUrl);
        if(!document.select("object[type='application/pdf']").isEmpty()) {
            throw new ContainsPDFException();
        }
        try {
            return document.select("div.section").first().nextElementSibling().outerHtml();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed parsing, trying alternate parsing");
            if (!document.select("div[lang='pa'] h1").isEmpty()) document.select("div[lang='pa'] h1").first().remove();
            if (!document.select("div[lang='pa'] h2").isEmpty()) document.select("div[lang='pa'] h2").first().remove();
            return document.select("div[lang='pa']").first().outerHtml();
        }
    }

}
