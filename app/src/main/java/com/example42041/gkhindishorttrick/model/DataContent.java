package com.example42041.gkhindishorttrick.model;

public class DataContent {
    String dataContent;
    int fav;

    /* renamed from: id */
    int f28id;
    int mainId;

    public DataContent(int id, int mainId2, String dataContent2, int fav2) {
        this.f28id = id;
        this.mainId = mainId2;
        this.dataContent = dataContent2;
        this.fav = fav2;
    }

    public int getId() {
        return this.f28id;
    }

    public int getMainId() {
        return this.mainId;
    }

    public String getDataContent() {
        return this.dataContent;
    }

    public int getFav() {
        return this.fav;
    }
}
