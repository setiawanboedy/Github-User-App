package com.attafakkur.githubuserapp.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.attafakkur.githubuserapp.adapter.UserAdapter
import com.attafakkur.githubuserapp.databinding.FragmentFollowersBinding
import com.attafakkur.githubuserapp.model.UserModel
import com.attafakkur.githubuserapp.utils.Constants
import com.attafakkur.githubuserapp.utils.hide
import com.attafakkur.githubuserapp.utils.show
import com.attafakkur.githubuserapp.viewmodel.fragmentviewmodel.FollowerViewModel
import com.attafakkur.githubuserapp.views.intentutil.ToDetail

class FollowersFragment : Fragment() {

    private lateinit var followerViewModel: FollowerViewModel
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollower.setHasFixedSize(true)
        val username = activity?.intent?.getStringExtra(Constants.EXTRA_USER)
        initLoadFollowers(username)

    }

    // Load follower data
    private fun initLoadFollowers(username: String?) {
        if (username != null) {

            binding.pbFollower.show()
            followerViewModel = ViewModelProvider(this)
                .get(FollowerViewModel::class.java)

            followerViewModel.loadAllFollowers(username)
            followerViewModel.getAllFollowers().observe(viewLifecycleOwner, { user ->
                binding.rvFollower.adapter = UserAdapter(user) { userFollow ->
                    userFollow as UserModel
                    userFollow.username?.let { ToDetail().showUserDetail(requireActivity(), it) }
                }
                binding.pbFollower.hide()
            })

            binding.rvFollower.layoutManager = LinearLayoutManager(activity)
        }
    }
}