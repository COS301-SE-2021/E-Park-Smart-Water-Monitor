import machine
import time
import utime

class JSN:
    
    def __init__(self, pinOut, pinIn):
        self.trigger = machine.Pin((pinOut), machine.Pin.OUT)
        self.echo = machine.Pin((pinIn), machine.Pin.IN)
        
    def measureDist(self): 
        self.trigger.value(1)
        utime.sleep(2)
        self.trigger.value(0)
        utime.sleep_us(15)
        self.trigger.value(1)

        while (self.echo.value() == 0):
            pulse_start = time.time()

        while (self.echo.value() == 1):
            pulse_end = time.time()

        pulse_duration = pulse_end - pulse_start

        distance = pulse_duration * 17150
        distance = round(distance, 2)

        if (distance > 20 and distance < 600):
            return distance
        else:
            return None