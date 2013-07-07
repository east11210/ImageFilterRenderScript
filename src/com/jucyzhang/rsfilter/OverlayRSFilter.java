package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

class OverlayRSFilter extends BaseRSFilter<ScriptC_overlay> {

  @Override
  protected Allocation onProcessImage(Context context, RenderScript rs) {
    ScriptC_overlay overlay = getScript();
    Allocation dstAll = getAllocation(context, rs, DST);
    Allocation srcAll = getAllocation(context, rs, SRC);
    Allocation outAll = Allocation.createTyped(rs, dstAll.getType());
    overlay.bind_dst(dstAll);
    overlay.bind_src(srcAll);
    overlay.invoke_filter(overlay, dstAll, outAll);
    return outAll;
  }

  @Override
  protected ScriptC_overlay onCreateScript(Context context, RenderScript rs) {
    return new ScriptC_overlay(rs);
  }

}
