package za.ac.up.cs.dynative.EParkSmartWaterMonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;

@SpringBootApplication
public class EParkSmartWaterMonitorApplication {


	public static void main(String[] args) {
		SpringApplication.run(EParkSmartWaterMonitorApplication.class, args);

	}




}
