package com.example42041.gkhindishorttrick;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example42041.gkhindishorttrick.adapters.ViewPagerAdapter;
import com.example42041.gkhindishorttrick.database.DbHelper;
import com.example42041.gkhindishorttrick.model.DataContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class desc extends AppCompatActivity implements View.OnClickListener {
    PagerAdapter adapter;
    int currentpos = 1;
    List<DataContent> dataContentList;
    ImageView iv_addfav;
    ImageView iv_favlist;
    ImageView iv_share;
  //  private AdView mAdView;
    int position = 1;
    LinearLayout tv_next;
    LinearLayout tv_prv;
    ViewPager viewPager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_favorites);
      //  callads();
        this.tv_prv = (LinearLayout) findViewById(R.id.tv_prv);
        this.tv_next = (LinearLayout) findViewById(R.id.tv_next);
        this.iv_favlist = (ImageView) findViewById(R.id.iv_favlist);
        this.iv_share = (ImageView) findViewById(R.id.iv_share);
        this.iv_addfav = (ImageView) findViewById(R.id.iv_addfav);
        this.tv_prv.setOnClickListener(this);
        this.tv_next.setOnClickListener(this);
        this.iv_favlist.setOnClickListener(this);
        this.iv_share.setOnClickListener(this);
        this.iv_addfav.setOnClickListener(this);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                desc.this.numberPicker();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.position = extras.getInt("id");
            this.currentpos = extras.getInt("pos");
        }
        this.dataContentList = DbHelper.getContentListName(this.position);
        this.viewPager = (ViewPager) findViewById(R.id.pager);
        this.adapter = new ViewPagerAdapter(this, this.dataContentList);
        this.viewPager.setAdapter(this.adapter);
        this.viewPager.setCurrentItem(this.currentpos);
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (desc.this.dataContentList.get(position).getFav() == 0) {
                    desc.this.iv_addfav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                } else {
                    desc.this.iv_addfav.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
            }

            public void onPageSelected(int position) {
                Log.i("", "");
            }

            public void onPageScrollStateChanged(int state) {
                Log.i("", "");
            }
        });
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

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back_slide_in, R.anim.back_slide_out);
    }

    @SuppressLint("WrongConstant")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_prv /*2131558516*/:
                int prvcurrentpos = this.viewPager.getCurrentItem() - 1;
                if (prvcurrentpos != -1) {
                    this.viewPager.setCurrentItem(prvcurrentpos, true);
                    return;
                }
                return;
            case R.id.tv_next /*2131558517*/:
                int nextcurrentpos = this.viewPager.getCurrentItem() + 1;
                if (nextcurrentpos != this.dataContentList.size()) {
                    this.viewPager.setCurrentItem(nextcurrentpos, true);
                    return;
                }
                return;
            case R.id.iv_favlist /*2131558522*/:
                ((ClipboardManager) getSystemService("clipboard")).setText(this.dataContentList.get(this.viewPager.getCurrentItem()).getDataContent().toString());
                Toast.makeText(this, "Copied to clipboard", 0).show();
                return;
            case R.id.iv_share /*2131558523*/:
                shareData();
                return;
            case R.id.iv_addfav /*2131558524*/:
                DbHelper.makeFavUnfavData(this.dataContentList.get(this.viewPager.getCurrentItem()).getId(), 1 > this.dataContentList.get(this.viewPager.getCurrentItem()).getFav() ? 1 : 0);
                this.dataContentList = DbHelper.getContentListName(this.position);
                if (this.dataContentList.get(this.viewPager.getCurrentItem()).getFav() == 1) {
                    this.iv_addfav.setImageResource(R.drawable.ic_baseline_favorite_24);
                    return;
                } else {
                    this.iv_addfav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    return;
                }
            default:
                return;
        }
    }

    public void numberPicker() {
        RelativeLayout linearLayout = new RelativeLayout(this);
        final NumberPicker aNumberPicker = new NumberPicker(this);
        aNumberPicker.setMaxValue(50

        );
        aNumberPicker.setMinValue(14);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(-2, -2);
        numPicerParams.addRule(14);
        linearLayout.setLayoutParams(params);
        linearLayout.addView(aNumberPicker, numPicerParams);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle((CharSequence) "Select the Size of Text");
        alertDialogBuilder.setView((View) linearLayout);
        alertDialogBuilder.setCancelable(false).setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.e("New", "New Quantity Value : " + aNumberPicker.getValue());
                desc.this.storeSharePreference(aNumberPicker.getValue());
                int nextcurrentpos1 = desc.this.viewPager.getCurrentItem();
                desc.this.adapter = new ViewPagerAdapter(desc.this, desc.this.dataContentList);
                desc.this.viewPager.setAdapter(desc.this.adapter);
                desc.this.viewPager.setCurrentItem(nextcurrentpos1);
            }
        }).setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.create().show();
    }

    /* access modifiers changed from: private */
    public void storeSharePreference(int size) {
        SharedPreferences.Editor editor = getSharedPreferences("appData", 0).edit();
        editor.putInt("textSize", size);
        editor.commit();
    }

    private void shareData() {
        Intent sharingIntent = new Intent("android.intent.action.SEND");
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra("android.intent.extra.SUBJECT", "Share this Data");
        sharingIntent.putExtra("android.intent.extra.TEXT", this.dataContentList.get(this.viewPager.getCurrentItem()).getDataContent());
        startActivity(Intent.createChooser(sharingIntent, "Share Data"));
    }

}