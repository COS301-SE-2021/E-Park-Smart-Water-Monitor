package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.SiteRepo;

import java.util.Collection;

@Service("SiteServiceImpl")
public class SiteServicesImpl implements SiteService
{

    SiteRepo siteRepo;

    public SiteServicesImpl(@Qualifier("SiteRepo") SiteRepo sRepo)
    {
        this.siteRepo = sRepo;
    }


    public void addSite() {
        Site siteToBeAdded = new Site("Lake A");

        siteRepo.save(siteToBeAdded);
    }

    public void addWaterSourceDevice(int siteID, WaterSourceDevice deviceToAdd)
    {
//        Optional<Site> siteToAddTo =findSite(siteID);
//        if (siteToAddTo.isPresent())
//        {
//            Site awe = siteToAddTo;
//        }
//        siteToAddTo.

    }

    public Collection<Site> getAll() {
        return siteRepo.findAll();
    }


    @Override
    public Site findSite() {
        System.out.println("FOUND");
        Long l = new Long(2);
        return siteRepo.findBySiteName("Lake A");


    }

}
