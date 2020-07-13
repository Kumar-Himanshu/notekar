package com.notekar

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.notekar.adapter.ListNoteAdapter
import com.notekar.database.AppDataBase
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ListNoteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        activity!!.actionBar!!.hide()
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        AsyncTask.execute { // Fetch Data in async method because Room not allowed data access over main ui
            val list = AppDataBase.getAppDataBase(requireContext())!!.getTextMessageDao().getAll()
            if (list.isEmpty()) {

            } else {
                linearLayoutManager = LinearLayoutManager(activity)
                listNotes.layoutManager = linearLayoutManager
                adapter = ListNoteAdapter(list, findNavController())
                listNotes.adapter = adapter
            }
        }
//        }
//        lifecycleScope.launch {
//            val list = AppDataBase.getAppDataBase(requireContext())!!.getTextMessageDao().getAll()
//            if (list.isEmpty()) {
//
//            } else {
//                linearLayoutManager = LinearLayoutManager(activity)
//                listNotes.layoutManager = linearLayoutManager
//                adapter = ListNoteAdapter(list, findNavController())
//                listNotes.adapter = adapter
//            }
//        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_delete).isVisible = false
        menu.findItem(R.id.action_cancel).isVisible = false
        menu.findItem(R.id.action_save).isVisible = false
        menu.findItem(R.id.action_share).isVisible = false
        menu.findItem(R.id.action_settings).isVisible = false
    }
}