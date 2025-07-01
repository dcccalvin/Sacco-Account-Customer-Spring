package ke.co.emtechhouse.Report_Service.Report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ReportService {

    public Map<String,Object> setParameters(ReportRequest reportRequest) {
        Map<String,Object> parameters = new HashMap<>();



        String imagePath = "C:\\Users\\chuma\\JaspersoftWorkspace\\MyReports\\" + reportRequest.getFileName() + ".jpg";

//        Confirm the file exists
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            log.error("Image not found at: {}", imagePath);
        }

        parameters.put("IMAGE_PATH", imagePath);

        return parameters;

    }

}
