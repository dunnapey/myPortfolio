package com.dunnaway;

import com.google.gson.InstanceCreator;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Peyton on 5/5/2017.
 */
public class User {
    private String name;

    @SerializedName("followers_count")
    private int followers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}
