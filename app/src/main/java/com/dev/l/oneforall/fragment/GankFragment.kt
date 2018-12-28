package com.dev.l.oneforall.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dev.l.oneforall.R
import com.dev.l.oneforall.adapter.GankAdapter
import com.dev.l.oneforall.adapter.GankAdapter.OnGankClickListener
import com.dev.l.oneforall.api.ApiManager
import com.dev.l.oneforall.entity.gank.Gank
import com.dev.l.oneforall.entity.gank.GankResult
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_gank.*

class GankFragment : BaseFragment() {
    private val TAG = "GankFragment"
    lateinit var gankList: ArrayList<Gank>
    lateinit var adapter: GankAdapter
    private var compositeDisposable: CompositeDisposable? = null
    private var needCleanList: Boolean = true
    private var page: Int = 1;
    private var isLoadingMore: Boolean = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getGankData()
    }

    private fun initRecyclerView() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvGank.layoutManager = layoutManager
        gankList = ArrayList()
        adapter = GankAdapter(requireContext())
        adapter.gankList = gankList
        adapter.onGankClickListener = object : OnGankClickListener {
            override fun onGankClick(position: Int) {
                Toast.makeText(context, "position:$position", Toast.LENGTH_SHORT).show()
            }
        }

        rvGank.adapter = adapter

        rvGank.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(IntArray(2))
                var tempMaxPosition = Integer.MIN_VALUE
                for (i in lastVisibleItemPositions) {
                    if (tempMaxPosition < i) {
                        tempMaxPosition = i
                    }
                }
                if (tempMaxPosition + 1 == layoutManager.itemCount && !srlRefresh.isRefreshing) {
                    srlRefresh.isRefreshing = true
                    getGankData()
                }
            }
        })

        srlRefresh.isRefreshing = true
        srlRefresh.setOnRefreshListener {
            page = 1
            needCleanList = true
            getGankData()
        }
    }

    private fun getGankData() {
        isLoadingMore = true
        ApiManager.getInstance().gankApi
                .getGankData(10, page)
                .doOnNext {
                    if (it.error) return@doOnNext
                    if (needCleanList) gankList.clear()
                    gankList.addAll(it.results)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GankResult> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                        if (compositeDisposable == null) compositeDisposable = CompositeDisposable()
                        compositeDisposable?.add(d)
                    }

                    override fun onNext(t: GankResult) {
//                        diffResult.dispatchUpdatesTo(adapter)
//                        adapter.setPhotoInfoList(photoList)
//                        rvUnsplash.scrollToPosition(adapter.getItemCount() - perPage - 1)
                        adapter.notifyDataSetChanged()
                        page++
                        needCleanList = false
                        srlRefresh.isRefreshing = false
                        isLoadingMore = false
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "get gank data error:" + e.message)
                        srlRefresh.isRefreshing = false
                    }
                })

    }
}