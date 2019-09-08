package com.hiraok.androidarchitecturecomponentsample

import com.xwray.groupie.Group
import com.xwray.groupie.Item
import com.xwray.groupie.NestedGroup

class ColumnGroup(items: List<Item<*>>) : NestedGroup() {

    private val items = ArrayList<Item<*>>()

    init {
        var index: Int
        for (i in items.indices) {
            if (i % 2 == 0) {
                index = i / 2
            } else {
                index = (i - 1) / 2 + (items.size / 2f).toInt()
                if (items.size % 2 == 1) index++
            }

            val trackItem = items[index]
            this.items.add(trackItem)
        }
    }

    override fun getGroup(position: Int): Group {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPosition(group: Group): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGroupCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItem(position: Int): Item<*> {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

}