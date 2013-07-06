package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;

public class GuassianBlurRSFilter extends BaseRSFilter<ScriptC_convolutionint> {
  private static final int[] BLUR = { 1, 2, 1, 2, 4, 2, 1, 2, 1 };

  public static final String ORIGIN = "origin";

  @Override
  protected ScriptC_convolutionint onCreateScript(Context context,
      RenderScript rs) {
    return new ScriptC_convolutionint(rs);
  }

  @Override
  protected Allocation onProcessImage(Context context, RenderScript rs) {
    Allocation blur = Allocation.createSized(rs, Element.I32(rs), 9,
        Allocation.USAGE_SCRIPT);
    blur.copyFrom(BLUR);
    ScriptC_convolutionint convolution = getScript();
    convolution.bind_convolution_factors(blur);
    Allocation in = getAllocation(context, rs, ORIGIN);
    convolution.bind_gPixels(in);
    Allocation out = Allocation.createTyped(rs, in.getType());
    convolution.invoke_filter(convolution, in, out);
    return out;
  }
}
