package io.appicenter.birthdayapp.feature_birthday_list.ui.users_list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.appicenter.birthdayapp.R
import io.appicenter.birthdayapp.feature_birthday_list.domain.model.User

class UsersAdapter(private val listener: OnUserClickListener) :
    ListAdapter<User, UsersAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_user, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ItemViewHolder(itemView: View, private val onUserClickListener: OnUserClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val initials: TextView = itemView.findViewById(R.id.txtInitials)
        private val name: TextView = itemView.findViewById(R.id.txtUserName)
        private val birthday: TextView = itemView.findViewById(R.id.txtBirthday)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: User) = with(itemView) {
            val ins = "${item.name.first.first()}${item.name.last.first()}"
            val fullName = "${item.name.first} ${item.name.last}"

            initials.text = ins
            name.text = fullName
            birthday.text = item.dateOfBirth.date.split("T").first()
        }

        override fun onClick(view: View) {
            onUserClickListener.onUserClick(bindingAdapterPosition)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}

interface OnUserClickListener {
    fun onUserClick(position: Int)
}