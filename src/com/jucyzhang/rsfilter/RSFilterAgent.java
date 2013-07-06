package com.jucyzhang.rsfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.Type;

public class RSFilterAgent {

  private RenderScript rs;
  private Context context;

  private List<BaseRSFilter<?>> filters = new ArrayList<BaseRSFilter<?>>();

  public static RSFilterAgent getAgent(Context context) {
    return new RSFilterAgent(context);
  }

  public RSFilterResult addFilter(BaseRSFilter<?> filter) {
    filters.add(filter);
    return new FutureAllocationWrapper(filter);
  }

  public Bitmap compute() {
    ListIterator<BaseRSFilter<?>> iterator = filters.listIterator();
    BaseRSFilter<?> filter = null;
    while (iterator.hasNext()) {
      filter = iterator.next();
      filter.createScript(context, rs);
      filter.processImage(context, rs);
      filter.destroy(context, rs);
      iterator.remove();
    }
    return getBitmapFromAllocation(filter.getResult());
  }

  private RSFilterAgent(Context context) {
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

  private static class FutureAllocationWrapper implements RSFilterResult {
    private BaseRSFilter<?> filter;

    public FutureAllocationWrapper(BaseRSFilter<?> filter) {
      this.filter = filter;
    }

    @Override
    public Allocation getFutureAllocation(Context context, RenderScript rs) {
      return filter.getResult();
    }
  }
}
