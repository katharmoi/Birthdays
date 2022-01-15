package io.appicenter.birthdayapp.feature_birthday_list.ui.user_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import io.appicenter.birthdayapp.R
import io.appicenter.birthdayapp.feature_birthday_list.ui.users_list.UsersViewModel
import kotlinx.android.synthetic.main.fragment_user_details.*

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_details) {

    private val viewModel: UsersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnDetailsBack.setOnClickListener {
            NavHostFragment
                .findNavController(this)
                .navigate(R.id.action_userDetailFragment_to_usersListFragment)
        }

        initUser()

    }


    private fun initUser() {
        txtDetailAge.text = arguments?.getString("age")
        txtDetailName.text = arguments?.getString("fullName")
        txtDetailInitials.text = arguments?.getString("initials")
    }


}