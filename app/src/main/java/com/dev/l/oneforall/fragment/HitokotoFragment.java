package com.dev.l.oneforall.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.l.oneforall.R;
import com.dev.l.oneforall.api.ApiManager;
import com.dev.l.oneforall.entity.hitokoto.Hitokoto;

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
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HitokotoFragment extends BaseFragment {
    @BindView(R.id.tv_hitokoto)
    TextView tvHitokoto;
    @BindView(R.id.tv_from)
    TextView tvFrom;

    private Unbinder unbinder;
    private CompositeDisposable compositeDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hitokoto, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startGetHitokoto();
    }

    private void startGetHitokoto() {
        ApiManager.getInstance().getHitokotoApi()
                .getHitokotoData()
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) {
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) {
                                return Observable.just(1).delay(5, TimeUnit.SECONDS);
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Hitokoto>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (compositeDisposable == null) {
                            compositeDisposable = new CompositeDisposable();
                        }
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Hitokoto hitokoto) {
                        tvHitokoto.setText(hitokoto.getHitokoto());
                        tvFrom.setText(String.format("——%s", hitokoto.getFrom()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
