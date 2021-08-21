//명현 firebase 실험
package org.tensorflow.lite.examples.posenet

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import org.tensorflow.lite.examples.posenet.Extensions.toast
import org.tensorflow.lite.examples.posenet.FirebaseUtils.firebaseAuth
import org.tensorflow.lite.examples.posenet.FirebaseUtils.firebaseUser

class RegisterActivity : AppCompatActivity() {
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    lateinit var userheight: Int
    lateinit var userweight: Int
    lateinit var userage: Int
    lateinit var username: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        createAccountInputsArray = arrayOf(etEmail, etPassword, etConfirmPassword)
        btnCreateAccount.setOnClickListener {
            login()
        }

        btnSignIn2.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            toast("계정을 입력하세요.")
            finish()
        }
    }

    private fun notEmpty(): Boolean = etEmail.text.toString().trim().isNotEmpty() &&
            etPassword.text.toString().trim().isNotEmpty() &&
            etConfirmPassword.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            etPassword.text.toString().trim() == etConfirmPassword.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} 이(가) 필요합니다."
                }
            }
        } else {
            toast("비밀번호가 틀렸습니다.")
        }
        return identical
    }

    private fun login() {
        if (identicalPassword()) {
            // identicalPassword() returns true only  when inputs are not empty and passwords are identical
            userEmail = etEmail.text.toString().trim()
            userPassword = etPassword.text.toString().trim()

            userheight = etheight.text.toString().trim()
            userweight =etweight.text.toString().trim()
            //userage=etage.text.toString().trim()
            //username=etname.text.toString().trim()

            val database = Firebase.database
            val myRef = database.getReference(userEmail)

            myRef.setValue(username)
            myRef.setValue(userage)
            myRef.setValue(userheight)
            myRef.setValue(userweight)


            /*create a user*/
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseAuth.currentUser
                            ?.sendEmailVerification()
                            ?.addOnCompleteListener{ task->
                                if (task.isSuccessful) {
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    toast("회원가입이 성공 하였습니다. 메일 인증을 해주세요. : $userEmail")
                                }
                            }
                    } else {
                        toast("회원가입이 실패 하였습니다.")
                    }
                }
        }
    }
}