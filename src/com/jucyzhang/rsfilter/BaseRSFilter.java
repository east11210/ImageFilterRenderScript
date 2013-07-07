package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;

abstract class BaseRSFilter<T extends ScriptC> extends RSFilter{

  private T script;

  /**
   * getScript() will be available after this method.
   * 
   * @param context
   * @param rs
   * @return
   */
  protected abstract T onCreateScript(Context context, RenderScript rs);

  protected final T getScript() {
    return script;
  }

  @Override
  final void createScript(Context context, RenderScript rs) {
    script = onCreateScript(context, rs);
  }

  @Override
  final void destroy(Context context, RenderScript rs) {
    script.destroy();
    script = null;
    super.destroy(context, rs);
  }
}
