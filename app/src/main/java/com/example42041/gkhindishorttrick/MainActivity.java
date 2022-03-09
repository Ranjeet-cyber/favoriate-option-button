package com.example42041.gkhindishorttrick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example42041.gkhindishorttrick.adapters.QuizListAdapter;
import com.example42041.gkhindishorttrick.database.DbHelper;
import com.example42041.gkhindishorttrick.model.MainContent;

public class MainActivity extends AppCompatActivity  implements QuizListAdapter.ItemClickListener {
        DbHelper dbHelper;

/* access modifiers changed from: protected */
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        this.dbHelper = new DbHelper(this);
        this.dbHelper.createDb(false);
        RecyclerView rv = (RecyclerView) findViewById(R.id.quiz_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DbHelper dbHelper2 = this.dbHelper;
        rv.setAdapter(new QuizListAdapter(this, DbHelper.getMainListName(), this));
        }

public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
        }

@SuppressLint("WrongConstant")
public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_like_list) {
        if (DbHelper.getFavoritesList().size() != 0) {
        startActivity(new Intent(this, Favorites.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else {
        Toast.makeText(this, "You don't have any Favorites List..", 0).show();
        }
        }
        return super.onOptionsItemSelected(item);
        }

public void itemClicked(MainContent item, int pos) {
        Intent desIntent = new Intent(this, desc.class);
        desIntent.putExtra("id", item.getId());
        desIntent.putExtra("pos", pos);
        startActivity(desIntent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.stay_rest);
        }
        }
