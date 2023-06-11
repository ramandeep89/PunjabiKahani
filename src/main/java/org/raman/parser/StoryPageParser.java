package org.raman.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.raman.encoder.PunjabiURLEncoder;

import java.io.IOException;
import java.net.URL;

public class StoryPageParser {
    public static String parse(final String storyUrl) throws IOException, NullPointerException {
        System.out.println("storyUrl = " + storyUrl);
        //        Document document = Jsoup.connect(storyUrl).get();
        Document document = Jsoup.parse(new URL(storyUrl).openStream(), "ISO-8859-1", storyUrl);
        if (!document.select("div[lang='pa'] h1").isEmpty()) document.select("div[lang='pa'] h1").first().remove();
        if (!document.select("div[lang='pa'] h2").isEmpty()) document.select("div[lang='pa'] h2").first().remove();
        return document.select("div[lang='pa']").first().outerHtml();
    }

    public static String parseAlternate(final String storyUrl) throws IOException {
        System.out.println("storyUrl = " + storyUrl);
        //        Document document = Jsoup.connect(storyUrl).get();
        Document document = Jsoup.parse(new URL(storyUrl).openStream(), "ISO-8859-1", storyUrl);
        return document.select("div.section").first().nextElementSibling().outerHtml();
    }
}
