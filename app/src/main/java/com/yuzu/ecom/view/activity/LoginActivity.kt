package com.yuzu.ecom.view.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.yuzu.ecom.R
import com.yuzu.ecom.view.fragment.LoginFragment
import java.security.MessageDigest

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

class LoginActivity: BaseViewActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.main_content,
                    LoginFragment()
                ).commit()
        }

        /*val info = packageManager.getPackageInfo(
            "com.yuzu.ecom",
            PackageManager.GET_SIGNATURES
        )
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
        }*/
    }
}