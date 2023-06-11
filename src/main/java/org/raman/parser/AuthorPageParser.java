package org.raman.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.raman.exception.ContainsPDFException;
import org.raman.pojo.Author;
import org.raman.encoder.PunjabiURLEncoder;
import org.raman.pojo.Story;

import java.io.IOException;
import java.util.*;

import static org.raman.constants.Constants.BASE_URL;

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
            // String storyLink = link.absUrl("href").split("#")[0];
            String storyLink = PunjabiURLEncoder.encode(BASE_URL + link.attr("href")).split("#")[0];
            if (!IGNORE_URL_LIST.contains(storyLink) && !parsedUrls.contains(storyLink)) {
                parsedUrls.add(storyLink);
                // Trying Alternate parser first as the website got updated and this saves time
                try {
                    stories.add(new Story(link.text(),
                            StoryPageParser.parse(storyLink)));
                } catch (ContainsPDFException e) {
                    System.out.println("Contains PDF Ebook, skipping");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Failed parsing, skipping story");
                }
            }
        }
        return new Author(authorName, bio, stories);
    }
}
