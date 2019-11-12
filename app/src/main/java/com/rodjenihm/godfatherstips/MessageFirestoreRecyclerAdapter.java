package com.rodjenihm.godfatherstips;

import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.rodjenihm.godfatherstips.model.Message;

public class MessageFirestoreRecyclerAdapter  extends FirestoreRecyclerAdapter<Message, MessageViewHolder> {
    private View view;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MessageFirestoreRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i, @NonNull Message message) {
        view = messageViewHolder.view;
        messageViewHolder.item = message;
        messageViewHolder.senderEmailView.setText(message.getSenderEmail());
        messageViewHolder.timeView.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", message.getTime()));
        messageViewHolder.textView.setText(message.getText());

        if (message.getSenderEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            view.findViewById(R.id.message_container).setBackground(view.getResources().getDrawable(R.drawable.custom_message_owner));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            params.gravity = Gravity.END;
            view.findViewById(R.id.message_container).setLayoutParams(params);
        } else {
            view.findViewById(R.id.message_container).setBackground(view.getResources().getDrawable(R.drawable.custom_message));
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message, parent, false);

        return new MessageViewHolder(view);
    }


}
