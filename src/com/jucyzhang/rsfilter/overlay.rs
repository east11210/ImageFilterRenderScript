#pragma version(1)
#pragma rs java_package_name(com.jucyzhang.rsfilter)

static int mImageWidth;
const uchar4 *dst;
const uchar4 *src;

const float4 kWhite = {
    1.0f, 1.0f, 1.0f, 1.0f
};
const float4 kBlack = {
    0.0f, 0.0f, 0.0f, 1.0f
};

void init() {
}

static float computeOverlayPixel(float src,float dst){
	if(dst<=0.5f){
		return 2*src*dst;
	}
	return 1-2*(1-src)*(1-dst);
} 


void root(const uchar4 *v_in, uchar4 *v_out, const void *usrData, uint32_t x, uint32_t y) {
    float4 dstf = rsUnpackColor8888(*v_in);
	float4 color;
	float4 srcf = rsUnpackColor8888(*(src + x + mImageWidth * y));
    color.a = 1.0f;
    color.r=computeOverlayPixel(srcf.r,dstf.r);
    color.g=computeOverlayPixel(srcf.g,dstf.g);
    color.b=computeOverlayPixel(srcf.b,dstf.b);
    *v_out = rsPackColorTo8888(color);
}


void filter(rs_script gScript,rs_allocation dst,rs_allocation gOut) {
    mImageWidth = rsAllocationGetDimX(dst);
    rsForEach(gScript, dst, gOut, NULL);
}