package com.explore01.kakao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BoardTradeFragment extends CommonBoardFragment {

    public BoardTradeFragment() {
    }

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("중고거래");
    }

    @Override
    public String getPostType() {
        return "중고거래";
    }
}
