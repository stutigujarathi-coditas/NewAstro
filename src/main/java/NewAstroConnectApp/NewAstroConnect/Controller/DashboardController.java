package NewAstroConnectApp.NewAstroConnect.Controller;

import NewAstroConnectApp.NewAstroConnect.Dto.ConsultationDto;
import NewAstroConnectApp.NewAstroConnect.Dto.DashboardDto;
import NewAstroConnectApp.NewAstroConnect.Dto.ResponseApoDto;
import NewAstroConnectApp.NewAstroConnect.Service.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // Endpoint to get the dashboard data
    @GetMapping
    public ResponseEntity<ResponseApoDto<DashboardDto>> getDashboardData(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo) {
        long totalClientsToday = dashboardService.getTotalClientsToday();
        List<ConsultationDto> upcomingConsultations = dashboardService.getUpcomingConsultations(pageNo);

        DashboardDto dashboardDto = new DashboardDto(totalClientsToday, upcomingConsultations);

        ResponseApoDto<DashboardDto> response = new ResponseApoDto<>(dashboardDto, HttpStatus.OK.value(), "Dashboard data fetched successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}