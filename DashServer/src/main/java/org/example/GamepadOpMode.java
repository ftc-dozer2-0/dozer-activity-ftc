package org.example;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import org.example.testopmode.TestOpMode;

public class GamepadOpMode extends TestOpMode {
    public double player1X = 0;
    public double player1Y = 0;
    public double player2X = 0;
    public double player2Y = 0;
    TestDashboardInstance dashboard;
    private double lastTime;

    public GamepadOpMode() {
        super("GamepadOpMode");
    }

    @Override
    protected void init() {
        dashboard = TestDashboardInstance.getInstance();
        lastTime = System.currentTimeMillis();
    }

    @Override
    protected void loop() throws InterruptedException {
        double offset = System.currentTimeMillis() - lastTime;
        if (dashboard.gamepad1 != null && dashboard.gamepad2 != null) {
            player1X += dashboard.gamepad1.left_stick_x * offset / 10;
            player1Y += -dashboard.gamepad1.left_stick_y * offset / 10;
            player2X += dashboard.gamepad2.left_stick_x * offset / 10;
            player2Y += -dashboard.gamepad2.left_stick_y * offset / 10;
        }

        drawPlayers();

        lastTime = System.currentTimeMillis();
    }

    void drawPlayers() {
        TelemetryPacket packet = new TelemetryPacket();
        packet.fieldOverlay()
                .setStroke("blue")
                .fillCircle(player1X, player1Y, 10)
                .setStroke("red")
                .fillCircle(player2X, player2Y, 10);
        packet.addLine(String.valueOf(player1X));
        packet.addLine(String.valueOf(player1Y));
        packet.addLine(String.valueOf(player2X));
        packet.addLine(String.valueOf(player2Y));
        dashboard.sendTelemetryPacket(packet);
    }
}
