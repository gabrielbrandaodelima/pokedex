package com.gabriel.pokedex.core.extensions

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView?.setUpRecyclerView(
        context: Context?,
        adapterSetCallback: (adapter: RecyclerView) -> Unit,
        spanCount:Int = 2,
        orientation: Int = RecyclerView.VERTICAL
) {
    this?.apply {
        setHasFixedSize(true)
        setItemViewCacheSize(20)
        context?.let {
            layoutManager = GridLayoutManager((it), spanCount, orientation, false)
        }

        adapterSetCallback(this)
    }
}

fun RecyclerView?.setUpPaging(
    needsLoading: MutableLiveData<Boolean>,
    refreshList: () -> Unit) {
    this?.apply {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                //check for scroll down
                {
                    (recyclerView.layoutManager?.let { it as LinearLayoutManager })?.let {
                        val visItemCount = it.childCount
                        val totItemCount = it.itemCount
                        val pastItemCount = it.findFirstVisibleItemPosition()

                        needsLoading.value?.let { load ->
                            if (load) {
                                if (visItemCount + pastItemCount >= totItemCount) {
                                    needsLoading.postValue(false)
                                    refreshList()
                                }
                            }

                        }
                    }

                }
            }
        })
    }
}