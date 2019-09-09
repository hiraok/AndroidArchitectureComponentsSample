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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.sort -> showPopup(toolbar, 1)
            R.id.filter -> showPopup(toolbar, 2)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showPopup(v: View, type: Int) {
        val popup = PopupMenu(this, v, Gravity.RIGHT)
        val inflater: MenuInflater = popup.menuInflater
        val resId = when (type) {
            1 -> R.menu.menu_sort
            2 -> R.menu.menu_filter
            else -> 0
        }
        inflater.inflate(resId, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        item ?: return false

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
        item.actionView = View(this)
        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                when (item.itemId) {
                    R.id.price -> {
                        sort()
                    }
                    R.id.filter1 -> {
                        item.isChecked = !item.isChecked
                        filter(1, item.isChecked)
                    }
                    R.id.filter2 -> {
                        item.isChecked = !item.isChecked
                        filter(2, item.isChecked)
                    }
                }
                return false
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                return false
            }

        })

        return false
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

    private fun filter(type: Int, isChecked: Boolean) {
        if (isChecked) {
            when (type) {
                1 -> list.filter { it.id == "2" }.also {
                    section.update(it)
                }
                2 -> list.filter { it.price[0].price >= 300 }.also {
                    section.update(it)
                }
            }
        } else {
            section.update(list)
        }

    }

}

