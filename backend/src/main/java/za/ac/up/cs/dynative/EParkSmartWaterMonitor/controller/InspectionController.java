package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.InspectionService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.AddInspectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.GetInspectionsRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/inspections")
public class InspectionController {

    InspectionService inspectionService;

    @Autowired
    InspectionController(@Qualifier("InspectionServiceImpl") InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    @PostMapping("/addInspection")
    public ResponseEntity<Object> addInspection(@RequestBody AddInspectionRequest request) {
        return new ResponseEntity<>(inspectionService.addInspection(request), HttpStatus.OK);
    }

    @PostMapping("/getInspections")
    public ResponseEntity<Object> getInspections(@RequestBody GetInspectionsRequest request) {
        return new ResponseEntity<>(inspectionService.getInspections(request), HttpStatus.OK);
    }
}
