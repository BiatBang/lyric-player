package org.junbang;

/*
* suppose to listen to currently playing music from spotify
* option 1: use spotify web api: needs to be a webapp
* option 2: listen to windows process of spotify and capture the playtrack ~
* */
public class PlayListener {
    LyricScraper lyricScraper;
    Player player;

    private static PlayListener playListener;

    private PlayListener() {
        lyricScraper = LyricScraper.getInstance();
        player = Player.getInstance();
    }

    public static PlayListener getInstance() {
        if(playListener == null) {
            playListener = new PlayListener();
        }
        return playListener;
    }

    public void loadLyric(String name, String singer, int duration) {
        player.load(name, singer, duration);
    }

    public void play(int startTime) throws InterruptedException {
        player.play(startTime);
    }
}
