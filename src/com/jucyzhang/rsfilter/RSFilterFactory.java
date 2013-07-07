package com.jucyzhang.rsfilter;

public class RSFilterFactory {
  public enum Filters {
    /**
     * @required ORIGINAL & TABLE
     */
    FIND_TABLE {
      @Override
      RSFilter get() {
        return new FindTableRSFilter();
      }
    },
    /**
     * @required ORIGINAL
     */
    GUASSIAN_BLUR {
      @Override
      RSFilter get() {
        return new GuassianBlurRSFilter();
      }
    },
    /**
     * @required SRC & DST
     */
    MULTIPLY {
      @Override
      RSFilter get() {
        return new MultiplyRSFilter();
      }
    },
    /**
     * @required SRC & DST
     */
    OVERLAY {
      @Override
      RSFilter get() {
        return new OverlayRSFilter();
      }
    },
    /**
     * @required SRC & DST
     */
    SCREEN {
      @Override
      RSFilter get() {
        return new ScreenRSFilter();
      }
    },
    /**
     * @required ORIGINAL
     */
    SHARPEN {
      @Override
      RSFilter get() {
        return new SharpenRSFilter();
      }
    },
    /**
     * @required SRC & DST
     */
    SRC_OVER {
      @Override
      RSFilter get() {
        return new SrcOverRSFilter();
      }
    };
    abstract RSFilter get();
  }

  public static RSFilter newFilter(Filters filter) {
    return filter.get();
  }

}
