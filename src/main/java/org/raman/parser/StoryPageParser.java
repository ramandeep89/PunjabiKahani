package org.raman.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.raman.encoder.PunjabiURLEncoder;

import java.io.IOException;

public class StoryPageParser {
    public static String parse(final String storyUrl) throws IOException, NullPointerException {
        System.out.println("storyUrl = " + storyUrl);
        Document document = Jsoup.connect(PunjabiURLEncoder.encode(storyUrl)).get();
        if (!document.select("div[lang='pa'] h1").isEmpty()) document.select("div[lang='pa'] h1").first().remove();
        if (!document.select("div[lang='pa'] h2").isEmpty()) document.select("div[lang='pa'] h2").first().remove();
        return document.select("div[lang='pa']").first().outerHtml();
    }

    public static String parseAlternate(final String storyUrl) throws IOException {
        System.out.println("storyUrl = " + storyUrl);
        Document document = Jsoup.connect(PunjabiURLEncoder.encode(storyUrl)).get();
        return document.select("div.section").first().nextElementSibling().outerHtml();
    }
}
