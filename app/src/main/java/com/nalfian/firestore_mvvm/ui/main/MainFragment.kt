package com.nalfian.firestore_mvvm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nalfian.firestore_mvvm.R
import com.nalfian.firestore_mvvm.data.db.entity.Note
import com.nalfian.firestore_mvvm.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MainFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModeFactory: MainViewFactory by instance()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModeFactory)
            .get(MainViewModel::class.java)
        bindUi()
        listenEvent()
    }

    private fun listenEvent() {
        addNote.setOnClickListener {
           createNote()
        }
    }

    private fun createNote() {
        viewModel.createNote(Note("Coba", System.currentTimeMillis()))
    }

    private fun bindUi() = launch {
        viewModel.notes.await().observe(this@MainFragment, Observer {
            textView.text = it.toString()
        })
    }

}
