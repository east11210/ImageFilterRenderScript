package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

class ScreenRSFilter extends BaseRSFilter<ScriptC_screen> {

  @Override
  protected ScriptC_screen onCreateScript(Context context, RenderScript rs) {
    return new ScriptC_screen(rs);
  }

  @Override
  protected Allocation onProcessImage(Context context, RenderScript rs) {
    Allocation dst = getAllocation(context, rs, DST);
    Allocation src = getAllocation(context, rs, SRC);
    Allocation out = Allocation.createTyped(rs, dst.getType());
    ScriptC_screen screen = getScript();
    screen.bind_src(src);
    screen.invoke_filter(screen, dst, out);
    return out;
  }

}
