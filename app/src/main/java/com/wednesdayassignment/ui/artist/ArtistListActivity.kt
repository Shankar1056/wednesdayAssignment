package com.wednesdayassignment.ui.artist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mpokketassignment.Utilz.Utility
import com.wednesdayassignment.R
import com.wednesdayassignment.databinding.ActivityArtistBinding
import com.wednesdayassignment.ui.artist.adapter.ArtistListAdapter
import kotlinx.android.synthetic.main.activity_artist.*

class ArtistListActivity : AppCompatActivity() {
    private lateinit var viewModel: ArtistViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityArtistBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_artist)
        viewModel = ViewModelProviders.of(this).get(ArtistViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        viewModel.getLocalData()

        observeChanges()

    }

    private fun observeChanges() {
        viewModel.showMessage.observe(this, Observer {
            Utility.showToast(this, it)
        })

        viewModel.onResponse.observe(this, Observer {
            rv_artist.adapter =
                ArtistListAdapter(it, object : ArtistListAdapter.ArtistClickListener {
                    override fun onItemClicked(pos: Int) {
                    }

                })

        })
    }


}
