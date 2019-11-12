package com.rodjenihm.godfatherstips;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rodjenihm.godfatherstips.activity.ChatActivity;
import com.rodjenihm.godfatherstips.model.Message;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    private static int MESSAGE_SENT = 0;
    private static int MESSAGE_RECEIVED = 1;

    private View view;
    private List<Message> messageList = new ArrayList<>();

    public MessageRecyclerAdapter() {
    }

    public MessageRecyclerAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = viewType == MESSAGE_SENT ? LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_sent, parent, false) : LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_received, parent, false);

        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int position) {
        Message message = messageList.get(position);
        view = messageViewHolder.view;
        messageViewHolder.item = message;
        messageViewHolder.senderEmailView.setText(message.getSenderEmail());
        messageViewHolder.timeView.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", message.getTime()));
        messageViewHolder.textView.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message item = messageList.get(position);
        return item.getSenderEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) ? MESSAGE_SENT : MESSAGE_RECEIVED;
    }
}
