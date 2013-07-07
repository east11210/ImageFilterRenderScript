package com.jucyzhang.rsfilter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

/**
 * @usage set the required allocations and add itself to @{link RSFilterEngine}
 * @author jucyzhang
 */
public abstract class RSFilter {

  public static final String SRC = "src";
  public static final String DST = "dst";
  public static final String ORIGINAL = "original";
  public static final String TABLE = "table";

  private Allocation result;
  private Map<String, FutureAllocation> map = new HashMap<String, FutureAllocation>();

  /**
   * Set a bitmap as input.
   * 
   * @param key
   * @param bitmap
   * @return filter itself for chain calling.
   */
  public final RSFilter setAllocation(String key, Bitmap bitmap) {
    map.put(key, BitmapFutureAllocation.create(bitmap));
    return this;
  }

  /**
   * Set the Result of any other filters as input.
   * 
   * @param key
   * @param value
   * @return filter itself for chain calling.
   */
  public final RSFilter setAllocation(String key, RSFilterResult value) {
    map.put(key, value);
    return this;
  }

  /**
   * Set a resource from ***RAW*** as input. the resource will load lazily.
   * 
   * @param key
   * @param rawId
   * @return filter itself for chain calling.
   */
  public final RSFilter setAllocation(String key, int rawId) {
    map.put(key, LazyLoadBitmapFutureAllocation.fromRaw(rawId));
    return this;
  }

  /**
   * Set a resource from ***DRAWABLE*** as input. the resource will load lazily.
   * 
   * @param key
   * @param id
   * @return filter itself for chain calling.
   */
  public final RSFilter setDrawableAllocation(String key, int id) {
    map.put(key, LazyLoadBitmapFutureAllocation.fromDrawable(id));
    return this;
  }

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
