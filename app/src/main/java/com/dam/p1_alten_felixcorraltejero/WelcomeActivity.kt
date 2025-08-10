package com.dam.p1_alten_felixcorraltejero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.dam.p1_alten_felixcorraltejero.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private var binding: ActivityWelcomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val user = intent.getParcelableExtra<User>(LogInActivity.STR_USER_OBJ)

        user?.let {
            binding?.welcomeLableTitle?.text = getString(R.string.welcome_user, it.name)
            binding?.welcomeLableName?.text = getString(R.string.name_format, it.name)
            binding?.welcomeLableEmail?.text = getString(R.string.email_format, it.email)
            binding?.welcomeLableAge?.text = getString(R.string.age_format, it.age)
            binding?.welcomeLableHeight?.text = getString(R.string.height_format, it.height)
            binding?.welcomeImgUser?.loadImage(it.imageUrl)
        }

        binding?.welcomeBtnLogout?.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.confirm_logout))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                val intent = Intent(this, LogInActivity::class.java).apply {
                    if (intent.getBooleanExtra(LogInActivity.STR_REMEMBER_ME, false)) {
                        val user = intent.getParcelableExtra<User>(LogInActivity.STR_USER_OBJ)
                        putExtra(LogInActivity.STR_EMAIL, user?.email)
                        putExtra(LogInActivity.STR_PASSWORD, user?.password)
                    }
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
                finish()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}