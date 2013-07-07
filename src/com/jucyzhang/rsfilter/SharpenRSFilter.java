package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;

class SharpenRSFilter extends BaseRSFilter<ScriptC_convolutionint> {

  private static final int[] FACTOR = { 0, -1, 0, -1, 5, -1, 0, -1, 0 };

  @Override
  protected ScriptC_convolutionint onCreateScript(Context context,
      RenderScript rs) {
    return new ScriptC_convolutionint(rs);
  }

  @Override
  protected Allocation onProcessImage(Context context, RenderScript rs) {
    Allocation origin = getAllocation(context, rs, ORIGINAL);
    ScriptC_convolutionint convolution = getScript();
    Allocation factor = Allocation.createSized(rs, Element.I32(rs), 9,
        Allocation.USAGE_SCRIPT);
    factor.copyFrom(FACTOR);
    convolution.bind_convolution_factors(factor);
    convolution.bind_gPixels(origin);
    Allocation out = Allocation.createTyped(rs, origin.getType());
    convolution.invoke_filter(convolution, origin, out);
    return out;
  }

}
