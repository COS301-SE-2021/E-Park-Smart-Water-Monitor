import time
import ntptime
import utime
import machine
import connection
import logger

def setTime():
    logger.log('Setting time...')
    if connection.test():
        tm = utime.gmtime(ntptime.time())
        machine.RTC().datetime((tm[0], tm[1], tm[2], tm[6] + 1, tm[3] + 2, tm[4], tm[5], 0))
        logger.log('Time is set!')
        return True
    else:
        logger.log('Time could not be set! No connection!')
        return False