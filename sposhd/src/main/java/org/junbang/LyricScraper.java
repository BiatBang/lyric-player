package org.junbang;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* utils to scrape links from syair,
* map lyric into map
* */
public class LyricScraper {
    private static String name;
    private static String singer;
    private static LyricScraper lyricScraper;
    private static final String rootURL = "https://www.syair.info";

    public static LyricScraper getInstance() {
        if(lyricScraper == null) {
            lyricScraper = new LyricScraper();
        }
        return lyricScraper;
    }

    public void setName(String name) {
        lyricScraper.name = name;
    }

    public void setSinger(String singer) {
        lyricScraper.singer = singer;
    }

    public static String getName() {
        return name;
    }

    public static String getSinger() {
        return singer;
    }

    /*
    * use the the link of lyric to return a map of lyric
    * { key: time, value: line }
    * time is using 10 millis as unit
    * ex: {1: we will , 3: we will, 5: rock you }
    * */
    public static Map<Integer, String> mapLyric(String link) {
        if(lyricScraper == null) throw new IllegalArgumentException("no scraper now");
        Map<Integer, String> lyricMap = new HashMap<>();
        StringBuilder searchURL = new StringBuilder(link);

        try {
            Document doc = Jsoup.connect(searchURL.toString()).get();
            Element lyricBlock = doc.selectFirst("div.entry");
            String lyricBlockStr = lyricBlock.html();
            // delete the "ad" part
            lyricBlockStr = lyricBlockStr.split("<div class=\"ads\">")[0];
            String[] sentences = lyricBlockStr.split("<br>");
            for(String sen: sentences) {
                // only extract lines with timestamps
                Pattern regex = Pattern.compile("\\[([0-9][0-9]:[0-9][0-9].[0-9][0-9])\\]");
                Matcher regexMatcher = regex.matcher(sen);
                while (regexMatcher.find()) {
                    String lyricLine = sen.substring(sen.lastIndexOf("]") + 1);
                    if(lyricLine.endsWith("\n")) {
                        lyricLine = lyricLine.substring(0, lyricLine.length()-1);
                    }
                    // find the timestamps, for duplicate lines
                    String ts = regexMatcher.group(1);
                    ts = ts.replaceAll("\\[|\\]", "");
                    // reform it into 10 millis
                    int time = 0;
                    time += Integer.valueOf(ts.split("\\.|:")[2]);
                    time += Integer.valueOf(ts.split("\\.|:")[1]) * 100;
                    time += Integer.valueOf(ts.split("\\.|:")[0]) * 60 * 100;
                    lyricMap.put(time, lyricLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lyricMap;
    }

    /*
    * use song's name and singer's name to make a search
    * usually there are multiple options from syair, pick the first one as default
    * @return List<String> results: links of lyrics
    * */
    public static List<String> findLyricLinks(String name, String singer) {
        StringBuilder searchURL = new StringBuilder(rootURL);
        searchURL.append("/search?q=");
        searchURL.append(name + " " + singer);
        List<String> urls = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(searchURL.toString()).get();
            Elements links = doc.select("a.title");
            for(Element link: links) {
                String url = rootURL + link.attr("href");
                urls.add(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;
    }
}
