package tegar.daily.bdc2017;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Acer on 5/24/2017.
 */

public class Example {
    /*
    //This method will get data from the web api
    public void editInfo(){

        nama = et_nama.getText().toString();
        alamat = et_alamat.getText().toString();

        //Showing a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(KomunitasDetail.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //Creating a json array request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sabda.esy.es/editkomunitasinfo.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Dismissing progress dialog
                        progressDialog.dismiss();
                        Log.d("response", response);
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        //calling method to parse json array
                        parseEdit(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan Pada Saat Pengambilan Data", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("idkomunitas", idkomunitas);
                params.put("nama", et_nama.getText().toString());
                params.put("alamat", et_alamat.getText().toString());
                params.put("deskripsi", et_deskripsi.getText().toString());
                params.put("visi", et_visi.getText().toString());
                params.put("misi", et_misi.getText().toString());
                params.put("informasi_tambahan", et_informasi_tambahan.getText().toString());
                return params;
            }
        };
        //Creating request queue
        //RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        //requestQueue.add(stringRequest);
        VolleyController.getInstance().addToRequestQueue(stringRequest, "edit_komunitas_info");
    }
    */
}
