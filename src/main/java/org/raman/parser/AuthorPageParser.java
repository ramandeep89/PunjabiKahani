package org.raman.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.raman.pojo.Author;
import org.raman.encoder.PunjabiURLEncoder;
import org.raman.pojo.Story;

import java.io.IOException;
import java.util.*;

public class AuthorPageParser {
    public static final List<String> IGNORE_URL_LIST = List.of(
    );

    public static final Set<String> parsedUrls = new HashSet<>();

    public static Author parse(final String authorName, final String authorUrl) throws IOException {
        System.out.println("authorUrl = " + authorUrl);
        Document document = Jsoup.connect(PunjabiURLEncoder.encode(authorUrl)).get();
        document.select("div[lang='pa'] h3").remove();
        String bio = document.select("div.left h2").text();
        Elements ulElements = document.select("ul");
        Elements list;
        if (ulElements.size() > 1) list = ulElements.get(1).select("a");
        else list = document.select("div.list a");
        list.select("i").remove();
        List<Story> stories = new ArrayList<>();
        for (Element link :
                list) {
            String storyLink = link.absUrl("href").split("#")[0];
            if (!IGNORE_URL_LIST.contains(storyLink) && !parsedUrls.contains(storyLink)) {
                parsedUrls.add(storyLink);
                try {
                    stories.add(new Story(link.text(),
                            StoryPageParser.parse(storyLink)));
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                    System.out.println("Trying alternate parser");
                    try {
                        stories.add(new Story(link.text(),
                                StoryPageParser.parseAlternate(storyLink)));
                    } catch (Exception e2) {
                        System.out.println(e2.getMessage());
                        System.out.println("Failed alternate parser, skipping story");
                    }
                }
            }
        }
        return new Author(authorName, bio, stories);
    }
}
