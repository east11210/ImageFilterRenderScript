package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

class SrcOverRSFilter extends BaseRSFilter<ScriptC_srcover> {

  @Override
  protected ScriptC_srcover onCreateScript(Context context, RenderScript rs) {
    return new ScriptC_srcover(rs);
  }

  @Override
  protected Allocation onProcessImage(Context context, RenderScript rs) {
    Allocation srcAllo = getAllocation(context, rs, SRC);
    Allocation dstAllo = getAllocation(context, rs, DST);
    Allocation outAllo = Allocation.createTyped(rs, srcAllo.getType());
    ScriptC_srcover srcover = getScript();
    srcover.bind_dst(dstAllo);
    srcover.invoke_filter(srcover, srcAllo, outAllo);
    return outAllo;
  }
}
