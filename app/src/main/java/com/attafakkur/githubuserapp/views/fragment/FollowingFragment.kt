package com.attafakkur.githubuserapp.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.attafakkur.githubuserapp.adapter.UserAdapter
import com.attafakkur.githubuserapp.databinding.FragmentFollowingBinding
import com.attafakkur.githubuserapp.model.UserModel
import com.attafakkur.githubuserapp.utils.Constants
import com.attafakkur.githubuserapp.utils.hide
import com.attafakkur.githubuserapp.utils.show
import com.attafakkur.githubuserapp.viewmodel.fragmentviewmodel.FollowingViewModel
import com.attafakkur.githubuserapp.views.intentutil.ToDetail

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var followingViewModel: FollowingViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollowing.setHasFixedSize(true)
        val username = activity?.intent?.getStringExtra(Constants.EXTRA_USER)
        initLoadFollowers(username)

    }

    // Load following data
    private fun initLoadFollowers(username: String?) {
        if (username != null) {

            binding.pbFollowing.show()
            followingViewModel = ViewModelProvider(this)
                .get(FollowingViewModel::class.java)
            followingViewModel.loadAllFollowing(username)
            followingViewModel.getAllFollowing().observe(viewLifecycleOwner, { user ->
                binding.rvFollowing.adapter = UserAdapter(user) { userFollow ->
                    userFollow as UserModel
                    userFollow.username?.let { ToDetail().showUserDetail(requireActivity(), it) }
                }
                binding.pbFollowing.hide()
            })
            binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        }
    }
}