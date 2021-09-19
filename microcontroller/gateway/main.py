from utils import *
from mqtt import MQTTClient
import _thread
import time
import json
import machine
import logger
import network
from machine import Pin, SPI
from sx127x import SX127x
import cryptolib


MQTT_CLIENT = None
LORA = None
DEVICE_CONFIG = None

LORA_DATA = []
MQTT_DATA = []

LORA_AVAIL = False
MQTT_AVAIL = False

LORA_LOCK = _thread.allocate_lock()
MQTT_LOCK = _thread.allocate_lock()

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


def write_config():
    logger.log("Writing config file...")
    global DEVICE_CONFIG
    
    try:
        f = open("config.txt", "w")
        f.write(json.dumps(DEVICE_CONFIG))
        f.close()
        
    except Exception as e:
        raise CustomException("ERROR in write_config(): " + str(e))
        

def connect_mqtt():    
    global MQTT_CLIENT
    global DEVICE_CONFIG
    logger.log("Connecting MQTT")
    
    try:
        logger.log("Reading private key...")
        with open(DEVICE_CONFIG["key_file"], "r") as f: 
            key = f.read()

        logger.log("Private key read")
        
        logger.log("Reading certificate...")
        with open(DEVICE_CONFIG["cert_file"], "r") as f: 
            cert = f.read()

        logger.log("Certificate read")

        MQTT_CLIENT = MQTTClient(client_id=DEVICE_CONFIG["device_id"], server=DEVICE_CONFIG["mqtt_host"], port=DEVICE_CONFIG["mqtt_port"], keepalive=5000, ssl=True, ssl_params={"cert":cert, "key":key, "server_side":False})
        MQTT_CLIENT.connect()
        logger.log('MQTT connected')
        MQTT_CLIENT.set_callback(handle_msg)

    except Exception as e:
        raise CustomException("ERROR in connect_mqtt(): " + str(e))
    
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
    logger.log("Sending LoRa message")
    
    try:
        LORA.println(message)
        
    except Exception as e:
        raise CustomException("ERROR in lora_send(): " + str(e))

def handle_msg(topic, msg):
    logger.log("Handling incoming message with topic: " + topic.decode())
    global DEVICE_CONFIG
    
    global MQTT_AVAIL
    global MQTT_DATA
    global MQTT_LOCK
    
    try:
        with MQTT_LOCK:
            if (topic.decode() == DEVICE_CONFIG["mqtt_topic_ping"] and json.loads(msg.decode())["DeviceName"] == DEVICE_CONFIG["device_id"]):
                message = {
                    "topic" : topic.decode(),
                    "message" : json.loads(msg.decode())["DeviceName"]
                }
                
            elif (topic.decode() == DEVICE_CONFIG["mqtt_topic_shadow"]):
                shadow = json.loads(msg)
                message = {
                    "topic" : topic.decode(),
                    "message" : str(next(i for i in shadow["state"]["reported"]["DeviceData"]["deviceConfiguration"] if i["sensorConfiguration"]["settingType"] == "reportingFrequency")["sensorConfiguration"]["value"])
                }
                
            else:
                return
            
            MQTT_DATA.append(json.dumps(message))
            MQTT_AVAIL = True        

    except Exception as e:
        raise CustomException("ERROR in handle_msg(): " + str(e))

def encrypt(string):
    logger.log("Encrypting string")
    global CRYP_ENC
    
    try:
        padding = "X" * (16 - len(string) % 16)
        string = padding + string
        return CRYP_ENC.encrypt(string)
    
    except Exception as e:
        raise CustomException("ERROR in encrypt(): " + str(e))
    
def decrypt(string):
    logger.log("Decrypting string")
    global CRYP_DEC
    
    try:
        while (string[0] == 'X'):
            string = string[1:]
        
        return CRYP_ENC.decrypt(string)
    
    except Exception as e:
        raise CustomException("ERROR in decrypt(): " + str(e))


def subscribe(topic):
    logger.log("Subscribing to " + topic)
    global MQTT_CLIENT
    global DEVICE_CONFIG
    
    try:
        MQTT_CLIENT.subscribe(topic=topic)
        logger.log("Subscribed to " + topic)
        
    except Exception as e:
        raise CustomException("ERROR in subscribe(): " + str(e))


def publish(msg, topic):
    logger.log("Running publish method...")
    global MQTT_CLIENT
    
    try:        
        logger.log("Pinging MQTT...")
        MQTT_CLIENT.ping()
        logger.log("Done")
        
        MQTT_CLIENT.publish(topic, msg)
        logger.log("Message published")
        
    except Exception as e:
        raise CustomException("ERROR in publish(): " + str(e))
    
    
