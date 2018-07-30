package id.revze.myfriendapp.ui.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import id.revze.myfriendapp.ObjectBox
import id.revze.myfriendapp.R
import id.revze.myfriendapp.model.Friend
import id.revze.myfriendapp.ui.adapter.FriendAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val friends = arrayListOf<Friend>()
        val friendBox = ObjectBox.boxStore.boxFor(Friend::class.java)
        val rvFriend = findViewById<RecyclerView>(R.id.rv_friend)
        val friendAdapter = FriendAdapter(this, friendBox)
        val friendLayoutManager = LinearLayoutManager(this)
        friendAdapter.friends = friends
        rvFriend.layoutManager = friendLayoutManager
        rvFriend.adapter = friendAdapter
        friends.addAll(friendBox.query().build().find())
        friendAdapter.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when(id) {
            R.id.item_add -> {
                val addFriendIntent = Intent(this, AddFriendActivity::class.java)
                startActivity(addFriendIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
