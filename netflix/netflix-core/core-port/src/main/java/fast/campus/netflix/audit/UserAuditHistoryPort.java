package fast.campus.netflix.audit;

public interface UserAuditHistoryPort {
    void create(String userId, String userRole, String clientIp, String reqMethod,
                String reqUrl, String reqHeader, String reqPayload);
}
