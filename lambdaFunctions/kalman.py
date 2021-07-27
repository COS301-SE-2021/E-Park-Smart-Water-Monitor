# kalman filter test implementation
def kalmanGain(errorEstimate, errorMeasurement):
  return errorEstimate/(errorEstimate + errorMeasurement)

def estimate(prevEst, kalmanGain, currMeasurement):
  return prevEst + kalmanGain*(currMeasurement - prevEst)

def estError(kalmanGain, prevEstError):
  return (1 - kalmanGain)*(prevEstError)
