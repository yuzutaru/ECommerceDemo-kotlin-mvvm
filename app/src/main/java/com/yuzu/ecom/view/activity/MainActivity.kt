package com.yuzu.ecom.view.activity

import android.os.Bundle
import com.yuzu.ecom.R
import com.yuzu.ecom.view.fragment.MainMenuFragment

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class MainActivity: BaseViewActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.main_content,
                    MainMenuFragment()
                ).commit()
        }
    }
}