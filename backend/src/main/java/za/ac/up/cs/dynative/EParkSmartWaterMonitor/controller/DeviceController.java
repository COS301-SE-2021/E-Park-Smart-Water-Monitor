package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DeviceController {

    DevicesService devicesService;


    @Autowired
    DeviceController(@Qualifier("DeviceSeriviceImpl") DevicesService devicesService){
        this.devicesService = devicesService;
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
