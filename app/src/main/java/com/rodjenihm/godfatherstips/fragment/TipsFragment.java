package com.rodjenihm.godfatherstips.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.rodjenihm.godfatherstips.R;
import com.rodjenihm.godfatherstips.TipViewHolder;
import com.rodjenihm.godfatherstips.TipsFirestoreRecyclerAdapter;
import com.rodjenihm.godfatherstips.UserViewHolder;
import com.rodjenihm.godfatherstips.model.AppUser;
import com.rodjenihm.godfatherstips.model.Tip;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipsFragment extends Fragment {
    private TipsFirestoreRecyclerAdapter adapter;
    private static String ACCESS_LEVEL = "accessLevel";
    private static String ACTIVE = "active";
    private int accessLevel = 1;
    private boolean active = true;

    public TipsFragment() {
        // Required empty public constructor
    }

    public static TipsFragment newInstance(int accessLevel, boolean active) {
        TipsFragment fragment = new TipsFragment();
        Bundle args = new Bundle();
        args.putInt(ACCESS_LEVEL, accessLevel);
        args.putBoolean(ACTIVE, active);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            accessLevel = getArguments().getInt(ACCESS_LEVEL);
            active = getArguments().getBoolean(ACTIVE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tips, container, false);

        Query query;
        if (active) {
            query = FirebaseFirestore.getInstance().collection("tips").whereEqualTo("status", 1);
        } else {
            query = FirebaseFirestore.getInstance().collection("tips").whereGreaterThan("status", 1);
        }

        FirestoreRecyclerOptions<Tip> options = new FirestoreRecyclerOptions.Builder<Tip>()
                .setQuery(query, Tip.class)
                .build();

        adapter = new TipsFirestoreRecyclerAdapter(options);

        RecyclerView tipsList = view.findViewById(R.id.tips_list);
        tipsList.setAdapter(adapter);

        if (accessLevel == 3 && active) {
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    new AlertDialog.Builder(viewHolder.itemView.getContext())
                            .setMessage("Pick tip status")
                            .setPositiveButton("Won", (dialog, id) -> {
                                adapter.archiveItem(position, 2);
                            })
                            .setNegativeButton("Lost", (dialog, id) -> {
                                adapter.archiveItem(position, 3);
                            })
                            .setOnCancelListener(dialog -> adapter.notifyItemChanged(position))
                            .create().show();
                }
            }).attachToRecyclerView(tipsList);
        }

        return view;
    }
}