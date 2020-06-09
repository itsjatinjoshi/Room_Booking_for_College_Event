package com.example.roombooking.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roombooking.R;
import com.example.roombooking.ViewModel.Get.Details;

import java.util.ArrayList;

public class SendRequestAdapter extends RecyclerView.Adapter<SendRequestAdapter.ViewHolder> {

    ArrayList<Details> mDetailsArrayList;
    Context mContext;
    private View.OnClickListener itemClick;

    public SendRequestAdapter(ArrayList<Details> detailsArrayList, Context context) {
        mDetailsArrayList = detailsArrayList;
        mContext = context;
    }

    public void setItemClick(View.OnClickListener itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String firstLetter = mDetailsArrayList.get(position).getPURPOSEOFVANUE();

        holder.tvvanueFirstLetter.setText(String.valueOf(firstLetter.charAt(0)));
        holder.tvReason.setText(mDetailsArrayList.get(position).getPURPOSEOFVANUE());
        holder.tvRoomType.setText(mDetailsArrayList.get(position).getROOMTYPE());
    }

    @Override
    public int getItemCount() {
        if (mDetailsArrayList.size() != 0) {
            return mDetailsArrayList.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvvanueFirstLetter, tvReason, tvRoomType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvvanueFirstLetter = itemView.findViewById(R.id.tvSenderLetter);
            tvReason = itemView.findViewById(R.id.tvSenderName);
            tvRoomType = itemView.findViewById(R.id.tvSubject);

            itemView.setTag(this);
            itemView.setOnClickListener(itemClick);
        }
    }
}
