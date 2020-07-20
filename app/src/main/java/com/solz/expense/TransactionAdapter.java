package com.solz.expense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solz.expense.data.AppDatabase;
import com.solz.expense.data.Transaction;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransViewHolder> {

    private List<Transaction> mTransEntries;
    private Context mContext;
    RecyclerViewClickListener mListener;

    public TransactionAdapter(Context mContext, RecyclerViewClickListener listener) {
        this.mContext = mContext;
        mListener = listener;
    }

    @NonNull
    @Override
    public TransViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.trans_view_main, parent, false);
        return new TransViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final TransViewHolder holder, final int position) {

        final Transaction transEntry = mTransEntries.get(position);

        String title = transEntry.getTitle();
        double amount = transEntry.getAmount();
        String am = String.valueOf(amount);
        String date = transEntry.getDate();

        holder.titleTxt.setText(title);
        holder.amountTxt.setText(am);
        holder.dateTxt.setText(date);

    }

    public void setTasks(List<Transaction> transEntries) {
        mTransEntries = transEntries;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mTransEntries == null) {
            return 0;
        }
        return mTransEntries.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position, List<Transaction> transEntries);
    }


    class TransViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTxt;
        TextView amountTxt;
        TextView dateTxt;
        ImageView image;
        RecyclerViewClickListener mListener;
        //View recView;

        public TransViewHolder(@NonNull View itemView, RecyclerViewClickListener listenert) {
            super(itemView);
            mListener = listenert;
            itemView.setOnClickListener(this);


            titleTxt = itemView.findViewById(R.id.decripId);
            amountTxt = itemView.findViewById(R.id.amountId);
            dateTxt = itemView.findViewById(R.id.dateId);
            image = itemView.findViewById(R.id.delete);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(getAdapterPosition());
                }
            });


        }


        //delete entry
        public void removeAt(int position){
            //mTransEntries.remove(position);

            Thread thread = new Thread(){
                @Override
                public void run() {
                    final AppDatabase mDb = AppDatabase.getInstance(mContext.getApplicationContext());
                    mDb.taskDao().deleteTask(mTransEntries.remove(getAdapterPosition()));
                }
            };
            thread.start();
            notifyItemRangeChanged(position,mTransEntries.size());
        }


        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition(), mTransEntries);
        }
    }
}
