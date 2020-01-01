package com.example.debuapp;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.AlluserFragment.utils.Common;
import com.example.ImageActivity;
import com.example.Model.User;
import com.example.NewActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Useradapter  extends FirebaseRecyclerAdapter<User, Useradapter.UserviewHolder> {
    Context context;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Useradapter(Context context,@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
        this.context=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull UserviewHolder holder, final int position, @NonNull final User user) {
        final String s = "https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg";
        holder.address.setText(user.getAddress());
        holder.email.setText(user.getEmail());
        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setContentHeight(Common.dpToPx(context,280))
                        .setContentWidth(Common.dpToPx(context,260))
                        .setExpanded(false)
                        .setContentHolder(new ViewHolder(R.layout.page))
                        .create();
                RelativeLayout layout = (RelativeLayout)dialog.getHolderView();
                ImageView imageView = layout.findViewById(R.id.image);


                Picasso.get().load(s).into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ImageActivity.class);
                        intent.putExtra("image",s);
                        context.startActivity(intent);
                    }
                });


                dialog.show();




            }
        });



       holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, NewActivity.class);
                intent.putExtra("id",getRef(position).getKey());
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
        TextView address,email;
        CircleImageView circleImageView;
        LinearLayout layout;
        public UserviewHolder(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.address);
            email=itemView.findViewById(R.id.email);
            circleImageView=itemView.findViewById(R.id.image);
            layout=itemView.findViewById(R.id.linear);
        }
    }
}