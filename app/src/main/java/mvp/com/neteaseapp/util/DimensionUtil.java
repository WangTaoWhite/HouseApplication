package mvp.com.neteaseapp.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by wangtao on 2018/5/23.
 */

public class DimensionUtil {

    public static int pxFromSp(Context context, int sp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float scale = displayMetrics.scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    public static int dpFromPx(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int density = displayMetrics.densityDpi;
        return px / (density / 160);
    }
}
