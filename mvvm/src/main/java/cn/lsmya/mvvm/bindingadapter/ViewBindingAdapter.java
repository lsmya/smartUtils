package cn.lsmya.mvvm.bindingadapter;

import android.os.SystemClock;
import android.view.View;

import androidx.databinding.BindingAdapter;

public class ViewBindingAdapter {

    @BindingAdapter({"android:onClick", "android:clickable"})
    public static void setOnClick(View view, View.OnClickListener clickListener,
                                  boolean clickable) {
        setOnClick(view, clickListener);
        view.setClickable(clickable);
    }

    @BindingAdapter("visibleOrGone")
    public static void visibleOrGone(View view, boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("visibleOrInvisible")
    public static void visibleOrInvisible(View view, boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("goneByString")
    public static void goneByString(View view, String visibility) {
        view.setVisibility(visibility != null && !"".equals(visibility) ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"android:onClick"})
    public static void setOnClick(View view, final View.OnClickListener clickListener) {
        final long[] mHits = new long[2];
        view.setOnClickListener(v -> {
            System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
            mHits[mHits.length - 1] = SystemClock.uptimeMillis();
            if (mHits[0] < (SystemClock.uptimeMillis() - 400)) {
                clickListener.onClick(v);
            }
        });
    }
}
