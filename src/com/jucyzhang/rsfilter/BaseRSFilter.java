package com.jucyzhang.rsfilter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;

abstract class BaseRSFilter<T extends ScriptC>{

  private Allocation result;
  private T script;

  private Map<String, RSFilterResult> map = new HashMap<String, RSFilterResult>();

  /**
   * getScript() will be available after this method.
   * 
   * @param context
   * @param rs
   * @return
   */
  protected abstract T onCreateScript(Context context, RenderScript rs);

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

  protected final Allocation getAllocation(Context context, RenderScript rs,
      String key) {
    RSFilterResult a = map.get(key);
    if (a == null) {
      return null;
    } else {
      return a.getFutureAllocation(context, rs);
    }
  }

  protected final T getScript() {
    return script;
  }

  public final void setAllocation(String key, Bitmap bitmap) {
    map.put(key, BitmapFutureAllocation.create(bitmap));
  }

  public final void setAllocation(String key, RSFilterResult value) {
    map.put(key, value);
  }

  public final void setRawAllocation(String key, int id) {
    map.put(key, LazyLoadBitmapFutureAllocation.fromRaw(id));
  }

  public final void setDrawableAllocation(String key, int id) {
    map.put(key, LazyLoadBitmapFutureAllocation.fromDrawable(id));
  }

  final void createScript(Context context, RenderScript rs) {
    script = onCreateScript(context, rs);
  }

  final Allocation processImage(Context context, RenderScript rs) {
    this.result = onProcessImage(context, rs);
    return result;
  }

  final void destroy(Context context, RenderScript rs) {
    map.clear();
    script.destroy();
    script = null;
    onDestroy(context, rs);
  }

  final Allocation getResult() {
    return result;
  }
}
