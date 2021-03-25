package org.raman;

public class Story {

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
