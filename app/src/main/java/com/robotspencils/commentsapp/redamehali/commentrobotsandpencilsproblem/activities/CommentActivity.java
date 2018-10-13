package com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.Fragments.CreateCommentFragment;
import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.R;
import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.adapters.CommentAdapter;
import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.db_models.CommentFirebaseDatabaseAccess;
import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.helpers.CommentHelper;
import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.interfaces.RemoveFragmentListener;
import com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.models.Comment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * This is the main Comment activity class
 * It will contain retrieved Comment from the remote Store
 * Comments will be displayed in a recyclerView that is instantiated
 * in this activity class. Also, the newest comment will show first.
 * <p>
 * * Project: Robots & Pencils Technical Problem
 */
public class CommentActivity extends AppCompatActivity implements RemoveFragmentListener {

    private static final String TAG = "[" + CreateCommentFragment.class.getName() + "]=";
    private List<Comment> commentList;
    private CommentAdapter commentAdapter;
    private RecyclerView commentRecyclerView;
    private Button createCommentButton;
    private ProgressBar commentRetrieveProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity_layout);

        FirebaseApp.initializeApp(this);

        createCommentButton = findViewById(R.id.createCommentButton);
        commentRecyclerView = findViewById(R.id.comment_recycler_view);
        commentRetrieveProgressBar = findViewById(R.id.commentRetrieveProgressBar);

        createCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentHelper.onLaunchCreateCommentFragment(CommentActivity.this);
            }
        });

        new RetrieveCommentFromDbTask(this).execute();
    }

    /**
     * Method to setup the recyclerView after comments have been retrieved
     * from the remote Store Firebase server
     */
    private void onSetUpCommentRecyclerView() {
        commentAdapter = new CommentAdapter(this, commentList);
        if (commentRecyclerView != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

            commentRecyclerView.setLayoutManager(layoutManager);
            commentRecyclerView.setAdapter(commentAdapter);
        }
    }

    /**
     * Interface callback called whenever this method is executed
     * from the fragment. The method finishes/destroys the CreateCommentFragment class
     */
    @Override
    public void onFragmentRemoved() {
        CommentHelper.onRemoveFragment(this);
    }

    /**
     * AsyncTask Class used to retrieve comments from the database.
     * <p>
     * The AsyncTask class serves as a background thread in order to not block the
     * main thread when we want to retrieve comments. Also, it's a static class but
     * in the same time, it executes some variables that may create memory leakage.
     * In order to fix this issue, we used a weak reference to avoid memory leaks
     * of those data variables or objects.
     *
     * @method onPreExecute is in main thread. It is used to display
     * a spinning progress bar while retrieving comments from server store.
     * @method doInBackground is used to retrieve comments from the server store
     * in the background in order to not block the main thread while doing that work.
     * @method onPostExecute hides progress bar, display recyclerView, and pass the
     * list of retrieve comments to the global variable inside our Comment Activity class.
     * Finally, we will use that list of comments that we retrieve and set it in our RecyclerView
     */
    private static class RetrieveCommentFromDbTask extends AsyncTask<Void, Void, List<Comment>> {

        WeakReference<CommentActivity> commentActivityWeakReference;

        RetrieveCommentFromDbTask(CommentActivity commentActivityWeakReference) {
            this.commentActivityWeakReference = new WeakReference<>(commentActivityWeakReference);
        }

        private void onSetViewsVisibility(boolean progressVisible) {
            if (commentActivityWeakReference.get() != null) {
                commentActivityWeakReference.get().commentRetrieveProgressBar.setVisibility(progressVisible ? View.VISIBLE : View.GONE);
                commentActivityWeakReference.get().commentRecyclerView.setVisibility(progressVisible ? View.GONE : View.VISIBLE);
            }
        }

        @Override
        protected void onPreExecute() {
            onSetViewsVisibility(true);
        }

        @Override
        protected List<Comment> doInBackground(Void... voids) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            List<Comment> comments = new ArrayList<>();

            if (commentActivityWeakReference.get() != null) {
                comments = CommentFirebaseDatabaseAccess.onRetrieveDataFromFirebaseDatabase(commentActivityWeakReference.get(), countDownLatch);
            }

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return comments;
        }

        @Override
        protected void onPostExecute(List<Comment> commentList) {
            super.onPostExecute(commentList);
            if (commentActivityWeakReference.get() != null) {
                commentActivityWeakReference.get().commentList = commentList;
                commentActivityWeakReference.get().onSetUpCommentRecyclerView();
            }

            onSetViewsVisibility(false);
        }
    }
}
