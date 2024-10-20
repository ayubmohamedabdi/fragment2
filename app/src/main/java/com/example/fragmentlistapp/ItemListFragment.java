package com.example.fragmentlistapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class ItemListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ArrayList<String> itemList;
    private Button addButton, removeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        removeButton = view.findViewById(R.id.remove_button);

        // Initialize the item list
        itemList = new ArrayList<>();

        // Set up RecyclerView
        itemAdapter = new ItemAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(itemAdapter);

        // Add Button functionality
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.add("New Item " + (itemList.size() + 1));
                itemAdapter.notifyDataSetChanged();
            }
        });

        // Remove Button functionality
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemList.isEmpty()) {
                    itemList.remove(itemList.size() - 1);
                    itemAdapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        // Save changes when fragment is no longer visible (e.g., save to SharedPreferences or database)
        saveItemList();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Load saved items when fragment is visible again
        loadItemList();
    }

    private void saveItemList() {
        // Implement saving logic, like saving to SharedPreferences
    }

    private void loadItemList() {
        // Implement loading logic, like loading from SharedPreferences
    }
}
