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

@CrossOrigin
@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    DevicesService devicesService;


    @Autowired
    DeviceController(@Qualifier("DeviceServiceImpl") DevicesService devicesService){
        this.devicesService = devicesService;
    }

    @PostMapping("/receiveDeviceData")
    public ResponseEntity<Object> receiveWaterDeviceData(@RequestBody ReceiveDeviceDataRequest request) {
        return new ResponseEntity<>(devicesService.receiveWaterDeviceData(request),HttpStatus.OK);
    }

    @GetMapping("/getDevice")
    public java.util.Collection<Device> getDevice() {
        return devicesService.getAll();
    }

    @GetMapping("/getStatus")
    public ResponseEntity<?> getStatus(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addDevice")
    public ResponseEntity<Object> addDevice(@RequestBody AddDeviceRequest addWSDRequest) throws InvalidRequestException {
        return new ResponseEntity<>(devicesService.addDevice(addWSDRequest),HttpStatus.OK);
    }

    @PostMapping("/getNumDevices")
    public ResponseEntity<Object> getNumDevices(@RequestBody GetNumDevicesRequest getNumDevicesRequest) throws InvalidRequestException {
        return new ResponseEntity<>(devicesService.getNumDevices(getNumDevicesRequest),HttpStatus.OK);
    }

    @PostMapping("/getParkDevices")
    public ResponseEntity<Object> getNumDevices(@RequestBody GetParkDevicesRequest getParkDevicesRequest) throws InvalidRequestException {
        return new ResponseEntity<>(devicesService.getParkDevices(getParkDevicesRequest), HttpStatus.OK);
    }

    @GetMapping("/getAllDevices")
    public ResponseEntity<Object> getNumDevices() {
        return new ResponseEntity<>(devicesService.getAllDevices(),HttpStatus.OK);
    }

    @PutMapping("/editDevice")
    public ResponseEntity<Object> editDevice(@RequestBody EditDeviceRequest editDeviceRequest ) {
        return new ResponseEntity<>(devicesService.editDevice(editDeviceRequest),HttpStatus.OK);
    }

    @PostMapping("/getById")
    public ResponseEntity<Object> getDeviceById(@RequestBody FindDeviceRequest findDeviceRequest) throws InvalidRequestException {
        return new ResponseEntity<>(devicesService.findDevice(findDeviceRequest),HttpStatus.OK);
    }

    @PostMapping("/getDeviceData")
    public ResponseEntity<Object> getDeviceData(@RequestBody GetDeviceDataRequest getDeviceDataRequest) throws InvalidRequestException {
        return new ResponseEntity<>(devicesService.getDeviceData(getDeviceDataRequest),HttpStatus.OK);
    }

    @PutMapping("/setMetricFrequency")
    public ResponseEntity<Object> setMetricFrequency(@RequestBody SetMetricFrequencyRequest setMetricFrequencyRequest) {
        return new ResponseEntity<>(devicesService.setMetricFrequency(setMetricFrequencyRequest), HttpStatus.OK);
    }

    @PostMapping("/dataNotification")
    public void dataNotification(@RequestBody DataNotificationRequest dataNotificationRequest) throws InvalidRequestException {
        devicesService.getDataNotification(dataNotificationRequest);
    }

    @DeleteMapping("/deleteDevice")
    public ResponseEntity<Object> deleteDevice(@RequestBody DeleteDeviceRequest deleteDeviceRequest) throws InvalidRequestException {
        return new ResponseEntity<>(devicesService.deleteDevice(deleteDeviceRequest),HttpStatus.OK);
    }

    @PostMapping("/pingDevice")
    public ResponseEntity<Object> pingDevice(@RequestBody PingDeviceRequest pingDeviceRequest) {
        return new ResponseEntity<>(devicesService.pingDevice(pingDeviceRequest), HttpStatus.OK);
    }
}
