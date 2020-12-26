package com.example.musicplayer;

public class Song {
    public String song;//歌曲名
    public String singer;//歌手
    public long size;//歌曲所占空间大小
    public int duration;//歌曲时间长度
    public String path;//歌曲地址
    public String album;//专辑名

    public String getSong() {
        return song;
    }

    public String getSinger() {
        return singer;
    }

    public long getSize() {
        return size;
    }

    public int getDuration() {
        return duration;
    }

    public String getPath() {
        return path;
    }

    public String getAlbum() {
        return album;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}