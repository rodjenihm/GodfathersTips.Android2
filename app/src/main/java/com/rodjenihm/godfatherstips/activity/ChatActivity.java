package com.rodjenihm.godfatherstips.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.rodjenihm.godfatherstips.MessageFirestoreRecyclerAdapter;
import com.rodjenihm.godfatherstips.R;
import com.rodjenihm.godfatherstips.model.Message;

import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private MessageFirestoreRecyclerAdapter adapter;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view1 -> {
            EditText input = findViewById(R.id.input);

            Message obj = new Message();
            obj.setText(input.getText().toString());
            obj.setTime(new Date());
            obj.setSenderEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());

            FirebaseFirestore.getInstance()
                    .collection("messages")
                    .add(obj)
                    .addOnFailureListener(e -> Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());

            input.clearFocus();
            input.setText("");
        });

        Query query = FirebaseFirestore.getInstance().collection("messages");
        FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build();

        adapter = new MessageFirestoreRecyclerAdapter(options);

        RecyclerView listOfMessages = findViewById(R.id.list_of_messages);
        listOfMessages.setAdapter(adapter);
    }
}
