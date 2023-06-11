package org.raman.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.raman.encoder.PunjabiURLEncoder;
import org.raman.pojo.Author;

import java.io.IOException;
import java.util.*;

import static org.raman.constants.Constants.BASE_URL;

public class MainPageParser {
    public static final List<String> IGNORE_URL_LIST = List.of(
            "https://www.punjabikahani.punjabi-kavita.com/PunjabiStories.php",
            // "https://www.punjabikahani.punjabi-kavita.com/Alif-Laila.php",
            "https://www.punjabikahani.punjabi-kavita.com/ChildrenStories.php",
            "https://www.punjabikahani.punjabi-kavita.com/Punjabi-Novels.php",
            "https://www.punjabikahani.punjabi-kavita.com/PunjabiNovels.php"
            // "https://www.punjabikahani.punjabi-kavita.com/LokKahanian.php",
            // "https://www.punjabikahani.punjabi-kavita.com/Panchtantra-Punjabi.php"
    );

    public static List<Author> parse(final String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements authorElements = document.select("div.full li a");
        List<Author> authors = new ArrayList<>();
        for (Element authorElement :
                authorElements) {
            // String authorLink = authorElement.absUrl("href");
            String authorLink = PunjabiURLEncoder.encode(BASE_URL + authorElement.attr("href"));
            if (!IGNORE_URL_LIST.contains(authorLink))
                try {
                    authors.add(AuthorPageParser.parse(authorElement.text(), authorLink));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Error parsing author at : " + authorLink);
                }
        }
        return Collections.unmodifiableList(authors);
    }
}
