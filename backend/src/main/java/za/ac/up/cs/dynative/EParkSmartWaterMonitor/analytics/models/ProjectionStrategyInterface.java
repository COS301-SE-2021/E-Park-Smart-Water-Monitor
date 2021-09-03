package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;

public interface ProjectionStrategyInterface {
    /**
        ProjectionStrategyInterface serves as the interface class for the predictive aspect of the project.
        It serves as the main "blueprint" of what functions will be needed to facilitate the prediction of
        - water level
        - temperature
        - water quality (ph)
     */
    DeviceProjectionResponse predict();
}
