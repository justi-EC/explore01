package com.explore01.kakao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BoardAnonymousFragment extends CommonBoardFragment {

    public BoardAnonymousFragment() {
    }

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("익명자유");
    }

    @Override
    public String getPostType() {
        return "익명자유";
    }
}
