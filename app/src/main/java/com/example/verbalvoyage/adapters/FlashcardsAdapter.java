package com.example.verbalvoyage.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton; // Import AppCompatButton
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.verbalvoyage.databinding.ItemFlashcardBinding;
import com.example.verbalvoyage.models.Word;
import com.example.verbalvoyage.utilities.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FlashcardsAdapter extends RecyclerView.Adapter<FlashcardsAdapter.FlashcardViewHolder> {
    List<Word> words;
    Fragment fragment;
    boolean answerInEnglish;
    int likedDrawableId; // Resource ID for the "liked" drawable
    int unlikedDrawableId; // Resource ID for the "unliked" drawable

    public FlashcardsAdapter(Fragment fragment, List<Word> words, boolean answerInEnglish, int likedDrawableId, int unlikedDrawableId) {
        this.fragment = fragment;
        this.words = words;
        this.answerInEnglish = answerInEnglish;
        this.likedDrawableId = likedDrawableId;
        this.unlikedDrawableId = unlikedDrawableId;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemFlashcardBinding binding = ItemFlashcardBinding.inflate(
                LayoutInflater.from(fragment.getActivity()), parent, false);
        return new FlashcardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FlashcardViewHolder holder, int position) {
        Word word = words.get(position);
        holder.bind(word);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public void addAll(List<Word> list) {
        words.addAll(list);
        notifyDataSetChanged();
    }

    public void add(Word word) {
        words.add(word);
        notifyItemInserted(words.size() - 1);
    }

    public void remove(int index) {
        words.remove(index);
        notifyItemRemoved(index);
    }

    public class FlashcardViewHolder extends RecyclerView.ViewHolder {
        ItemFlashcardBinding binding;

        public FlashcardViewHolder(@NonNull ItemFlashcardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Word word) {
            AppCompatButton starButtonFront = binding.layoutFront.btnStar; // Use AppCompatButton
            AppCompatButton starButtonRear = binding.layoutRear.btnStar; // Use AppCompatButton

            updateStarButton(starButtonFront, word.getIsStarred());
            updateStarButton(starButtonRear, word.getIsStarred());

            starButtonFront.setOnClickListener(v -> {
                word.toggleIsStarred();
                updateStarButton(starButtonFront, word.getIsStarred());
                updateStarButton(starButtonRear, word.getIsStarred());
                // Save the updated state to Parse (if applicable)
                word.saveInBackground();
            });

            starButtonRear.setOnClickListener(v -> {
                word.toggleIsStarred();
                updateStarButton(starButtonRear, word.getIsStarred());
                updateStarButton(starButtonFront, word.getIsStarred());
                // Save the updated state to Parse (if applicable)
                word.saveInBackground();
            });

            // ... rest of the bind method ...
        }

        private void updateStarButton(AppCompatButton button, boolean isStarred) {
            if (isStarred) {
                button.setBackgroundResource(likedDrawableId);
            } else {
                button.setBackgroundResource(unlikedDrawableId);
            }
        }
    }
}