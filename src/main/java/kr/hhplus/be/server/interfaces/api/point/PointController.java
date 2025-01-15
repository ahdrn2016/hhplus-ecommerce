package kr.hhplus.be.server.interfaces.api.point;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.domain.point.PointInfo;
import kr.hhplus.be.server.domain.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/point")
public class PointController {

    private final PointService pointService;

    @GetMapping(path = "/{userId}", produces = { "application/json" })
    @Operation(summary = "포인트 조회", description = "포인트를 조회합니다.", tags = { "point" })
    public ResponseEntity<PointResponse.Point> point(
            @PathVariable Long userId
    ) {
        PointInfo.Point point = pointService.point(userId);
        return ResponseEntity.ok(PointResponse.of(point));
    }

    @PostMapping(path = "/charge", produces = { "application/json" }, consumes = { "application/json" })
    @Operation(summary = "포인트 충전", description = "포인트를 충전합니다.", tags = { "point" })
    public ResponseEntity<PointResponse.Point> charge(
            @RequestBody PointRequest.Charge request
    ) {
        PointInfo.Point point = pointService.charge(request.toCommand());
        return ResponseEntity.ok(PointResponse.of(point));
    }
}
