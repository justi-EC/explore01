package com.explore01.kakao.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.explore01.kakao.BoardTabFragment;
import com.explore01.kakao.BoardWriteActivity;
import com.explore01.kakao.CommentListActivity;

import com.explore01.kakao.R;
import com.explore01.kakao.singleton.Post;
import com.explore01.kakao.singleton.User;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView titleTextView;
    private TextView contentsTextView;
    private TextView authorTextView;
    private TextView dateTextView;
    private TextView commentCountTextView;

    private ImageButton dropdownButton;
    private String dataRefKey;
    private String postType;
    private Context context;
    private DatabaseReference mDatabase;
    private Post post;

    public PostViewHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();
        titleTextView = (TextView) itemView.findViewById(R.id.post_title_text_view);
        contentsTextView = (TextView) itemView.findViewById(R.id.post_contents_text_view);
        authorTextView = (TextView) itemView.findViewById(R.id.post_author_text_view);
        dateTextView = (TextView) itemView.findViewById(R.id.post_date_text_view);
        commentCountTextView = (TextView) itemView.findViewById(R.id.post_comment_number);
        dropdownButton = (ImageButton) itemView.findViewById(R.id.dropdown_button);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        dropdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, dropdownButton);
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.popup_delete) {
                            if (Objects.equals(post.getAuthorUid(), User.getInstance().getUid())) {
                                mDatabase.child("comments").child(dataRefKey).removeValue();
                                mDatabase.child(postType).child(dataRefKey).removeValue();
                            } else {
                                Snackbar.make(dateTextView, "권한이 없습니다", Snackbar.LENGTH_SHORT).show();
                            }
                        } else if (item.getItemId() == R.id.popup_rewrite) {
                            Intent intent = new Intent(context, BoardWriteActivity.class);
                            intent.putExtra("CURRENT_BOARD_TAB", BoardTabFragment.getCurrentTab());
                            intent.putExtra("BOARD_TITLE", post.getTitle());
                            intent.putExtra("BOARD_CONTENTS", post.getContents());
                            intent.putExtra("CORRECT_POST_KEY", dataRefKey);
                            intent.putExtra("COMMENT_COUNT", post.getCommentCount());
                            context.startActivity(intent);
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });

        itemView.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, CommentListActivity.class);
        intent.putExtra("POST_KEY", dataRefKey);
        intent.putExtra("POST_TYPE", postType);
        context.startActivity(intent);

        ((Activity) context).overridePendingTransition(R.anim.slide_up_anim, R.anim.no_change);
    }

    public void bindPost( final Post model, String postKey, String postType) {
        post = model;

        titleTextView.setText(model.getTitle());
        contentsTextView.setText(model.getContents());
        authorTextView.setText(model.getAuthor());
        dateTextView.setText(model.getTimeStamp());
        commentCountTextView.setText(String.valueOf(model.getCommentCount()));
        this.dataRefKey = postKey;
        this.postType = postType;

        int visibility = Objects.equals(model.getAuthorUid(), User.getInstance().getUid()) ?
                View.VISIBLE : View.GONE;

        dropdownButton.setVisibility(visibility);

    }

}
