package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.ReceiveDeviceDataRequest;

import java.util.Optional;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    DevicesService devicesService;


    @Autowired
    DeviceController(@Qualifier("DeviceSeriviceImpl") DevicesService devicesService){
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

    @GetMapping("addDevice")
    public ResponseEntity<?> addDevice() {
        devicesService.addDevice();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/getById")
    public Optional<WaterSourceDevice> getDeviceById() {
        return devicesService.findDevice();
    }
}
