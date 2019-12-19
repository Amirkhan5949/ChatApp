package com.example.debuapp.utils;

import android.content.Context;
import android.view.Gravity;

import com.example.debuapp.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class Loader {
    DialogPlus dialogPlus;
    Context context;

public Loader(Context context){
    this.context=context;

    dialogPlus = DialogPlus.newDialog(context)
            .setMargin(100,0,100,0)
            .setGravity(Gravity.CENTER)
            .setContentBackgroundResource(R.color.transparent)
            .setContentHolder(new ViewHolder(R.layout.content))
            .setCancelable(false)
            .create();

 }
 public void show(){
  dialogPlus.show();
 }

 public void dismiss(){
    dialogPlus.dismiss();
 }


}
