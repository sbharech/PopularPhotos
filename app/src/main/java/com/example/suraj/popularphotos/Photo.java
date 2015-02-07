package com.example.suraj.popularphotos;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by suraj on 03/02/15.
 */

public class Photo {
    private String mediaID;
    private String type;
    private String url;
    private String userName;
    private String caption;
    private String userProfilePic;
    private String relativeTime;
    private int    likesCount;
    private int    commentCount;
    private int    photoHeight;
    private int    photoWidth;

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getMediaID() {
        return mediaID;
    }

    public void setMediaID(String mediaID) {
        this.mediaID = mediaID;
    }

    public int getPhotoHeight() { return photoHeight;}

    public void setPhotoHeight(int photoHeight) {
        this.photoHeight = photoHeight;
    }

    public int getPhotoWidth() {
        return photoWidth;
    }

    public void setPhotoWidth(int photoWidth) {
        this.photoWidth = photoWidth;
    }

    public class Comment {
        public String userName;
        public String comment;
        Comment(String userName, String comment) {
            this.userName = userName;
            this.comment = comment;
        }
    }

    private ArrayList<Comment> comments = new ArrayList<Comment>();

    public ArrayList<Comment> getComments() { return comments;}

    public void addComment(String userName, String comment) { comments.add(new Comment(userName, comment));}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public static ArrayList<Photo> populatePhotoInfoFromJSON(JSONArray photoDataArray) {
        ArrayList<Photo> photoList = new ArrayList<Photo>();

        for (int i = 0; i < photoDataArray.length(); ++i) {
            try {
                JSONObject photoData = photoDataArray.getJSONObject(i);
                Photo photo = new Photo();

                photo.setType((String) photoData.get("type"));
                photo.setMediaID(photoData.getString("id"));

                try {
                    photo.setCaption((String) photoData.getJSONObject("caption").get("text"));
                } catch (JSONException e) {
                    photo.setCaption("");
                    Log.i("Error....", e.toString());
                }

                photo.setUrl((String) photoData.getJSONObject("images").getJSONObject("standard_resolution").get("url"));
                photo.setUserName(photoData.getJSONObject("user").getString("username"));
                photo.setLikesCount(photoData.getJSONObject("likes").getInt("count"));
                photo.setUserProfilePic((String) photoData.getJSONObject("user").get("profile_picture"));
                photo.setRelativeTime(Utility.getRelativeTime(photoData.getLong("created_time")));
                photo.setPhotoHeight(photoData.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                photo.setPhotoWidth(photoData.getJSONObject("images").getJSONObject("standard_resolution").getInt("width"));

                try {
                    photo.setCommentCount(photoData.getJSONObject("comments").getInt("count"));
                    JSONArray comments = photoData.getJSONObject("comments").getJSONArray("data");
                    for (int j = 0; j < comments.length(); ++j) {
                        String userName = comments.getJSONObject(j).getJSONObject("from").getString("username");
                        String comment = comments.getJSONObject(j).getString("text");
                        photo.addComment(userName, comment);
                    }
                } catch (Exception e) {
                    Log.i("Error....", e.toString());
                }

                photoList.add(photo);
            } catch (JSONException e) {
                Log.i("Error....", e.toString());
            }
        }

        return photoList;
    }
}
