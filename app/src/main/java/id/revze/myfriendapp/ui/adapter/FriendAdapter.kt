package id.revze.myfriendapp.ui.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.revze.myfriendapp.R
import id.revze.myfriendapp.model.Friend
import id.revze.myfriendapp.model.Friend_
import id.revze.myfriendapp.ui.view.FriendDetailActivity
import id.revze.myfriendapp.ui.InterfaceManager
import id.revze.myfriendapp.ui.view.EditFriendActivity
import io.objectbox.Box
import kotlinx.android.synthetic.main.item_row_friend.view.*

class FriendAdapter(context: Context, friendBox: Box<Friend>) : RecyclerView.Adapter<FriendAdapter.FriendAdapterViewHolder>() {
    lateinit var friends: MutableList<Friend>
    private val context = context
    private val friendBox = friendBox

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendAdapterViewHolder {
        val itemRow = LayoutInflater.from(parent.context).inflate(R.layout.item_row_friend, parent, false)
        return FriendAdapterViewHolder(itemRow)
    }

    override fun onBindViewHolder(holder: FriendAdapterViewHolder, position: Int) {
        val friend = friends[position]
        val selectFriendQuery = friendBox.query().equal(Friend_.id, friend.id).build()
        holder.tvName.text = friend.name
        holder.tvBirthDate.text = friend.birthDate
        holder.tvGender.text = if (friend.gender == 1) context.getString(R.string.male_radio_option)
                               else context.getString(R.string.female_radio_option)
        holder.layoutFriend.setOnClickListener { view ->
            val friendDetailIntent = Intent(context, FriendDetailActivity::class.java)
            friendDetailIntent.putExtra(FriendDetailActivity.FRIEND_ID_KEY, friend.id)
            context.startActivity(friendDetailIntent)
        }
        holder.btnEdit.setOnClickListener { view ->
            val editFriendIntent = Intent(context, EditFriendActivity::class.java)
            editFriendIntent.putExtra(EditFriendActivity.FRIEND_ID_KEY, friend.id)
            context.startActivity(editFriendIntent)
        }
        holder.btnDelete.setOnClickListener { view ->
            InterfaceManager(context).showConfirmationDialog(context.getString(R.string.main_delete_friend_confirmation),
                                                             DialogInterface.OnClickListener { dialog, which ->
                                                                 selectFriendQuery.remove()
                                                                 friends.removeAt(position)
                                                                 notifyItemRemoved(position)
                                                                 notifyItemRangeChanged(0, itemCount)
                                                             })
        }
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    class FriendAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layoutFriend = view.layout_friend
        val tvName = view.tv_name
        val tvBirthDate = view.tv_birth_date
        val tvGender = view.tv_gender
        val btnEdit = view.btn_edit
        val btnDelete = view.btn_delete
    }
}