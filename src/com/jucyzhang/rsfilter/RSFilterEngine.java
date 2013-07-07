package com.jucyzhang.rsfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.Type;

/**
 * Engine of RSFilters.
 * 
 * @usage Simply follow three steps: getInstance() -> addFilter() -> compute()
 * 
 * @author jucyzhang
 * 
 */
public class RSFilterEngine {

  private RenderScript rs;
  private Context context;

  private List<RSFilter> filters = new ArrayList<RSFilter>();

  /**
   * create a new instance of RSFilterEngine.
   * 
   * @param context
   * @return
   */
  public static RSFilterEngine getInstance(Context context) {
    return new RSFilterEngine(context);
  }

  /**
   * add a filter to the engine.
   * 
   * @param filter
   * @return a RSFilterResult object holding future result of the filter. If you
   *         don't need the result anymore, clear the reference immediately for
   *         memory performance.
   */
  public RSFilterResult addFilter(RSFilter filter) {
    filters.add(filter);
    return new FutureAllocationWrapper(filter);
  }

  /**
   * compute all filters inside the engine
   * 
   * @return result Bitmap of the ***LAST*** filter in the engine.
   */
  public Bitmap compute() {
    ListIterator<RSFilter> iterator = filters.listIterator();
    RSFilter filter = null;
    while (iterator.hasNext()) {
      filter = iterator.next();
      filter.createScript(context, rs);
      filter.processImage(context, rs);
      filter.destroy(context, rs);
      iterator.remove();
    }
    return getBitmapFromAllocation(filter.getResult());
  }

  /**
   * retrieve the result inside the RSFilterResult
   * 
   * @param result
   * @return the result Bitmap of filter.
   */
  public Bitmap getBitmapFromResult(RSFilterResult result) {
    Allocation a = result.getFutureAllocation();
    if (a != null) {
      return getBitmapFromAllocation(a);
    } else {
      return null;
    }
  }

  private RSFilterEngine(Context context) {
    rs = RenderScript.create(context);
    this.context = context;
  }

  private Bitmap getBitmapFromAllocation(Allocation a) {
    Type type = a.getType();
    Bitmap bitmap = Bitmap.createBitmap(type.getX(), type.getY(),
        Bitmap.Config.ARGB_8888);
    a.copyTo(bitmap);
    return bitmap;
  }

  private static class FutureAllocationWrapper extends RSFilterResult {
    private RSFilter filter;

    FutureAllocationWrapper(RSFilter filter) {
      this.filter = filter;
    }

    @Override
    Allocation getFutureAllocation() {
      return filter.getResult();
    }
  }
}
