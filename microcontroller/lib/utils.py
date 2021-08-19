import network

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
