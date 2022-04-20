package com.example.quiz_android.placeholder;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class PlaceholderContent {
    public static final List<PlaceholderItem> ITEMS = new ArrayList<>();

    public static class PlaceholderItem {
        public final String id;
        public final String content;

        public PlaceholderItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @NonNull
        @Override
        public String toString() {
            return content;
        }
    }
}