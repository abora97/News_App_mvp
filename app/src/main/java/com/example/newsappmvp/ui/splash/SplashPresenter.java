package com.example.newsappmvp.ui.splash;

import android.content.Intent;
import android.os.Handler;

class SplashPresenter {

    private SplashView splashView;

    SplashPresenter(SplashView splashView) {
        this.splashView = splashView;
        timerStart();
    }

    private void timerStart() {
        new Handler().postDelayed(() -> splashView.getStarted(), 3000);
    }
}
