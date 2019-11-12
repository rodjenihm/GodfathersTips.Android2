package com.rodjenihm.godfatherstips.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.rodjenihm.godfatherstips.MessageRecyclerAdapter;
import com.rodjenihm.godfatherstips.R;
import com.rodjenihm.godfatherstips.model.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ChatActivity extends AppCompatActivity {
    private MessageRecyclerAdapter adapter;
    private ListenerRegistration registration;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        registration.remove();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view1 -> {
            EditText input = findViewById(R.id.input);

            if (input.getText().toString().trim().length() <= 0) {
                return;
            }

            Message obj = new Message();
            obj.setText(input.getText().toString());
            obj.setTime(new Date());
            obj.setSenderEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());

            FirebaseFirestore.getInstance()
                    .collection("messages")
                    .add(obj)
                    .addOnFailureListener(e -> Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());

            input.setText("");
        });

        List<Message> messageList = new ArrayList<>();
        adapter = new MessageRecyclerAdapter(messageList);
        RecyclerView listOfMessages = findViewById(R.id.list_of_messages);
        listOfMessages.setAdapter(adapter);

        Query query = FirebaseFirestore.getInstance().collection("messages").orderBy("time", Query.Direction.ASCENDING);

        registration = query.
                addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc != null) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                messageList.add(doc.getDocument().toObject(Message.class));
                                adapter.notifyDataSetChanged();
                                listOfMessages.scrollToPosition(messageList.size() - 1);
                            }
                        }
                    }
                });
    }
}
