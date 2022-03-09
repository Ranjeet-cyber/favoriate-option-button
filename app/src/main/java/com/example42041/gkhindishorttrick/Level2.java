package com.example42041.gkhindishorttrick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example42041.gkhindishorttrick.adapters.QuizListAdapter;
import com.example42041.gkhindishorttrick.database.DbHelper;
import com.example42041.gkhindishorttrick.model.MainContent;

/* renamed from: com.allcommon.listdesign.ui.Level2 */
public class Level2 extends AppCompatActivity implements QuizListAdapter.ItemClickListener {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        RecyclerView rv = (RecyclerView) findViewById(R.id.quiz_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new QuizListAdapter(this, DbHelper.getMainListName(), this));
    }

    @SuppressLint("WrongConstant")
    public void itemClicked(MainContent item, int pos) {
        Toast.makeText(this, "" + item.getId(), 0).show();
        startActivity(new Intent(this, desc.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back_slide_in, R.anim.back_slide_out);
    }
}
