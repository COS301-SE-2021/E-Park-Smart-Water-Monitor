import plotly.graph_objects as go
import matplotlib.pyplot as plt
import numpy as np

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

def printA(a, dataDescription):
  print()
  print(dataDescription)
  print("Measurement Estimate KalmanGain Est_Error")
  for row in a:
      for col in row:
          print("{:9.3f}".format(col), end=" ")
      print("")

def drawScatter(measurements):
  xVals = range(0,20)
  model = np.polyfit(np.array(xVals), np.array(measurements), 5)
  print(model)
  predict = np.poly1d(model)
  x_lin_reg = range(0, 20)
  y_lin_reg = predict(x_lin_reg)
  plt.scatter(xVals, measurements)
  plt.plot(x_lin_reg, y_lin_reg, c = 'r')
  plt.show()

def drawGraph(xVals, measurement, estimate, title):
  fig = go.Figure()
  fig.add_trace(go.Scatter(x=xVals, y=measurement, name='Mesurement',
                         line=dict(color='firebrick', width=4)))
  fig.add_trace(go.Scatter(x=xVals, y=estimate, name = 'Estimate',
                         line=dict(color='royalblue', width=4)))

  fig.update_layout(title='Measurement vs Estimate: ' + title,
                   xaxis_title='Data point',
                   yaxis_title='Temperature (degrees F)')


  fig.show()