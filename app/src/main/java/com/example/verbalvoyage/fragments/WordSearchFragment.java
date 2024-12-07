package com.example.verbalvoyage.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.verbalvoyage.R;
import com.example.verbalvoyage.adapters.WordListAdapter;
import com.example.verbalvoyage.adapters.WordSearchAdapter;
import com.example.verbalvoyage.databinding.FragmentWordSearchBinding;
import com.example.verbalvoyage.databinding.ItemWordSearchBinding;
import com.example.verbalvoyage.models.Clue;
import com.example.verbalvoyage.models.GridLocation;
import com.example.verbalvoyage.models.Word;
import com.example.verbalvoyage.models.WordSearch;
import com.example.verbalvoyage.utilities.Utils;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.graphics.Color;
import android.view.MotionEvent;
public class WordSearchFragment extends RecyclerView.Adapter<WordSearchFragment.WordSearchViewHolder> {

    private final char[] letters;
    private final Set<Integer> selectedPositions = new HashSet<>();
    private final WordSearch wordSearch;
    private final ItemTouchHelper itemTouchHelper;

    public WordSearchFragment(char[] letters, WordSearch wordSearch, RecyclerView recyclerView) {
        this.letters = letters;
        this.wordSearch = wordSearch;

        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlags, 0);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // Handle item movement if needed
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Handle swipe actions if needed
            }

            @Override
            public void onSelectedChanged(@NonNull RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    int position = viewHolder.getAdapterPosition();
                    updateSelectedPositions(position);
                    notifyDataSetChanged();
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                selectedPositions.clear();
                notifyDataSetChanged();
            }
        };

        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void updateSelectedPositions(int draggedPosition) {
        // Implement logic to update the selected positions based on the drag direction and dragged item
        // ...
    }

    @NonNull
    @Override
    public WordSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWordSearchBinding binding = ItemWordSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WordSearchViewHolder(binding);
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

        public WordSearchViewHolder(@NonNull ItemWordSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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

                    // Validate the selected word
                    StringBuilder selectedWord = new StringBuilder();
                    for (int position : selectedPositions) {
                        selectedWord.append(letters[position]);
                    }
                    if (wordSearch.isValidWord(selectedWord.toString())) {
                        // Handle valid word selection (e.g., highlight, remove from word list)
                    } else {
                        // Handle invalid selection (e.g., clear selection)
                        selectedPositions.clear();
                    }

                    notifyDataSetChanged();
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}