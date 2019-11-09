package com.rodjenihm.godfatherstips;


import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodjenihm.godfatherstips.model.AppUser;

public class UserViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    public final TextView emailView;
    public final TextView createdAtView;
    public final TextView statusView;
    public final Switch switchView;
    public AppUser item;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        emailView = view.findViewById(R.id.email);
        createdAtView = view.findViewById(R.id.created_at);
        statusView = view.findViewById(R.id.status);
        switchView = view.findViewById(R.id.toggle_vip);
    }

    @Override
    public String toString() {
        return "UserViewHolder{" +
                "view=" + view +
                ", emailView=" + emailView +
                ", createdAtView=" + createdAtView +
                ", statusView=" + statusView +
                ", switchView=" + switchView +
                ", item=" + item +
                '}';
    }
}
