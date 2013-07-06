package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

public class MultiplyRSFilter extends BaseRSFilter<ScriptC_multiply> {

  public static final String DST = "dst";
  public static final String SRC = "src";

  @Override
  protected ScriptC_multiply onCreateScript(Context context, RenderScript rs) {
    return new ScriptC_multiply(rs);
  }

  @Override
  protected Allocation onProcessImage(Context context, RenderScript rs) {
    Allocation dst = getAllocation(context, rs, DST);
    Allocation src = getAllocation(context, rs, SRC);
    Allocation out = Allocation.createTyped(rs, dst.getType());
    ScriptC_multiply multiply = getScript();
    multiply.bind_src(src);
    multiply.invoke_filter(multiply, dst, out);
    return out;
  }

}
