package com.attafakkur.githubuserapp.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.attafakkur.githubuserapp.adapter.UserAdapter
import com.attafakkur.githubuserapp.databinding.FragmentSearchBinding
import com.attafakkur.githubuserapp.model.UserModel
import com.attafakkur.githubuserapp.utils.Constants
import com.attafakkur.githubuserapp.utils.hide
import com.attafakkur.githubuserapp.utils.refresh
import com.attafakkur.githubuserapp.utils.show
import com.attafakkur.githubuserapp.viewmodel.activityviewmodel.SearchViewModel
import com.attafakkur.githubuserapp.views.intentutil.ToDetail


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerSearch.setHasFixedSize(true)

        val query = arguments?.getString(Constants.EXTRA_USER)

        refresh(binding.swipeRefresh) {
            initSearchUser(query)
        }

        initSearchUser(query)
    }

    // Load search data
    private fun initSearchUser(query: String?) {
        if (query != null) {
            binding.pbSearch.show()
            searchViewModel = ViewModelProvider(this)
                .get(SearchViewModel::class.java)

            context?.let {
                searchViewModel.loadSearchUser(
                    it,
                    binding.rootSearch,
                    query
                )
            }

            searchViewModel.getSearchUsers().observe(viewLifecycleOwner, { user ->
                binding.recyclerSearch.adapter = UserAdapter(user) { userSearch ->
                    userSearch as UserModel
                    userSearch.username?.let { ToDetail().showUserDetail(context, it) }
                }
                binding.pbSearch.hide()
            })

            searchViewModel.getResultSearch().observe(viewLifecycleOwner, { result ->
                if (result.totalResult != 0) {
                    binding.tvTotal.text = result.totalResult.toString()
                } else {
                    binding.searchEmpty.visibility = View.VISIBLE
                }

                binding.tvResult.show()

            })

        }
        binding.recyclerSearch.layoutManager =
            LinearLayoutManager(context)
    }
}