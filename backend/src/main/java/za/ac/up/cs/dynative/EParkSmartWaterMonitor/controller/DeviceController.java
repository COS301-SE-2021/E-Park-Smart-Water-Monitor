package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.EditDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.DeleteUserRequest;

import java.util.List;
import java.util.Optional;

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
    public java.util.Collection<WaterSourceDevice> getDevice() {
        return devicesService.getAll();
    }

    @GetMapping("/getStatus")
    public ResponseEntity<?> getStatus(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addDevice")
    public ResponseEntity<Object> addDevice(@RequestBody AddWaterSourceDeviceRequest addWSDRequest) {
        return new ResponseEntity<>(devicesService.addDevice(addWSDRequest),HttpStatus.OK);
    }

    @PostMapping("/getNumDevices")
    public ResponseEntity<Object> getNumDevices(@RequestBody GetNumDevicesRequest getNumDevicesRequest) {
        return new ResponseEntity<>(devicesService.getNumDevices(getNumDevicesRequest),HttpStatus.OK);
    }

    @GetMapping("/getAllDevices")
    public ResponseEntity<Object> getNumDevices() {
        return new ResponseEntity<>(devicesService.getAllDevices(),HttpStatus.OK);
    }

    @PutMapping("/editDevice")
    public ResponseEntity<Object> editDevice(@RequestBody EditDeviceRequest editDeviceRequest ) {
        return new ResponseEntity<>(devicesService.editDevice(editDeviceRequest),HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<Object> getDeviceById(@RequestBody FindDeviceRequest findDeviceRequest) {
        return new ResponseEntity<>(devicesService.findDevice(findDeviceRequest),HttpStatus.OK);
    }

    @PostMapping("/getDeviceData")
    public ResponseEntity<Object> getDeviceData(@RequestBody GetDeviceDataRequest getDeviceDataRequest) {
        return new ResponseEntity<>(devicesService.getDeviceData(getDeviceDataRequest),HttpStatus.OK);
    }

}
