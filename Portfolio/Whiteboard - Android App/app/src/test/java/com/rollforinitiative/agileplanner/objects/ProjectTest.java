package com.rollforinitiative.agileplanner.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectTest {

    @Test
    public void createProject() {
        String title = "New Project";
        String description = "New Project Description";
        Project project = new Project(0, title, description);

        assertEquals(title, project.getTitle());
        assertEquals(description, project.getDescription());
    }

}