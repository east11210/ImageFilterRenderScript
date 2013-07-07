package com.jucyzhang.rsfilter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

public abstract class RSFilter {

  public static final String SRC = "src";
  public static final String DST = "dst";
  public static final String ORIGINAL = "original";
  public static final String TABLE = "table";

  private Allocation result;
  private Map<String, FutureAllocation> map = new HashMap<String, FutureAllocation>();

  /**
   * Process Image in this method.
   * 
   * @param context
   * @param rs
   * @return
   */
  protected abstract Allocation onProcessImage(Context context, RenderScript rs);

  /**
   * Hook for Objects that need extra release procedure.
   * 
   * @param context
   * @param rs
   */
  protected void onDestroy(Context context, RenderScript rs) {

  }

  public final RSFilter setAllocation(String key, Bitmap bitmap) {
    map.put(key, BitmapFutureAllocation.create(bitmap));
    return this;
  }

  public final RSFilter setAllocation(String key, RSFilterResult value) {
    map.put(key, value);
    return this;
  }

  public final RSFilter setAllocation(String key, int rawId) {
    map.put(key, LazyLoadBitmapFutureAllocation.fromRaw(rawId));
    return this;
  }

  public final RSFilter setDrawableAllocation(String key, int id) {
    map.put(key, LazyLoadBitmapFutureAllocation.fromDrawable(id));
    return this;
  }

  final Allocation getAllocation(Context context, RenderScript rs, String key) {
    FutureAllocation future = map.get(key);
    if (future == null) {
      return null;
    } else {
      return future.getFutureAllocation(context, rs);
    }
  }

  void createScript(Context context, RenderScript rs) {

  }

  Allocation processImage(Context context, RenderScript rs) {
    this.result = onProcessImage(context, rs);
    return result;
  }

  void destroy(Context context, RenderScript rs) {
    map.clear();
    onDestroy(context, rs);
  }

  final Allocation getResult() {
    return result;
  }
}
