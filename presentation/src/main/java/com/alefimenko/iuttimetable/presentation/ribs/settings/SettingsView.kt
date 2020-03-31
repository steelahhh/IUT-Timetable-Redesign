package com.alefimenko.iuttimetable.presentation.ribs.settings

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.alefimenko.iuttimetable.presentation.BuildConfig
import com.alefimenko.iuttimetable.presentation.R
import com.alefimenko.iuttimetable.presentation.data.SettingsItem
import com.alefimenko.iuttimetable.presentation.data.SettingsItemKey
import com.alefimenko.iuttimetable.presentation.ribs.settings.SettingsView.Event
import com.alefimenko.iuttimetable.presentation.ribs.settings.SettingsView.ViewModel
import com.badoo.ribs.core.view.RibView
import com.badoo.ribs.core.view.ViewFactory
import com.badoo.ribs.customisation.inflate
import com.jakewharton.rxrelay2.PublishRelay
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.rib_settings.view.*

interface SettingsView : RibView,
    ObservableSource<Event>,
    Consumer<ViewModel> {

    fun onThemeClick()
    fun onAboutClick()
    fun showUpdateDialog(updated: Boolean)
    fun showErrorDialog()

    sealed class Event {
        object OnBackClick : Event()
        data class SettingsItemClicked(val key: SettingsItemKey) : Event()
    }

    data class ViewModel(
        val items: List<SettingsItem> = emptyList()
    )

    interface Factory : ViewFactory<Nothing?, SettingsView>
}

class SettingsViewImpl private constructor(
    override val androidView: ViewGroup,
    private val events: PublishRelay<Event> = PublishRelay.create()
) : SettingsView,
    ObservableSource<Event> by events,
    Consumer<ViewModel> {

    class Factory(
        @LayoutRes private val layoutRes: Int = R.layout.rib_settings
    ) : SettingsView.Factory {
        override fun invoke(deps: Nothing?): (ViewGroup) -> SettingsView = {
            SettingsViewImpl(inflate(it, layoutRes))
        }
    }

    private val settingsAdapter = GroupAdapter<GroupieViewHolder>()

    init {
        androidView.settingsBackButton.setOnClickListener {
            events.accept(Event.OnBackClick)
        }
        androidView.settingsRecycler.apply {
            adapter = settingsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }

        settingsAdapter.setOnItemClickListener { item, _ ->
            events.accept(Event.SettingsItemClicked((item as SettingsItem).key))
        }
    }

    override fun accept(vm: ViewModel) {
        settingsAdapter.clear()
        settingsAdapter.addAll(vm.items)
    }

    override fun onThemeClick() {
        androidView.context.activity()?.recreate()
    }

    override fun onAboutClick() {
        MaterialDialog(androidView.context).show {
            title(text = androidView.context.getString(R.string.about_title, BuildConfig.VERSION_NAME))
            message(
                res = R.string.about_body
            ) {
                html()
                lineSpacing(1.4F)
            }
            positiveButton(res = R.string.common_ok)
        }
    }

    override fun showUpdateDialog(updated: Boolean) {
        MaterialDialog(androidView.context).show {
            title(text = "Обновление расписания")
            message(text = if (updated) "Расписание успешно обновлено" else "Расписание в обовлении не нуждается")
            positiveButton(res = R.string.common_ok)
        }
    }

    override fun showErrorDialog() {
        MaterialDialog(androidView.context).show {
            title(text = "Ошибка")
            message(text = "Произошла ошибка при обновлении расписания")
            positiveButton(res = R.string.common_ok)
        }
    }

    tailrec fun Context?.activity(): Activity? = when (this) {
        is Activity -> this
        else -> (this as? ContextWrapper)?.baseContext?.activity()
    }
}