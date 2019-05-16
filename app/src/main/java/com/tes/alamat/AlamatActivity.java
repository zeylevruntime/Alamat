package com.tes.alamat;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tes.alamat.Constants.isOnline;
import static com.tes.alamat.Constants.showSnackbarConnection;

public class AlamatActivity extends AppCompatActivity implements View.OnClickListener {
TextInputLayout prov,kab,kec,kel,kpos,alamat;
static TextInputEditText et_prov,et_kab,et_kec,et_kel,et_kpos,et_alamat;
ProgressBar footer;
ArrayList<String> kode,province_name;
ArrayList<String> kab_kode,kab_name;
DetailAlamat da = new DetailAlamat();
Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamat);
        kode = new ArrayList<String>();
        province_name = new ArrayList<String>();
        kab_kode = new ArrayList<String>();
        kab_name = new ArrayList<String>();
        setToolbar("Shipping Address");
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(this);
        alamat=findViewById(R.id.alamat);
        footer=findViewById(R.id.footer);
        prov=findViewById(R.id.prov);
        kab=findViewById(R.id.kab);
        kec=findViewById(R.id.kec);
//        kel=findViewById(R.id.kel);
//        kpos=findViewById(R.id.kpos);
        et_alamat=findViewById(R.id.et_alamat);
        et_prov=findViewById(R.id.et_prov);
        et_kab=findViewById(R.id.et_kab);
        et_kec=findViewById(R.id.et_kec);
//        et_kel=findViewById(R.id.et_kel);
//        et_kpos=findViewById(R.id.et_kpos);
        et_kab.setVisibility(View.GONE);
        et_kec.setVisibility(View.GONE);
//        et_kel.setVisibility(View.GONE);
//        et_kpos.setVisibility(View.GONE);
        footer.setVisibility(View.GONE);
        et_prov.setOnClickListener(this);

