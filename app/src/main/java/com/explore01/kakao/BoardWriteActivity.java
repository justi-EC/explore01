package com.explore01.kakao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.explore01.kakao.model.PostModel;
import com.explore01.kakao.singleton.User;

public class BoardWriteActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentsEditText;
    private Spinner board_spinner;
    private int currentPosition;
    private boolean rewrite;
    private String postKey;
    private PostModel postModel;
    private ActionBar actionBar;
    private TextView toolbarText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        toolbarText = findViewById(R.id.toolbartext);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        currentPosition = getIntent().getExtras().getInt("CURRENT_BOARD_TAB");
        String reWriteTitle = getIntent().getExtras().getString("BOARD_TITLE");
        String reWriteContents = getIntent().getExtras().getString("BOARD_CONTENTS");
        postKey = getIntent().getExtras().getString("CORRECT_POST_KEY");

        titleEditText = (EditText) findViewById(R.id.board_write_title_edit_text);
        contentsEditText = (EditText) findViewById(R.id.board_write_content_edit_text);
        ImageButton writeButton = (ImageButton) findViewById(R.id.board_write_finish);
        ImageButton closeButton = (ImageButton) findViewById(R.id.board_write_close_button);
        board_spinner = (Spinner) findViewById(R.id.board_spinner);

        board_spinner.post(new Runnable() {
            @Override
            public void run() {
                board_spinner.setSelection(currentPosition);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BoardWriteActivity.this, R.array.board_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        board_spinner.setAdapter(adapter);

        postModel = new PostModel((String) board_spinner.getSelectedItem());


        if (reWriteContents != null && reWriteTitle != null) {
            titleEditText.setText(reWriteTitle);
            contentsEditText.setText(reWriteContents);
            board_spinner.setVisibility(View.GONE);
            rewrite = true;

        }


        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextInputError())
                    return;
                String userName = getPostAuthorName();
                sendPost(userName);
                finish();
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private boolean isTextInputError() {
        if (TextUtils.isEmpty(titleEditText.getText().toString())) {
            titleEditText.requestFocus();
            titleEditText.setError("제목을 입력해주세요\n");
            return true;
        } else if (TextUtils.isEmpty(contentsEditText.getText().toString())) {
            contentsEditText.setError("내용을 입력해주세요\n");
            contentsEditText.requestFocus();
            return true;
        }

        return false;
    }

    public void sendPost(String userName) {
        if (rewrite) {
            int commentCount = getIntent().getExtras().getInt("COMMENT_COUNT");
            postModel.correctPost((String) board_spinner.getSelectedItem(), userName, titleEditText.getText().toString(), contentsEditText.getText().toString(), postKey, commentCount);

        } else
            postModel.writePost((String) board_spinner.getSelectedItem(), userName, titleEditText.getText().toString(), contentsEditText.getText().toString());

        Toast.makeText(getApplicationContext(), "작성이 완료되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private String getPostAuthorName() {
        String authorName = board_spinner.getSelectedItemPosition() == 2 ? "익명" : User.getInstance().getUserName();
        return authorName;
    }


}