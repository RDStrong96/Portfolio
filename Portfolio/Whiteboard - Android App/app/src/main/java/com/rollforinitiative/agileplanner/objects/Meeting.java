package com.rollforinitiative.agileplanner.objects;

import java.util.ArrayList;

public class Meeting implements IMagnet {
    private String name;
    private String description;
    private ArrayList<TeamMember> attendees;

    public Meeting(String name) {
        this.name = name;
        description = "";
        attendees = new ArrayList<>();
    }

    public void addAttendee(TeamMember teamMember) {
        attendees.add(teamMember);
    }

    public String displayMagnet() {
        return "meeting_icon120_120";
    }

    public void showDetailedInfo() {
        // launch new activity?
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public ArrayList<TeamMember> getAttendees() { return attendees; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
}