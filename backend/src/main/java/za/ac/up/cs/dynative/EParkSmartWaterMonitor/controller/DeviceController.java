package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.DataNotification;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;

/**
 * This class will map all the http requests made related to devices to their
 * appropriate functions in the device service implementation.
 * The http methods that will be used: get, post, delete and put.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    /**
     * The deviceService will be used to call functions in the device service implementation.
     */
    DevicesService devicesService;

    /**
     * This constructor will set the deviceService variable defined previously so that it is not of null value
     * @param devicesService will be set and used across the rest of this class.
     */
    @Autowired
    DeviceController(@Qualifier("DeviceServiceImpl") DevicesService devicesService){
        this.devicesService = devicesService;
    }

    /**
     * When a post http request is made with the url /api/devices/receiveDeviceDate this function will send
     * the JSON object to the device service implementation for processing.
     * @param request will contain a list of measurements and the device name.
     * @return This function will return a ReceiveDeviceDataResponse object containing a success and status value
     */
    @PostMapping("/receiveDeviceData")
    public ResponseEntity<Object> receiveWaterDeviceData(@RequestBody ReceiveDeviceDataRequest request) {
        return new ResponseEntity<>(devicesService.receiveWaterDeviceData(request),HttpStatus.OK);
    }

    /**
     * @return all the the devices in the form of a collection.
     */
    @GetMapping("/getDevice")
    public java.util.Collection<Device> getDevice() {
        return devicesService.getAll();
    }

    @GetMapping("/getStatus")
    public ResponseEntity<?> getStatus(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This post http request will add a device to a specific water site within a park. The request sent in will be
     * directed as input for the device service's addDevice method.
     * @param addWSDRequest will be an object of type AddDeviceRequest containing the device id,
     *      the water site id, the device model, the device name, the device coordinates in the form of
     *      longitude and latitude and lastly also the park name.
     * @return AddDeviceResponse will be an object containing the success and status values respectively.
     * @throws InvalidRequestException
     */
    @PostMapping("/addDevice")
    public ResponseEntity<Object> addDevice(@RequestBody AddDeviceRequest addWSDRequest) throws InvalidRequestException {
        return new ResponseEntity<>(devicesService.addDevice(addWSDRequest),HttpStatus.OK);
    }

    /**
     * When this post request is made a park id will be used to determine how many devices there are and return that number
     * @param getNumDevicesRequest will contain the park id that is given as input to the device service's getNumDevices method.
     * @return A GetNumDevicesResponse object will be returned, this object will contain a number and the success value.
     * @throws InvalidRequestException
     */
    @PostMapping("/getNumDevices")
    public ResponseEntity<Object> getNumDevices(@RequestBody GetNumDevicesRequest getNumDevicesRequest) throws InvalidRequestException {
        return new ResponseEntity<>(devicesService.getNumDevices(getNumDevicesRequest),HttpStatus.OK);
    }

    /**
     * This post request will retrieve and return all the devices allocated to a park.
     * @param getParkDevicesRequest will contain the park id that needs to be used and searched with.
     * @return A collection of devices will be returned along with the success value and status.
     */
    @PostMapping("/getParkDevices")
    public ResponseEntity<Object> getNumDevices(@RequestBody GetParkDevicesRequest getParkDevicesRequest) throws InvalidRequestException {
        return new ResponseEntity<>(devicesService.getParkDevices(getParkDevicesRequest), HttpStatus.OK);
    }

    /**
     * This get request will return all the devices in the system.
     * @return A collection of devices will be returned along with the success value and status.
     */
    @GetMapping("/getAllDevices")
    public ResponseEntity<Object> getNumDevices() {
        return new ResponseEntity<>(devicesService.getAllDevices(),HttpStatus.OK);
    }

    /**
     * This put request will update device details.
     * @param editDeviceRequest will contain the parameters to be updated. The options to update: device model, device name, device type, latitude and longitude.
     *                          The device id will also be contained in the request object.
     * @return An EditDeviceResponse object with a success value and status will be sent back.
     */
    @PutMapping("/editDevice")
    public ResponseEntity<Object> editDevice(@RequestBody EditDeviceRequest editDeviceRequest ) {
        return new ResponseEntity<>(devicesService.editDevice(editDeviceRequest),HttpStatus.OK);
    }

    /**
     * This post request will ask for a specific device based on an id.
     * @param findDeviceRequest contains an id that will be used to search the db.
     * @return a FindDeviceResponse will be returned. This object will contain the success and status values along with
     * the device object itself.
     */
    @PostMapping("/getById")
    public ResponseEntity<Object> getDeviceById(@RequestBody FindDeviceRequest findDeviceRequest)  {
        return new ResponseEntity<>(devicesService.findDevice(findDeviceRequest),HttpStatus.OK);
    }

    /**
     * This post method retrieves device data.
     * @param getDeviceDataRequest contains the device name, the number of results and an indication if the data should be sorted.
     * @return a GetDeviceDataResponse is returned with the usual success value and status; along with the device name and the
     * inner response containing the device's data.
     */
    @PostMapping("/getDeviceData")
    public ResponseEntity<Object> getDeviceData(@RequestBody GetDeviceDataRequest getDeviceDataRequest) {
        return new ResponseEntity<>(devicesService.getDeviceData(getDeviceDataRequest),HttpStatus.OK);
    }

    /**
     * This put method updates the frequency of device readings of a specific device.
     * @param setMetricFrequencyRequest contains the device id and the new value.
     * @return a SetMetricFrequencyResponse will be sent back containing a status and success value to determine if the operation was successful.
     */
    @PutMapping("/setMetricFrequency")
    public ResponseEntity<Object> setMetricFrequency(@RequestBody SetMetricFrequencyRequest setMetricFrequencyRequest) {
        return new ResponseEntity<>(devicesService.setMetricFrequency(setMetricFrequencyRequest), HttpStatus.OK);
    }

    /**
     * This post method will request the incoming data to come through.
     * @param dataNotificationRequest contains a list of data
     */
    @PostMapping("/dataNotification")
    public void dataNotification(@RequestBody DataNotificationRequest dataNotificationRequest) throws InvalidRequestException {
        devicesService.getDataNotification(dataNotificationRequest);
    }

    /**
     * This delete endpoint will delete the device specified in the request.
     * @param deleteDeviceRequest contains the device that is to be deleted id.
     * @return A DeleteDeviceResponse will be returned with the success value and a status.
     */
    @DeleteMapping("/deleteDevice")
    public ResponseEntity<Object> deleteDevice(@RequestBody DeleteDeviceRequest deleteDeviceRequest){
        return new ResponseEntity<>(devicesService.deleteDevice(deleteDeviceRequest),HttpStatus.OK);
    }

    /**
     * This post endpoint will activate the device to send current readings.
     * @param pingDeviceRequest will contain the id of the device that needs to be pinged and this will be used as input in the device service.
     * @return A PingDeviceResponse object containing a status, success and deviceName along with an inner response containing measurements.
     */
    @PostMapping("/pingDevice")
    public ResponseEntity<Object> pingDevice(@RequestBody PingDeviceRequest pingDeviceRequest) {
        return new ResponseEntity<>(devicesService.pingDevice(pingDeviceRequest), HttpStatus.OK);
    }
}
