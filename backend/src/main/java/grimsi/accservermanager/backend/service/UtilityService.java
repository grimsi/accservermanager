package grimsi.accservermanager.backend.service;

import grimsi.accservermanager.backend.enums.OperatingSystem;
import grimsi.accservermanager.backend.exception.UnknownHostOsException;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {

    public OperatingSystem getHostOS(){
        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win")){
            return OperatingSystem.WINDOWS;
        }
        else if(os.contains("mac")){
            return OperatingSystem.MAC;
        }
        else if(os.contains("nix") || os.contains("nux") || os.contains("aix")){
            return OperatingSystem.UNIX;
        }

        throw new UnknownHostOsException();
    }
}
