package org.junbang;

import java.util.HashMap;
import java.util.Map;

public class Song {
    private String name;
    private String singer;
    private long duration;
    Map<Integer, String> lyric = new HashMap<>();

    public Song(String _name, String _singer, long _duration, Map<Integer, String> _map) {
        this.name = _name;
        this.singer = _singer;
        this.duration = _duration;
        this.lyric = _map;
    }

    public Song(String _name, String _singer, long _duration) {
        this.name = _name;
        this.duration = _duration;
        this.singer = _singer;
    }

    public long getDuration() {
        return duration;
    }

    public Map<Integer, String> getLyric() {
        return lyric;
    }

    public void setLyric(Map<Integer, String> lyric) {
        this.lyric = lyric;
    }
}
