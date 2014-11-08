package vu.co.mikeroll.ebony.app

import android.app.Activity
import android.os.Bundle
import vu.co.mikeroll.ebony.db.Database

public class EbonyMain() : Activity() {

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        Database.connect(this)
        setContentView(R.layout.activity_ebony_main)
    }
}
