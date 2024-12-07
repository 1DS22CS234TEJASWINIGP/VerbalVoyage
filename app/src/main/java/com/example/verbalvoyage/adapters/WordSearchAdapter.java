package com.example.verbalvoyage.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.verbalvoyage.databinding.ItemWordSearchBinding;

import java.util.HashSet;
import java.util.Set;

public class WordSearchAdapter extends RecyclerView.Adapter<WordSearchAdapter.WordSearchViewHolder> {

    private final char[] letters;
    private final Set<Integer> selectedPositions = new HashSet<>();
    private int startX, startY;
    private final RecyclerView recyclerView;

    public WordSearchAdapter(char[] letters, RecyclerView recyclerView) {
        this.letters = letters;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public WordSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWordSearchBinding binding = ItemWordSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WordSearchViewHolder(binding, recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordSearchViewHolder holder, int position) {
        char letter = letters[position];
        holder.bind(letter, position, selectedPositions.contains(position));
    }

    @Override
    public int getItemCount() {
        return letters.length;
    }

    public class WordSearchViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
        private final ItemWordSearchBinding binding;
        private final RecyclerView recyclerView;

        public WordSearchViewHolder(@NonNull ItemWordSearchBinding binding, RecyclerView recyclerView) {
            super(binding.getRoot());
            this.binding = binding;
            this.recyclerView = recyclerView;
            itemView.setOnTouchListener(this);
        }

        public void bind(char letter, int position, boolean isSelected) {
            binding.tvLetter.setText(String.valueOf(letter).toUpperCase());
            binding.getRoot().setBackgroundColor(isSelected ? Color.GRAY : Color.WHITE);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int endX = (int) event.getX();
                    int endY = (int) event.getY();

                    // Calculate the selection rectangle
                    int minX = Math.min(startX, endX);
                    int maxX = Math.max(startX, endX);
                    int minY = Math.min(startY, endY);
                    int maxY = Math.max(startY, endY);

                    // Clear the previous selection
                    selectedPositions.clear();

                    // Iterate over the visible items and check if they are within the selection rectangle
                    for (int i = 0; i < recyclerView.getChildCount(); i++) {
                        View child = recyclerView.getChildAt(i);
                        int childX = (int) child.getX();
                        int childY = (int) child.getY();
                        int childWidth = child.getWidth();
                        int childHeight = child.getHeight();

                        if (minX <= childX && maxX >= childX + childWidth && minY <= childY && maxY >= childY + childHeight) {
                            selectedPositions.add(recyclerView.getChildAdapterPosition(child));
                        }
                    }

                    // Notify the adapter to update the UI
                    notifyDataSetChanged();
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}