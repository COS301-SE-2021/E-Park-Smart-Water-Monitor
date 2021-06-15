package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection;

import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.repositories.InspectionRepo;

@Service("InspectionService")
public class InspectionServiceImpl {

    private final InspectionRepo inspectionRepo;
}
