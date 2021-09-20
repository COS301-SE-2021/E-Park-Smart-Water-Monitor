from utils import *
import time
import json
import jsn
import machine
import bmp280
import gc
from ph import PH
import logger
import network
from machine import Pin, SPI
from sx127x import SX127x
import _thread
import cryptolib
import ubinascii

DEVICE_CONFIG = None
LORA = None
LORA_SENDING = False
CRYP_ENC = None
CRYP_DEC = None

def read_config():
    logger.log("Reading config file...")
    global DEVICE_CONFIG
    
    try:
        with open("config.txt", "r") as f:
            DEVICE_CONFIG = json.loads(f.read())
            f.close()
    
    except Exception as e:
        raise CustomException("ERROR in read_config(): " + str(e))


def write_config():
    logger.log("Writing config file...")
    global DEVICE_CONFIG
    
    try:
        f = open("config.txt", "w")
        f.write(json.dumps(DEVICE_CONFIG))
        f.close()
        
    except Exception as e:
        raise CustomException("ERROR in write_config(): " + str(e))
    
def cryp_init():
    logger.log("Initializing encryption")
    global CRYP_ENC
    global CRYP_DEC
    global DEVICE_CONFIG
    
    try:
        CRYP_ENC = cryptolib.aes(DEVICE_CONFIG["cryp_key"], 1)
        CRYP_DEC = cryptolib.aes(DEVICE_CONFIG["cryp_key"], 1)
        
    except Exception as e:
        raise CustomException("ERROR in cryp_init(): " + str(e))

def read_measurements():
    logger.log('Getting readings...')
    global DEVICE_CONFIG

    try:
        #rtc = machine.RTC()
        #localtime = rtc.datetime()
        #localtime = time.localtime()
        #timeString = str("%02d"%localtime[0]) + "-" + str("%02d"%localtime[1]) + "-" + str("%02d"%localtime[2]) + " " + str("%02d"%localtime[4]) + ":" + str("%02d"%localtime[5]) + ":" + str("%02d"%localtime[6])
        
        
        message = {
            "n": DEVICE_CONFIG["device_id"],
            "m": []
        }
        
        lvl = -999
        temp = -999
        ph = -999
            
        if "depth" in DEVICE_CONFIG["sensor_modules"]:
            lvl = read_depth()
        
        message["m"].append(
            {
                "t": "WL",
                "v": lvl,
            }
        )
        
        if "temp" in DEVICE_CONFIG["sensor_modules"]:
            temp = read_temp()
            
        message["m"].append(
            {
                "t": "WT",
                "v": temp,
            }
        )
            

        if "ph" in DEVICE_CONFIG["sensor_modules"]:
            ph = read_ph()
            
        message["m"].append(
            {
                "t": "WQ",
                "v": ph,
            }
        )
    
        return message
    
    except Exception as e:
        raise CustomException("ERROR in read_measurements(): " + str(e))
    
def read_temp():
    try:
        logger.log("Reading temperature...")
        i2c = machine.I2C(1, scl=machine.Pin(22), sda=machine.Pin(21))
        sensor = bmp280.BMP280(i2c)
        temp = str(sensor.getTemp())
        logger.log("Temperature = " + temp + "C")
        return temp
        
    except Exception as e:
        raise CustomException("ERROR in read_temp(): " + str(e))
    
def read_depth():
    try:
        logger.log("Reading water level...")
        depthSensor = jsn.JSN(14, 13)
        lvl = str(depthSensor.measureDist())
        logger.log("Distance = " + lvl + "cm")
        return lvl
        
    except Exception as e:
        raise CustomException("ERROR in read_depth(): " + str(e))
    
def read_ph():
    try:
        logger.log("Reading water PH...")
        phSensor = PH(32)
        ph = str(phSensor.readPH())
        logger.log("PH = " + ph)
        return ph
        
    except Exception as e:
        raise CustomException("ERROR in read_ph(): " + str(e))
    
def handle_exception(loop, context):
    msg = context.get("exception", context["message"])
    raise CustomException("Error in thread: " + str(msg))

