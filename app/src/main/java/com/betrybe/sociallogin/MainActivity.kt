package com.betrybe.sociallogin
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar

const val LENGTH = 4
class MainActivity : AppCompatActivity() {
    private val emailInput: TextInputLayout by lazy { findViewById(R.id.email_text_input_layout) }
    private val passwordInput: TextInputLayout by lazy { findViewById(R.id.password_text_input_layout) }
    private val buttonLogin: Button by lazy { findViewById(R.id.login_button) }
    private val mainLayout: ConstraintLayout by lazy { findViewById(R.id.main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //https://stackoverflow.com/questions/69269352/how-to-use-textwatcher-to-enable-validation-button-in-kotlin
        //https://stackoverflow.com/questions/73978218/how-can-i-use-doontextchanged-without-overriding-textwatcher
        emailInput.editText?.doOnTextChanged { _, _, _, _ -> validateButton() }
        passwordInput.editText?.doOnTextChanged { _, _, _, _ -> validateButton() }
        buttonLogin.setOnClickListener {
            validateEmail()
            validatePassword()
            successLogin()
        }
    }

    private fun validateButton() {
        val validEmail = emailInput.editText?.text?.isNotEmpty()!!
        val validPassword = passwordInput.editText?.text?.isNotEmpty()!!
        buttonLogin.isEnabled = validEmail && validPassword
    }

    // https://developermemos.com/posts/validating-email-addresses-kotlin
    // https://regex101.com/r/DXhTrH/1
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

    private fun isValidEmail(email: String): Boolean {
        return email.matches(emailRegex.toRegex())
    }

    private fun validateEmail() {
        val emailValid = isValidEmail(emailInput.editText?.text.toString())
        if (!emailValid){
            emailInput.error = "Email inválido"
        } else{
            emailInput.error = null
        }
    }


    private fun isValidPassword(password: String): Boolean {
        return password.length > LENGTH

    }

    private fun validatePassword() {
        val passwordValid = isValidPassword(passwordInput.editText?.text.toString())
        if (!passwordValid){
            passwordInput.error = "Senha deve ter mais de 4 caracteres"
        } else{
            passwordInput.error = null
        }
    }

    private fun successLogin() {
            Snackbar.make(mainLayout, "Login efetuado com sucesso", Snackbar.LENGTH_LONG).show()
    }

}
