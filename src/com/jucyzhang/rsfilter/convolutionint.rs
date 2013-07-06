#pragma version(1)
#pragma rs java_package_name(com.jucyzhang.rsfilter)

static int mImageWidth;
static int mImageHeight;
const uchar4 *gPixels;
const int32_t* convolution_factors;

void init() {
}

const static int kBlurWidth=1;
const static int kBlurTotalWidth=2*kBlurWidth+1;

void root(const uchar4 *v_in, uchar4 *v_out, const void *usrData, uint32_t x, uint32_t y) {

	int32_t sum_factors=0;
	int4 tep=0;
	
	//uint32_t currentPos=x+y*mImageWidth;
	//x = currentPos%mImageWidth;
	//y = currentPos/mImageWidth;
	int32_t now_x;
	int32_t now_y;
	int32_t factor;
    for (int i = -kBlurWidth; i <= kBlurWidth; i++) {
		for(int j=-kBlurWidth;j<=kBlurWidth;j++){
			now_x = x+i;
			now_y = y+j;
			if(now_x<0||now_x>=mImageWidth||now_y<0||now_y>=mImageHeight){
			
			}else{
				factor = convolution_factors[i+kBlurWidth+(j+kBlurWidth)*kBlurTotalWidth];
				tep+=convert_uint4(gPixels[now_x+now_y*mImageWidth])*factor;
				sum_factors+=factor;
			}
		}
    }
    tep/=sum_factors;
    tep.a=rsClamp(tep.a,0,255);
    tep.r=rsClamp(tep.r,0,255);
    tep.g=rsClamp(tep.g,0,255);
    tep.b=rsClamp(tep.b,0,255);
    *v_out=convert_uchar4(tep);
}


void filter(rs_script gScript,rs_allocation gIn,rs_allocation gOut) {
    mImageWidth = rsAllocationGetDimX(gIn);
    mImageHeight=rsAllocationGetDimY(gIn);
    rsDebug("Image size is ", rsAllocationGetDimX(gIn), rsAllocationGetDimY(gOut));
    rsForEach(gScript, gIn, gOut, NULL);
}