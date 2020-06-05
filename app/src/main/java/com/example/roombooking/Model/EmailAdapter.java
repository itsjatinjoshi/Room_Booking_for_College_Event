package com.example.roombooking.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roombooking.R;
import com.example.roombooking.ViewModel.Get.EmailDetails;

import java.util.ArrayList;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> {
    private static final String TAG = "EmailAdapter";
    private ArrayList<EmailDetails> mEmailAttributes;
    private Context mContext;

    private View.OnClickListener itemOnClickListener;

    public void setItemOnClickListener(View.OnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public EmailAdapter(ArrayList<EmailDetails> emailAttributes, Context context) {
        Log.d(TAG, "EmailAdapter: " +"CHECK POINT 1");
        mEmailAttributes = emailAttributes;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: " + "CHECK POINT 2");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + "CHECK POINT 3");
        String firstLetter = mEmailAttributes.get(position).getSenderName();

        holder.tvSenderLetter.setText(String.valueOf(firstLetter.charAt(0)));
        holder.tvSenderName.setText(mEmailAttributes.get(position).getSenderName());
        holder.tvSubject.setText(mEmailAttributes.get(position).getEmailTitle());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + "CHECK POINT 4");
        return mEmailAttributes == null ? 0 : mEmailAttributes.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";
        TextView tvSenderLetter, tvSenderName, tvSubject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: " + "CHECK POINT 5");
            tvSenderLetter = itemView.findViewById(R.id.tvSenderLetter);
            tvSenderName = itemView.findViewById(R.id.tvSenderName);
            tvSubject = itemView.findViewById(R.id.tvSubject);

            itemView.setTag(this);
            itemView.setOnClickListener(itemOnClickListener);
        }
    }

}
