package com.hiraok.androidarchitecturecomponentsample

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.hiraok.androidarchitecturecomponentsample.databinding.ActivityMainBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class MainActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private lateinit var groupAdapter: GroupAdapter<ViewHolder>
    private val section: Section by lazy {
        Section(
            HeaderItem(R.string.header_title, R.string.header_subtitle)
        )
    }

    private val list by lazy {
        val rdm = Random()
        MutableList(24) {
            SampleData(it.toString(), arrayListOf(Price(rdm.nextInt(100), it * 0.8)))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbar()
        setupRecyclerView()
        for (item in list) println(item)
    }

    private fun setUpToolbar() {
        val toolbar = binding.toolbar.toolbar
        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.string_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(this, v, Gravity.RIGHT)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_sort, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_option -> showPopup(toolbar)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        when (p0?.itemId) {
            R.id.price -> sort()
        }
        return true
    }

    private fun setupRecyclerView() {
        groupAdapter = GroupAdapter<ViewHolder>().apply {
            spanCount = 3
        }
        section.apply {
            add(ColumnGroup(list))
            groupAdapter.add(this)
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, groupAdapter.spanCount).apply {
                spanSizeLookup = groupAdapter.spanSizeLookup
            }
            adapter = groupAdapter
        }
    }

    private fun sort() {
        val li = list.sortedBy { it.price[0].taxPrice }
        section.update(li)
    }

}

