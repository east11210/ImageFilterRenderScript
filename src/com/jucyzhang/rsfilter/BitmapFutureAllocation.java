package com.jucyzhang.rsfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Allocation.MipmapControl;
import android.renderscript.RenderScript;

class BitmapFutureAllocation implements FutureAllocation {

  private Bitmap bitmap;

  private BitmapFutureAllocation() {
  }

  static FutureAllocation create(Bitmap bitmap) {
    BitmapFutureAllocation a = new BitmapFutureAllocation();
    a.bitmap = bitmap;
    return a;
  }

  @Override
  public Allocation getFutureAllocation(Context context, RenderScript rs) {
    return Allocation.createFromBitmap(rs, bitmap, MipmapControl.MIPMAP_NONE,
        Allocation.USAGE_SCRIPT);
  }
}
