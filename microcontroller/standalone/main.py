from utils import *
from mqtt import MQTTClient
import time
import json
import jsn
import machine
import bmp280
import gc
from ph import PH
import uasyncio
import logger
import network


MQTT_CLIENT = None
DEVICE_CONFIG = None


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


def read_measurements():
    logger.log('Getting readings...')
    global DEVICE_CONFIG

    try:
        rtc = machine.RTC()
        localtime = rtc.datetime()
        #localtime = time.localtime()
        timeString = str("%02d"%localtime[0]) + "-" + str("%02d"%localtime[1]) + "-" + str("%02d"%localtime[2]) + " " + str("%02d"%localtime[4]) + ":" + str("%02d"%localtime[5]) + ":" + str("%02d"%localtime[6])
        
        
        message = {
            "deviceName": "Device1",
            "measurements": [],
            "reason": "STARTUP"
        }
        
        lvl = -999
        temp = -999
        ph = -999
            
        if "depth" in DEVICE_CONFIG["sensor_modules"]:
            lvl = read_depth()
        
        message["measurements"].append(
            {
                "type": "WATER_LEVEL",
                "value": lvl,
                "unitOfMeasurement": "CENTIMETER",
                "deviceDateTime": timeString
            }
        )
        
        if "temp" in DEVICE_CONFIG["sensor_modules"]:
            temp = read_temp()
            
        message["measurements"].append(
            {
                "type": "WATER_TEMP",
                "value": temp,
                "unitOfMeasurement": "CENTIGRADE",
                "deviceDateTime": timeString
            }
        )
            

        if "ph" in DEVICE_CONFIG["sensor_modules"]:
            ph = read_ph()
            
        message["measurements"].append(
            {
                "type": "WATER_QUALITY",
                "value": ph,
                "unitOfMeasurement": "PH",
                "deviceDateTime": timeString
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
        depthSensor = jsn.JSN(13, 12)
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


def handle_msg(topic, msg):
    logger.log("Handling incoming message with topic: " + str(topic))
    global DEVICE_CONFIG
    
    try:
        if (topic.decode() == DEVICE_CONFIG["mqtt_topic_ping"] and json.loads(msg.decode())["DeviceName"] == DEVICE_CONFIG["device_id"]):
            logger.log("Ping topic received")
            publish(read_measurements(), DEVICE_CONFIG["mqtt_topic_main"])
        
        elif (topic.decode() == DEVICE_CONFIG["mqtt_topic_shadow"]):
            logger.log("Shadow topic received")
            shadow = json.loads(msg)
            DEVICE_CONFIG["publish_interval"] = float(next(i for i in shadow["state"]["reported"]["DeviceData"]["deviceConfiguration"] if i["sensorConfiguration"]["settingType"] == "reportingFrequency")["sensorConfiguration"]["value"])
            write_config()
            #time.sleep(5)
            #logger.log("Restarting device...")
            #machine.reset()
            
        elif (topic.decode() == "iot/dump"):
            logger.log("Log dump topic received")
            f = open("log.txt", "r")
            logs = f.read()
            message = {
                "deviceName": "Device1",
                "logs": logs
            }
            publish(message, "iot/logdump")
        
        else:
            logger.log("Unrecognizable topic")

    except Exception as e:
        raise CustomException("ERROR in handle_msg(): " + str(e))


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
        MQTT_CLIENT.publish(topic, json.dumps(msg))
        logger.log("Message published")
        
    except Exception as e:
        raise CustomException("ERROR in publish(): " + str(e))


async def pub_loop():
    logger.log("Starting publish loop...")
    global DEVICE_CONFIG
    
    try:
        while True:
            publish(read_measurements(), DEVICE_CONFIG["mqtt_topic_main"])
            await uasyncio.sleep(3600 * float(DEVICE_CONFIG["publish_interval"]))
            
    except Exception as e:
        raise CustomException("ERROR in pub_loop(): " + str(e))

async def sub_loop():
    logger.log("Starting subscribe loop...")
    global MQTT_CLIENT
    
    try:
        while True:
            wlan = network.WLAN(network.STA_IF)
            if (wlan.isconnected() != True):
                logger.log("WiFi has disconnected!")
            
            logger.log("Checking for messages...")
            
            while MQTT_CLIENT.check_msg() != None:
                pass
            
            logger.log("Subscribe sleeping...")
            await uasyncio.sleep(30)
            
    except Exception as e:
        raise CustomException("ERROR in sub_loop(): " + str(e))


def handle_exception(loop, context):
    msg = context.get("exception", context["message"])
    raise CustomException("Error in thread: " + str(msg))


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
    
    
    
    connect_mqtt()
    subscribe(DEVICE_CONFIG["mqtt_topic_ping"])
    subscribe(DEVICE_CONFIG["mqtt_topic_shadow"])
    
    for i in DEVICE_CONFIG["topics"]:
        subscribe(i)
        
    
    
    loop = uasyncio.get_event_loop()
    loop.create_task(sub_loop())
    loop.create_task(pub_loop())
    loop.set_exception_handler(handle_exception)
    loop.run_forever()

except Exception as e:
    logger.log(str(e), "SEVERE")
    logger.log("Exception caught. Restarting device...", "SEVERE")
    machine.reset()
