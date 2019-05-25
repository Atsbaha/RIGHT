package com.example.saintmary.right.Model;

public class MusicOrder {
    private String MusicName;

public MusicOrder(){

}

    public MusicOrder(String musicName) {
        MusicName=musicName;
    }



    public String getMusicName() {
        return MusicName;
    }

    public void setMusicName(String musicName) {
       MusicName = musicName;
    }


}

