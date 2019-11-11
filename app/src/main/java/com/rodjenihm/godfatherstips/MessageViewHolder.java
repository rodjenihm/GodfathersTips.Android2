package com.rodjenihm.godfatherstips;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodjenihm.godfatherstips.model.Message;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    public final TextView senderEmailView;
    public final TextView timeView;
    public final TextView textView;
    public Message item;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        senderEmailView = view.findViewById(R.id.sender_email);
        timeView = view.findViewById(R.id.time);
        textView = view.findViewById(R.id.text);
    }

    @Override
    public String toString() {
        return "MessageViewHolder{" +
                "view=" + view +
                ", senderEmailView=" + senderEmailView +
                ", timeView=" + timeView +
                ", textView=" + textView +
                ", item=" + item +
                '}';
    }
}
