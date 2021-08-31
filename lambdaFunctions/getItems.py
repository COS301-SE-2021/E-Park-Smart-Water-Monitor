import json
import boto3
from boto3.dynamodb.conditions import Key


print('Loading function')


def lambda_handler(event, context):
    # print("Received event: " + json.dumps(event, indent=2))
    print("------> device: = " + event["deviceName"])
    #raise Exception('Something went wrong')

    client = boto3.resource('dynamodb')
    table = client.Table("WaterSourceData")
    print(table.table_status)

    result = table.query(Limit=10,
        KeyConditionExpression=Key('deviceName').eq('D1'),
        ScanIndexForward=False
    )
    print(result['Items'][0])
    print(result['Items'][1])
