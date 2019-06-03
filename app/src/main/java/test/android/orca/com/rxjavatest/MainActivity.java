package test.android.orca.com.rxjavatest;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Observable<String> animalsObservable ;
    Observer<String> animalsObserver ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animalsObservable = Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
        findViewById(R.id.action).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                animalsObserver = getAnimalsObserver();
                // observer subscribing to observable
                // observer subscribing to observable
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(animalsObserver);
            }
        });

    }
    private Observer<String> getAnimalsObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("Test", "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d("Test", "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Test", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("Test", "All items are emitted!");
            }
        };
    }
}
