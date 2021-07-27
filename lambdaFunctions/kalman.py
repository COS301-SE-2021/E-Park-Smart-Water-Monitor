# kalman filter test implementation
def kalmanGain(errorEstimate, errorMeasurement):
  return errorEstimate/(errorEstimate + errorMeasurement)

def estimate(prevEst, kalmanGain, currMeasurement):
  return prevEst + kalmanGain*(currMeasurement - prevEst)

def estError(kalmanGain, prevEstError):
  return (1 - kalmanGain)*(prevEstError)

def kalmanAlg(measurements, initialEstimate, initialEstimateError):
  measurement_Result_Matrix = []
  numDataPoints = len(measurements)
  measurementError = 4

  for i in range(0, numDataPoints):
    tableEntry = []
    if i == 0:
      KG = kalmanGain(initialEstimateError, measurementError)
      EST = estimate(initialEstimate, KG, measurements[i])
      EST_ERR = estError(KG, initialEstimateError)
      tableEntry.append(measurements[i])
      tableEntry.append(EST)
      tableEntry.append(KG)
      tableEntry.append(EST_ERR)
    else:
      KG = kalmanGain(measurement_Result_Matrix[i - 1][3], measurementError)
      EST = estimate(measurement_Result_Matrix[i - 1][1], KG, measurements[i])
      EST_ERR = estError(KG, measurement_Result_Matrix[i - 1][3])
      tableEntry.append(measurements[i])
      tableEntry.append(EST)
      tableEntry.append(KG)
      tableEntry.append(EST_ERR)
    measurement_Result_Matrix.append(tableEntry)

  return measurement_Result_Matrix