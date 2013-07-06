package com.jucyzhang.rsfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Allocation.MipmapControl;
import android.renderscript.RenderScript;

class BitmapAllocation implements FutureAllocation {

  private Bitmap bitmap;

  private BitmapAllocation() {
  }

  static FutureAllocation create(Bitmap bitmap) {
    BitmapAllocation a = new BitmapAllocation();
    a.bitmap = bitmap;
    return a;
  }

  @Override
  public Allocation getFutureAllocation(Context context, RenderScript rs) {
    return Allocation.createFromBitmap(rs, bitmap, MipmapControl.MIPMAP_NONE,
        Allocation.USAGE_SCRIPT);
  }
}
