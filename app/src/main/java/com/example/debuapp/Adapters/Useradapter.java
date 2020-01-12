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
import com.example.debuapp.Activities.ImageActivity;
import com.example.Model.User;
import com.example.debuapp.Activities.ChatActivity;
import com.example.debuapp.R;
import com.example.debuapp.UI.Fragment.ProfileFragment;
import com.example.debuapp.utils.FirebaseConstants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Useradapter extends FirebaseRecyclerAdapter<User, Useradapter.UserviewHolder> {


    Context context;
    FragmentManager fragmentManager;

    public Useradapter(Context context, @NonNull FirebaseRecyclerOptions<User> options, FragmentManager fragmentManager) {
        super(options);
        this.fragmentManager = fragmentManager;
        this.context = context;
    }





    @Override
    protected void onBindViewHolder(@NonNull final UserviewHolder holder, final int position, @NonNull final User user) {

        Log.i("frfrg", "onBindViewHolder: "+user.toString());


        final String s = user.getImage();
        holder.address.setText(user.getUser());
        holder.email.setText(user.getEmail());
        Picasso.get().load(s).into(holder.circleImageView);
        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
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


                Picasso.get().load(s).into(imageView);

                final TextView text = layout.findViewById(R.id.text);
                ImageView info = layout.findViewById(R.id.info);


                text.setText(user.getUser());


                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        String id = getRef(position).getKey();

                        replace(new ProfileFragment(id));
                    }
                });

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, ImageActivity.class);
                        intent.putExtra("image", s);
                        context.startActivity(intent);
                    }
                });

                getRef(position).getKey().toString();

                dialog.show();


            }
        });


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("id", getRef(position).getKey());
                context.startActivity(intent);


            }
        });


    }

    @NonNull
    @Override
    public UserviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user, parent, false);

        return new UserviewHolder(view);
    }

    class UserviewHolder extends RecyclerView.ViewHolder {
        TextView address, email;
        CircleImageView circleImageView;
        LinearLayout layout;

        public UserviewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            email = itemView.findViewById(R.id.email);
            circleImageView = itemView.findViewById(R.id.image);
            layout = itemView.findViewById(R.id.linear);
        }
    }

    void replace(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.addToBackStack("ProfileFragment");
        fragmentTransaction.commit();
    }
}