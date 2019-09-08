package com.hiraok.androidarchitecturecomponentsample

import com.hiraok.androidarchitecturecomponentsample.databinding.ItemCardBinding
import com.xwray.groupie.databinding.BindableItem

data class SampleData(val id: String, val price: List<Price>) : BindableItem<ItemCardBinding>() {
    override fun bind(viewBinding: ItemCardBinding, position: Int) {
        viewBinding.cardViewText.text = price[0].taxPrice.toString()
    }

    override fun getLayout(): Int = R.layout.item_card
}
