package com.swc.common.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.swc.common.R
import com.swc.common.extension.convertDpToPixel
import com.swc.common.ui.adapter.BaseAdapter
import com.swc.common.util.LOG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit


/**
Created by sangwn.choi on2020-07-08

 **/
class SRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var mSlideDisposable: Disposable? = null
    private var mScrollListener: OnScrollListener? = null
    private var mIsAutoSlide: Boolean = false
    private var mIsPagedLoadingEnabled: Boolean = false
    private var mOrientation: Int = VERTICAL
    private var mGridNumColumns: Int = 0

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SRecyclerView, 0, 0)
        try {
            mIsPagedLoadingEnabled = ta.getBoolean(R.styleable.SRecyclerView_pagedLoading, false)
            mIsAutoSlide = ta.getBoolean(R.styleable.SRecyclerView_autoSlide, false)
            mOrientation = ta.getInt(R.styleable.SRecyclerView_rvOrientation, VERTICAL)
            mGridNumColumns = ta.getInt(R.styleable.SRecyclerView_numGridCols, 0)
        } finally {
            ta.recycle()
        }
    }

    fun initialize(adapter: BaseAdapter<*>) {
        if (mOrientation == VERTICAL) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        } else if (mOrientation == HORIZONTAL){
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            clipToPadding = false
            setPadding(11.convertDpToPixel(context).toInt(), 0, 11.convertDpToPixel(context).toInt(), 0)
            PagerSnapHelper().attachToRecyclerView(this)

        } else {
            layoutManager = GridLayoutManager(context, mGridNumColumns)
        }
        this.adapter = adapter

        overScrollMode = View.OVER_SCROLL_NEVER
    }

    fun enablePagedLoading(listener: LoadMoreListener) {
        //adapter canLoadMore 활성화
//        adapter?.canLoadMore = true
        //paging

        mScrollListener = object: OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (layoutManager as? LinearLayoutManager)?.run {

                    //아래 / 오른쪽 방향 스크롤 경우에만 추가 로딩 진행.
                    if (dy > 0 && orientation == VERTICAL || dx > 0 && orientation == HORIZONTAL) {
                        val totalCount = itemCount
                        val visibleItemCount = childCount
                        val pastVisibleItems = (this as LinearLayoutManager).findFirstVisibleItemPosition()
                        LOG.e("scrolled, totalCount $totalCount, visibleItemCount $visibleItemCount, pastVisibleItems $pastVisibleItems, targetCount: ${adapter?.targetCount}")

                        //미노출된 아이템 개수가 LOAD_MORE_THRESHOLD (5개) 이상일때 추가 로딩 진행.
                        if (visibleItemCount + pastVisibleItems + LOAD_MORE_THRESHOLD >= totalCount) {
                            LOG.e("SRecyclerView try load more data")
                            listener.onLoadMore()
                        }
                    }
                }
            }
        }.apply {
            addOnScrollListener(this)
        }
    }


    override fun getAdapter(): BaseAdapter<*>? {
        return super.getAdapter() as? BaseAdapter<*>
    }

    override fun onDetachedFromWindow() {
        mSlideDisposable?.run {
            if (!isDisposed) {
                dispose()
//                LOG.e("autoslide dispose")
            }
            mSlideDisposable = null
        }

        mScrollListener?.run {
            removeOnScrollListener(this)
        }
        super.onDetachedFromWindow()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        LOG.e("onAttachedToWindow curPos ${(layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition()}")

        (layoutManager as? LinearLayoutManager)?.run {
            if (findLastCompletelyVisibleItemPosition() == NO_POSITION) {
                val lastPos = findLastVisibleItemPosition()
                val pos =
                    if (lastPos == NO_POSITION) {
                        0
                    } else {
                        lastPos
                    }

                smoothScrollToPosition(pos)
            }
        }

        mScrollListener?.run {
            addOnScrollListener(this)
        }



        adapter?.run {
            if (mIsAutoSlide && mSlideDisposable == null) {
                mSlideDisposable = Observable.interval(5000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({

                        val pos = (layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition()?: NO_POSITION
                        if (pos >= 0) {
                            smoothScrollToPosition((pos+1) % getDataCount())
                        }
//                        LOG.e("$it autoslide pos $pos")
                    }, {
//                        LOG.e("$it autoslide err...")

                    })
            }
        }


    }

    companion object {
        const val LOAD_MORE_THRESHOLD: Int = 5
    }

}

interface LoadMoreListener {
    fun onLoadMore()

}


