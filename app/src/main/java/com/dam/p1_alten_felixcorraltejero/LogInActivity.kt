package com.dam.p1_alten_felixcorraltejero

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dam.p1_alten_felixcorraltejero.databinding.ActivityLoginBinding

class LogInActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null
    private var isPasswordVisible = false
    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val LOGIN_DELAY_MS = 1000L
        const val STR_EMAIL = "EMAIL"
        const val STR_PASSWORD = "PASSWORD"
        const val STR_USER_OBJ = "USER_OBJ"
        const val STR_REMEMBER_ME = "REMEMBER_ME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        with(binding) {
            this?.loginInputEmail?.setText(intent.getStringExtra(STR_EMAIL) ?: "")
            this?.loginInputPassword?.setText(intent.getStringExtra(STR_PASSWORD) ?: "")
        }

        checkFormValidity()
        setupListeners()
    }

    private fun setupListeners() {
        binding?.loginImgCloseApp?.setOnClickListener { showExitDialog() }
        binding?.loginImgShowPassword?.setOnClickListener { togglePasswordVisibility() }

        binding?.loginInputEmail?.addTextChangedListener(loginTextWatcher)
        binding?.loginInputPassword?.addTextChangedListener(loginTextWatcher)

        binding?.loginInputEmail?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                binding?.loginInputPassword?.requestFocus()
                return@setOnEditorActionListener true
            }
            false
        }
        binding?.loginInputPassword?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && binding?.loginBtnEnter?.isEnabled == true) {
                attemptLogin()
                return@setOnEditorActionListener true
            }
            false
        }
        binding?.loginBtnEnter?.setOnClickListener { attemptLogin() }
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            checkFormValidity()
        }
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.close_app))
            .setMessage(getString(R.string.confirm_close))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> finishAffinity() }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        binding?.loginInputPassword?.transformationMethod = if (isPasswordVisible) {
            HideReturnsTransformationMethod.getInstance()
        } else {
            PasswordTransformationMethod.getInstance()
        }
        binding?.loginImgShowPassword?.setImageResource(
            if (isPasswordVisible) R.drawable.hidepass
            else R.drawable.showpass
        )
        binding?.loginInputPassword?.setSelection(binding?.loginInputPassword?.text?.length ?: 0)
    }

    private fun attemptLogin() {
        showLoading(true)
        handler.postDelayed({
            val email = binding?.loginInputEmail?.text.toString().trim()
            val password = binding?.loginInputPassword?.text.toString().trim()

            if (!Validator.isPasswordValid(password)) {
                showError(getString(R.string.err_password_format))
                return@postDelayed
            }
            val user = User.VALID_USERS.find { it.email == email && it.password == password }

            if (user != null) {
                navigateToWelcome(user)
                showError(getString(R.string.no_error))
            } else {
                showError(getString(R.string.err_invalid_credentials))
            }
        }, LOGIN_DELAY_MS)
    }

    private fun checkFormValidity() {
        val email = binding?.loginInputEmail?.text.toString().trim()
        val password = binding?.loginInputPassword?.text.toString().trim()
        val isFormValid = Validator.areFieldsFilled(email, password)
        binding?.loginBtnEnter?.isEnabled = isFormValid
        binding?.loginBtnEnter?.backgroundTintList = getColorStateList(
            if (isFormValid) R.color.orange else R.color.gray
        )
    }

    private fun showLoading(show: Boolean) {
        binding?.loginBtnEnter?.visibility = if (show) View.GONE else View.VISIBLE
        binding?.loginLabelWrongCredentials2?.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        showLoading(false)
        binding?.loginLabelWrongCredentials?.text = message
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        handler.removeCallbacksAndMessages(null)
    }

    private fun navigateToWelcome(user: User) {
        val intent = Intent(this, WelcomeActivity::class.java).apply {
            putExtra(STR_USER_OBJ, user)
            putExtra(STR_REMEMBER_ME, binding?.loginSwitchRememberMe?.isChecked)
        }
        startActivity(intent)
        finish()
    }

}