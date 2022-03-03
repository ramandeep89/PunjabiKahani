package org.raman;

import java.io.Serializable;

public class Story implements Serializable {

    private final String storyName;
    private final String storyText;

    public Story(String storyName, String storyText) {
        this.storyName = storyName;
        this.storyText = storyText;
    }

    @Override
    public String toString() {
        return "Story{" +
                "storyName='" + storyName + '\'' +
                '}';
    }

    public String getStoryName() {
        return storyName;
    }

    public String getStoryText() {
        return storyText;
    }
}
