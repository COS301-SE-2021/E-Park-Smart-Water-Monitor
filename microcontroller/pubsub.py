from awscrt import io, mqtt, auth, http
from awsiot import mqtt_connection_builder
import sys
import time
import json
import random
from datetime import datetime

# Callback when connection is accidentally lost.
def on_connection_interrupted(connection, error, **kwargs):
    print("Connection interrupted. error: {}".format(error))


# Callback when an interrupted connection is re-established.
def on_connection_resumed(connection, return_code, session_present, **kwargs):
    print("Connection resumed. return_code: {} session_present: {}".format(return_code, session_present))

    if return_code == mqtt.ConnectReturnCode.ACCEPTED and not session_present:
        print("Session did not persist. Resubscribing to existing topics...")
        resubscribe_future, _ = connection.resubscribe_existing_topics()

        # Cannot synchronously wait for resubscribe result because we're on the connection's event-loop thread,
        # evaluate result with a callback instead.
        resubscribe_future.add_done_callback(on_resubscribe_complete)


def on_resubscribe_complete(resubscribe_future):
        resubscribe_results = resubscribe_future.result()
        print("Resubscribe results: {}".format(resubscribe_results))

        for topic, qos in resubscribe_results['topics']:
            if qos is None:
                sys.exit("Server rejected resubscribe to topic: {}".format(topic))


# Callback when the subscribed topic receives a message
def on_message_received(topic, payload, dup, qos, retain, **kwargs):
    print("Received message from topic '{}': {}".format(topic, payload))

# Function that returns the sensor data to be sent
def sensorPayload():
    randValue1 = random.randint(0,100)
    randValue2 = random.randint(0,50)
    randValue3 = random.randint(0,70)
    sensorMeasuremenTime = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    payload = json.dumps({
        "deviceName": "D1",
        "measurements":
        [
            {
                "type": "WATER_LEVEL",
                "value": randValue1,
                "unitOfMeasurement": "METER",
                "deviceDateTime": sensorMeasuremenTime
            }
            ,
            {
                "type": "WATER_TEMP",
                "value": randValue2,
                "unitOfMeasurement": "CENTIGRADE",
                "deviceDateTime": sensorMeasuremenTime
            }
            ,
            {
                "type": "WATER_QUALITY",
                "value": randValue3,
                "unitOfMeasurement": "PPM",
                "deviceDateTime": sensorMeasuremenTime
            }
        ]
    })
    return payload


if __name__ == '__main__':

    #----------CONNECTION ESTABLISHMENT SECTION----------
    # cert_filepath, pri_key_filepath and endpoint removed for security reasons
    event_loop_group = io.EventLoopGroup(1)
    host_resolver = io.DefaultHostResolver(event_loop_group)
    client_bootstrap = io.ClientBootstrap(event_loop_group, host_resolver)

    proxy_options = None
    mqtt_connection = mqtt_connection_builder.mtls_from_path(
        endpoint="",
        port=8883,
        cert_filepath="",
        pri_key_filepath="",
        client_bootstrap=client_bootstrap,
        ca_filepath="",
        on_connection_interrupted=on_connection_interrupted,
        on_connection_resumed=on_connection_resumed,
        client_id="NA",
        clean_session=False,
        keep_alive_secs=6,
        http_proxy_options=proxy_options)

    connect_future = mqtt_connection.connect()
    connect_future.result() # Future.result() waits until a result is available
    print("Connected!")
    #----------CONNECTION ESTABLISHMENT SECTION----------


    #----------SUBSCRIBE TO A TOPIC, I.E. LISTEN TO EVENT ON SHADOW FOR EXAMPLE----------
    # Subscribe to a topic, when another device publishes to the subscribed topic the IoT device receives this message
    topicName = "iot/addWaterSourceData"
    print("Subscribing to topic '{}'...".format(topicName))
    subscribe_future, packet_id = mqtt_connection.subscribe(
        topic=topicName,
        qos=mqtt.QoS.AT_LEAST_ONCE,
        callback=on_message_received)

    subscribe_result = subscribe_future.result()
    print("Subscribed with {}".format(str(subscribe_result['qos'])))
    #----------SUBSCRIBE TO A TOPIC, I.E. LISTEN TO EVENT ON SHADOW FOR EXAMPLE----------


    #----------PUBLISH TO A TOPIC, I.E. PUBLISH/SEND DATA TO AWS IOT CORE/DYNAMO_DB----------
    # Publish message to server desired number of times.
    # Current configuration: publishes data every 30 seconds up until 1000 payloads have been published
    print("Sending messages: ")
    publish_count = 1
    while (publish_count <= 1000):
        message = sensorPayload()
        print("Publishing message to topic iot/addWaterSourceData: ")

        mqtt_connection.publish(
            topic="iot/addWaterSourceData",
            payload=message,
            qos=mqtt.QoS.AT_LEAST_ONCE)
        time.sleep(30)
        publish_count += 1
    #----------PUBLISH TO A TOPIC, I.E. PUBLISH/SEND DATA TO AWS IOT CORE/DYNAMO_DB----------


    #----------DISCONNECTION SECTION----------
    print("Disconnecting...")
    disconnect_future = mqtt_connection.disconnect()
    disconnect_future.result()
    print("Disconnected!")
    #----------DISCONNECTION SECTION----------
