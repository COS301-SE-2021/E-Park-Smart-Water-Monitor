from machine import ADC
from machine import Pin

class PH:
    def __init__(self, pin):
        self.adc = ADC(Pin(pin))
        self.adc.atten(ADC.ATTN_11DB)
        self._temperature      = 25.0
        self._acidVoltage      = 2032.44
        self._neutralVoltage   = 1500.0
        
    def readPH(self):
        voltage = self.adc.read()

        slope     = (7.0-4.0)/((self._neutralVoltage-1500.0)/3.0 - (self._acidVoltage-1500.0)/3.0)
        intercept = 7.0 - slope*(self._neutralVoltage-1500.0)/3.0
        _phValue  = slope*(voltage-1500.0)/3.0+intercept
        round(_phValue,2)
        return _phValue
