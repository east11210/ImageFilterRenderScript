package com.android.example.hellocompute;

import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.BaseObj;
import android.renderscript.RenderScript;

public abstract class BaseRSFilter<T extends BaseObj> implements
    FutureAllocation {

  private Allocation result;

  private Map<String, FutureAllocation> map;

  protected abstract Allocation onProcessImage(Context context, RenderScript rs);

  public abstract void onDestroy(Context context, RenderScript rs);

  @Override
  public final Allocation getFutureAllocation(Context context, RenderScript rs) {
    return result;
  }

  protected final Allocation getAllocation(Context context, RenderScript rs,
      String key) {
    FutureAllocation a = map.get(key);
    if (a == null) {
      return null;
    } else {
      return a.getFutureAllocation(context, rs);
    }
  }

  public final void setAllocation(String key, Bitmap bitmap) {
    map.put(key, BitmapAllocation.create(bitmap));
  }

  public final void setAllocation(String key, FutureAllocation value) {
    map.put(key, value);
  }

  final Allocation processImage(Context context, RenderScript rs) {
    this.result = onProcessImage(context, rs);
    return result;
  }
}
