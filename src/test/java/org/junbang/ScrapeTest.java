package org.junbang;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

public class ScrapeTest {
    PlayListener playListener = PlayListener.getInstance();
    LyricScraper scraper = LyricScraper.getInstance();

    @Test
    @Ignore
    public void getLyricList() {
        String name = "makes me wonder";
        String singer = "maroon5";
        playListener.loadLyric(name, singer, 10000);
    }

    @Test
    @Ignore
    public void findLyricBlock() {
        String url = "https://www.syair.info/lyrics/maroon/makes-me-wonder/SDlZ";
        Map<Integer, String> map = scraper.mapLyric(url);
        System.out.println(map);
    }

    @Test
    public void findLyricWithNameAndSinger() throws InterruptedException {
        playListener.loadLyric("young and beautiful", "lana del rey", (180+31) * 100);
        playListener.play(0);
    }
}
