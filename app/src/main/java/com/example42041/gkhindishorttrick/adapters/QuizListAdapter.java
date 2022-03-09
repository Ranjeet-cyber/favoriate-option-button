package com.example42041.gkhindishorttrick.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example42041.gkhindishorttrick.R;
import com.example42041.gkhindishorttrick.model.MainContent;

import java.util.List;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.ViewHolder> {
    Context context;
    /* access modifiers changed from: private */
    public ItemClickListener itemClickListener;
    private List<MainContent> items;

    public interface ItemClickListener {
        void itemClicked(MainContent mainContent, int i);
    }

    public QuizListAdapter(Context context2, List<MainContent> objects, @NonNull ItemClickListener itemClickListener2) {
        this.items = objects;
        this.itemClickListener = itemClickListener2;
        this.context = context2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return ViewHolder.newInstance(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quiz_card, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final MainContent item = this.items.get(position);
        viewHolder.setTitle(item.getName());
        viewHolder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                QuizListAdapter.this.itemClickListener.itemClicked(item, position);
            }
        });
    }

    public int getItemCount() {
        return this.items.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView main_icon;
        private final View parent;
        private final TextView title;

        public static ViewHolder newInstance(View parent2) {
            return new ViewHolder(parent2, (TextView) parent2.findViewById(R.id.quiz_question), (ImageView) parent2.findViewById(R.id.main_icon));
        }

        private ViewHolder(View parent2, TextView title2, ImageView card) {
            super(parent2);
            this.parent = parent2;
            this.title = title2;
            this.main_icon = card;
        }

        public void setTitle(CharSequence text) {
            this.title.setText(text);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            this.parent.setOnClickListener(listener);
        }
    }
}
