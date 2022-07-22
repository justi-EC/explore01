package com.explore01.kakao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BoardLossFragment extends CommonBoardFragment {

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("분실물신고");
    }

    @Override
    public String getPostType() {
        return "분실물신고";
    }

    public BoardLossFragment() {
    }
}