package io.appicenter.birthdayapp.feature_birthday_list.ui.users_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.appicenter.birthdayapp.R
import io.appicenter.birthdayapp.common.Result
import io.appicenter.birthdayapp.feature_birthday_list.domain.model.User
import kotlinx.android.synthetic.main.fragment_user_list.*

@AndroidEntryPoint
class UsersListFragment : Fragment(R.layout.fragment_user_list), OnUserClickListener {

    private val viewModel: UsersViewModel by viewModels()
    private val usersAdapter = UsersAdapter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = usersAdapter
        }

        viewModel.state.observe(requireActivity()) { parseResponse(it) }
    }

    private fun parseResponse(result: Result<List<User>>?) {

        when (result) {
            is Result.Error -> Toast.makeText(
                requireContext(),
                result.err.message,
                Toast.LENGTH_SHORT
            ).show()
            is Result.Loading -> Toast.makeText(
                requireContext(),
                "Loading...",
                Toast.LENGTH_SHORT
            ).show()
            is Result.Success -> usersAdapter.submitList(result.data)
        }
    }

    override fun onUserClick(position: Int) {
        NavHostFragment
            .findNavController(this)
            .navigate(R.id.action_usersListFragment_to_userDetailFragment,getBundle(usersAdapter.currentList[position]))
    }

    private fun getBundle(user: User): Bundle {
        val initials = "${user.name.first.first()}${user.name.last.first()}"
        val fullName = "${user.name.first} ${user.name.last}"
        val age = "${user.dateOfBirth.age} YEARS OLD"
        return bundleOf("initials" to initials, "fullName" to fullName, "age" to age)
    }


}