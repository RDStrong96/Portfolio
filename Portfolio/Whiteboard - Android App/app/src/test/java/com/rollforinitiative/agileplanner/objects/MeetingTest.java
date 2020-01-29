package com.rollforinitiative.agileplanner.objects;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeetingTest {

    @Test
    public void addAttendee() {
    }

    @Test
    public void getName() {
        String meetingName = "Meeting 1";
        Meeting meeting = new Meeting(meetingName);
        assertEquals(meetingName, meeting.getName());
    }

    @Test
    public void getAttendees() {
        String meetingName = "Meeting 1";
        Meeting meeting = new Meeting(meetingName);
        TeamMember member = new TeamMember("Robert");

        ArrayList<TeamMember> attendees =  meeting.getAttendees();
        assertEquals(attendees.size(), 0);

        meeting.addAttendee(member);
        attendees =  meeting.getAttendees();
        assertEquals(attendees.size(), 1);
    }

    @Test
    public void setName() {
        String oldName = "Meeting 1";
        String newName = "Meeting 1.1";

        Meeting meeting = new Meeting(oldName);
        meeting.setName(newName);
        assertEquals(meeting.getName(), newName);
    }

    @Test
    public void setDescription() {
        String description = "This is a meeting about the futility of this project";

        Meeting meeting = new Meeting("Meeting 1");
        meeting.setDescription(description);
        assertEquals(meeting.getDescription(), description);
    }
}