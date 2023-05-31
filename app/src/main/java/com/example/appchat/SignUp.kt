package com.example.appchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var kullaniciAdi: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var hesapAc: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        kullaniciAdi = findViewById(R.id.kullaniciAdi)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        hesapAc = findViewById(R.id.hesapAc)

        hesapAc.setOnClickListener {
            val name = kullaniciAdi.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()
            signUp(name,email,password)
        }

    }
    private fun signUp(name:String, email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }else{
                    Toast.makeText(this@SignUp,"Bir hata Oluştu !",Toast.LENGTH_LONG).show()
                }
            }

    }
    private fun addUserToDatabase(name:String,email:String,uid:String){
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))

    }
}