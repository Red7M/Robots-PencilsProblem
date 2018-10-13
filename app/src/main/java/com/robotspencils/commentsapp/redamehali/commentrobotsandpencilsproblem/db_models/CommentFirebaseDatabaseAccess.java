package com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.db_models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.models.Comment;
import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.utils.Constant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Reda Mehali on 10/12/18.
 * <p>
 * Class for Firebase Database entry points
 * It creates a JSON structure in our database
 * Necessary for real life examples of Big data
 * such as the case of storing comments in our database
 * This structure will make it easy for us to retrieve and save
 * data to our database.
 * <p>
 * * Project: Robots & Pencils Technical Problem
 */
public class CommentFirebaseDatabaseAccess {

    private static final String TAG = "[" + CommentFirebaseDatabaseAccess.class.getName() + "]=";


    // Database reference instance for entry point to our REST API
    // * This is an instance that contains a reference to a firebase rest api
    // * That we have created through Firebase. For example, it could be something
    // * like https://robotspencilsproblem-d1234.firebaseio.com
    private static DatabaseReference databaseReference;

    private static void initializeFirebase(Context context) {
        FirebaseApp.initializeApp(context);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * This method establish a connection to that firebase url that we
     * already created and creates a JSON child to it by the use of "Constant.COMMENT_STRING"
     * In this case, our url path is as follow: https://robotspencilsproblem-d1234.firebaseio.com/comments
     * This way, we know that all of our comments will be stored inside that child structure
     * So in case we would like to retrieve these comments, we just need to hit the url path
     * https://robotspencilsproblem-d1234.firebaseio.com/comments
     *
     * @param comment comment object containing comments data
     */
    public static void onStoreDataInFirebaseDatabase(Comment comment) {
        //This time in millis string is used in order to assign a unique time
        //to each comment created. This way, there will be NO OVERRIDDEN COMMENT case
        //as each comment will be unique. Will pass that node underneath our "Constant.COMMENT_STRING"
        //child. For example, the url after the comment is passed and created will look something
        // like this: https://robotspencilsproblem-d1234.firebaseio.com/comments/1539314542459
        String tempTimeInMillis = String.valueOf(Calendar.getInstance().getTimeInMillis());
        databaseReference.child(Constant.COMMENT_STRING).child(tempTimeInMillis).setValue(comment);

    }

    /**
     * This method is used to retrieve data from the remote RESTFUL Server
     * It contains a countDownLatch since it needs to wait until comments are retrieved
     * before returning them. This will run in the background so it won't block the main
     * thread. Also, it will organize comments in newest first order.
     *
     * @param context        context from the main comment activity
     * @param countDownLatch counter for blocking background thread until data is retrieved, then release.
     * @return mCommentList returns list of all comments from the DB.
     */

    //TODO CAVEAT: THIS METHOD WILL FAIL SINCE THE CURRENT PROJECT DOES NOT CONTAIN A REAL RESTFUL SERVER.
    //TODO THE CRASH MIGHT BE Caused by: org.gradle.api.GradleException: File google-services.json is missing.
    //TODO The Google Services Plugin cannot function without it.
    public static List<Comment> onRetrieveDataFromFirebaseDatabase(Context context, CountDownLatch countDownLatch) {
        initializeFirebase(context);

        final List<Comment> mCommentList = new ArrayList<>();
        final CountDownLatch finalCountDownLatch = countDownLatch;
        databaseReference.child(Constant.COMMENT_STRING).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Comment comment = snapshot.getValue(Comment.class);

                            if (comment != null) {
                                mCommentList.add(comment);
                            }
                        }
                        // Reversing the order of comments so that newest comments
                        // shows on the top of the recyclerView
                        Collections.reverse(mCommentList);
                        finalCountDownLatch.countDown();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "[onRetrieveDataFromFirebaseDatabase] Failed to read value.", databaseError.toException());
                    }
                }
        );
        return mCommentList;
    }

}
