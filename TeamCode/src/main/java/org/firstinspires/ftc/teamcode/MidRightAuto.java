package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="1 Right Side")
public class MidRightAuto extends LinearOpMode {

    @Override
    public void runOpMode() {
        Drivetrain dt = new Drivetrain(this, hardwareMap);
        ViperSlideClawCamera vscc = new ViperSlideClawCamera(this, hardwareMap);

        ElapsedTime runtime = new ElapsedTime();
        runtime.startTime();

        vscc.initializeCamera();
        waitForStart();

        while (opModeIsActive()) {
            vscc.closeClaw();
            int position = vscc.getPosition();
            int coneY = vscc.getAvgConeY();

            runtime.reset();
            while (runtime.milliseconds() < 1000) {
                position = vscc.getPosition();
                coneY = vscc.getAvgConeY();
            }

            telemetry.addData("Position", position);
            telemetry.addData("Cone Y", coneY);
            telemetry.update();

            vscc.slideTo(1000,0.7);
            dt.wait(300);
            dt.piddrive(2000,1/ 1000.0);
            dt.piddrive(-450);
            dt.strafe(410, 0.3, "left");
            vscc.slideTo(3950, 0.7);
            dt.piddrive(60);
            vscc.openClaw();
            dt.wait(200);
            dt.piddrive(-80);
            dt.strafe(460, 0.3, "right");
            vscc.slideTo(0, 0.6);

            if (position == 3) {
                dt.strafe(800,0.4,"right");
                dt.piddrive(-40);
            } else if (position == 1) {
                dt.strafe(800,0.4,"left");
                dt.piddrive(-40);
            }

            break;
        }
    }
}