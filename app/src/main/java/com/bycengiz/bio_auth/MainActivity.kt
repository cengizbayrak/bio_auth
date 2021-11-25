package com.bycengiz.bio_auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tailoredapps.biometricauth.BiometricAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        biometricAuth()
        biometricManager()
    }

    private fun biometricAuth() {
        val biometricAuth = BiometricAuth.create(this) // where this is an (AppCompat-)Activity

        if (!biometricAuth.hasFingerprintHardware) {
            //The devices does not support fingerprint authentication (i.e. has no fingerprint hardware)
            Toast.makeText(this, "Device does not support fingerprint", Toast.LENGTH_SHORT).show()
        } else if (!biometricAuth.hasFingerprintsEnrolled) {
            //The user has not enrolled any fingerprints (i.e. fingerprint authentication is not activated by the user)
            Toast.makeText(this, "User has not enrolled any fingerprints", Toast.LENGTH_SHORT)
                .show()
        } else {
            biometricAuth
                .authenticate(
                    title = "Please authenticate",
                    subtitle = "'Awesome Feature' requires your authentication",
                    description = "'Awesome Feature' exposes data private to you, which is why you need to authenticate.",
                    negativeButtonText = "Cancel",
                    prompt = "Touch the fingerprint sensor",
                    notRecognizedErrorText = "Not recognized"
                )
                .subscribe(
                    { Log.d("BiometricAuth", "User authentication successful.") },
                    { throwable ->
                        when (throwable) {
                            is BiometricAuthenticationCancelledException -> {
                                Log.d("BiometricAuth", "User cancelled the operation")
                            }
                            is BiometricAuthenticationException -> {
                                Log.d("BiometricAuth", "Unrecoverable authentication error")
                            }
                            else -> {
                                Log.d("BiometricAuth", "Error during user authentication.")
                            }
                        }
                    }
                )
        }
    }

    private fun biometricManager() {
        val biometricCallback = object : BiometricCallback {
            override fun onSdkVersionNotSupported() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onBiometricAuthenticationNotSupported() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onBiometricAuthenticationNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onBiometricAuthenticationPermissionNotGranted() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onBiometricAuthenticationInternalError(error: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAuthenticationFailed() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAuthenticationCancelled() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAuthenticationSuccessful() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        BiometricManager.BiometricBuilder(this@MainActivity)
            .setTitle("Add a title")
            .setSubtitle("Add a subtitle")
            .setDescription("Add a description")
            .setNegativeButtonText("Add a cancel button")
            .build()
            .authenticate(biometricCallback)
    }
}
