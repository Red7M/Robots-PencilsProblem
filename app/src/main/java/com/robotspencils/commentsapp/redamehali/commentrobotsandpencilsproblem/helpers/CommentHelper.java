package com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.helpers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.Fragments.CreateCommentFragment;
import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.R;
import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.models.Comment;

import java.util.List;

/**
 * Created by Reda Mehali on 10/13/18.
 * <p>
 * This class works as a helper class for the whole application
 * <p>
 * * Project: Robots & Pencils Technical Problem
 */
//TODO in real life example, we will have different helper classes
//TODO for different types of classes in order to maintain organization
//TODO throughout the whole project. However, for the sake of this example,
//TODO we will include all helper methods in this class

public class CommentHelper {

    /**
     * This method modifies a comment that has already been made in the past
     *
     * @param commentList     comment list of all the comments that we have from the Remote Store
     * @param modifiedComment the comment that we would like to swap
     * @param commentContent  the comment that we are searching for
     * @param userName        the user name that we are searching for
     */
    public static void modifyingAnExistingComment(List<Comment> commentList, Comment modifiedComment, String commentContent, String userName) {
        for (int i = 0; i < commentList.size(); i++) {
            if (commentList.get(i).getCommentText().equalsIgnoreCase(commentContent) &&
                    commentList.get(i).getUserName().equalsIgnoreCase(userName)) {
                commentList.set(i, modifiedComment);
            }
        }
    }

    /**
     * Method used to launch fragment where user can write a comment
     * @param context context used in the fragment launch
     */
    public static void onLaunchCreateCommentFragment(Context context) {

        Fragment fragment = ((AppCompatActivity) context).getSupportFragmentManager()
                .findFragmentByTag(CreateCommentFragment.class.getName());

        if (fragment == null) {
            fragment = Fragment.instantiate(context, CreateCommentFragment.class.getName());
        }
        FragmentTransaction fragmentTransaction = ((AppCompatActivity) context)
                .getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.activityCommentLayout, fragment,
                CreateCommentFragment.class.getName()).commit();

    }

    /**
     * Method used to remove current running fragment
     * @param context context used in this fragment removal method
     */
    public static void onRemoveFragment(Context context) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(((AppCompatActivity) context).getSupportFragmentManager()
                    .findFragmentByTag(CreateCommentFragment.class.getName())).commit();

        }
    }

}
