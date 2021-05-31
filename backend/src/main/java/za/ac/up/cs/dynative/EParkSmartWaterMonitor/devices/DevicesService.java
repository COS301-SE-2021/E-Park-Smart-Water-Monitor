package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import java.util.Collection;
import java.util.Optional;

public interface DevicesService {
    public void addDevice();
    public Collection<WaterSourceDevice> getAll();
    public Optional<WaterSourceDevice> findDevice();
}
