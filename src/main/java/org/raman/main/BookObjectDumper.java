package org.raman.main;

import org.raman.pojo.Author;
import org.raman.pojo.Book;
import org.raman.parser.MainPageParser;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

import static org.raman.constants.Constants.MAIN_PAGE_URL;

public class BookObjectDumper {

    public static final String OBJECT_FILE = "D:/books/Punjabi.dat";

    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
        Book book = new Book(MainPageParser.parse(MAIN_PAGE_URL));
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(
                new DeflaterOutputStream(new FileOutputStream(OBJECT_FILE))))) {
            outputStream.writeObject(book);
        }
        System.out.println("Downloaded " + book.getAuthors().size() + " authors and " +
                book.getAuthors().stream().map(Author::getStories).mapToInt(List::size).sum() + " stories in total.");
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Finished in " +  duration.toMinutesPart() + " minutes "
                + duration.toSecondsPart() + " seconds.");

    }

}
