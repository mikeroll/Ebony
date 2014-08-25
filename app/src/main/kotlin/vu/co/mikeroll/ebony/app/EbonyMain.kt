package vu.co.mikeroll.ebony.app

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

public class EbonyMain() : Activity() {

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ebony_main)
    }

    override fun onCreateOptionsMenu(menu : Menu?):Boolean {
        getMenuInflater().inflate(R.menu.ebony_main, menu)
    return true
    }

    override fun onOptionsItemSelected(item : MenuItem?) : Boolean {
        val id = item?.getItemId()
        if (id == R.id.action_settings)
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
