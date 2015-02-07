package com.example.suraj.popularphotos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CommentsActivity extends ActionBarActivity {
    private String getAllCommentsURL(String mediaID) {
        return "https://api.instagram.com/v1/media/" + mediaID + "/comments?client_id=" + PopularPhotoActivity.clientID;
    }

    private ArrayList<Comment> commentList;
    private CommentAdapter commentAdapter;
    private ListView commentListView;
    private ProgressBar progressBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initParams();
        fetchAllComments();
    }

    private void initParams() {
        commentList = new ArrayList<Comment>();
        commentListView = (ListView)findViewById(R.id.lvComments);
        progressBarView = (ProgressBar)findViewById(R.id.pvLoading);
        commentAdapter = new CommentAdapter(this, commentList);
        commentListView.setAdapter(commentAdapter);
    }

    private void fetchAllComments() {
        Intent intent = getIntent();
        String mediaID = intent.getStringExtra("media_id");

        final String commentsURL = getAllCommentsURL(mediaID);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(commentsURL,null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            JSONArray commentDataArray = response.getJSONArray("data");
                            progressBarView.setVisibility(View.INVISIBLE);
                            commentAdapter.addAll(Comment.getAllCommentsFromJSON(commentDataArray));
                        } catch (JSONException e) {
                            Log.i("Error....", e.toString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        progressBarView.setVisibility(View.INVISIBLE);
                        NetworkErrorDialog errorDialog = new NetworkErrorDialog(CommentsActivity.this);
                        errorDialog.createNetworkErrorDialog();
                    }
                }
        );



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
