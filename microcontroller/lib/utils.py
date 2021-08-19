import time
import ntptime
import utime
import machine
import logger

class CustomException(Exception):
    pass

def connect_wifi(ssid, pw):
    try:
        logger.log("Connecting to WiFi...")
        import network
        wlan = network.WLAN(network.STA_IF)
        wlan.active(True)
        
        if(wlan.isconnected()):
            wlan.disconnect()
        
        if not wlan.isconnected():
            wlan.active(True)
            wlan.connect(ssid, pw)
            while not wlan.isconnected():
                pass
        
        logger.log("Connected to " + ssid)
        
    except Exception as e:
        raise CustomException("ERROR in connect_wifi(): " + str(e))

def set_time():
    try:
        logger.log('Setting time...')
        tm = utime.gmtime(ntptime.time())
        machine.RTC().datetime((tm[0], tm[1], tm[2], tm[6] + 1, tm[3] + 2, tm[4], tm[5], 0))
        logger.log('Time is set!')
        
    except Exception as e:
        print(e.message)
        raise CustomException("ERROR in set_time(): " + str(e))

