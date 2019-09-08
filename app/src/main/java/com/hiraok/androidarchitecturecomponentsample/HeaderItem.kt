package com.hiraok.androidarchitecturecomponentsample

import androidx.annotation.StringRes
import com.hiraok.androidarchitecturecomponentsample.databinding.ItemHeaderBinding
import com.xwray.groupie.databinding.BindableItem

open class HeaderItem(
    @StringRes private val titleResId: Int,
    @StringRes private val subTitleResId: Int? = null
) : BindableItem<ItemHeaderBinding>() {
    override fun getLayout(): Int = R.layout.item_header
    override fun bind(viewBinding: ItemHeaderBinding, position: Int) {
        viewBinding.title.text = "aiuaiu"
        subTitleResId?.let { viewBinding.subTitle.setText(it) }
    }

}
