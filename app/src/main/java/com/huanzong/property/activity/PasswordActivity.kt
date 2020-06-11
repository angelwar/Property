package com.huanzong.property.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.huanzong.property.R
import com.huanzong.property.database.DataBase
import com.huanzong.property.http.HttpServer
import com.huanzong.property.util.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
    }

    fun onConfirm(v : View){

        if (et_confirm_password.text.toString().trim().equals(et_password.text.toString().trim())){
            changePassword()
        }else{
            Toast.makeText(this,"两次密码不一致，请重新输入",Toast.LENGTH_SHORT).show()
        }
    }

    private fun changePassword() {
        HttpServer.getAPIService().modifyPassword(SharedPreferencesUtil.queryUid(this),et_password.text.toString().trim()).enqueue(object :
            Callback<DataBase<String>> {
            override fun onFailure(call: Call<DataBase<String>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<DataBase<String>>,
                response: Response<DataBase<String>>
            ) {
                if (response.body()?.code!=1){
                    Toast.makeText(this@PasswordActivity,"改密码失败，请重试",Toast.LENGTH_SHORT).show()
                    return
                }
                Toast.makeText(this@PasswordActivity,response.body()?.msg,Toast.LENGTH_SHORT).show()
                SharedPreferencesUtil.addUid(this@PasswordActivity,-1)
                val intent = Intent(this@PasswordActivity, LoginActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

        })
    }

    fun onBack(v : View){
        finish()
    }
}
