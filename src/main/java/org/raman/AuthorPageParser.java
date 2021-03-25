package org.raman;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuthorPageParser {
    public static final List<String> IGNORE_URL_LIST = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("https://www.punjabikahani.punjabi-kavita.com/JammuJiTusinBareRaaSujanSingh.php");
                add("https://www.punjabikahani.punjabi-kavita.com/ਸ਼ੀਸ਼ੇ-’ਚੋਂ-ਝਾਕਦੀ-ਆਤਮ-ਗਿਲਾਨੀ-ਕੇ-ਐਲ-ਗਰਗ.php");
                add("https://www.punjabikahani.punjabi-kavita.com/ਤੂਫ਼ਾਨ-ਖ਼ਲੀਲ-ਜਿਬਰਾਨ.php");
                add("https://www.punjabikahani.punjabi-kavita.com/ਓਹਲਿਆਂ-ਅਤੇ-ਹਨੇਰਿਆਂ-ਦੇ-ਚਿਰਾਗ਼-ਜਸਬੀਰ-ਭੁੱਲਰ.php");
                add("https://www.punjabikahani.punjabi-kavita.com/ਪੰਛੀਆਂ-ਦੀ-ਤਫ਼ਤੀਸ਼-ਦਾ-ਸਾਰ-ਜਸਬੀਰ-ਭੁੱਲਰ.php");
                add("https://www.punjabikahani.punjabi-kavita.com/ਸਿਆਣੀ-ਮੁੰਨੀ-ਫ਼ਕੀਰ-ਚੰਦ-ਸ਼ੁਕਲਾ.php");
                add("https://www.punjabikahani.punjabi-kavita.com/ਜਨਮ-ਦਿਨ-ਦੀ-ਪਾਰਟੀ-ਫ਼ਕੀਰ-ਚੰਦ-ਸ਼ੁਕਲਾ.php");
                add("https://www.punjabikahani.punjabi-kavita.com/ਨਲਕਾ-ਗੇੜਿਆ-ਕਾਵਾਂ-ਮਨਮੋਹਨ-ਸਿੰਘ-ਦਾਊਂ.php");
                add("https://www.punjabikahani.punjabi-kavita.com/ਸ਼ੇਰ-ਦਾ-ਸ਼ਿਕਾਰ-ਮੁਨਸ਼ੀ-ਪ੍ਰੇਮਚੰਦ.php");
                add("https://www.punjabikahani.punjabi-kavita.com/ਗੁਬਾਰੇ-ਉੱਤੇ-ਚੀਤਾ-ਮੁਨਸ਼ੀ-ਪ੍ਰੇਮਚੰਦ.php");

                add("http://www.punjabi-kavita.com/Ram-Sarup-Ankhi.php");
            }}
    );

    public static final Author parse(final String authorName, final String authorUrl) throws IOException {
        System.out.println("authorUrl = " + authorUrl);
        Document document = Jsoup.connect(authorUrl).get();
        document.select("div[lang='pa'] h3").remove();
        String bio = document.select("div[lang='pa'] p").text();
        Elements list = document.select("ul").get(1).select("a");
        list.select("i").remove();
        List<Story> stories = new ArrayList<>();
        for (Element link :
                list) {
//            storyLinksMap.put(link.text(), link.absUrl("href"));
            String storyLink = link.absUrl("href");
            if (!IGNORE_URL_LIST.contains(storyLink))
                stories.add(new Story(link.text(), StoryPageParser.parse(storyLink)));
        }
        return new Author(authorName, bio, stories);
    }
}
