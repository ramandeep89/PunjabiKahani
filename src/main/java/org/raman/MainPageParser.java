package org.raman;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class MainPageParser {
    public static final List<String> IGNORE_URL_LIST = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("https://www.punjabikahani.punjabi-kavita.com/PunjabiStories.php");
                add("https://www.punjabikahani.punjabi-kavita.com/Alif-Laila.php");
                add("https://www.punjabikahani.punjabi-kavita.com/ChildrenStories.php");
                add("https://www.punjabikahani.punjabi-kavita.com/Punjabi-Novels.php");
                add("https://www.punjabikahani.punjabi-kavita.com/PunjabiNovels.php");
                add("https://www.punjabikahani.punjabi-kavita.com/LokKahanian.php");
                add("https://www.punjabikahani.punjabi-kavita.com/Panchtantra-Punjabi.php");

//                Author Exceptions - to be read manually
                add("https://www.punjabikahani.punjabi-kavita.com/ਹਰੀ-ਕ੍ਰਿਸ਼ਨ-ਮਾਇਰ.php");
                add("https://www.punjabikahani.punjabi-kavita.com/ਦਰਸ਼ਨ-ਸਿੰਘ-ਆਸ਼ਟ.php");

            }}
    );

    public static final List<Author> parse(final String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements authorElements = document.select("div.full li a");
        List<Author> authors = new ArrayList<>();
        for (Element authorElement :
                authorElements) {
            String authorLink = authorElement.absUrl("href");
            if (!IGNORE_URL_LIST.contains(authorLink))
                authors.add(AuthorPageParser.parse(authorElement.text(), authorLink));
        }
        return Collections.unmodifiableList(authors);
    }
}
