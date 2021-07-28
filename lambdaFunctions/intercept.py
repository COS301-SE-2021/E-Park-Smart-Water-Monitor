import json
import boto3
import time
from boto3.dynamodb.conditions import Key
from decimal import Decimal


print('Loading function')


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

    # newMeasuments=[]
    # for x in range(len(measurements)):
    #     helper=Decimal(measurements[x]['value'])
    #     newMeasuments.append(
    #         {'type':measurements[x]['type'],
    #     'value':helper,
    #     'unitOfMeasurement':measurements[x]['unitOfMeasurement'],
    #     'deviceDateTime':measurements[x]['deviceDateTime']
    #     })


    print("+++ADDING+++")

    # newMeasuments = json.loads(json.dumps(newMeasuments), parse_float=Decimal)
    print(measurements)
    date=str(int(time.time()))
    print(date)
    response = table.put_item(
        Item={'deviceName':deviceName,
            'timestamp':date,
            'WaterSourceData':{
                'deviceName':deviceName,
                'measurements':measurements
            }

    })
    print(response)
    print("GETTING ITEM")

    gettem = table.query(KeyConditionExpression=Key('deviceName').eq('D1'),Limit=1,ScanIndexForward=False)

    # gettem = table.get_item(Key={'deviceName': 'D1'})
    print(gettem['Items'])
