package com.explore01.kakao.model;

import com.explore01.kakao.R;
import com.explore01.kakao.singleton.Post;
import com.explore01.kakao.viewholder.PostViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PostModel {
    private DatabaseReference databaseReference;
    private OnDataChangedListener onDataChangedListener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }

    public PostModel(String postType) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(postType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (onDataChangedListener != null) {
                    onDataChangedListener.onDataChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void writePost(String postType, String userName, String title, String contents) {
        databaseReference.child(postType).
                push().setValue(Post.newPost(userName, title, contents, 0));
    }//등록

    public void correctPost(String postType, String userName, String title, String contents, String postKey, int commentCount) {
        databaseReference.child(postType).
                child(postKey).setValue(Post.newPost(userName, title, contents, commentCount));

    }//수정

    //원하는 데이터를 얻기 위해 데이터베이스에 정보를 요청(Request)하는 것=쿼리
    public FirebaseRecyclerAdapter setAdapter(Query query, final String postType) {

        FirebaseRecyclerAdapter<Post, PostViewHolder> adapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class, R.layout.list_item_post,
                PostViewHolder.class, query) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                DatabaseReference postRef = getRef(position);
                String postKey = postRef.getKey();
                viewHolder.bindPost(model, postKey, postType);
            }
        };
        return adapter;
    }
}
