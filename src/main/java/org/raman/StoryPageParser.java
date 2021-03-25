package org.raman;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class StoryPageParser {
    public static String parse(final String storyUrl) throws IOException {
        System.out.println("storyUrl = " + storyUrl);
        Document document = Jsoup.connect(storyUrl).get();
        document.select("div[lang='pa'] h2").first().remove();
        return document.select("div[lang='pa']").first().outerHtml();
    }
}
