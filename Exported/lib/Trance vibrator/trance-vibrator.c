// This program requires libusb. I used version 0.10.
//
// Compilation on Linux:
//
// gcc -g -o trance-vibrator trance-vibrator.c -lusb
//
// Compilation on MacOS X: first use fink to install libusb, then
//
// gcc -I/sw/include -L/sw/lib -g -o trance-vibrator trance-vibrator.c -lusb
//
// Compilation on Windows:
//
// Install the libusb-win32 filter driver (libusb-win32-filter-bin) from:
//     http://libusb-win32.sourceforge.net/
//
// Install MinGW (www.mingw.org) packages binutils, gcc-core, gcc-g++,
// mingw-runtime, and w32api from:
//     http://sourceforge.net/project/showfiles.php?group_id=2435
//     
// Assuming you put MinGW in c:\mingw, issue this set of commands (the 
// gcc command should be all on one line):
//
// PATH=c:\mingw\bin;%PATH%
// gcc -L"c:\program files\libusb-win32\lib\gcc" 
//     -I"c:\program files\libusb-win32\include" 
//     -g -o trance-vibrator trance-vibrator.c -lusb
//
// On Linux, you must run this program as a user that has write access
// to the appropriate file in /proc/bus/usb. Typically that means you
// must either be root or chown or chmod the appropriate file.
//
// The only message that the vibrator understands (apparently) is one
// that sets the speed of vibration. The possible speeds are 0-255.
//
// This program simply reads speeds from standard input (in plain ASCII,
// separated by whitespace), and sets the vibrator speed. For example,
// to turn the vibration off:
//
// echo 0 | trance-vibrator
//
// or perhaps
//
// echo 0 | sudo trance-vibrator
//
// To turn on maximum vibration speed:
//
// echo 255 | trance-vibrator
//
// To run full speed for one second, then half speed for one second,
// then stop:
//
// ( echo 255; sleep 1; echo 128; sleep 1; echo 0 ) | trance-vibrator
//
// It should also now accept a single vibrator speed on the command
// line so that the following commands would turn on maximum speed and
// then turn off.
//
// trance-vibrator 255
// trance-vibrator 0
//

#include <stdio.h>
#include <usb.h>
#include <errno.h>
#include <string.h>

int
main(int argc, char** argv)
{
    struct usb_bus* bus = 0;
    struct usb_device* dev = 0;
    struct usb_dev_handle* h;

    usb_init();
    usb_find_busses();
    usb_find_devices();

    for (bus = usb_get_busses(); bus != 0; bus = bus->next) {
	for (dev = bus->devices; dev != 0; dev = dev->next) {
	    if (dev->descriptor.idVendor == 0x0b49
		&& dev->descriptor.idProduct == 0x064f
	    )
		goto done;
	}
    }

done:
    if (dev == 0) {
	fprintf(stderr, "error: no trance vibrator found\n");
	return 1;
    }

    h = usb_open(dev);
    if (h == 0) {
	fprintf(stderr, "error: could not open device\n");
	return 1;
    }

#if 0
    // This stuff doesn't seem to be necessary...

    if (usb_set_configuration(h, 1) < 0) {
	fprintf(stderr, "error: could not set configuration\n");
	return 1;
    }

    if (usb_claim_interface(h, 0) < 0) {
	fprintf(stderr, "error: could not claim interface\n");
	return 1;
    }

    if (usb_set_altinterface(h, 0) < 0) {
	fprintf(stderr, "error: could not set altinterface\n");
	return 1;
    }
#endif

    while (1) {
	int i;
	unsigned int speed; // tim used unsigned int

        // if there was an argument on the command line, use that
        // instead of stdin
        if (argc > 1) {
           sscanf(argv[1],"%d",&speed);
        } else {
           scanf("%d", &speed);
           if (feof(stdin))
	    break;
        }
	// tim's code:
	speed = (speed + speed * 256); //  0x42 -> 0x4242

	// The vibrator is flaky, so try the request a few times...
	for (i = 0; i < 3; ++i) {
                // tim's usb_control_msg call:
        	if (usb_control_msg(h, 0x41, 0x00, speed, 0x0300 + (speed & 0x0F), NULL, 0, 1000) < 0)
	    {
		fprintf(stderr,
		    "warning: error sending control request: %s\n",
		    strerror(errno));
	    }
	    else break;
	}
        // if there was a command line argument then we'll quit
        // after issuing it
        if (argc > 1) { break; }
    }

    return 0;
}

