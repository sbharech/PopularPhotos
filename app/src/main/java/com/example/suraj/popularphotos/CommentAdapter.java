package com.example.suraj.popularphotos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by suraj on 04/02/15.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {

    private static class ViewHolder {
        RoundedImageView profilePic;
        TextView         userName;
        TextView         relativeCreationTime;
        TextView         comment;
    }


    CommentAdapter(Context context, ArrayList<Comment> commentList) {
        super(context, 0, commentList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = getItem(position);

        ViewHolder v;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
            v = new ViewHolder();

            v.profilePic = (RoundedImageView)convertView.findViewById(R.id.ivProfile);
            v.userName = (TextView)convertView.findViewById(R.id.tvUserName);
            v.relativeCreationTime = (TextView)convertView.findViewById(R.id.tvRelativeTime);
            v.comment = (TextView)convertView.findViewById(R.id.tvComment);
            convertView.setTag(v);
        } else {
            v = (ViewHolder)convertView.getTag();
        }


        v.userName.setText(comment.getUserName());
        v.comment.setText(comment.getComment());
        v.relativeCreationTime.setText(comment.getRelativeTime());
        v.profilePic.setImageResource(0);
        Picasso.with(getContext()).load(comment.getUserProfilePic()).placeholder(R.drawable.ic_defaultprofile).into(v.profilePic);
        return convertView;
    }
}
