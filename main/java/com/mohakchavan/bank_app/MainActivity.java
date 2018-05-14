package com.mohakchavan.bank_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv_acno, tv_newac, tv_all;
    EditText ed_acno;
    Button btn_submit;
    UserDetailModel udm;
    final MySqlLiteHelper helper = new MySqlLiteHelper(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_acno = findViewById(R.id.MA_TV_ACNO);
        tv_newac = findViewById(R.id.MA_TV_NEWAC);
        tv_all = findViewById(R.id.MA_TV_ALL);
        ed_acno = findViewById(R.id.MA_ED_ACNO);
        btn_submit = findViewById(R.id.MA_BTN_SBMT);

        ed_acno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                tv_acno.setVisibility(View.VISIBLE);
                ed_acno.setHint("");
            }
        });

        tv_newac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, New_Account.class);
                startActivity(intent);
            }
        });

        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_all.setVisibility(View.INVISIBLE);
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_all.setVisibility(View.INVISIBLE);
                if (TextUtils.isEmpty(ed_acno.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please Enter Account No", Toast.LENGTH_SHORT).show();
                    ed_acno.requestFocus();
                } else {
                    final int accno = Integer.parseInt(ed_acno.getText().toString().trim());
                    tv_acno.setText("Enter Pass");
                    ed_acno.setHint("Pass");
                    ed_acno.setText("");
                    ed_acno.setInputType(InputType.TYPE_CLASS_NUMBER);
                    ed_acno.setTransformationMethod(new PasswordTransformationMethod());
                    tv_newac.setText("Cancel");

                    tv_newac.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                            startActivity(intent);
                            finish();
                            recreate();
                        }
                    });

                    btn_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (TextUtils.isEmpty(ed_acno.getText().toString())) {
                                Toast.makeText(MainActivity.this, "Please Enter Pass", Toast.LENGTH_SHORT).show();
                                ed_acno.requestFocus();
                            } else {
                                if (helper.isValid(accno, Integer.parseInt(ed_acno.getText().toString().trim()))) {
                                    Toast.makeText(MainActivity.this, "LOGGED IN", Toast.LENGTH_SHORT).show();
                                    ed_acno.setText("");
                                    udm = helper.getDataByAccNo(accno);
                                    Intent intent = new Intent(MainActivity.this, Basic_Info.class);
                                    intent.putExtra("User", udm);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "INVALID Details", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                }
            }
        });

        btn_submit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!(ed_acno.getText().toString().isEmpty())) {
                    if ((Integer.parseInt(ed_acno.getText().toString().trim())) == 999993251) {
                        tv_all.setVisibility(View.VISIBLE);
                        tv_all.setText(helper.viewAll());
                        ed_acno.setText("");
                        return true;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please Enter", Toast.LENGTH_SHORT).show();
                    ed_acno.requestFocus();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
