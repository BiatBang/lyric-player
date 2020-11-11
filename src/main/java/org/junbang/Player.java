package org.junbang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junbang.LyricScraper.findLyricLinks;

public class Player {
    private static Song song;
    private static Player playerInstance;
    private List<String> urls;
    private int currentUseLink;

    private Player() { currentUseLink = 0; }

    public static Player getInstance() {
        if(playerInstance == null) {
            playerInstance = new Player();
        }
        return playerInstance;
    }

    public boolean addUrl(String url) {
        if(this.urls == null) this.urls = new ArrayList<>();
        this.urls.add(url);
        return true;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public void setSong(String name, String singer, long duration) {
        song = new Song(name, singer, duration);
    }

    /*
    * play the lyric from the startTime
    * */
    public void play(int startTime) throws InterruptedException{
        if(song == null || song.getLyric() == null) throw new IllegalArgumentException();

        Map<Integer, String> lyricMap = song.getLyric();
        long duration = song.getDuration();

        for(int i = startTime; i < duration; i++) {
            if(i % 100 == 0) System.out.println(i);
            if(lyricMap.containsKey(i)) {
                // play the lyric line here
                // System.out.println(lyricMap.get(i));
                App.setText(lyricMap.get(i));
            }
            Thread.sleep(9);
        }
    }

    /*
    * when the current lyric doesn't match, change to another one
    * */
    public void setLyric(int index) {
        if(index >= urls.size()) throw new IllegalArgumentException("index exceed size");
        String url = urls.get(index);
        Map<Integer, String> lyrics = LyricScraper.mapLyric(url);
        song.setLyric(lyrics);
    }

    public void setLyric() {
        if(this.currentUseLink >= urls.size()) {
            System.out.println("no more lyrics");
            this.currentUseLink = 0;
        }
        setLyric(this.currentUseLink);
    }

    /*
    * move to another link
    * */
    public void letNextLyric() {
        this.currentUseLink++;
        setLyric();
    }

    /*
    * load the lyric, duration to refrain the length of playing time
    * */
    public void load(String name, String singer, int duration) {
        setSong(name, singer, duration);
        List<String> urls = findLyricLinks(name, singer);
        setUrls(urls);
        setLyric();
    }
}
