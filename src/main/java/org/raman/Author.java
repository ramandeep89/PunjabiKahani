package org.raman;

import java.util.Collections;
import java.util.List;

public class Author {
    private final String name;
    private final String bio;
    private final List<Story> stories;

    public Author(String name, String bio, List<Story> stories) {
        this.name = name;
        this.bio = bio;
        this.stories = Collections.unmodifiableList(stories);
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", stories=" + stories +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public List<Story> getStories() {
        return stories;
    }
}
