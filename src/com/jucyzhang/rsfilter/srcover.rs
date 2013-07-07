#pragma version(1)
#pragma rs java_package_name(com.jucyzhang.rsfilter)

const uchar4 *dst;
static const uint32_t white=(uint32_t)255;
static int mImageWidth;

void init() {
}

void root(const uchar4 *v_in, uchar4 *v_out, const void *usrData, uint32_t x, uint32_t y) {
	uint4 srcco=convert_uint4(*v_in);
	uint4 dstco=convert_uint4(*(dst+x+y*mImageWidth));
	uint4 color;
	color=srcco+(white-srcco.a)*dstco/white;
	*v_out=convert_uchar4(color);
}


void filter(rs_script gScript,rs_allocation gIn,rs_allocation gOut) {
	mImageWidth = rsAllocationGetDimX(gIn);
    rsForEach(gScript, gIn, gOut, NULL);
}