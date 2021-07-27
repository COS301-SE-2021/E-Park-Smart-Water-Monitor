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

def main():
    initialEstimate_1 = 72
    initialEstimate_2 = 102
    initialEstimate_3 = 51
    initialEstimate_4 = 24

    # 20 data points in each measurement set
    # sudden drop in measurement(waterlevel) -> followed by steady measurements
    measurements_1 = [75, 71, 70, 74, 30, 28, 25, 30, 24, 23, 18, 22, 21, 18, 18, 16, 14, 10, 11, 9]

    # sudden drop in measurement(waterlevel) -> followed by continuous drop in measurements
    measurements_2 = [100, 98, 87, 90, 90, 91, 80, 60, 40, 30, 20, 10, 5, 2, 5, 10, 4, 1, 1, 1]

    # sudden increase in measurement(waterlevel) -> followed by steady measurements
    measurements_3 = [50, 51, 49, 48, 48, 77, 79, 77, 78, 79, 80, 81, 82, 80, 82, 82, 80, 86, 87, 88]

    # sudden increase in measurement(waterlevel) -> followed by continuous increase
    measurements_4 = [20, 21, 22, 23, 24, 21, 40, 50, 70, 90, 150, 160, 180, 181, 182, 179, 180, 182, 183, 185]

    xVals = ['1', '2', '3', '4', '5', '6', '7',
             '8', '9', '10', '11', '12', '13', '14', '15',
             '16', '17', '18', '19', '20']

    dataDescription = [
                       "Sudden drop in measurement(waterlevel) -> followed by steady measurements",
                       "Sudden drop in measurement(waterlevel) -> followed by continuous drop in measurements",
                       "Sudden increase in measurement(waterlevel) -> followed by steady measurements",
                       "Sudden increase in measurement(waterlevel) -> followed by continuous increase"]


    scenario_1_data = kalmanAlg(measurements_1, initialEstimate_1, 2)
    scenario_1_measurement = []
    scenario_1_estimate = []

    scenario_2_data = kalmanAlg(measurements_2, initialEstimate_2, 2)
    scenario_2_measurement = []
    scenario_2_estimate = []

    scenario_3_data = kalmanAlg(measurements_3, initialEstimate_3, 2)
    scenario_3_measurement = []
    scenario_3_estimate = []

    scenario_4_data = kalmanAlg(measurements_4, initialEstimate_4, 2)
    scenario_4_measurement = []
    scenario_4_estimate = []

    for i in range (0, len(scenario_1_data)):
      scenario_1_measurement.append(scenario_1_data[i][0])
      scenario_1_estimate.append(scenario_1_data[i][1])

      scenario_2_measurement.append(scenario_2_data[i][0])
      scenario_2_estimate.append(scenario_2_data[i][1])

      scenario_3_measurement.append(scenario_3_data[i][0])
      scenario_3_estimate.append(scenario_3_data[i][1])

      scenario_4_measurement.append(scenario_4_data[i][0])
      scenario_4_estimate.append(scenario_4_data[i][1])


    printA(kalmanAlg(measurements_1, initialEstimate_1, 2), dataDescription[0])
    drawGraph(xVals, scenario_1_measurement, scenario_1_estimate, dataDescription[0])
    drawScatter(measurements_1)
    drawScatter(scenario_1_estimate)
    print("--------------------------------------------------------------")
    printA(kalmanAlg(measurements_2, initialEstimate_2, 2), dataDescription[1])
    drawGraph(xVals, scenario_2_measurement, scenario_2_estimate, dataDescription[1])
    drawScatter(measurements_2)
    drawScatter(scenario_2_estimate)
    print("--------------------------------------------------------------")
    printA(kalmanAlg(measurements_3, initialEstimate_3, 2), dataDescription[2])
    drawGraph(xVals, scenario_3_measurement, scenario_3_estimate, dataDescription[2])
    drawScatter(measurements_3)
    drawScatter(scenario_3_estimate)
    print("--------------------------------------------------------------")
    printA(kalmanAlg(measurements_4, initialEstimate_4, 2), dataDescription[3])
    drawGraph(xVals, scenario_4_measurement, scenario_4_estimate, dataDescription[3])
    drawScatter(measurements_4)
    drawScatter(scenario_4_estimate)