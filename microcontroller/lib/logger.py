import time

def log(message):
    log_file = open('../log.txt', 'a')
    localtime = time.localtime()
    now = str("%02d"%localtime[2]) + "/" + str("%02d"%localtime[1]) + "/" + str(localtime[0]) + "-" + str("%02d"%localtime[3]) + ":" + str("%02d"%localtime[4]) + ":" + str("%02d"%localtime[5])
    print(now + " - " + message)
    log_file.write(now + " - " + message + '\n')
    log_file.flush()
    
