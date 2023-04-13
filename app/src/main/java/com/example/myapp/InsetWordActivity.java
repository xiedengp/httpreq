package com.example.myapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapp.database.Word;
import com.example.myapp.database.WordViewMode;
import com.google.gson.Gson;

public class InsetWordActivity extends AppCompatActivity {
    private EditText etEg, etChinese;
    private Button add;
    WordViewMode wordViewMode;

    Word word = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inset_word);
//        wordViewMode = new WordViewMode(getApplication());
        wordViewMode = new ViewModelProvider(this).get(WordViewMode.class);
        etEg = findViewById(R.id.et_eg);
        etChinese = findViewById(R.id.et_chinese);
        add = findViewById(R.id.add_word);
        String str = getIntent().getStringExtra("word");
        if (!TextUtils.isEmpty(str)) {
            word = new Gson().fromJson(str, Word.class);
            etEg.setText(word.getWord());
            etChinese.setText(word.getMeaning());
            add.setText("编辑");
        } else
            add.setEnabled(false);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                add.setEnabled(etChinese.getText().toString().trim().length() > 0 && etEg.getText().toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        etChinese.addTextChangedListener(textWatcher);
        etEg.addTextChangedListener(textWatcher);
        add.setOnClickListener(v -> {
            if (word != null) {
                word.setWord(etEg.getText().toString());
                word.setMeaning(etChinese.getText().toString());
                wordViewMode.update(word);
                Toast.makeText(this, "编辑成功！", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            wordViewMode.inset(new Word(etEg.getText().toString(), etChinese.getText().toString()));
            Toast.makeText(this, "添加成功！", Toast.LENGTH_SHORT).show();
            etEg.requestFocus();
            etChinese.clearFocus();
            etEg.setText("");
            etChinese.setText("");
        });
    }
}
