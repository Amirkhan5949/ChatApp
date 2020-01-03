package com.example.debuapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.Model.Message;
import com.example.debuapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends FirebaseRecyclerAdapter<Message, MessageAdapter.MessageViewHolder>{
    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param context
     * @param options
     */
    public MessageAdapter(Context context, @NonNull FirebaseRecyclerOptions<Message> options) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message model) {

        holder.send.setText(model.getMessage());
        holder.recieve.setText(model.getMessage());


        if (model.getType().equals(FirebaseConstants.Message.Type.SEND)){
            holder.send.setText(holder.send.getText().toString());
            holder.recieve.setVisibility(View.GONE);
            holder.send.setVisibility(View.VISIBLE);

        }
        else {
            holder.recieve.setText((holder.recieve.getText().toString()));
            holder.send.setVisibility(View.GONE);
            holder.recieve.setVisibility(View.VISIBLE);
        }

    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.screen,parent,false);
        return new MessageViewHolder(view);
    }


    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView send,recieve;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            send=itemView.findViewById(R.id.send);
            recieve=itemView.findViewById(R.id.recieve);

        }
    }

}
