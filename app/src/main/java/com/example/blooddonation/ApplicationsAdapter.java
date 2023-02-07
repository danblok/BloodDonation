package com.example.blooddonation;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ApplicationsAdapter extends RecyclerView.Adapter<ApplicationsAdapter.ApplicationViewHolder> {

    private OnApplicationClickListener onApplicationClickListener;

    private List<Application> applications = new ArrayList<>();

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
        notifyDataSetChanged();
    }

    public void setOnApplicationClickListener(OnApplicationClickListener onApplicationClickListener) {
        this.onApplicationClickListener = onApplicationClickListener;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.application_item,
                parent,
                false
        );
        return new ApplicationViewHolder(view);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        Application application = applications.get(position);

        switch (application.getStatus()) {
            case "ACCEPTED":
                holder.textViewStatus.setText(
                        holder.itemView.getContext().getString(R.string.accepted)
                );
                break;
            case "CLOSED":
                holder.textViewStatus.setText(
                        holder.itemView.getContext().getString(R.string.closed)
                );
                break;
        }

        holder.textViewDate.setText(
                new SimpleDateFormat("dd.MM.yyyy").format(application.getDonationDate())
        );
        holder.textViewBloodComponent.setText(application.getBloodComponent());
        holder.textViewDonationType.setText(application.getDonationType());
        holder.textViewDonationPlace.setText(application.getDonationPlace());
        holder.applicationItem.setOnClickListener(v -> {
            if (onApplicationClickListener != null) {
                onApplicationClickListener.onApplicationClick(application);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    interface OnApplicationClickListener {
        void onApplicationClick(Application application);
    }

    static class ApplicationViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewStatus;
        private TextView textViewDate;
        private TextView textViewBloodComponent;
        private TextView textViewDonationType;
        private TextView textViewDonationPlace;
        private MaterialCardView applicationItem;

        public ApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewBloodComponent = itemView.findViewById(R.id.textViewBloodComponent);
            textViewDonationType = itemView.findViewById(R.id.textViewDonationType);
            textViewDonationPlace = itemView.findViewById(R.id.textViewDonationPlace);
            applicationItem = itemView.findViewById(R.id.applicationItem);
        }
    }
}
