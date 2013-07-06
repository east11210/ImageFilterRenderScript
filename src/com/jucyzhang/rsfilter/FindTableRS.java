package com.jucyzhang.rsfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Allocation.MipmapControl;
import android.renderscript.RenderScript;

public class FindTableRS {
  public static Bitmap blur(Context context, Bitmap bitmap,Bitmap tableB) {
    RenderScript rs = RenderScript.create(context);
    Allocation inBitmap = Allocation.createFromBitmap(rs, bitmap,
        MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
    Allocation tableBitmap = Allocation.createFromBitmap(rs, tableB,
        MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
    Allocation outBitmap = Allocation.createTyped(rs, inBitmap.getType());
    ScriptC_findtable table = new ScriptC_findtable(rs, context.getResources(),
        R.raw.findtable);
    table.bind_table(tableBitmap);
    table.invoke_filter(table, inBitmap, outBitmap);
    Bitmap res = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
        Bitmap.Config.ARGB_8888);
    outBitmap.copyTo(res);
    return res;
  }
}
