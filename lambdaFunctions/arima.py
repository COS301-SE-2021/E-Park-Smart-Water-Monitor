from statsmodels.tsa.ar_model import AR
from statsmodels.tsa.arima_model import ARIMA
import warnings
warnings.filterwarnings('ignore')
import sys

phReadings = list()
for i in range(1,len(sys.argv)):
    phReadings.append(float(sys.argv[i].replace(",","")))

training = [val for val in phReadings]
predictions = list()
numPredictions = 5
for num in range(numPredictions):
    try:
        model = ARIMA(training, order=(1,0,1))
        mode_fit = model.fit(disp=-1)
        forecast = mode_fit.forecast()[0]
        phReadings.append(forecast[0])

        if num % 5 == 0 and num > 0:
            training.append(training[num])
        else:
            training.append(forecast)
    except:
        continue

print(phReadings)

# install statsmodels version 0.10.0
# install numpy version 1.16.5
