package com.example.echoloc

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.echoloc.adapter.ViewPagerAdapter
import com.example.echoloc.database.Pref
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var pref: Pref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref=Pref(applicationContext)
        btn_setting.setOnClickListener(this)
        btn_createroom.setOnClickListener(this)
        var fragment= arrayListOf(
            PublicRooms()
        )
        viewpager.adapter=ViewPagerAdapter(this,fragment)

        TabLayoutMediator(tablayout,viewpager, object :TabLayoutMediator.TabConfigurationStrategy
        {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                when(position)
                {
                    0 ->
                    {
                        tab.text="방 리스트"
                    }
                }
            }

        }).attach()

    }
    override fun onClick(p0: View?) {
        when(p0)
        {
            /*btn_logout ->
            {
                pref.clearData()
                startActivity(Intent(applicationContext, SplashScreen::class.java))
                finish()
            }*/
            btn_createroom -> {
                startActivity(Intent(applicationContext,CreateRoom::class.java))
            }

            btn_setting -> {
                intent = Intent(applicationContext, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}