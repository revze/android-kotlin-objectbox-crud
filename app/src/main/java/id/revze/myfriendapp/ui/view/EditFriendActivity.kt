package id.revze.myfriendapp.ui.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import id.revze.myfriendapp.ObjectBox
import id.revze.myfriendapp.R
import id.revze.myfriendapp.model.Friend
import id.revze.myfriendapp.model.Friend_
import id.revze.myfriendapp.ui.InterfaceManager
import io.objectbox.Box

class EditFriendActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var friendBox: Box<Friend>
    private lateinit var etName: EditText
    private lateinit var etBirthDate: EditText
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var btnSave: Button
    private lateinit var friend: Friend

    companion object {
        val FRIEND_ID_KEY = "friend_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_friend)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val outerIntent = intent
        friendBox = ObjectBox.boxStore.boxFor(Friend::class.java)
        friend = friendBox.query().equal(Friend_.id, outerIntent.getLongExtra(FRIEND_ID_KEY, 0)).build().findFirst()!!
        etName = findViewById(R.id.et_name)
        etBirthDate = findViewById(R.id.et_birth_date)
        rbMale = findViewById(R.id.rb_male)
        rbFemale = findViewById(R.id.rb_female)
        btnSave = findViewById(R.id.btn_save)
        btnSave.setOnClickListener(this)

        etName.setText(friend.name)
        etBirthDate.setText(friend.birthDate)
        when (friend.gender) {
            1 -> rbMale.isChecked = true
            else -> rbFemale.isChecked = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_save -> updateFriend()
        }
    }

    fun updateFriend() {
        val name = etName.text.toString().trim()
        val birthDate = etBirthDate.text.toString().trim()
        val isGenderNotSelected = !rbMale.isChecked && !rbFemale.isChecked
        val selectedGender = if (rbMale.isChecked) 1 else 2

        if (name.equals("")) InterfaceManager(this).showFailedDialog(getString(R.string.name_empty_error))
        else if (birthDate.equals("")) InterfaceManager(this).showFailedDialog(getString(R.string.birth_date_empty_error))
        else if (isGenderNotSelected) InterfaceManager(this).showFailedDialog(getString(R.string.gender_empty_error))
        else {
            friend.name = name
            friend.birthDate = birthDate
            friend.gender = selectedGender
            friendBox.put(friend)
            val mainIntent = Intent(this, MainActivity::class.java)
            mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mainIntent)
        }
    }
}
