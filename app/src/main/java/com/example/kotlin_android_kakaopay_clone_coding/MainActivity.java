package com.example.kotlin_android_kakaopay_clone_coding;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.kotlin_android_kakaopay_clone_coding.databinding.ActivityMainBinding;
import com.example.kotlin_android_kakaopay_clone_coding.ui.main.SectionsPagerAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bi;
    private LinearLayout mBottomSheet;
    private ImageView ivBarcode;
    private ImageView ivQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        mBottomSheet = findViewById(R.id.ll_bottom_sheet);
        ivQR = findViewById(R.id.iv_qr);
        ivBarcode = findViewById(R.id.iv_barcode);
        Drawable drawable = ivQR.getDrawable();
        drawable.setAlpha(0);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);

        int height = bottomSheetBehavior.getPeekHeight();
        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // set callback for changes
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // Called every time when the bottom sheet changes its state.
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                transitionBottomSheetBackgroundColor(slideOffset);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ivBarcode.getLayoutParams();
                layoutParams.topMargin = (int) (dpToPx(250)*slideOffset);
                ivBarcode.setLayoutParams(layoutParams);
                ivQR.animate().scaleX(slideOffset).scaleY(slideOffset).setDuration(0).start();

            }
        });
    }

    private float prevOffset = 0f;
    private void transitionBottomSheetBackgroundColor(float slideOffset) {
        int colorFrom = getResources().getColor(R.color.color_1d1d1d);
        int colorTo = getResources().getColor(R.color.colorPrimary);
        Drawable alpha = ivQR.getDrawable();
        alpha.setAlpha((int) (slideOffset*255f));
//        mBottomSheet.setBackgroundColor(interpolateColor(slideOffset,
//                colorFrom, colorTo));
    }

    /**
     * This function returns the calculated in-between value for a color
     * given integers that represent the start and end values in the four
     * bytes of the 32-bit int. Each channel is separately linearly interpolated
     * and the resulting calculated values are recombined into the return value.
     *
     * @param fraction The fraction from the starting to the ending values
     * @param startValue A 32-bit int value representing colors in the
     * separate bytes of the parameter
     * @param endValue A 32-bit int value representing colors in the
     * separate bytes of the parameter
     * @return A value that is calculated to be the linearly interpolated
     * result, derived by separating the start and end values into separate
     * color channels and interpolating each one separately, recombining the
     * resulting values in the same way.
     */
    private int interpolateColor(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;
        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;
        return ((startA + (int) (fraction * (endA - startA))) << 24) |
                ((startR + (int) (fraction * (endR - startR))) << 16) |
                ((startG + (int) (fraction * (endG - startG))) << 8) |
                ((startB + (int) (fraction * (endB - startB))));
    }

    public int dpToPx(int dp) { float density = getResources().getDisplayMetrics().density; return Math.round((float) dp * density); }

}
