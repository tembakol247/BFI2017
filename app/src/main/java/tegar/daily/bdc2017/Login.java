package tegar.daily.bdc2017;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Acer on 5/28/2017.
 */

public class Login extends AppCompatActivity {

    Button masuk, bt_daftar, btn_masuk;
    EditText et_nama, et_password;
    ImageView iv_password;
    SessionLogin sessionLogin;
    Boolean visible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        sessionLogin = new SessionLogin(getApplicationContext());
        if (sessionLogin.isLoggedIn()) {
            Intent intent = new Intent(Login.this, HomeActivity.class);
            startActivity(intent);
        }
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_nama.getText().toString().length()>0){
                    if (et_password.getText().toString().length()>=5){
                        loginUser();
                        //Intent intent = new Intent(Activity_Login.this, Activity_Utama.class);
                        //startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "Anda Belum Mengisi Passsword", Toast.LENGTH_LONG).show();
                        et_password.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Anda Belum Mengisi Nama", Toast.LENGTH_LONG).show();
                    et_nama.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });


        iv_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visible.equals(false)){
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_password.setImageResource(R.drawable.unvisible);
                    visible = true;
                }else {
                    iv_password.setImageResource(R.drawable.visible);
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    visible=false;
                }
                et_password.setSelection(et_password.length());
            }
        });
    }

    private void initView(){
        masuk = (Button)findViewById(R.id.btn_masuk);
        btn_masuk = (Button)findViewById(R.id.btn_masuk);
        et_nama = (EditText) findViewById(R.id.et_nama);
        et_password = (EditText) findViewById(R.id.et_password);
        iv_password = (ImageView)findViewById(R.id.iv_password);
    }

    //This method will get data from the web api
    public void loginUser(){
        //Showing a progress dialog
        final ProgressDialog loading;
        loading = new ProgressDialog(this);
        loading.setMessage("Loading...");
        loading.setCancelable(false);
        loading.show();

        //Creating a json array request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.bukalapak.com/v2/authenticate.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Dismissing progress dialog
                        loading.dismiss();
                        Log.d("response", response);
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        //calling method to parse json array
                        parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan Pada Saat Pengambilan Data", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s",et_nama.getText().toString(),et_password.getText().toString());
                String auth = "Basic " + "VGVtYmFrb2wyMDE3OnN1a2VsYWxvMjQ3";
                params.put("Authorization", auth);
                return params;
            }
        };
        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    //This method will parse json data
    private void parseData(String response){

        try {
            JSONObject json = new JSONObject(response);
            String status = json.getString("status");
            String message = json.getString("message");

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            if (status.equals("OK")){
                String iduser = json.getString("user_id");
                String username = json.getString("user_name");
                String token = json.getString("token");
                String email = json.getString("email");
                sessionLogin.createLoginSession(username, iduser, token, email);
                Toast.makeText(getApplicationContext(), message + " " + iduser, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, HomeActivity.class);
                startActivity(intent);
                //Intent intent = new Intent(Activity_Daftar.this, Activity_Login.class);
                //intent.putExtra("idkampanye", idkampanye);
                //startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

