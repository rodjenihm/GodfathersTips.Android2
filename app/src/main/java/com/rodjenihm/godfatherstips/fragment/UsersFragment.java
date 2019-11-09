package com.rodjenihm.godfatherstips.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.rodjenihm.godfatherstips.UserViewHolder;
import com.rodjenihm.godfatherstips.model.AppUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {


    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        Query query = FirebaseFirestore.getInstance().collection("users");
        FirestoreRecyclerOptions<AppUser> options = new FirestoreRecyclerOptions.Builder<AppUser>()
                .setLifecycleOwner(this)
                .setQuery(query, AppUser.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<AppUser, UserViewHolder>(options) {
            @Override
            public void onBindViewHolder(UserViewHolder holder, int position, AppUser model) {
                String status;
                int accessLevel = model.getAccessLevel();

                switch (accessLevel) {
                    case 1:
                        status = "MEMBER";
                        holder.switchView.setVisibility(View.VISIBLE);
                        holder.switchView.setChecked(false);
                        break;
                    case 2:
                        status = "VIP";
                        holder.switchView.setVisibility(View.VISIBLE);
                        holder.switchView.setChecked(true);
                        break;
                    case 3:
                        status = "ADMIN";
                        break;
                    default:
                        status = "";
                        break;
                }

                holder.item = model;
                holder.emailView.setText(model.getEmail());
                holder.createdAtView.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getCreatedAt()));
                holder.statusView.setText(status);
                holder.switchView.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    String Uid = model.getUserId();
                    if (isChecked) {
                        FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(Uid)
                                .update("accessLevel", 2)
                                .addOnSuccessListener(aVoid -> {
                                    FirebaseFirestore.getInstance()
                                            .collection("roles")
                                            .document("VIP")
                                            .update("users", FieldValue.arrayUnion(Uid))
                                            .addOnFailureListener(e -> Toast.makeText(holder.view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
                                });
                    } else {
                        FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(Uid)
                                .update("accessLevel", 1)
                                .addOnSuccessListener(aVoid -> {
                                    FirebaseFirestore.getInstance()
                                            .collection("roles")
                                            .document("VIP")
                                            .update("users", FieldValue.arrayRemove(Uid))
                                            .addOnFailureListener(e -> Toast.makeText(holder.view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
                                });
                    }
                });
            }

            @Override
            public UserViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.user, group, false);

                return new UserViewHolder(view);
            }
        };

        RecyclerView usersList = view.findViewById(R.id.users_list);
        usersList.setAdapter(adapter);

        return view;
    }
}
