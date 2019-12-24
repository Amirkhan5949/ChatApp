package com.example.debuapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Useradapter  extends FirebaseRecyclerAdapter<User, Useradapter.UserviewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Useradapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserviewHolder holder, int position, @NonNull User user) {

        holder.address.setText(user.getAddress());
        holder.email.setText(user.getEmail());

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
        public UserviewHolder(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.address);
            email=itemView.findViewById(R.id.email);
        }
    }
}
