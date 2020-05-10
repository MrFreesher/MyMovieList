package com.freesher.mymovielist

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.freesher.mymovielist.utils.logout
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.signOut -> {

                AlertDialog.Builder(this)
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                        FirebaseAuth.getInstance().signOut()
                        logout()

                    }).setNegativeButton(
                        "No",
                        DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() }).setTitle("Are you sure to logout?")
                    .show()


            }
        }
        return super.onOptionsItemSelected(item)
    }
}
