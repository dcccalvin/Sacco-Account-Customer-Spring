package ke.co.emtechhouse.Report_Service.Report;

import com.google.gson.Gson;
import ke.co.emtechhouse.Report_Service.Utils.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/download")
public class ReportController {
    @Autowired
    ReportService reportService;
    @Value("${sacco.reports_path}")
    private String path;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    @PostMapping("/pdf")
    public ResponseEntity<?> downloadPdf(@RequestBody ReportRequest reportRequestObject){
//        ReportRequest reportRequestObject = new Gson().fromJson(reportRequest,ReportRequest.class);
        EntityResponse response= new EntityResponse<>();
        log.info("Requestparams " + reportRequestObject);

        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(path+reportRequestObject.getFileName()));
            Map<String,Object> parameters = reportService.setParameters(reportRequestObject);
            JasperPrint printReport = JasperFillManager.fillReport(compileReport,parameters,connection);
            byte[] data = JasperExportManager.exportReportToPdf(printReport);
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=report.pdf");
            response.setEntity(data);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("PDF generated succesfully");
            log.info("Pdf Gnerated Succesfully: " + data);
            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
        }catch (Exception e){
            log.info("Error: "+e);
            response.setEntity(null);
            response.setStatusCode(500);
            response.setMessage("Error generating PDF");
            return null;

        }


    }

}
