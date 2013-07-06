package com.jucyzhang.rsfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.renderscript.Allocation;
import android.renderscript.Allocation.MipmapControl;
import android.renderscript.RenderScript;

public class SrcOverRS {
  public static Bitmap blur(Context context, Bitmap src, Bitmap dst) {
    RenderScript rs = RenderScript.create(context);
    Allocation srcAllo = Allocation.createFromBitmap(rs, src,
        MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
    Allocation dstAllo = Allocation.createFromBitmap(rs, dst,
        MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
    Allocation outAllo = Allocation.createTyped(rs, srcAllo.getType());
    ScriptC_srcover srcover = new ScriptC_srcover(rs, context.getResources(),
        R.raw.srcover);
    srcover.bind_dst(dstAllo);
    srcover.invoke_filter(srcover, srcAllo, outAllo);
    Bitmap result = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
        Config.ARGB_8888);
    outAllo.copyTo(result);
    return result;
  }
}
