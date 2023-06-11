package org.raman.main;

import com.google.common.collect.Lists;
import org.raman.pojo.Author;
import org.raman.pojo.Book;
import org.raman.pojo.Story;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.zip.InflaterInputStream;

public class BookHtmlDumper {
    public static final String HTML_FILES = "D:/books/punjabi/";
    public static final boolean COMBINE_AUTHORS = false;
    public static final int AUTHORS_PER_BOOK = 28;
    public static final String OBJECT_FILE = BookObjectDumper.OBJECT_FILE;

    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
        Book book;
        try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(
                new InflaterInputStream(new FileInputStream(OBJECT_FILE))))) {
            book = (Book)inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Book object not found, please run BookObjectDumper before trying again.");
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        int bookCount = 0;
        if(COMBINE_AUTHORS) {
            for (List<Author> authors :
                    Lists.partition(book.getAuthors(), AUTHORS_PER_BOOK)) {
                String filename = HTML_FILES + ++bookCount + ".html";
                writeBooks(authors, filename);
            }
        } else {
            for (List<Author> authors :
                    Lists.partition(book.getAuthors(), 1)) {
                String filename = HTML_FILES + authors.get(0).getName().replaceAll("/", "").replaceAll("'", "") + ".html";
                writeBooks(authors, filename);
            }
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Finished in " +  duration.toMinutesPart() + " minutes "
                + duration.toSecondsPart() + " seconds.");
    }

    private static void writeBooks(List<Author> authors, String filename) throws IOException {
        System.out.println(filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter( filename ))) {
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
                writer.write("<br/>");
            }
        }
    }
}
