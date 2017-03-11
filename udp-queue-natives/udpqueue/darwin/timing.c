#include <time.h>
#include <mach/mach_time.h>

#include "../timing.h"

int64_t timing_get_nanos(void) {
    static mach_timebase_info_data_t info = {0, 0};
  
    if (info.denom == 0) {
        mach_timebase_info(&info);
    }

    return mach_absolute_time() * info.numer / info.denom;
}

void timing_sleep(int64_t nanos) {
	struct timespec tv = {
		nanos / 1000000000LL,
		nanos % 1000000000LL
	};
	nanosleep(&tv, NULL);
}
