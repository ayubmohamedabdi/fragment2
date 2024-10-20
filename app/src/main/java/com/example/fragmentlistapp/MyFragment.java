package com.example.fragmentlistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Random;

public class MyFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<Item> itemList;
    private Button removeButton;
    private Button addButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String SAVED_LIST_KEY = "savedItemList";

    // Array of image resource IDs
    private int[] imageResIds = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Restore saved state
        if (savedInstanceState != null) {
            itemList = (ArrayList<Item>) savedInstanceState.getSerializable(SAVED_LIST_KEY);
        } else {
            itemList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        initializeViews(view);
        setupRecyclerView();
        swipeRefreshLayout.setEnabled(true);

        setupAddButtonClickListener();
        setupRemoveButtonClickListener();
        setupSwipeRefreshLayout();

        return view;
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        removeButton = view.findViewById(R.id.remove_button);
        addButton = view.findViewById(R.id.add_button);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myAdapter = new MyAdapter(itemList);
        recyclerView.setAdapter(myAdapter);
    }

    private void setupAddButtonClickListener() {
        addButton.setOnClickListener(v -> addItem());
    }

    private void addItem() {
        String newItemText = "New Item " + (itemList.size() + 1);

        // Pick a random image
        int randomImageResId = getRandomImage();

        // Show Toast message when item is added
        Toast.makeText(getContext(), newItemText + " added", Toast.LENGTH_SHORT).show();

        // Add new item with a random image
        itemList.add(new Item(newItemText, randomImageResId));
        myAdapter.notifyItemInserted(itemList.size() - 1);
        recyclerView.smoothScrollToPosition(itemList.size() - 1);
    }

    // Helper method to select a random image
    private int getRandomImage() {
        Random random = new Random();
        return imageResIds[random.nextInt(imageResIds.length)];
    }

    private void setupRemoveButtonClickListener() {
        removeButton.setOnClickListener(v -> {
            if (!itemList.isEmpty()) {
                itemList.remove(itemList.size() - 1);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Last item removed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "No items to remove", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            itemList.clear();
            myAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), "Items cleared", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_LIST_KEY, itemList);
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getContext(), "Items saved", Toast.LENGTH_SHORT).show();
    }
}
