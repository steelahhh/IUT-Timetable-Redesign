package com.alefimenko.iuttimetable.feature.pickgroup.model

import android.view.View
import android.widget.TextView
import com.alefimenko.iuttimetable.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem

/*
 * Created by Alexander Efimenko on 22/11/18.
 */

data class GroupItem(
    val id: Int,
    val label: String
) : AbstractItem<GroupItem, GroupItem.VH>() {
    override fun getType(): Int = R.id.item_group_id
    override fun getViewHolder(v: View) = VH(v)
    override fun getLayoutRes() = R.layout.item_group

    class VH(view: View): FastAdapter.ViewHolder<GroupItem>(view) {
        private val title = itemView.findViewById<TextView>(R.id.group_title)

        override fun bindView(item: GroupItem, payloads: MutableList<Any>) {
            title.text = item.label
        }

        override fun unbindView(item: GroupItem) {
            title.text = null
        }
    }
}
