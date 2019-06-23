package com.example.excelergo.mydictionary;

public class WordPos {
    private String word;
    private String pos;

    public WordPos(String word,String pos) {
                this.word=word;
                this.pos=pos;
    }
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

}
