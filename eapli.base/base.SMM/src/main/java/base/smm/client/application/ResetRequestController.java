package base.smm.client.application;

public class ResetRequestController {
    private final ResetRequestService svc = new ResetRequestService();

    public void sendResetRequest(String ip) throws Exception {
        svc.SendUdpCliTo(ip);
    }
}
