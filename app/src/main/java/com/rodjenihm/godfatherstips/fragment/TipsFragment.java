package com.rodjenihm.godfatherstips.fragment;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.rodjenihm.godfatherstips.UserViewHolder;
import com.rodjenihm.godfatherstips.model.AppUser;
import com.rodjenihm.godfatherstips.model.Tip;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipsFragment extends Fragment {
    private FirestoreRecyclerAdapter adapter;
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

        adapter = new FirestoreRecyclerAdapter<Tip, TipViewHolder>(options) {
            @Override
            public void onBindViewHolder(TipViewHolder holder, int position, Tip model) {
                int status = model.getStatus();

                switch (status) {
                    case 1:
                        holder.view.setBackground(getResources().getDrawable(R.drawable.tip_active));
                        if (accessLevel == 3) {
                            holder.archiveView.setVisibility(View.VISIBLE);
                            holder.archiveView.setOnClickListener(v -> {
                                String[] options = {"won", "lost"};

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Pick tip status");
                                builder.setItems(options, (dialog, which) -> {
                                    String tipId = holder.item.getTipId();
                                    FirebaseFirestore.getInstance()
                                            .collection("tips")
                                            .document(tipId)
                                            .update("status", which + 2)
                                            .addOnFailureListener(e -> Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
                                });
                                builder.show();
                            });
                        }
                        break;
                    case 2:
                        holder.view.setBackground(getResources().getDrawable(R.drawable.tip_won));
                        holder.archiveView.setVisibility(View.GONE);
                        break;
                    case 3:
                        holder.view.setBackground(getResources().getDrawable(R.drawable.tip_lost));
                        holder.archiveView.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }

                holder.item = model;
                holder.rivalsView.setText(model.getRivals());
                holder.timeView.setText(model.getTime());
                holder.tipView.setText(model.getTip());
                holder.oddsView.setText(model.getOdds());
            }

            @Override
            public TipViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.tip, group, false);

                return new TipViewHolder(view);
            }
        };

        RecyclerView usersList = view.findViewById(R.id.tips_list);
        usersList.setAdapter(adapter);

        return view;
    }

}
