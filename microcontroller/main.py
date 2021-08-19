import logger
import connection
import urequests
import time
import gc
import machine
import ntptime
import utime
import bmp280
import esp32
import ujson

def setTime():
    logger.log('Setting time...')
    if connection.test():
        tm = utime.gmtime(ntptime.time())
        machine.RTC().datetime((tm[0], tm[1], tm[2], tm[6] + 1, tm[3] + 2, tm[4], tm[5], 0))
        logger.log('Time is set!')
    else:
        logger.log('Time could not be set!')

    
def logTemp():
    logger.log('Clearing memory...')
    gc.collect()
    logger.log('Setting up I2C pins...')
    i2c = machine.I2C(1, scl=machine.Pin(18), sda=machine.Pin(19))
    sensor = bmp280.BMP280(i2c)
    logger.log('Reading temperature...')
    temp = str(sensor.getTemp())
    waterLevel = 6.66
    waterQuality = 7.77
    press = str(sensor.getPress())
    logger.log('Temperature read: ' + str(temp))
    logger.log('Pressure read: ' + str(press))
    logger.log('Logging measurements to Firebase...')
    localtime = time.localtime()
    now = str(localtime[3]) + ":" + str(localtime[4]) + ":" + str(localtime[5])
    
    post_data = ujson.dumps({
        "deviceName": "Water1000",
        "measurements":
        [
            {
                "type": "WATER_LEVEL",
                "value": 10,
                "unitOfMeasurement": "METER",
                "deviceDateTime": str(localtime[2]) + "-" + str(localtime[1]) + "-" + str(localtime[0])
            } 
            ,
            {
                "type": "WATER_TEMP",
                "value": temp,
                "unitOfMeasurement": "CENTIGRADE",
                "deviceDateTime": str(localtime[2]) + "-" + str(localtime[1]) + "-" + str(localtime[0])
            }
            ,   
            {
                "type": "WATER_QUALITY",
                "value": press,
                "unitOfMeasurement": "PPM",
                "deviceDateTime": str(localtime[2]) + "-" + str(localtime[1]) + "-" + str(localtime[0])
            }
        ]
    })
    
    res = urequests.post('http://192.168.9.102:8080/api/devices/receiveDeviceData', headers = {'content-type': 'application/json'}, data = post_data).json()


if machine.reset_cause() == machine.DEEPSLEEP_RESET:
    logger.log('Awaking from deepsleep...')
    logger.log('Reason for wake: ' + ('touch' if machine.wake_reason() == 5 else 'RTC' if machine.wake_reason() == 4 else 'unknown'))
    connection.setup()
else:
    logger.log('Starting up for the first time...')
    connection.setup()
    setTime()
 
t = machine.TouchPad(machine.Pin(13))
t.config(500)
esp32.wake_on_touch(True)
 
logTemp()

for i in range(5, 0, -1):
    print('Entering deepsleep in ' + str(i))
    time.sleep(1)

logger.log('Entering deepsleep...')
machine.deepsleep(1800000)




