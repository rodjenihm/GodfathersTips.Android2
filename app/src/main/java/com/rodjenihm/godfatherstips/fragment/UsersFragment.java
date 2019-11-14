package com.rodjenihm.godfatherstips.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.rodjenihm.godfatherstips.R;
import com.rodjenihm.godfatherstips.UsersFirestoreRecyclerAdapter;
import com.rodjenihm.godfatherstips.model.AppUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {
    private FirestoreRecyclerAdapter adapter;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        Query query = FirebaseFirestore.getInstance().collection("users");
        FirestoreRecyclerOptions<AppUser> options = new FirestoreRecyclerOptions.Builder<AppUser>()
                .setQuery(query, AppUser.class)
                .build();

        adapter = new UsersFirestoreRecyclerAdapter(options);

        RecyclerView usersList = view.findViewById(R.id.users_list);
        usersList.setAdapter(adapter);

        return view;
    }
}
