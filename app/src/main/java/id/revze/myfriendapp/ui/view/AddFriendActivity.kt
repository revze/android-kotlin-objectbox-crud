package id.revze.myfriendapp.ui.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import id.revze.myfriendapp.ObjectBox
import id.revze.myfriendapp.R
import id.revze.myfriendapp.model.Friend
import id.revze.myfriendapp.ui.InterfaceManager
import io.objectbox.Box

class AddFriendActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var friendBox: Box<Friend>
    lateinit var etName: TextView
    lateinit var etBirthDate: TextView
    lateinit var rbMale: RadioButton
    lateinit var rbFemale: RadioButton
    lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        friendBox = ObjectBox.boxStore.boxFor(Friend::class.java)
        etName = findViewById(R.id.et_name)
        etBirthDate = findViewById(R.id.et_birth_date)
        rbMale = findViewById(R.id.rb_male)
        rbFemale = findViewById(R.id.rb_female)
        btnSave = findViewById(R.id.btn_save)

        btnSave.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        when (id) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun addFriend() {
        val name = etName.text.toString().trim()
        val birthDate = etBirthDate.text.toString().trim()
        val isGenderNotSelected = !rbMale.isChecked && !rbFemale.isChecked
        val selectedGender = if (rbMale.isChecked) 1 else 2

        if (name.equals("")) InterfaceManager(this).showFailedDialog(getString(R.string.name_empty_error))
        else if (birthDate.equals("")) InterfaceManager(this).showFailedDialog(getString(R.string.birth_date_empty_error))
        else if (isGenderNotSelected) InterfaceManager(this).showFailedDialog(getString(R.string.gender_empty_error))
        else {
            friendBox.put(Friend(name = name, birthDate = birthDate, gender = selectedGender))
            val mainIntent = Intent(this, MainActivity::class.java)
            mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mainIntent)
        }
    }

    override fun onClick(v: View?) {
        val id = v?.id

        when (id) {
            R.id.btn_save -> addFriend()
        }
    }
}
