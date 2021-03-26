package org.raman;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class AuthorPageParser {
    public static final List<String> IGNORE_URL_LIST = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("https://www.punjabikahani.punjabi-kavita.com/JammuJiTusinBareRaaSujanSingh.php");
                add("http://www.punjabi-kavita.com/Ram-Sarup-Ankhi.php");
            }}
    );

    public static final Set<String> parsedUrls = new HashSet<>();

    public static final Author parse(final String authorName, final String authorUrl) throws IOException {
        System.out.println("authorUrl = " + authorUrl);
        Document document = Jsoup.connect(PunjabiURLEncoder.encode(authorUrl)).get();
        document.select("div[lang='pa'] h3").remove();
        String bio = document.select("div[lang='pa'] p").text();
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
                stories.add(new Story(link.text(), StoryPageParser.parse(storyLink)));
            }
        }
        return new Author(authorName, bio, stories);
    }
}
