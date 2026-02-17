package fast.campus.netflix.audit;

import fast.campus.netflix.logging.CreateAuditLog;
import fast.campus.netflix.logging.LogUserAuditHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuditHistoryService implements LogUserAuditHistoryUseCase {

    private final UserAuditHistoryPort userAuditHistoryPort;

    @Override
    public void log(CreateAuditLog auditLog) {
        userAuditHistoryPort.create(
                auditLog.userId(),
                auditLog.userRole(),
                auditLog.clientIp(),
                auditLog.reqMethod(),
                auditLog.reqUrl(),
                auditLog.reqHeader(),
                auditLog.reqPayload()
        );
    }
}
