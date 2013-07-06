#pragma version(1)
#pragma rs java_package_name(com.jucyzhang.rsfilter)

const uchar4 *src;
static int mImageWidth;
const uint32_t white=(uint32_t)255;

const float4 kWhite = {
    1.0f, 1.0f, 1.0f, 1.0f
};
const float4 kBlack = {
    0.0f, 0.0f, 0.0f, 1.0f
};

void init() {
}

void root(const uchar4 *v_in, uchar4 *v_out, const void *usrData, uint32_t x, uint32_t y) {
	uint4 src_in=convert_uint4(*(src+x+y*mImageWidth));
	uint4 dst_in=convert_uint4(*v_in);
	*v_out=convert_uchar4(src_in+dst_in-src_in*dst_in/white);
}


void filter(rs_script gScript,rs_allocation dst,rs_allocation gOut) {
	mImageWidth=rsAllocationGetDimX(dst);
    rsForEach(gScript, dst, gOut, NULL);
}