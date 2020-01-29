package com.rollforinitiative.agileplanner.presentation;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rollforinitiative.agileplanner.R;
import com.rollforinitiative.agileplanner.objects.Project;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    private List<Project> projects;

    // Provide a reference to the views for each data item
    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public TextView projectTitle;
        public TextView projectSubtitle;

        public ProjectViewHolder(View v) {
            super(v);
            view = v;
            projectTitle = (TextView) v.findViewById(R.id.projectDescription);
            projectSubtitle = (TextView) v.findViewById(R.id.projectSubtitle);
        }

    }

    // Constructor for list adapter initializes the list
    public ProjectAdapter(ArrayList<Project> projects) {
        this.projects = projects;
    }

    // Create new list items (invoked by the layout manager)
    @Override
    public ProjectAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new list item view from the XML file
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_list_item, parent, false);


        ProjectViewHolder holder = new ProjectViewHolder(listItem);
        return holder;
    }

    // Set the contents of a list item when it is created
    @Override
    public void onBindViewHolder(final ProjectViewHolder holder, int position) {
        // - holder gives the view holder for the list item
        // - position gives the index of the list
        holder.projectTitle.setText(projects.get(position).getTitle());
        holder.projectSubtitle.setText(projects.get(position).getDescription());

        //create an onClickListener for each item in the list
        //selected Project is projects.get(holder.getAdapterPosition())
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked item at position " + holder.getAdapterPosition());
                Log.d(TAG, projects.get(holder.getAdapterPosition()).getTitle());

                //create a new intent which will open the ProjectDetailView activity
                //pass the appropriate project to the new view
                Intent intent = new Intent(v.getContext(), ProjectDetailActivity.class);
                intent.putExtra("Project", projects.get(holder.getAdapterPosition()));
                v.getContext().startActivity(intent);
            }
        });
    }

    // Return the number of items in the list
    @Override
    public int getItemCount() {
        //the projects list will initially be null, so trying to access the size of it will result
        //in a runtime error
        if (projects == null) {
            return 0;
        }
        else {
            return projects.size();
        }
    }

    //public method called by mainactivity when the database of tables is updated,
    // it takes the new list of projects and notifies itself that the data has changed so that
    // the list in the recyclerview can be updated
    public void setProjects(List<Project> projects) {
        this.projects = projects;
        notifyDataSetChanged();
    }


}
