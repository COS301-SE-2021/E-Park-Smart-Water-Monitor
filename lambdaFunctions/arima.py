  GNU nano 4.8                                              arima_server.py
from flask import Flask, request, jsonify, redirect, url_for, render_template
from statsmodels.tsa.ar_model import AR
from statsmodels.tsa.arima_model import ARIMA
import warnings
warnings.filterwarnings('ignore')

app = Flask(__name__)

@app.route("/")
def home():
        return "Home."

@app.route("/arimaPrediction",methods=["POST"])
def predict():
        phReadings = request.json["phLevels"].split(",")
        training = [val for val in phReadings]
        predictions = list()
        numPredictions = 5
        for num in range(numPredictions):
                try:
                        model = ARIMA(training, order=(1,0,1))
                        model_fit = model.fit(disp=-1)
                        forecast = model_fit.forecast()[0]
                        phReadings.append(forecast[0])

                        if num % 5 == 0 and num > 0:
                                training.append(training[num])
                        else:
                                training.append(forecast[0])
                except:
                        continue
        phReadings = str(phReadings)
        return phReadings

if __name__ == "__main__":
        app.run(host = '0.0.0.0',port=5000,debug=True)


