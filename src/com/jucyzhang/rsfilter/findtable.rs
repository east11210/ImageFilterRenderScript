#pragma version(1)
#pragma rs java_package_name(com.jucyzhang.rsfilter)

const uchar4 *table;

void init() {
}

static const int kBlurWidth = 1;

void root(const uchar4 *v_in, uchar4 *v_out, const void *usrData, uint32_t x, uint32_t y) {
	v_out->a=(uchar)0xFF;
	v_out->r=(table+(*v_in).r)->r;
	v_out->g=(table+(*v_in).g)->g;
	v_out->b=(table+(*v_in).b)->b;
}


void filter(rs_script gScript,rs_allocation gIn,rs_allocation gOut) {
    rsForEach(gScript, gIn, gOut, NULL);
}