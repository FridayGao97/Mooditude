package com.cmput301t14.mooditude.models;

import com.google.firebase.Timestamp;

import java.io.Serializable;


/**
 * MoodEvent class contains:
 * Author
 * mood
 * location
 * datetime
 * socialsituation
 * textComment
 */
public class MoodEvent implements Serializable, Comparable {

    private String author;
    private Mood mood;
    private Timestamp datetime;
    private Location location;
    private SocialSituation socialSituation;
    private String textComment;
    private String email;
//    photoComment: Bitmap

    /**
     * MoodEvent constructor
     * @param mood
     * @param location
     * @param socialSituation
     * @param textComment
     */
    public MoodEvent(String author,Mood mood, Location location, SocialSituation socialSituation, String textComment) {
        this.author = author;
        this.mood = mood;
        this.location = location;
        this.socialSituation = socialSituation;
        this.textComment = textComment;
//        this.datetime=LocalDateTime.now();
        this.datetime= Timestamp.now();
    }

    public MoodEvent(Mood mood, Location location, SocialSituation socialSituation, String textComment) {
        this.author = "";
        this.mood = mood;
        this.location = location;
        this.socialSituation = socialSituation;
        this.textComment = textComment;
//        this.datetime=LocalDateTime.now();
        this.datetime= Timestamp.now();
    }



    /**
     * MoodEvent constructor
     * @param mood
     * @param location
     * @param socialSituation
     * @param textComment
     * @param timestamp
     */
    public MoodEvent(String author, Mood mood, Location location, SocialSituation socialSituation, String textComment, Timestamp timestamp) {
        this.author = author;
        this.mood = mood;
        this.location = location;
        this.socialSituation = socialSituation;
        this.textComment = textComment;
        this.datetime=timestamp;
    }

    public MoodEvent(String author, Mood mood, Location location, SocialSituation socialSituation, String textComment, Timestamp timestamp , String email) {
        this.author = author;
        this.mood = mood;
        this.location = location;
        this.socialSituation = socialSituation;
        this.textComment = textComment;
        this.datetime=timestamp;
        this.email = email;
    }



    public MoodEvent(Mood mood, Location location, SocialSituation socialSituation, String textComment, Timestamp timestamp) {
        this.author = "";
        this.mood = mood;
        this.location = location;
        this.socialSituation = socialSituation;
        this.textComment = textComment;
        this.datetime=timestamp;
    }

    public String getEmail() {
        return email;
    }

    public String getAuthor() {
        return author;
    }

    /**
     * Mood getter
     * @return
     */
    public Mood getMood() {
        return this.mood;
    }

    /**
     * mood setter
     * @param mood
     */
    public void setMood(Mood mood) {
        this.mood = mood;
    }

    /**
     * datetime getter
     * @return
     */
    public Timestamp getDatetime() {
        return datetime;
    }

    /**
     * datetime setter
     * @param datetime
     */
    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    /**
     * location getter
     * @return
     */
    public Location getLocation() {
        return location;
    }

    /**
     * location setter
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * SocialSituation getter
     * @return
     */
    public SocialSituation getSocialSituation() {
        return socialSituation;
    }

    /**
     *  SocialSituation setter
     * @param socialSituation
     */
    public void setSocialSituation(SocialSituation socialSituation) {
        this.socialSituation = socialSituation;
    }

    /**
     * textComment getter
     * @return
     */
    public String getTextComment() {
        return textComment;
    }

    /**
     * textComment setter
     * @param textComment
     */
    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    /**
     * Override compareTo method of Comparable interface
     * @param moodEvent the MoodEvent object to compare to
     */
    @Override
    public int compareTo(Object moodEvent) {
        int result = 0;
        if (moodEvent != null) {
            if (moodEvent instanceof MoodEvent) {
                MoodEvent m = (MoodEvent) moodEvent;
                result = this.getDatetime().compareTo(m.getDatetime());
            }
            else{
                throw new IllegalArgumentException("Invalid input type, must be MoodEvent");
            }
        }
        else{
            throw new IllegalArgumentException("Input cannot be null, must be MoodEvent");
        }

        return result;
    }
}