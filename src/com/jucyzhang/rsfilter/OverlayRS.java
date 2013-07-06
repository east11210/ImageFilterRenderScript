package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

import com.android.example.hellocompute.R;

public class OverlayRS extends BaseRSFilter<ScriptC_overlay> {

  public static final String DST = "dst";
  public static final String SRC = "src";

  @Override
  protected Allocation onProcessImage(Context context, RenderScript rs) {
    ScriptC_overlay overlay = new ScriptC_overlay(rs, context.getResources(),
        R.raw.overlay);
    Allocation dstAll = getAllocation(context, rs, DST);
    Allocation srcAll = getAllocation(context, rs, SRC);
    Allocation outAll = Allocation.createTyped(rs, dstAll.getType());
    overlay.bind_dst(dstAll);
    overlay.bind_src(srcAll);
    overlay.invoke_filter(overlay, dstAll, outAll);
    return outAll;
  }

  @Override
  public void onDestroy(Context context, RenderScript rs) {
    
  }

}
