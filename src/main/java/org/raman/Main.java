package org.raman;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Main {

    public static final String MAIN_PAGE_URL = "https://www.punjabikahani.punjabi-kavita.com/PunjabiStories.php";

    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
        List<Author> authors = MainPageParser.parse(MAIN_PAGE_URL);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:/books/Punjabi.html"))) {
            for (Author author :
                    authors) {
                writer.write("<h1>" + author.getName() + "</h1>");
                writer.write("<p>" + author.getBio() + "</p>");
                for (Story story :
                        author.getStories()) {
                    writer.write("<h2>" +
                            (story.getStoryName().contains(":")
                                    ?story.getStoryName().substring(0, story.getStoryName().lastIndexOf(':')).trim()
                                    :story.getStoryName()) +
                            "</h2>");
                    writer.write("<p>" + story.getStoryText() + "</p>");
                }
            }
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Finished in " +  duration.toMinutesPart() + " minutes "
                + duration.toSecondsPart() + " seconds.");
    }

}
