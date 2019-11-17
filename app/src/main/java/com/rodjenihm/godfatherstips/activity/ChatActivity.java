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
import com.rodjenihm.godfatherstips.model.AppUser;
import com.rodjenihm.godfatherstips.model.Message;
import com.rodjenihm.godfatherstips.service.AppDatabase;

import java.util.Date;
import java.util.List;


public class ChatActivity extends AppCompatActivity {
    private RecyclerView listOfMessages;
    private MessageRecyclerAdapter adapter;
    private ListenerRegistration registration;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (registration != null) {
            registration.remove();
        }
        AppUser.CURRENT_USER.setLastSeen(new Date());
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
            obj.setMessageId(String.valueOf(obj.hashCode()));

            FirebaseFirestore.getInstance()
                    .collection("messages")
                    .add(obj)
                    .addOnFailureListener(e -> Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());

            input.setText("");
        });

        List<Message> messageList =  AppDatabase.getInstance(this).messageDao().getAll();
        adapter = new MessageRecyclerAdapter(messageList);
        listOfMessages = findViewById(R.id.list_of_messages);
        listOfMessages.setAdapter(adapter);

        Query query = FirebaseFirestore.getInstance()
                .collection("messages")
                .whereGreaterThan("time", AppUser.CURRENT_USER.getLastSeen())
                .orderBy("time", Query.Direction.ASCENDING);

        registration = query.
                addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (dc != null) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Message newMessage = dc.getDocument().toObject(Message.class);
                                AppUser.CURRENT_USER.setLastSeen(newMessage.getTime());
                                AppDatabase.getInstance(this).messageDao().insert(newMessage);
                                messageList.add(newMessage);
                                adapter.notifyDataSetChanged();
                                listOfMessages.scrollToPosition(messageList.size() - 1);
                            }
                        }
                    }
                });
    }
}
