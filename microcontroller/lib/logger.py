import time
import machine

def log(message):
    log_file = open('log.txt', 'a')
    rtc = machine.RTC()
    localtime = rtc.datetime()
    #localtime = time.localtime()
    now = str("%02d"%localtime[0]) + "-" + str("%02d"%localtime[1]) + "-" + str(localtime[2]) + " " + str("%02d"%localtime[4]) + ":" + str("%02d"%localtime[5]) + ":" + str("%02d"%localtime[6])
    print(now + " - " + message)
    log_file.write(now + " - " + message + '\n')
    log_file.close()
