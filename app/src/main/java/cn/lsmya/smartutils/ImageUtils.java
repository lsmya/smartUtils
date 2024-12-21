package cn.lsmya.smartutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.palette.graphics.Palette;

import java.util.HashMap;
import java.util.Map;

import cn.lsmya.smart.utils.BitmapUtil;

public class ImageUtils {
    public static void getPicUrl(Context context, String path, ImageView imageView1, ImageView imageView2) {
        Bitmap bitmap = BitmapUtil.getBitmapByUrl(context, path);
// 假设bitmap是你需要处理的图片
        Palette.from(bitmap).generate(palette -> {
            // 提取深色
            Palette.Swatch darkVibrantSwatch = palette.getDarkMutedSwatch();
            Log.e("====",""+(darkVibrantSwatch == null));
            if (darkVibrantSwatch != null) {
                // 使用getRgb()获取颜色值，使用getBodyTextColor()获取文本颜色
                int darkColor = darkVibrantSwatch.getRgb();
                Drawable darkDrawable = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false));
                // 保存为图片等操作...
                imageView1.setImageDrawable(darkDrawable);
            }

            // 提取浅色
            Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
            if (lightVibrantSwatch != null) {
                int lightColor = lightVibrantSwatch.getRgb();
                Drawable lightDrawable = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false));
                // 保存为图片等操作...
                imageView2.setImageDrawable(lightDrawable);
            }
        });

    }

    public static Map<Integer, Bitmap> splitImageByColor(Bitmap sourceBitmap) {
        Map<Integer, Bitmap> splitBitmaps = new HashMap<>();
        Log.e("==",sourceBitmap.getHeight()+"");
        Log.e("==",sourceBitmap.getWidth()+"");
        for (int y = 0; y < sourceBitmap.getHeight(); y++) {
            for (int x = 0; x < sourceBitmap.getWidth(); x++) {
                int pixelColor = sourceBitmap.getPixel(x, y);
                int red = Color.red(pixelColor);
                int green = Color.green(pixelColor);
                int blue = Color.blue(pixelColor);
                // 可以根据需要拆分的颜色数量进行更复杂的颜色分类
                int colorKey = Color.rgb(red, green, blue);
                Bitmap targetBitmap = splitBitmaps.get(colorKey);
                if (targetBitmap == null) {
                    targetBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), sourceBitmap.getConfig());
                    splitBitmaps.put(colorKey, targetBitmap);
                }
                // 设置新位置的像素
                targetBitmap.setPixel(x, y, pixelColor);
            }
        }
        return splitBitmaps;
    }

}
