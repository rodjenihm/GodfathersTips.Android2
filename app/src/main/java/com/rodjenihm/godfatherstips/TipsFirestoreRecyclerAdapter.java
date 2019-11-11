package com.rodjenihm.godfatherstips;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.rodjenihm.godfatherstips.model.Tip;

public class TipsFirestoreRecyclerAdapter extends FirestoreRecyclerAdapter<Tip, TipViewHolder> {
    private View view;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TipsFirestoreRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Tip> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TipViewHolder tipViewHolder, int i, @NonNull Tip tip) {
        view = tipViewHolder.view;

        int status = tip.getStatus();

        switch (status) {
            case 1:
                tipViewHolder.view.setBackground(view.getResources().getDrawable(R.drawable.tip_active));
                break;
            case 2:
                tipViewHolder.view.setBackground(view.getResources().getDrawable(R.drawable.tip_won));
                break;
            case 3:
                tipViewHolder.view.setBackground(view.getResources().getDrawable(R.drawable.tip_lost));
                break;
            default:
                break;
        }

        tipViewHolder.item = tip;
        tipViewHolder.rivalsView.setText(tip.getRivals());
        tipViewHolder.timeView.setText(tip.getTime());
        tipViewHolder.tipView.setText(tip.getTip());
        tipViewHolder.oddsView.setText(tip.getOdds());
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tip, parent, false);

        return new TipViewHolder(view);
    }

    public void archiveItem(int position, int value) {

        getSnapshots().getSnapshot(position).getReference()
                .update("status", value)
                .addOnFailureListener(e -> Toast.makeText(view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
    }
}

