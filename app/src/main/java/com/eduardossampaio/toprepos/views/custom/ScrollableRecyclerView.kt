package com.eduardossampaio.toprepos.views.custom

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


interface OnReachEndOfListListener{
    fun onReachEnd();
}
class ScrollableRecyclerView : RecyclerView{

    var onReachEndOfListListener: OnReachEndOfListListener? = null;
    constructor(context: Context):super(context)  {
    }

    constructor(context: Context, attrs: AttributeSet?):super(context,attrs) {

    }

    constructor(context: Context, attrs: AttributeSet?,defStyleAttr:Int):super(context,attrs,defStyleAttr) {

    }

    override fun onScrolled(dx: Int, dy: Int) {

        if (dy > 0) {
            adapter.let{adapter ->
                if(layoutManager is LinearLayoutManager &&  (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == adapter?.itemCount!! - 1 ){
                    onReachEndOfListListener?.onReachEnd();
                }
            }


        }
        super.onScrolled(dx, dy)
    }

}
