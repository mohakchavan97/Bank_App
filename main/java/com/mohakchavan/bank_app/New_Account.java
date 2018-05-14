package com.mohakchavan.bank_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class New_Account extends AppCompatActivity {

    EditText ed_name, ed_sname, ed_amt;
    TextView tv_name, tv_sname, tv_amt;
    Button btn_create, btn_cancel;
    private MySqlLiteHelper helper;
    private UserDetailModel udm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        getSupportActionBar().setTitle("Create A New Account");
        ed_amt = findViewById(R.id.NA_ED_AMT);
        ed_name = findViewById(R.id.NA_ED_NAME);
        ed_sname = findViewById(R.id.NA_ED_SURNAME);
        tv_name = findViewById(R.id.NA_TV_NAME);
        tv_sname = findViewById(R.id.NA_TV_SURNAME);
        tv_amt = findViewById(R.id.NA_TV_AMT);
        btn_cancel = findViewById(R.id.NA_BTN_CANCEL);
        btn_create = findViewById(R.id.NA_BTN_CREATE);
        helper = new MySqlLiteHelper(New_Account.this);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMain();
            }
        });

        tv_amt.setVisibility(View.INVISIBLE);
        tv_sname.setVisibility(View.INVISIBLE);
        tv_name.setVisibility(View.INVISIBLE);

        ed_amt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tv_amt.setVisibility(View.VISIBLE);
                ed_amt.setHint("");
                tv_name.setVisibility(View.INVISIBLE);
                tv_sname.setVisibility(View.INVISIBLE);
                if(TextUtils.isEmpty(ed_name.getText().toString()))
                    ed_name.setHint("Name");
                if(TextUtils.isEmpty(ed_sname.getText().toString()))
                    ed_sname.setHint("Surname");
            }
        });

        ed_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tv_name.setVisibility(View.VISIBLE);
                ed_name.setHint("");
                tv_sname.setVisibility(View.INVISIBLE);
                tv_amt.setVisibility(View.INVISIBLE);
                if(TextUtils.isEmpty(ed_sname.getText().toString()))
                    ed_sname.setHint("Surname");
                if(TextUtils.isEmpty(ed_amt.getText().toString()))
                    ed_amt.setHint("Amount");
            }
        });

        ed_sname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tv_sname.setVisibility(View.VISIBLE);
                ed_sname.setHint("");
                tv_name.setVisibility(View.INVISIBLE);
                tv_amt.setVisibility(View.INVISIBLE);
                if(TextUtils.isEmpty(ed_name.getText().toString()))
                    ed_name.setHint("Name");
                if(TextUtils.isEmpty(ed_amt.getText().toString()))
                    ed_amt.setHint("Amount");
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amt = Double.parseDouble(ed_amt.getText().toString().trim());
                if (TextUtils.isEmpty(ed_name.getText().toString())) {
                    Toast.makeText(New_Account.this, "Name NOT Entered", Toast.LENGTH_SHORT).show();
                    ed_name.requestFocus();
                } else if (TextUtils.isEmpty(ed_sname.getText().toString())) {
                    Toast.makeText(New_Account.this, "Surname NOT Entered", Toast.LENGTH_SHORT).show();
                    ed_sname.requestFocus();
                } else if (TextUtils.isEmpty(ed_amt.getText().toString())) {
                    Toast.makeText(New_Account.this, "Amount NOT Entered", Toast.LENGTH_SHORT).show();
                    ed_amt.requestFocus();
                } else if (amt < 100) {
                    Toast.makeText(New_Account.this, "Amount LESS THAN 100", Toast.LENGTH_SHORT).show();
                    ed_amt.requestFocus();
                } else {
                    String name = ed_name.getText().toString().trim();
                    String sname = ed_sname.getText().toString().trim();
                    udm = new UserDetailModel();
                    if (helper.addUser(name, sname, Double.parseDouble(String.format("%.2f", amt)))) {
                        Toast.makeText(New_Account.this, "Account Created", Toast.LENGTH_SHORT).show();
                        udm = helper.getDataByName(name);
                        new AlertDialog.Builder(New_Account.this).setTitle("Remember").setMessage("Account Number: " + udm.getId()
                                + "\nPass: " + udm.getPass()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                gotoMain();
                            }
                        }).show();
                    }
                }
            }
        });
    }

    void gotoMain() {
        Intent intent = new Intent(New_Account.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

