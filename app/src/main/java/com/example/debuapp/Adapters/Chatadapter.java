package com.example.debuapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.AlluserFragment.utils.Common;
import com.example.debuapp.Activities.ChatActivity;
import com.example.debuapp.Activities.ImageActivity;
import com.example.debuapp.R;
import com.example.debuapp.UI.Fragment.ProfileFragment;
import com.example.debuapp.utils.FirebaseConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Chatadapter extends RecyclerView.Adapter<Chatadapter.ChatViewHolder> {
    ArrayList arrayList;
    Context context;
    FragmentManager fragmentManager;

    List<String> list;

    public Chatadapter(List<String> list, Context context,FragmentManager fragmentManager){
        this.context=context;
        this.list = list;
        this.fragmentManager=fragmentManager;
    }
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.chat,parent,false);
        return new ChatViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ChatViewHolder holder, final int position) {






        final String id = list.get(position);


        FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String s=""+dataSnapshot.child(FirebaseConstants.User.user).getValue().toString();
                        holder.username.setText(s);




                        final String a=dataSnapshot.child(FirebaseConstants.User.image).getValue().toString();
                        Picasso.get().load(a).into(holder.imageView);
                        Log.i("asaddd", "onDataChange: "+a);


                        holder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                final DialogPlus dialog = DialogPlus.newDialog(context)
                                        .setGravity(Gravity.CENTER)
                                        .setContentHeight(Common.dpToPx(context, 280))
                                        .setContentWidth(Common.dpToPx(context, 260))
                                        .setExpanded(false)
                                        .setContentHolder(new ViewHolder(R.layout.page))
                                        .create();
                                RelativeLayout layout = (RelativeLayout) dialog.getHolderView();
                                ImageView imageView = layout.findViewById(R.id.image);
                                Picasso.get().load(a).into(imageView);

                                TextView text = layout.findViewById(R.id.text);
                                ImageView info = layout.findViewById(R.id.info);


                                text.setText(s);

                                info.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();


                                        replace(new ProfileFragment(id));

                                    }
                                });

                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(context, ImageActivity.class);
                                        intent.putExtra("image", a);
                                        context.startActivity(intent);
                                    }
                                });
                                dialog.show();
                            }
                        });



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChatActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);


            }
        });

        FirebaseDatabase.getInstance().getReference()
                .child("Message")
                .child(FirebaseAuth.getInstance().getUid())
                .child(id)
                .limitToLast(1)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    holder.status.setText(snapshot.child(FirebaseConstants.Message.message).getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView username,status;
        LinearLayout layout;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.png);
            username=itemView.findViewById(R.id.username);
            status=itemView.findViewById(R.id.status);
            layout=itemView.findViewById(R.id.layout);
        }
    }

    void replace(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.addToBackStack("ProfileFragment");
        fragmentTransaction.commit();
    }



}