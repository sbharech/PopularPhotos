package com.example.suraj.popularphotos;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by suraj on 03/02/15.
 */
public class PhotoAdapter extends ArrayAdapter<Photo> {
    static String intFormatPattern = "###,###,###,###";
    static DecimalFormat formatter = new DecimalFormat(intFormatPattern);

    private int screenWidth;

    private static class ViewHolder {
        LinearLayout title;
        View titleView;
        ImageView profilePic;
        TextView userName;
        TextView  relativeCreationTime;
        ImageView image;
        ImageView   mediaType;
        TextView  caption;
        TextView   likeCount;
        TextView    commentCount;
        LinearLayout comments;
        View inflatedCommentView1;
        TextView comment1;
        View inflatedCommentView2;
        TextView comment2;
    }

    static public String integerFormatter(int value ) {
        return formatter.format(value);
    }

    public PhotoAdapter(Context context, ArrayList<Photo> photoArrayList) {
        super(context, 0, photoArrayList);
        screenWidth = Utility.getDisplayWidth(context);
    }

    private String getFormattedComment(Photo.Comment c) {
        String s = "<font color=" + R.color.user_name +  ">" + c.userName + "</font> " + c.comment;
        return  s;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Photo photo = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            viewHolder.title = (LinearLayout)convertView.findViewById(R.id.llTitle);
            viewHolder.image = (ImageView)convertView.findViewById(R.id.ivPhoto);
            viewHolder.mediaType = (ImageView)convertView.findViewById(R.id.ivMediaType);
            viewHolder.caption = (TextView)convertView.findViewById(R.id.tvPhotoDetails);
            viewHolder.likeCount = (TextView)convertView.findViewById(R.id.tvLikeCount);
            viewHolder.commentCount = (TextView)convertView.findViewById(R.id.tvCommentCount);
            viewHolder.comments = (LinearLayout)convertView.findViewById(R.id.llComments);


            viewHolder.titleView = LayoutInflater.from(getContext()).inflate(R.layout.item_title, parent, false);
            viewHolder.profilePic = (ImageView)viewHolder.titleView.findViewById(R.id.ivProfile);
            viewHolder.userName = (TextView)viewHolder.titleView.findViewById(R.id.tvUserName);
            viewHolder.relativeCreationTime = (TextView)viewHolder.titleView.findViewById(R.id.tvRelativeTime);

            viewHolder.inflatedCommentView1 = LayoutInflater.from(getContext()).inflate(R.layout.item_short_comment, parent, false);
            viewHolder.comment1 = (TextView) viewHolder.inflatedCommentView1.findViewById(R.id.tvComment);

            viewHolder.inflatedCommentView2 = LayoutInflater.from(getContext()).inflate(R.layout.item_short_comment, parent, false);
            viewHolder.comment2 = (TextView) viewHolder.inflatedCommentView2.findViewById(R.id.tvComment);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.title.removeAllViews();
        viewHolder.profilePic.setImageResource(0);
        Picasso.with(getContext()).load(photo.getUserProfilePic()).placeholder(R.drawable.ic_defaultprofile).into(viewHolder.profilePic);

        viewHolder.userName.setText(photo.getUserName());
        viewHolder.relativeCreationTime.setText(photo.getRelativeTime());

        viewHolder.title.addView(viewHolder.titleView);

        viewHolder.image.setImageResource(0);
        viewHolder.image.setMinimumWidth(screenWidth);
        viewHolder.image.setMinimumHeight(photo.getPhotoHeight()*screenWidth/photo.getPhotoWidth());

        Picasso.with(getContext()).load(photo.getUrl()).placeholder(R.drawable.ic_defaultimage).into(viewHolder.image);

        viewHolder.mediaType.setImageResource(0);

        if ("video".equalsIgnoreCase(photo.getType())) {
            viewHolder.mediaType.setImageResource(R.drawable.ic_video);
        }

        viewHolder.caption.setText(photo.getCaption());

        if (photo.getLikesCount() > 0)
            viewHolder.likeCount.setText(integerFormatter(photo.getLikesCount()) + " likes");

        viewHolder.comments.removeAllViews();

        if (photo.getCommentCount() > 0) {

            if (photo.getCommentCount() > 2) {
                viewHolder.commentCount.setText(Html.fromHtml("<font color=\"grey\">view all " + integerFormatter(photo.getCommentCount()) + " comments </font>"));
                viewHolder.commentCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("Info", "Clicked media id " + photo.getMediaID());
                        Intent commentsIntent = new Intent(getContext(), CommentsActivity.class);
                        commentsIntent.putExtra("media_id", photo.getMediaID());
                        getContext().startActivity(commentsIntent);
                    }
                });
            }

            if (photo.getComments().size() > 1) {
                viewHolder.comment1.setText(Html.fromHtml(getFormattedComment(photo.getComments().get(photo.getComments().size() - 2))));
                viewHolder.comments.addView(viewHolder.inflatedCommentView1);
            }


            viewHolder.comment2.setText(Html.fromHtml(getFormattedComment(photo.getComments().get(photo.getComments().size() - 1))));
            viewHolder.comments.addView(viewHolder.inflatedCommentView2);

        } else {
            viewHolder.commentCount.setText("");
        }

        return convertView;
    }
}
