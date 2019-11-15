package com.rodjenihm.godfatherstips;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rodjenihm.godfatherstips.model.AppUser;

public class UsersFirestoreRecyclerAdapter extends FirestoreRecyclerAdapter<AppUser, UserViewHolder> {
    private View view;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UsersFirestoreRecyclerAdapter(@NonNull FirestoreRecyclerOptions<AppUser> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i, @NonNull AppUser user) {
        view = userViewHolder.view;

        userViewHolder.view.setBackground(view.getResources().getDrawable(R.drawable.user));

        String status;
        int accessLevel = user.getAccessLevel();

        switch (accessLevel) {
            case 1:
                status = "MEMBER";
                userViewHolder.switchView.setChecked(false);
                break;
            case 2:
                status = "VIP";
                userViewHolder.switchView.setChecked(true);
                break;
            case 3:
                status = "ADMIN";
                userViewHolder.switchView.setVisibility(View.GONE);
                break;
            default:
                status = "";
                break;
        }

        userViewHolder.item = user;
        userViewHolder.emailView.setText(user.getEmail());
        userViewHolder.createdAtView.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", user.getCreatedAt()));
        userViewHolder.statusView.setText(status);

        userViewHolder.switchView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                DocumentReference userReference = getSnapshots().getSnapshot(i).getReference();
                String uid = userReference.getId();

                if (isChecked) {
                    userReference
                            .update("accessLevel", 2)
                            .addOnSuccessListener(aVoid -> {
                                FirebaseFirestore.getInstance()
                                        .collection("roles")
                                        .document("VIP")
                                        .update("users", FieldValue.arrayUnion(uid));
                            })
                            .addOnFailureListener(e -> Toast.makeText(view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
                } else {
                    userReference
                            .update("accessLevel", 1)
                            .addOnSuccessListener(aVoid -> {
                                FirebaseFirestore.getInstance()
                                        .collection("roles")
                                        .document("VIP")
                                        .update("users", FieldValue.arrayRemove(uid));
                            })
                            .addOnFailureListener(e -> Toast.makeText(view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user, parent, false);

        return new UserViewHolder(view);
    }
}