def handle_publish(data):
    logger.log("Parsing measurement data")
    
    try:
        data = json.loads(data)
        
        rtc = machine.RTC()
        localtime = rtc.datetime()
        timeString = str("%02d"%localtime[0]) + "-" + str("%02d"%localtime[1]) + "-" + str("%02d"%localtime[2]) + " " + str("%02d"%localtime[4]) + ":" + str("%02d"%localtime[5]) + ":" + str("%02d"%localtime[6])
        
        message = {
            "deviceName": data["n"],
            "measurements": []
        }
        
        for m in data["m"]:
            message["measurements"].append(
                {
                      "type": m["t"],
                      "value": m["v"],
                      "unitOfMeasurement": m["u"],
                      "deviceDateTime": timeString
                }    
            )
        
        return message
    
    except Exception as e:
        raise CustomException("ERROR in handle_publish(): " + str(e))

def handle_exception(loop, context):
    msg = context.get("exception", context["message"])
    raise CustomException("Error in thread: " + str(msg))

def lora_loop():
    logger.log("Starting LoRa loop")
    
    global LORA
    global MQTT_AVAIL
    global MQTT_DATA
    global MQTT_LOCK
    global LORA_AVAIL
    global LORA_DATA
    global LORA_LOCK
    
    try:
        while True:
            if (MQTT_AVAIL and MQTT_LOCK.locked() == False):
                with MQTT_LOCK:
                    for m in MQTT_DATA[::-1]:
                        logger.log("Sending LoRa message: " + m)
                        lora_send(m)
                        
                    MQTT_DATA = []
                    MQTT_AVAIL = False
                
            if LORA.received_packet():
                with LORA_LOCK:
                    m = handle_publish(LORA.read_payload().decode())
                    LORA_DATA.append(json.dumps(m))
                    logger.log("LoRa message received: " + json.dumps(m))
                    LORA_AVAIL = True
        
    except Exception as e:
        raise CustomException("ERROR in lora_loop(): " + str(e))
    
def mqtt_loop():
    logger.log("Starting MQTT loop")
    
    global DEVICE_CONFIG
    global LORA_AVAIL
    global LORA_DATA
    global LORA_LOCK
    
    try:
        while True:
            if LORA_AVAIL:
                with LORA_LOCK:
                    for m in LORA_DATA[::-1]:
                        logger.log("Sending MQTT message: " + m)
                        publish(m, DEVICE_CONFIG["mqtt_topic_main"])
                    LORA_DATA = []
                    LORA_AVAIL = False
                
            wlan = network.WLAN(network.STA_IF)
            if (wlan.isconnected() != True):
                logger.log("WiFi has disconnected!")
                
            logger.log("Pinging MQTT...")
            MQTT_CLIENT.ping()
            logger.log("Done")
            
            logger.log("Checking for MQTT messages...")
            
            while MQTT_CLIENT.check_msg() != None:
                pass
            
            logger.log("MQTT sleeping...")
            time.sleep(15)
            
            
    except Exception as e:
        logger.log(str(e), "SEVERE")
        logger.log("Exception caught in thread. Restarting device...", "SEVERE")
        machine.reset()
        
def mqtt_init():
    global DEVICE_CONFIG
    
    try:
        connect_mqtt()
        subscribe(DEVICE_CONFIG["mqtt_topic_ping"])
        subscribe(DEVICE_CONFIG["mqtt_topic_shadow"])
        
        for i in DEVICE_CONFIG["topics"]:
            subscribe(i)
    
    except Exception as e:
        raise CustomException("ERROR in mqtt_init(): " + str(e))        
 
try:
    read_config()
    connect_wifi(DEVICE_CONFIG["wifi_ssid"], DEVICE_CONFIG["wifi_pw"])
    
    rtc = machine.RTC()
    t = rtc.datetime()
    
    if (t[0] < 2021):
        import ntptime
        logger.log('Setting time...')
        tm = utime.gmtime(ntptime.time())
        machine.RTC().datetime((tm[0], tm[1], tm[2], tm[6] + 1, tm[3] + 2, tm[4], tm[5], 0))
        logger.log('Time is set!')
    
    mqtt_init()
    lora_init()
    
    _thread.start_new_thread(mqtt_loop, ())
    lora_loop()

except Exception as e:
    logger.log(str(e), "SEVERE")
    logger.log("Exception caught. Restarting device...", "SEVERE")
    machine.reset()

