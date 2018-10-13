package com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.R;
import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.models.Comment;
import com.squareup.picasso.Picasso;



import java.util.List;

/**
 * Created by Reda Mehali on 10/13/18.
 * Adapter Class used to set up comments view data blocks
 * one by one while going throughout all comments retrieved
 * from the Firebase DB
 *
 *  * Project: Robots & Pencils Technical Problem
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<Comment> commentList;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userNameTextView.setText(commentList.get(position).getUserName());
        holder.commentPostedDateTextView.setText(String.valueOf(commentList.get(position).getCommentPostedDate()));
        Picasso.with(context).load(commentList.get(position).getUserPhotoUrl()).centerCrop().into(holder.userPhotoUrlImageView);
        holder.commentTextView.setText(commentList.get(position).getCommentText());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView userNameTextView;
        TextView commentPostedDateTextView;
        ImageView userPhotoUrlImageView;
        TextView commentTextView;

        ViewHolder(View itemView) {
            super(itemView);

            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            commentPostedDateTextView = itemView.findViewById(R.id.commentPostedDateTextView);
            userPhotoUrlImageView = itemView.findViewById(R.id.userPhotoUrlImageView);
            commentTextView = itemView.findViewById(R.id.commentTextView);
        }
    }
}
