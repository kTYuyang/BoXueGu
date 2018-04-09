package com.yuyang.boxuegu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.boxuegu.R;
import com.yuyang.boxuegu.utils.MD5Utils;

import static com.yuyang.boxuegu.utils.MD5Utils.md5;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_register; //注册按钮
    private EditText et_psw;
    private EditText et_psw_again;
    private TextView tv_back;  //返回按钮
    private RelativeLayout rl_title_bar;
    private EditText et_user_name;
    private String userName;
    private String psw;
    private String pwdAgain;
    private TextView tv_main_title; //标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        //从main_title_bar.xml页面布局中获取对应的UI控件
        TextView tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_back = (TextView) findViewById(R.id.tv_back);
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);

        //从activity-register.xml页面布局获取对应控件
        btn_register = (Button) findViewById(R.id.btn_register);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_psw = (EditText) findViewById(R.id.et_psw);
        et_psw_again = (EditText) findViewById(R.id.et_psw_again);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在响应控件的字符串
                getEditString();

                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(pwdAgain)) {
                    Toast.makeText(RegisterActivity.this,"请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!psw.equals(pwdAgain)){
                    Toast.makeText(RegisterActivity.this,"请输入相同密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (isExitUserName(userName)){
                    Toast.makeText(RegisterActivity.this,"此账户名已经存在", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //把用户名和密码保存到Sharepreference里面
                    saveRegisterInfo(userName, psw);
                    //注册成功把用户名传入到LoginActivity.java中
                    Intent data = new Intent();
                    data.putExtra("userName",userName);
                    setResult(RESULT_OK, data);
                    RegisterActivity.this.finish();

                }

            }
        });

    }

    private void saveRegisterInfo(String userName, String pwd) {
        String md5Psw = MD5Utils.md5(pwd);  //把密码用MD5加密
        //loginInfo是sp的文件名
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();  //获取sp的编辑器
        //userName作为key，密码作为value
        editor.putString(userName, md5Psw);
        editor.commit();  //提交修改

    }

    /**
     * 从sharedpreferences中读取输入的用户名，判断sharedpreferences中判断是否有此用户名
     * @param userName
     * @return
     */
    private boolean isExitUserName(String userName) {
        boolean has_userName = false;
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String spPwd = sp.getString("userName", "");
        if (!TextUtils.isEmpty(spPwd)){
            has_userName = true;
        }

        return has_userName;
    }

    /*
    *
    * 获取控件种的字符串
    * */
    private void getEditString() {

        userName = et_user_name.getText().toString().trim();
        psw = et_psw.getText().toString().trim();
        pwdAgain = et_psw_again.getText().toString().trim();


    }
}
