package com.example.debuapp;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.debuapp.utils.FirebaseConstants;
import com.example.debuapp.utils.Loader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
   private ImageView imageView;
   private ImageView edit,editname,editemail,editadd;
   View view;
   private final int PERMISSION_ALL = 1234;
   private Loader loader;
   ImagePicker imagePicker;
   private TextView number,name,email,address;
   DatabaseReference User;



    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_image, container, false);

        imageView=view.findViewById(R.id.imageview);
        edit=view.findViewById(R.id.edit);
        number=view.findViewById(R.id.no);
        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        address=view.findViewById(R.id.address);
        editname=view.findViewById(R.id.editname);
        editemail=view.findViewById(R.id.editemail);
        editadd=view.findViewById(R.id.editadd);

        loader=new Loader(getContext());

        getuserinfo();

        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialog = DialogPlus.newDialog(getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog))
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)

                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                LinearLayout layout=(LinearLayout)dialog.getHolderView();
                final EditText name=layout.findViewById(R.id.name);

                final Button cancel=layout.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                final  Button enter=layout.findViewById(R.id.enter);
                        enter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                Map<String,Object>map=new HashMap<>();
                                map.put(FirebaseConstants.User.user,name.getText().toString());

                                FirebaseDatabase.getInstance().getReference()
                                        .child("User")
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .updateChildren(map).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                ProfileFragment.this.name.setText(name.getText().toString());
                                            }
                                        });


                            }
                        });
                dialog.show();

            }
        });

        editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogemail))
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                LinearLayout layout=(LinearLayout)dialog.getHolderView();
                final EditText email=layout.findViewById(R.id.editmail);

                final Button cancel=layout.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                final Button enter=layout.findViewById(R.id.enter);
                enter.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 dialog.dismiss();
                                                 Map<String,Object>map=new HashMap<>();
                                                 map.put(FirebaseConstants.User.email,email.getText().toString());

                                                 FirebaseDatabase.getInstance().getReference()
                                                         .child("User")
                                                         .child(FirebaseAuth.getInstance().getUid())
                                                         .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<Void> task) {
                                                         ProfileFragment.this.email.setText(email.getText().toString());
                                                     }
                                                 });
                                             }
                                         }
                );
                dialog.show();
            }
        });

        editadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogadd))
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                LinearLayout layout=(LinearLayout)dialog.getHolderView();
                final  EditText add= layout.findViewById(R.id.editadd);

                final Button cancel=layout.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                final Button enter=layout.findViewById(R.id.enter);
                enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Map<String,Object>map=new HashMap<>();
                        map.put(FirebaseConstants.User.address,add.getText().toString());

                        FirebaseDatabase.getInstance().getReference()
                                .child("User")
                                .child(FirebaseAuth.getInstance().getUid())
                                .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                ProfileFragment.this.address.setText(add.getText().toString());
                            }
                        });
                    }
                });

                dialog.show();
            }
        });

        Log.i("nhnnhnghghn", ": done ");


        final String[] PERMISSIONS = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        };

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermissions( getContext(), PERMISSIONS)) {
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
                }
                else {
                    imagePicker.choosePicture(true /*show camera intents*/);

                }

            }
        });

        imagePicker = new ImagePicker(getActivity(), /* activity non null*/
                this, /* fragment nullable*/
                new OnImagePickedListener() {
                    @Override
                    public void onImagePicked(Uri imageUri) {
                        UCrop.of(imageUri, getTempUri())
                                .withAspectRatio(1, 1)
                                .start ((AppCompatActivity) getActivity());


                    }
                }

        );



        return view;
    }

    private  Uri getTempUri(){

        String dri= Environment.getExternalStorageDirectory()+ File.separator+"Temp";

        File drifile= new File(dri);
        drifile.mkdir();

        String file=dri+File.separator+"Temp.png";
        File tempfile=new File(file);
        try {
            tempfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(tempfile);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissionsList, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
        switch (requestCode) {
            case PERMISSION_ALL:{
                if (grantResults.length > 0) {
                    boolean flag=true;
                    for (int i=0;i<permissionsList.length;i++) {
                        if(grantResults[i] == PackageManager.PERMISSION_DENIED){

                            flag=false;
                        }
                    }

                    if (flag=true){
                        imagePicker.choosePicture(true /*show camera intents*/);

                    }
                    // Show permissionsDenied
                }
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode, data);

        Log.i("nhnnhnghghn", "onActivityResult: done ");


        if (resultCode == RESULT_OK  && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            upload(resultUri);


        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            Log.i("failed", "onActivityResult: ");
        }
    }

    void upload(Uri uri) {
        loader.show();
        final StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("Temp/"+System.currentTimeMillis()+".png");

        riversRef.putFile(uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("avani", "onFailure: "+exception.getMessage());
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                loader.dismiss();
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...

                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageView.setImageURI(null);
                        Picasso.get().load(uri).into(imageView);



                    }
                });
            }
        });
    }

    private void  getuserinfo(){
        User=FirebaseDatabase.getInstance().getReference().child("User");

        User.child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String numberm= "Number :"+dataSnapshot.child(FirebaseConstants.User.number).getValue(String.class);
                        number.setText(numberm);

                        String user="User Name :"+dataSnapshot.child(FirebaseConstants.User.user).getValue(String.class);
                        name.setText(user);

                        String email_s="Email :"+dataSnapshot.child(FirebaseConstants.User.email).getValue(String.class);
                        email.setText(email_s);

                        String add="Address :"+dataSnapshot.child(FirebaseConstants.User.address).getValue(String.class);
                        address.setText(add);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
