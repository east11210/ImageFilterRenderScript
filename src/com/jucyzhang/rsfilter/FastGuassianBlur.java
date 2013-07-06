package com.jucyzhang.rsfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.renderscript.Allocation;
import android.renderscript.Allocation.MipmapControl;
import android.renderscript.Element;
import android.renderscript.RenderScript;

import com.android.example.hellocompute.R;

public class FastGuassianBlur {
  private static final int[] BLUR = { 1, 2, 1, 2, 4, 2, 1, 2, 1 };

  public static Bitmap blur(Context context, Bitmap bitmap) {
    RenderScript rs = RenderScript.create(context);
    ScriptC_convolutionint convolution = new ScriptC_convolutionint(rs,
        context.getResources(), R.raw.convolutionint);
    System.out.println("1");
    Allocation blur = Allocation.createSized(rs, Element.I32(rs), 9,
        Allocation.USAGE_SCRIPT);
    System.out.println("2");
    blur.copyFrom(BLUR);
    System.out.println("3");
    convolution.bind_convolution_factors(blur);
    System.out.println("4");
    Allocation in = Allocation.createFromBitmap(rs, bitmap,
        MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
    Allocation out = Allocation.createTyped(rs, in.getType());
    convolution.bind_gPixels(in);
    System.out.println("5");
    convolution.invoke_filter(convolution, in, out);
    Bitmap result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
        Config.ARGB_8888);
    System.out.println("6");
    out.copyTo(result);
    return result;
  }
}
