package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.AddWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetNumDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.ReceiveDeviceDataRequest;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/getById")
    public Optional<WaterSourceDevice> getDeviceById() {
        return devicesService.findDevice();
    }
}
