package com.yuzu.ecom.view.activity

import android.os.Bundle
import com.yuzu.ecom.R
import com.yuzu.ecom.view.fragment.LoginFragment

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

class LoginActivity: BaseViewActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_content,
                    LoginFragment()
                ).commit()
        }
    }
}