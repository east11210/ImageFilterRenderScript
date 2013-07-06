#pragma version(1)
#pragma rs java_package_name(com.jucyzhang.rsfilter)

static int mImageWidth;
static int mImageHeight;
const uchar4 *gPixels;
const uint32_t* convolution_factors;

void init() {
}

const static int kBlurWidth=1;
const static int kBlurTotalWidth=2*kBlurWidth+1;

void root(const uchar4 *v_in, uchar4 *v_out, const void *usrData, uint32_t x, uint32_t y) {

	uint32_t sum_factors=0;
	uint4 tep=0;
	
	//uint32_t currentPos=x+y*mImageWidth;
	//x = currentPos%mImageWidth;
	//y = currentPos/mImageWidth;
	int32_t now_x;
	int32_t now_y;
	uint32_t factor;
    for (int i = -kBlurWidth; i < kBlurWidth; i++) {
		for(int j=-kBlurWidth;j<kBlurWidth;j++){
			now_x = x+i;
			now_y = y+j;
			if(now_x<0||now_x>=mImageHeight||now_y<0||now_y>=mImageWidth){
			
			}else{
				factor = convolution_factors[i+kBlurWidth+(j+kBlurWidth)*kBlurTotalWidth];
				tep+=convert_uint4(gPixels[now_x+now_y*mImageWidth])*factor;
				sum_factors+=factor;
			}
		}
    }
    *v_out=convert_uchar4(tep/sum_factors);
}


void filter(rs_script gScript,rs_allocation gIn,rs_allocation gOut) {
    mImageWidth = rsAllocationGetDimX(gIn);
    mImageHeight=rsAllocationGetDimY(gIn);
    rsDebug("Image size is ", rsAllocationGetDimX(gIn), rsAllocationGetDimY(gOut));
    rsForEach(gScript, gIn, gOut, NULL);
}