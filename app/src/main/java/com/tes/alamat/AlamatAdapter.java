package com.tes.alamat;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AlamatAdapter extends RecyclerView.Adapter<AlamatAdapter.MovieVH> {
    private List<Alamat> dataResults = new ArrayList<>();
    AlamatActivity.ListDialog1 listDialog1;
    public AlamatAdapter(AlamatActivity.ListDialog1 listDialog1) {
        this.listDialog1=listDialog1;
    }


    @NonNull
    @Override
    public MovieVH onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_demo1, parent, false);
        return new MovieVH (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVH holder, int position) {
        final Alamat result = dataResults.get(position);
        System.out.println("result = "+new Gson().toJson(result));
        holder.kec.setText("Kecamatan : "+result.kecamatan);
        holder.kel.setText("Kelurahan : "+result.kelurahan);
        holder.kpos.setText("KodePos : "+result.kodepos);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a= "Kecamatan : "+result.getKecamatan()+"\n"
                        +"Kelurahan : "+result.getKelurahan()+"\n"
                        +"KodePos : "+result.getKodepos();
                AlamatActivity.setAlamat(a);
                listDialog1.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataResults == null ? 0 : dataResults.size();
    }

    public void addAll(List<Alamat> alamat) {
        System.out.println("result addAll= "+alamat.size());
        for (int i=0; i<alamat.size(); i++)  {
            add(alamat.get(i));
        }
    }

    private void add(Alamat alamat) {
//        System.out.println("result r= "+new Gson().toJson(alamat));
        dataResults.add(alamat);
        notifyItemInserted(dataResults.size() - 1);
    }

    public void newrecord() {
        dataResults.clear();
    }

    public class MovieVH extends RecyclerView.ViewHolder {
        TextView kec,kel,kpos;
        CardView cv;
        public MovieVH(@NonNull View itemView) {
            super(itemView);
            cv =  itemView.findViewById(R.id.cv);
            kec = itemView.findViewById(R.id.kecamatan);
            kel = itemView.findViewById(R.id.kelurahan);
            kpos = itemView.findViewById(R.id.kodepos);
        }
    }
}
