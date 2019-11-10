package com.rodjenihm.godfatherstips;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodjenihm.godfatherstips.model.Tip;

public class TipViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    public final TextView rivalsView;
    public final TextView timeView;
    public final TextView tipView;
    public final TextView oddsView;
    public final Button archiveView;
    public Tip item;

    public TipViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        rivalsView = view.findViewById(R.id.rivals);
        timeView = view.findViewById(R.id.time);
        tipView = view.findViewById(R.id.tip);
        oddsView = view.findViewById(R.id.odds);
        archiveView = view.findViewById(R.id.archive);
    }

    @Override
    public String toString() {
        return "TipViewHolder{" +
                "view=" + view +
                ", rivalsView=" + rivalsView +
                ", timeView=" + timeView +
                ", tipView=" + tipView +
                ", oddsView=" + oddsView +
                ", archiveView=" + archiveView +
                ", item=" + item +
                '}';
    }
}
