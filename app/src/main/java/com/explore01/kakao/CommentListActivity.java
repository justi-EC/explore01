package com.explore01.kakao;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.explore01.kakao.model.CommentModel;
import com.explore01.kakao.model.OnDataChangedListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.annotations.Nullable;


public class CommentListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CommentModel model;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        String dataRefKey = getIntent().getExtras().getString("POST_KEY");
        String postType = getIntent().getExtras().getString("POST_TYPE");
        model = new CommentModel(dataRefKey, postType);
        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);

        LinearLayoutManager mManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mManager);

        final EditText commentEdit = (EditText) findViewById(R.id.comment_edit);
        final ImageButton sendButton = (ImageButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(commentEdit.getText().toString())) {
                    Snackbar snackbar = Snackbar.make(sendButton, "댓글을 입력하세요", Snackbar.LENGTH_SHORT);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.parseColor("#F1B3B8"));
                    snackbar.show();
                    return;
                }

                model.writeComment(commentEdit.getText().toString());
                commentEdit.setText("");
            }
        });

        model.setOnDataChangedListener(new OnDataChangedListener() {
            @Override
            public void onDataChanged() {
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }
        });

        setCommentList();

    }

    private void setCommentList() {
        recyclerView.setAdapter(model.loadCommentList());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down_anim);
        model.removeListener();
    }


}