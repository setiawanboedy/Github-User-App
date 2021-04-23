package com.attafakkur.githubuserapp.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.attafakkur.githubuserapp.adapter.UserAdapter
import com.attafakkur.githubuserapp.databinding.FragmentHomeBinding
import com.attafakkur.githubuserapp.model.UserModel
import com.attafakkur.githubuserapp.utils.hide
import com.attafakkur.githubuserapp.utils.refresh
import com.attafakkur.githubuserapp.utils.setDate
import com.attafakkur.githubuserapp.utils.show
import com.attafakkur.githubuserapp.viewmodel.activityviewmodel.UsersViewModel
import com.attafakkur.githubuserapp.views.intentutil.ToDetail


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var usersViewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerMain.setHasFixedSize(true)
        refresh(binding.swipeRefresh) {
            initUserAll()
        }
        initUserAll()
        binding.tvDate.text = setDate
    }

    // Load data main
    private fun initUserAll() {
        binding.pbHome.show()
        usersViewModel = ViewModelProvider(this)
            .get(UsersViewModel::class.java)
        context?.let {
            usersViewModel.loadAllUsers(
                it,
                binding.rootHome
            )
        }

        usersViewModel.getAllUsers().observe(viewLifecycleOwner, { user ->
            binding.recyclerMain.adapter = UserAdapter(user) { userAll ->
                userAll as UserModel
                userAll.username?.let { ToDetail().showUserDetail(context, it) }
            }
            binding.pbHome.hide()
        })

        binding.recyclerMain.layoutManager =
            LinearLayoutManager(context)
    }
}
