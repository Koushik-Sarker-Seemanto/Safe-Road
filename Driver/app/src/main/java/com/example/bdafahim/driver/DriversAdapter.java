package com.example.bdafahim.driver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.MyViewHolder> {

    private Context mCtx;
    private List<Driver_Info> driverList;

    public DriversAdapter(Context mCtx, List<Driver_Info> driverList) {
        this.mCtx = mCtx;
        this.driverList = driverList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_driver, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Driver_Info driver = driverList.get(position);
        holder.textViewName.setText(driver.getName());
        holder.textViewGenre.setText("Phone: " + driver.getPhoneNo());
        holder.textViewAge.setText("Email: " + driver.getMail());
        holder.textViewAge.setText("Penalty: " + driver.getPoints());
        int p = driver.getPoints();
        if(p>800)
            holder.isSafe.setText("Unsafe Driver");
        else
            holder.isSafe.setText("Safe Driver");
        //holder.textViewCountry.setText("Country: " + artist.country);
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewGenre, textViewAge,textPoints,isSafe;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.nameofDriver);
            textViewGenre = itemView.findViewById(R.id.phoneofDriver);
            textViewAge = itemView.findViewById(R.id.emailofDriver);
            textPoints = itemView.findViewById(R.id.pointsofDriver);
            isSafe = itemView.findViewById(R.id.isSafe);
           // textViewCountry = itemView.findViewById(R.id.text_view_country);
        }
    }

}
