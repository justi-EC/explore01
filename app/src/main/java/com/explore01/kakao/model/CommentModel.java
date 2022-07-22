package com.explore01.kakao.model;

import com.explore01.kakao.R;
import com.explore01.kakao.singleton.Comment;
import com.explore01.kakao.singleton.User;
import com.explore01.kakao.viewholder.CommentViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class CommentModel {
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private String dataRefKey;
    private String postType;
    private int commentCount;
    private OnDataChangedListener onDataChangedListener;


    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }


    public CommentModel(String dataRef, String post) {

        this.dataRefKey = dataRef;
        this.postType = post;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        valueEventListener = databaseReference.child("comments").child(dataRefKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentCount = (int) dataSnapshot.getChildrenCount();
                databaseReference.child(postType).child(dataRefKey).child("commentCount").setValue(commentCount);

                if (onDataChangedListener != null) {
                    onDataChangedListener.onDataChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }


    public FirebaseRecyclerAdapter<Comment, CommentViewHolder> loadCommentList(){
        FirebaseRecyclerAdapter<Comment, CommentViewHolder> mAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(Comment.class, R.layout.list_item_comment,
                CommentViewHolder.class, databaseReference.child("comments").child(dataRefKey)) {
            @Override
            protected void populateViewHolder(CommentViewHolder viewHolder, Comment model, int position) {
                String commentKey = getRef(position).getKey();
                viewHolder.bindComment(model, dataRefKey, commentKey);
            }

        };

        return mAdapter;
    }

    public void writeComment(String message) {
        databaseReference.child("comments").child(dataRefKey).push().setValue(Comment.newComment(getUserName(), message));
    }

    private String getUserName() {
        String userName;

        if (Objects.equals(postType, "익명자유"))
            userName = "익명";
        else
            userName = User.getInstance().getUserName();

        return userName;

    }

    public void removeListener() {
        databaseReference.child("comments").child(dataRefKey).removeEventListener(valueEventListener);
    }
}
