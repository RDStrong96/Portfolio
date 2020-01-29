package com.rollforinitiative.agileplanner.objects;

public class TeamMember implements IMagnet {
    private String name;
    private String image;

    public TeamMember(String name) {
        this.name = name;
        image = "team_member_icon120_120";
    }

    public TeamMember(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String displayMagnet() {
        return image;
    }

    public void showDetailedInfo() {
        // launch a new activity?
    }

    public void setName(String name) { this.name = name; }
    public void setImage(String image) { this.image = image; }

    public String getName() { return name; }
}