package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Telemetry")
public class Telemetry extends LinearOpMode {

    @Override
    public void runOpMode() {
        ViperSlideClawCamera vscc = new ViperSlideClawCamera(this, hardwareMap);
        vscc.initializeCamera();

        waitForStart();

        while (opModeIsActive()) {
            sleep(300);
            int position = vscc.getPosition();
            telemetry.addData("Position", position);
            telemetry.addData("Cone Y", vscc.getAvgConeY());
            telemetry.update();
        }
    }
}