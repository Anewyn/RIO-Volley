package com.alejandro.rio.model;

import android.graphics.Bitmap;

public class PJ {
    private String name;
    private String characterClass;
    private String spec;
    private String role;
    private String race;
    private String realm;
    private String thumbnailURL;
    private int achievementPoints;
    private int honorableKills;
    private Guild guild;
    private Bitmap thumbnail;

    public PJ(String name, String characterClass, String spec, String role, String race,
              String realm, String thumbnailURL, int achievementPoints, int honorableKills,
              Guild guild) {
        this.name = name;
        this.characterClass = characterClass;
        this.spec = spec;
        this.role = role;
        this.race = race;
        this.realm = realm;
        this.thumbnailURL = thumbnailURL;
        this.achievementPoints = achievementPoints;
        this.honorableKills = honorableKills;
        this.guild = guild;
    }

    public PJ() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getAchievementPoints() {
        return achievementPoints;
    }

    public void setAchievementPoints(int achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    public int getHonorableKills() {
        return honorableKills;
    }

    public void setHonorableKills(int honorableKills) {
        this.honorableKills = honorableKills;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
