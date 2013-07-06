package com.jucyzhang.rsfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Allocation.MipmapControl;
import android.renderscript.RenderScript;

import com.android.example.hellocompute.R;

public class GuassianBlurRS {
  public static Bitmap blur(Context context, Bitmap bitmap) {
    RenderScript rs = RenderScript.create(context);
    Allocation inBitmap = Allocation.createFromBitmap(rs, bitmap,
        MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
    Allocation outBitmap = Allocation.createTyped(rs, inBitmap.getType());
    ScriptC_convolution convolution = new ScriptC_convolution(rs,
        context.getResources(), R.raw.convolution);
    convolution.bind_gPixels(inBitmap);
    convolution.invoke_filter(convolution, inBitmap, outBitmap);
    Bitmap res = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
        Bitmap.Config.ARGB_8888);
    outBitmap.copyTo(res);
    return res;
  }
}
