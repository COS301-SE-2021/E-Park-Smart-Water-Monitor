var AWS = require('aws-sdk');
const IOT_ENDPOINT = "xxxxxxxxxxxxxxxxxx.iot.us-east-2.amazonaws.com"

var iotdata = new AWS.IotData({endpoint:IOT_ENDPOINT});

exports.handler = function(event, context, callback) {
    let deviceName = event.DeviceName
    let message = `{ "DeviceName": "${deviceName}"}`
    var params = {
        topic: 'iot/ping',
        payload: message,
        qos: 1
    };

    return iotdata.publish(params, function(err, data) {
        if(err){
            console.log(err);
        }
        else{
            console.log("Success.");
            //context.succeed();
        }
    });
};