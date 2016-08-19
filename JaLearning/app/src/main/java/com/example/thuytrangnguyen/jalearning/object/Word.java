package com.example.thuytrangnguyen.jalearning.object;

/**
 * Created by Thuy Trang Nguyen on 8/10/2016.
 */
public class Word {
    int id;
    private String word;
    private String mean;
    int status;
    int id_rank;
    String a;
    String b;
    String c;

    public Word(){

    }
    public Word(int id, String w,String m, int s,int id_rank, String a, String b, String c){
        this.id = id;
        this.word = w;
        this.mean = m;
        this.status = s;
        this.id_rank = id_rank;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_rank() {
        return id_rank;
    }

    public void setId_rank(int id_rank) {
        this.id_rank = id_rank;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
