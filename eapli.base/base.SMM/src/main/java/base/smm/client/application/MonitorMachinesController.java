package base.smm.client.application;

public class MonitorMachinesController {
    private final MonitorMachinesService svc = new MonitorMachinesService();

    public void startMachineMonitoring() throws Exception {
        svc.SendUdpCliTo();
    }
}
