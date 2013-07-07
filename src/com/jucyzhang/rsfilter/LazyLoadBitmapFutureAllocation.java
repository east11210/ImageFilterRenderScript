package com.jucyzhang.rsfilter;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Allocation.MipmapControl;
import android.renderscript.RenderScript;

class LazyLoadBitmapFutureAllocation implements FutureAllocation {

  private int rawId = -1;
  private int drawableId = -1;

  static FutureAllocation fromRaw(int id) {
    LazyLoadBitmapFutureAllocation a = new LazyLoadBitmapFutureAllocation();
    a.rawId = id;
    return a;
  }

  static FutureAllocation fromDrawable(int id) {
    LazyLoadBitmapFutureAllocation a = new LazyLoadBitmapFutureAllocation();
    a.drawableId = id;
    return a;
  }

  @Override
  public Allocation getFutureAllocation(Context context, RenderScript rs) {
    if (rawId != -1) {
      InputStream is = context.getResources().openRawResource(rawId);
      Bitmap b = BitmapFactory.decodeStream(is);
      try {
        is.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return Allocation.createFromBitmap(rs, b, MipmapControl.MIPMAP_NONE,
          Allocation.USAGE_SCRIPT);
    } else if (drawableId != -1) {
      Bitmap b = BitmapFactory.decodeResource(context.getResources(),
          drawableId);
      return Allocation.createFromBitmap(rs, b, MipmapControl.MIPMAP_NONE,
          Allocation.USAGE_SCRIPT);
    }
    return null;
  }

  private LazyLoadBitmapFutureAllocation() {
  }

}
