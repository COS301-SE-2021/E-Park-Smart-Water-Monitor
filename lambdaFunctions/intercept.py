import json
import boto3
import time
from boto3.dynamodb.conditions import Key
from decimal import Decimal


print('Loading function')


# kalman filter test implementation
def kalmanGain(errorEstimate, errorMeasurement):
  return errorEstimate/(errorEstimate + errorMeasurement)

def estimate(prevEst, kalmanGain, currMeasurement):
  return prevEst + kalmanGain*(currMeasurement - prevEst)

def estError(kalmanGain, prevEstError):
  return (1 - kalmanGain)*(prevEstError)


def lambda_handler(event, context):



    print("Dump")
    print("Received event: " + json.dumps(event, indent=0))
    print("------> device: = " + event["deviceName"])
    #raise Exception('Something went wrong')

    client = boto3.resource('dynamodb')
    # table = client.Table("Test")
    table = client.Table("WaterSourceData")

    print(table.table_status)

    data=json.loads(json.dumps(event, indent=2),parse_float=Decimal)
    deviceName=data["deviceName"]
    measurements=[]
    print("LENGTH: ",len(data["measurements"]))
    size=len(data["measurements"])
    measurements=data["measurements"]
    print("ALL MEASUREMENTS ",measurements)

    print("before ",measurements[0]['value'])
    # measurements[0]['value']=12.9
    print("ALL MEASUREMENTS ",measurements)

    #GETTING PREVIOUS ITEM FOR KALMAN
    print("GETTING ITEM")
    prevItem = table.query(KeyConditionExpression=Key('deviceName').eq(deviceName),Limit=1,ScanIndexForward=False)
    print("LEEEENGTH: ",len(prevItem['Items']))

    # print(prevItem['Items'][0])

    prevMeasurements=None
    exists=False
    if not table.item_count == 0 and not len(prevItem['Items'])==0:
        prevItem=prevItem['Items'][0]
        exists = ("EstimateValue" in prevItem["WaterSourceData"]["measurements"][0] or 'EstimateValue' in prevItem["WaterSourceData"]["measurements"][0])
        prevMeasurements = prevItem["WaterSourceData"]["measurements"]

    print("TEST ",exists)


    newMeasurements=[]
    for x in range(len(measurements)):

        val=measurements[x]['value']

        #KALMAN ALG

        currMeasurementError = 4
        initialEstimateError = 2
        initialEstimate = val + 4

        EST=0
        EST_ERR=0

        if not exists:
            KG = kalmanGain(initialEstimateError, currMeasurementError)
            EST = estimate(initialEstimate, KG, val)
            EST_ERR = estError(KG, initialEstimateError)
        else:
            prevMeasurementSet=prevMeasurements[x]
            prevErrorEstimate=prevMeasurementSet['EstimateError']
            prevEstimate=prevMeasurementSet['EstimateValue']

            KG = kalmanGain(prevErrorEstimate, currMeasurementError)
            EST = estimate(prevEstimate, KG, val)
            EST_ERR = estError(KG, prevErrorEstimate)



        helper=Decimal(measurements[x]['value'])
        newMeasurements.append(
            {'type':measurements[x]['type'],
        'value':helper,
        'unitOfMeasurement':measurements[x]['unitOfMeasurement'],
        'deviceDateTime':measurements[x]['deviceDateTime'],
        'EstimateValue': Decimal(str(EST)),
        'EstimateError': Decimal(str(EST_ERR))
        })


    print("+++ADDING+++")

    # newMeasuments = json.loads(json.dumps(newMeasuments), parse_float=Decimal)
    print(newMeasurements)
    date=str(int(time.time()))
    print(date)
    response = table.put_item(
        Item={'deviceName':deviceName,
            'timestamp':date,
            'WaterSourceData':{
                'deviceName':deviceName,
                'measurements':newMeasurements
            }

    })
    print(response)


# # kalman filter test implementation
# def kalmanGain(errorEstimate, errorMeasurement):
#   return errorEstimate/(errorEstimate + errorMeasurement)
#
# def estimate(prevEst, kalmanGain, currMeasurement):
#   return prevEst + kalmanGain*(currMeasurement - prevEst)
#
# def estError(kalmanGain, prevEstError):
#   return (1 - kalmanGain)*(prevEstError)
#
#
# def kalmanAlg(measurement):
#   currMeasurementError = 4
#   initialEstimateError = 2
#   initialEstimate = measurement + 4
#
#   if previousEntryVals == -1:
#     KG = kalmanGain(initialEstimateError, currMeasurementError)
#     EST = estimate(initialEstimate, KG, measurement)
#     EST_ERR = estError(KG, initialEstimateError)
#   else:
#     KG = kalmanGain(prevErrorEstimate, currMeasurementError)
#     EST = estimate(prevEstimate, KG, measurement)
#     EST_ERR = estError(KG, prevEstimateError)
#
#   #add calculated values to json object
#
# kalmanAlg(measurements)
