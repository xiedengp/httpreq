package com.example.myapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.database.Word;

public class WordAdapter extends ListAdapter<Word, WordAdapter.WordHolder> {
    private Context context;
    private ItemListener listener = null;

    public void setListener(ItemListener listener) {
        this.listener = listener;
    }

    public WordAdapter() {
        super(new DiffUtil.ItemCallback<Word>() {
            @Override
            public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                return oldItem.id == newItem.id;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                return oldItem.meaning.equals(newItem.meaning) && oldItem.word.equals(newItem.word);
            }
        });
    }


    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_word, parent, false);
        return new WordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder holder, int position) {
        Word word = getCurrentList().get(position);
        holder.word.setText(word.getWord());
        holder.chinese.setText(word.getMeaning());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null)
                listener.selectWord(word);
        });
        holder.edit.setOnClickListener(v->{
            if(listener!=null)
                listener.editWord(word);
        });
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    static class WordHolder extends RecyclerView.ViewHolder {
        private final TextView word;
        private final TextView chinese;
        private final TextView edit;

        public WordHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.word);
            chinese = itemView.findViewById(R.id.chinese);
            edit = itemView.findViewById(R.id.edit);
        }
    }

    public interface ItemListener {
        void selectWord(Word word);
        void editWord(Word word);
    }

}