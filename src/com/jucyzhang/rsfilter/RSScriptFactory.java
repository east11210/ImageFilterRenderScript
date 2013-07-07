package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;

interface RSScriptFactory<T extends ScriptC> {
  T onCreateScript(Context context, RenderScript rs);
}