def handle_message(data):
    global DEVICE_CONFIG
    try:
        parsed = json.loads(data)
        topic = parsed["topic"]
        message = parsed["message"]
        logger.log("Handling message with topic: " + parsed["topic"])
        logger.log("Message: " + message)
        global LORA_SENDING
        
        if (topic == "iot/ping" and message == DEVICE_CONFIG["device_id"]):
            logger.log("Ping topic received")
            if (LORA_SENDING == False):
                LORA_SENDING = True                
                lora_send(json.dumps(read_measurements()))
                LORA_SENDING = False
        
        elif (topic == DEVICE_CONFIG["mqtt_topic_shadow"]):
            logger.log("Shadow topic received")
            #shadow = json.loads(msg)
            DEVICE_CONFIG["publish_interval"] = float(message)
            write_config()
            #time.sleep(5)
            #logger.log("Restarting device...")
            #machine.reset()
            
        #elif (topic == "iot/dump"):
            #logger.log("Log dump topic received")
            #f = open("log.txt", "r")
            #logs = f.read()
            #message = {
                #"deviceName": "Device1",
                #"logs": logs
            #}
            #publish(message, "iot/logdump") 
        
        else:
            logger.log("Unrecognizable topic")
        
    except Exception as e:
        raise CustomException("ERROR in handle_message(): " + str(e))
    
def lora_init():
    global LORA
    logger.log("Initializing LoRa interface...")
    
    try:  
        lora_config = {
            'miso':19,
            'mosi':27,
            'ss':18,
            'sck':5,
            'dio_0':26,
            'reset':14,
            'led':2, 
        }
            
        device_spi = SPI(baudrate = 10000000, 
            polarity = 0, phase = 0, bits = 8, firstbit = SPI.MSB,
            sck = Pin(lora_config['sck'], Pin.OUT, Pin.PULL_DOWN),
            mosi = Pin(lora_config['mosi'], Pin.OUT, Pin.PULL_UP),
            miso = Pin(lora_config['miso'], Pin.IN, Pin.PULL_UP))

        LORA = SX127x(device_spi, pins=lora_config)
        
        logger.log("LoRa interface initialized.")
        
    except Exception as e:
        raise CustomException("ERROR in lora_init(): " + str(e))
    
def lora_send(message):
    global LORA
    logger.log("Sending LoRa message: " + message)
    
    try:
        LORA.println(encrypt(message))
        
    except Exception as e:
        raise CustomException("ERROR in lora_send(): " + str(e))
    
def encrypt(string):
    logger.log("Encrypting string")
    global CRYP_ENC
    
    try:
        padding = "X" * (16 - len(string) % 16)
        string = padding + string
        encrypted = CRYP_ENC.encrypt(string)
        hexlified = ubinascii.hexlify(encrypted)
        payload = hexlified.decode()
        print(payload)
        return payload
    
    except Exception as e:
        raise CustomException("ERROR in encrypt(): " + str(e))
    
def decrypt(payload):
    logger.log("Decrypting payload")
    global CRYP_DEC
    
    try:
        encrypted = ubinascii.unhexlify(payload)
        string = CRYP_DEC.decrypt(encrypted).decode()
        
        while (string[0] == 'X'):
            string = string[1:]
        
        return string
    
    except Exception as e:
        raise CustomException("ERROR in decrypt(): " + str(e))
    
def send_loop():
    logger.log("Starting send loop")
    global DEVICE_CONFIG
    global LORA_SENDING
    
    try:
        while True:
            if (LORA_SENDING == False):
                LORA_SENDING = True
                #message = {
                    #"topic" : "iot/test",
                    #"message" : read_measurements()
                #}
                
                lora_send(json.dumps(read_measurements()))
                LORA_SENDING = False
            time.sleep(300)
            
    except Exception as e:
        raise CustomException("ERROR in send_loop(): " + str(e))
    
def receive_loop():
    logger.log("Starting receive loop")
    global LORA
    global LORA_SENDING
    
    try:
        while True:
            if (LORA_SENDING != True):
                if LORA.received_packet():
                    handle_message(decrypt(LORA.read_payload()))
            
    except Exception as e:
        raise CustomException("ERROR in receive_loop(): " + str(e))

try:
    read_config()
    cryp_init()
    lora_init()
    
    _thread.start_new_thread(send_loop, ())
    receive_loop()

except Exception as e:
    logger.log(str(e), "SEVERE")
    logger.log("Exception caught. Restarting device...", "SEVERE")
    machine.reset()

