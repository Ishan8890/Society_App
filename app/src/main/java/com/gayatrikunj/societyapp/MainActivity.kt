package com.gayatrikunj.societyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gayatrikunj.societyapp.Db.SocietyDatabase
import com.gayatrikunj.societyapp.Db.SocietyRepositary
import com.gayatrikunj.societyapp.Model.DaggerTestClass
import com.gayatrikunj.societyapp.Model.MemberData
import com.gayatrikunj.societyapp.Model.RoomModule
import com.gayatrikunj.societyapp.Model.ThirdPartyModule
import com.gayatrikunj.societyapp.dagger.DaggerComponent
import com.gayatrikunj.societyapp.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
@Inject
lateinit var component:DaggerTestClass
@Inject
lateinit var database:ThirdPartyModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        var binding:ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        var admin = button
        admin.setOnClickListener {
            startActivity(Intent(this@MainActivity,ExpensesActivity::class.java))
        }
        (application as MyApplication).component.inject(this)
        component.getDagger()
//        database.invalidationTracker
    }
}
