package id.revze.myfriendapp.ui.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import id.revze.myfriendapp.ObjectBox
import id.revze.myfriendapp.R
import id.revze.myfriendapp.model.Friend
import id.revze.myfriendapp.model.Friend_

class FriendDetailActivity : AppCompatActivity() {

    companion object {
        val FRIEND_ID_KEY = "friend_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val outerIntent = intent
        val friendBox = ObjectBox.boxStore.boxFor(Friend::class.java)
        val friend = friendBox.query().equal(Friend_.id, outerIntent.getLongExtra(FRIEND_ID_KEY, 0)).build().findFirst()
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvBirthDate = findViewById<TextView>(R.id.tv_birth_date)
        val tvGender = findViewById<TextView>(R.id.tv_gender)
        tvName.text = friend?.name
        tvBirthDate.text = friend?.birthDate
        tvGender.text = if (friend?.gender == 1) "Male" else "Female"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