//        et_kel.setOnClickListener(this);
//        et_kpos.setOnClickListener(this);
        getProvList();
    }

    public class ListDialog extends Dialog {

        private ListView list;
        private EditText filterText = null;
        ArrayAdapter<String> adapter = null;
        private static final String TAG = "ProvList";

        public ListDialog(Context context, final ArrayList<String> List, String title, final int init) {
            super(context);
            setContentView(R.layout.citylistview);
            this.setTitle(title);
            filterText =  findViewById(R.id.EditBox);
            filterText.addTextChangedListener(filterTextWatcher);
            list =  findViewById(R.id.List);
            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, List);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Log.d(TAG, "Selected Item is = "+list.getItemAtPosition(position));
                    int b=List.indexOf(list.getItemAtPosition(position));

                    System.out.println("index="+kode.get(b));
                    if (init==1){
                        getKabList(kode.get(b));
                        et_prov.setText(String.valueOf(list.getItemAtPosition(position)));
                        kab.setVisibility(View.VISIBLE);
                        et_kab.setVisibility(View.VISIBLE);
                    }
                    else if(init==2){
                        getKecList(kab_kode.get(b));
                        et_kab.setText(String.valueOf(list.getItemAtPosition(position)));
                        kec.setVisibility(View.VISIBLE);
                        et_kec.setVisibility(View.VISIBLE);
                    }
                    dismiss();
                }
            });
        }

        private TextWatcher filterTextWatcher = new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                adapter.getFilter().filter(s);
            }
        };
        @Override
        public void onStop(){
            filterText.removeTextChangedListener(filterTextWatcher);
        }}
    public class ListDialog1 extends Dialog {

        private RecyclerView list1;
        private EditText filterText = null;
        AlamatAdapter adapter1;

        public ListDialog1(Context context, final List<Alamat> alamat, String title) {
            super(context);
            setContentView(R.layout.citylistview);
            this.setTitle(title);
            filterText =  findViewById(R.id.EditBox);
//            filterText.addTextChangedListener(filterTextWatcher);
            System.out.println("param"+new Gson().toJson(alamat));
            list1 =  findViewById(R.id.List1);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false);
            list1.setLayoutManager(layoutManager);
            adapter1 = new AlamatAdapter(ListDialog1.this);
            list1.setAdapter(adapter1);
            adapter1.newrecord();
            adapter1.addAll(alamat);

//            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, List);
            /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    int b=List.indexOf(list.getItemAtPosition(position));
                    System.out.println("index="+kode.get(b));
                    if (init==1){
                        getKabList(kode.get(b));
                        et_prov.setText(String.valueOf(list.getItemAtPosition(position)));
                        kab.setVisibility(View.VISIBLE);
                        et_kab.setVisibility(View.VISIBLE);
                    }
                    else if(init==2){
                        getKecList(kab_kode.get(b));
                        et_kab.setText(String.valueOf(list.getItemAtPosition(position)));
                        kec.setVisibility(View.VISIBLE);
                        et_kec.setVisibility(View.VISIBLE);
                    }
                    dismiss();
                }
            });*/
        }
        /*private TextWatcher filterTextWatcher = new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                adapter.getFilter().filter(s);
            }
        };
        @Override
        public void onStop(){
            filterText.removeTextChangedListener(filterTextWatcher);
        }*/
    }
    public static void setAlamat(String alamat){
        et_kec.setText(alamat);
    }
    private void getKecList(String b) {
        String url_kec = "/kota_kab/"+b+".json";
        if (isOnline(getApplicationContext())){
        }
        else {
            showSnackbarConnection(getApplicationContext());
            return;
        }
        footer.setVisibility(View.VISIBLE);
        ApiServ service = ServiceGenerator.createService(ApiServ.class);
        Call<String> call = service.get(url_kec);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                footer.setVisibility(View.GONE);
                kab.setVisibility(View.VISIBLE);
                et_kec.setOnClickListener(AlamatActivity.this);
                System.out.println("res.raw()= "+response.raw());
                System.out.println("res="+response.body());
                String res;
                res=response.body();
                if (res!=null){
                    res="{\"alamat\":"+res+"}";
                    System.out.println("resq"+res);
                    da = new Gson().fromJson(res,DetailAlamat.class);

                    if (da!=null){
                        et_kec.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Constants.showSnackbarConnection(AlamatActivity.this);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Constants.showSnackbarConnection(AlamatActivity.this);
            }
        });
    }
    public void setToolbar(String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        TextView title;
        ImageView back;
        title = findViewById(R.id.title);
        title.setText(name.toUpperCase());
        back = findViewById(R.id.back);
        back.setVisibility(View.GONE);

    }
    private void getKabList(String b) {
        String url_kab = "/list_kotakab/"+b+".json";
        if (isOnline(getApplicationContext())){
        }
        else {
            showSnackbarConnection(getApplicationContext());
            return;
        }
        footer.setVisibility(View.VISIBLE);
        ApiServ service = ServiceGenerator.createService(ApiServ.class);
        Call<String> call = service.get(url_kab);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                footer.setVisibility(View.GONE);
                kab.setVisibility(View.VISIBLE);
                System.out.println("res.raw()= "+response.raw());
                System.out.println("res="+response.body());
                String res;
                res=response.body().replace("\"","");
                if (res!=null){
                    et_kab.setOnClickListener(AlamatActivity.this);
                    res=res.replace("{","");
                    res=res.replace("}","");
                    String[] output = res.split(",");
                    for (int i=0;i<output.length;i++){
//                        System.out.println(output[i]);
                        String[] output1 = output[i].split(":");
                        System.out.println("kode="+output1[0]);
                        System.out.println("kab="+output1[1]);
                        kab_kode.add(output1[0]);
                        kab_name.add(output1[1]);
                    }
                }
                else {
                    Constants.showSnackbarConnection(AlamatActivity.this);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Constants.showSnackbarConnection(AlamatActivity.this);
            }
        });
    }
    private void getProvList() {
        if (isOnline(getApplicationContext())){
        }
        else {
            showSnackbarConnection(getApplicationContext());
            return;
        }
        footer.setVisibility(View.VISIBLE);
        ApiServ service = ServiceGenerator.createService(ApiServ.class);
        Call<String> call = service.getProvince();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                footer.setVisibility(View.GONE);
                kab.setVisibility(View.VISIBLE);
                System.out.println("res.raw()= "+response.raw());
                System.out.println("res="+response.body());
                String res;
                res=response.body().replace("\"","");
                if (res!=null){
                    res=res.replace("{","");
                    res=res.replace("}","");
//                res.replace(":","");
                    String[] output = res.split(",");
                    for (int i=0;i<output.length;i++){
//                        System.out.println(output[i]);
                        String[] output1 = output[i].split(":");
//                        System.out.print(output1[0]);
//                        System.out.println(output1[1]);
                        kode.add(output1[0]);
                        province_name.add(output1[1]);
                    }

                }
                else {
                    Constants.showSnackbarConnection(AlamatActivity.this);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Constants.showSnackbarConnection(AlamatActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.et_prov){
            String banding = et_prov.getText().toString().trim();
            if (province_name!=null){
                new ListDialog(AlamatActivity.this,province_name,"Pilih Provinsi",1).show();
            }
            else {
                getProvList();
            }
        }
        else if (v.getId()==R.id.et_kab){
            if (kab_name!=null){

                new ListDialog(AlamatActivity.this,kab_name,"Pilih Kabupaten/Kota",2).show();
            }
        }
        else if (v.getId()==R.id.et_kec){
            new ListDialog1(AlamatActivity.this,da.alamat,"Pilih Kecamatan,Kelurahan,Kode Pos").show();

        }else if (v.getId()==R.id.submit){
            Toast.makeText(AlamatActivity.this,"Data Terkirim",Toast.LENGTH_LONG);
        }
    }




}
