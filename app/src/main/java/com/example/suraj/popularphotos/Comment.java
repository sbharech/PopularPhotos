package com.example.suraj.popularphotos;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by suraj on 04/02/15.
 */
public class Comment {
    private String userName;
    private String userProfilePic;
    private String relativeTime;
    private String comment;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    public String getRelativeTime() {
        return relativeTime;
    }

    public void setRelativeTime(String relativeTime) {
        this.relativeTime = relativeTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static ArrayList<Comment> getAllCommentsFromJSON(JSONArray commentDataArray) {
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        for (int i = 0; i < commentDataArray.length(); ++i) {
            try {
                JSONObject commentData = commentDataArray.getJSONObject(i);
                Comment comment = new Comment();

                comment.setComment(commentData.getString("text"));
                comment.setRelativeTime(Utility.getRelativeTime(commentData.getLong("created_time")));
                comment.setUserName(commentData.getJSONObject("from").getString("username"));
                comment.setUserProfilePic(commentData.getJSONObject("from").getString("profile_picture"));

                commentList.add(comment);

            } catch (JSONException e) {
                Log.i("Error....", e.toString());
            }
        }

        return commentList;
    }

}
