#pragma version(1)
#pragma rs java_package_name(com.jucyzhang.rsfilter)

static int mImageWidth;
const uchar4 *gPixels;

static const float4 kWhite = {
    1.0f, 1.0f, 1.0f, 1.0f
};
static const float4 kBlack = {
    0.0f, 0.0f, 0.0f, 1.0f
};

void init() {
}

static const int kBlurWidth = 1;
static const float kMultiplier = 1.0f / (float)(kBlurWidth * 2 + 1);

void root(const uchar4 *v_in, uchar4 *v_out, const void *usrData, uint32_t x, uint32_t y) {
    float4 original = rsUnpackColor8888(*v_in);

    float4 colour = original * kMultiplier;

    int y_component = mImageWidth * y;

    for ( int i = -kBlurWidth; i < 0; i++) {
        float4 temp_colour;

        if ( (int)x + i >= 0) {
            temp_colour = rsUnpackColor8888(gPixels[x+i + y_component]);
        }
        else {
            temp_colour = kWhite;
        }

        colour += temp_colour * kMultiplier;
    }
    for ( int i = 1; i <= kBlurWidth; i++) {
        float4 temp_colour;

        if ( x + i < mImageWidth) {
            temp_colour = rsUnpackColor8888(gPixels[x+i + y_component]);
        }
        else {
            temp_colour = kWhite;
        }

        colour += temp_colour * kMultiplier;
    }

    colour.a = 1.0f;
    *v_out = rsPackColorTo8888(colour);
}


void filter(rs_script gScript,rs_allocation gIn,rs_allocation gOut) {
    mImageWidth = rsAllocationGetDimX(gIn);
    rsDebug("Image size is ", rsAllocationGetDimX(gIn), rsAllocationGetDimY(gOut));
    rsForEach(gScript, gIn, gOut, NULL);
}