package com.dev.l.oneforall.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dev.l.oneforall.R;
import com.dev.l.oneforall.adapter.UnsplashAdapter;
import com.dev.l.oneforall.api.ApiManager;
import com.dev.l.oneforall.callback.PhotoDiffCallback;
import com.dev.l.oneforall.entity.unsplash.PhotoInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class UnsplashFragment extends BaseFragment {
    private static final String TAG = "UnsplashFragment";
    @BindView(R.id.rv_unsplash)
    RecyclerView rvUnsplash;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private Unbinder unbinder;
    private List<PhotoInfo> photoList = new ArrayList<>();
    private int currentPage = 1;
    private int perPage = 10;
    private int currentRetryCount = 0;
    private int maxRetryCount = 3;
    private int waitRetryTime = 5;
    private boolean isLoadingMore;
    private boolean needCleanList;
    private UnsplashAdapter adapter;
    private DiffUtil.DiffResult diffResult;
    private CompositeDisposable compositeDisposable;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unsplash, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUnsplashPhoto();
    }

    private void getUnsplashPhoto() {
        isLoadingMore = true;
        Log.e(TAG, "getUnsplashPhoto: 正在加载第" + currentPage + "页数据");
        ApiManager.getInstance().getUnsplashApi()
                .getUnsplashData(currentPage, perPage)
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) {
                                Log.e(TAG, "发生异常 = " + throwable.toString());
                                if (throwable instanceof IOException) {
                                    if (currentRetryCount < maxRetryCount) {
                                        // 记录重试次数
                                        currentRetryCount++;
                                        Log.e(TAG, "重试次数 = " + currentRetryCount);
                                        return Observable.just(1).delay(waitRetryTime, TimeUnit.SECONDS);
                                    } else {
                                        // 若重试次数已 > 设置重试次数，则不重试
                                        isLoadingMore = false;
                                        currentRetryCount = 0;
                                        return Observable.error(new Throwable("重试次数已超过设置次数 = " + currentRetryCount + "，即 不再重试"));
                                    }
                                }
                                // 若发生的异常不属于I/O异常，则不重试
                                else {
                                    isLoadingMore = false;
                                    return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                                }
                            }
                        });
                    }
                })
                .doOnNext(new Consumer<List<PhotoInfo>>() {
                    @Override
                    public void accept(List<PhotoInfo> photoInfos) {
                        if (needCleanList) {
                            photoList.clear();
                        }
                        // TODO: 2018/11/9 弱智服务器不知道为什么第二页开始会返回重复的数据导致照片重复，以后再解决
                        photoList.addAll(photoInfos);
                        PhotoDiffCallback callback = new PhotoDiffCallback(adapter.getPhotoInfoList(), photoList);
                        diffResult = DiffUtil.calculateDiff(callback);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PhotoInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (compositeDisposable == null) {
                            compositeDisposable = new CompositeDisposable();
                        }
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<PhotoInfo> photoInfos) {
                        diffResult.dispatchUpdatesTo(adapter);
                        adapter.setPhotoInfoList(photoList);
                        rvUnsplash.scrollToPosition(adapter.getItemCount() - perPage - 1);
                        currentPage++;
                        needCleanList = false;
                        refreshLayout.setRefreshing(false);
                        isLoadingMore = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.toString());
                        refreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext());
        rvUnsplash.setLayoutManager(layoutManager);
        adapter = new UnsplashAdapter(getContext());

        adapter.setOnPhotoClickListener(new UnsplashAdapter.OnPhotoClickListener() {
            @Override
            public void onPhotoClick(View view, int position) {
                //点击图片

            }
        });

        adapter.setOnDownloadClickListener(new UnsplashAdapter.OnDownloadClickListener() {
            @Override
            public void onDownloadClick(int position) {
                //点击下载按钮

            }
        });

        rvUnsplash.setAdapter(adapter);

        //设置上拉加载更多
        rvUnsplash.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition == photoList.size() && !refreshLayout.isRefreshing()) {
                    //滑动到footerView时加载更多
                    if (!isLoadingMore && currentPage != 1) {
                        getUnsplashPhoto();
                    }
                }
            }
        });

        //开启刷新栏
        refreshLayout.setRefreshing(true);
        //设置下拉刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "onRefresh: 321");
                currentPage = 1;
                needCleanList = true;
                getUnsplashPhoto();
            }
        });


    }

    @Override
    public void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        unbinder.unbind();
        super.onDestroy();
    }
}
