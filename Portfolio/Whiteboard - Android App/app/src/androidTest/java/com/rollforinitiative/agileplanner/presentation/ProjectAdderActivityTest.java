package com.rollforinitiative.agileplanner.presentation;

import androidx.recyclerview.widget.RecyclerView;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.rollforinitiative.agileplanner.R;
import com.rollforinitiative.agileplanner.objects.Project;
import com.rollforinitiative.agileplanner.presentation.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class ProjectAdderActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    //this test ensures that projects are added to the recyclerview properly
    @Test
    public void testAddProject() {
        RecyclerView projectList = activityRule.getActivity().findViewById(R.id.project_recycler);
        int numberOfProjects;

        //add a new project
        onView(withId(R.id.addProject)).perform(click());
        onView((withId(R.id.projectNameText))).perform(typeText("New Project"), closeSoftKeyboard());
        onView((withId(R.id.addProjectButton))).perform(click());

        //select the new project
        onView(withId(R.id.project_recycler)).check(matches(hasDescendant(withText("New Project"))));

    }

    //this test ensures that empty projects are  not added to the recyclerview
    @Test
    public void testAddEmptyProject() {
        RecyclerView projectList = activityRule.getActivity().findViewById(R.id.project_recycler);
        int initialNumberOfProjects = projectList.getAdapter().getItemCount();
        int updatedNumberOfProjects;

        //add a new project with no name
        onView(withId(R.id.addProject)).perform(click());
        onView((withId(R.id.addProjectButton))).perform(click());

        updatedNumberOfProjects = projectList.getAdapter().getItemCount();
        assertEquals(initialNumberOfProjects, updatedNumberOfProjects);
    }

    @Test
    public void createProject() {
        String title = "New Project";
        String description = "New Project Description";
        Project project = new Project(0, title, description);

        assertEquals(title, project.getTitle());
        assertEquals(description, project.getDescription());
    }

}
