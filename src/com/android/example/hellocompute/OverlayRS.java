package com.android.example.hellocompute;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.Allocation.MipmapControl;

public class OverlayRS {
  public static Bitmap blur(Context context, Bitmap dst, Bitmap src) {
    RenderScript rs = RenderScript.create(context);
    Allocation dstAll = Allocation.createFromBitmap(rs, dst,
        MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
    Allocation srcAll = Allocation.createFromBitmap(rs, src,
        MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
    Allocation outAll = Allocation.createTyped(rs, dstAll.getType());
    ScriptC_overlay overlay = new ScriptC_overlay(rs, context.getResources(),
        R.raw.overlay);
    overlay.bind_dst(dstAll);
    overlay.bind_src(srcAll);
    overlay.invoke_filter(overlay, dstAll, outAll);
    Bitmap res = Bitmap.createBitmap(dst.getWidth(), dst.getHeight(),
        Bitmap.Config.ARGB_8888);
    outAll.copyTo(res);
    return res;
  }
}
