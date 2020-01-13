package com.example.debuapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.Model.Message;
import com.example.debuapp.R;
import com.example.debuapp.utils.FirebaseConstants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends FirebaseRecyclerAdapter<Message, MessageAdapter.MessageViewHolder>{
    Context context;
    String id;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param context
     * @param options
     */
    public MessageAdapter(Context context, @NonNull FirebaseRecyclerOptions<Message> options,String id) {
        super(options);
        this.context=context;
        this.id = id;

    }

    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, final int position, @NonNull Message model) {


        if (model.getType().equals("SEND")){

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            String dateString = formatter.format(new Date(model.getTime_stemp()));

            holder.sendText.setText(model.getMessage());
            holder.send_Time.setText(dateString);





            holder.recieve.setVisibility(View.GONE);
            holder.send.setVisibility(View.VISIBLE);




        }

        else {
            Log.i("askjbda", "onBindViewHolder: recieve");

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            String dateString = formatter.format(new Date(model.getTime_stemp()));

            holder.recieve_Time.setText(dateString);
            holder.recieveText.setText(model.getMessage());
            holder.recieveText.setText((model.getMessage()));

            holder.send.setVisibility(View.GONE);
            holder.recieve.setVisibility(View.VISIBLE);


        }
        if (model.getType().equals("RECEIVE")&&model.getStatus()==false){
            Log.i("dsfccz", "onBindViewHolder: ");
            HashMap<String,Object>map=new HashMap<>();
            map.put(FirebaseConstants.Message.status,true);
            FirebaseDatabase.getInstance().getReference()
                    .child("Message")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child(id)
                    .child(getRef(position).getKey())
                    .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override

                public void onComplete(@NonNull Task<Void> task) {
                    Log.i("sdsd", "onComplete: ");
                    HashMap<String,Object>map=new HashMap<>();
                    map.put(FirebaseConstants.Message.status,true);
                    FirebaseDatabase.getInstance().getReference()
                            .child("Message")
                            .child(id)
                            .child(FirebaseAuth.getInstance().getUid())
                            .child(getRef(position).getKey())
                            .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });



    }

        if (model.getStatus()==false){
            holder.onetick.setVisibility(View.VISIBLE);
            holder.twotick.setVisibility(View.GONE);
            Log.i("jhjgj", "onBindViewHolder: asdadaxa");
        }
        else {
            holder.twotick.setVisibility(View.VISIBLE);
            holder.onetick.setVisibility(View.GONE);
            Log.i("jhjgj", "onBindViewHolder: dadxxzx ");
        }
        Log.i("sadd", "onBindViewHolder: "+model.getStatus());



    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.screen,parent,false);
        return new MessageViewHolder(view);
    }


    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView sendText,recieveText,send_Time,recieve_Time;
        ImageView onetick,twotick;
        LinearLayout  send,recieve;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            send=itemView.findViewById(R.id.send);
            recieve=itemView.findViewById(R.id.recieve);
            sendText=itemView.findViewById(R.id.sendText);
            recieveText=itemView.findViewById(R.id.recieveText);
            send_Time=itemView.findViewById(R.id.send_time);
            recieve_Time=itemView.findViewById(R.id.recieve_time);
            onetick=itemView.findViewById(R.id.onetick);
            twotick=itemView.findViewById(R.id.twotick);

        }
    }

}
