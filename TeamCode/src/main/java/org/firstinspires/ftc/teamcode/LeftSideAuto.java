package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="1+1 Left Side")
public class LeftSideAuto extends LinearOpMode {

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
            while (runtime.milliseconds() < 500) {
                position = vscc.getPosition();
                coneY = vscc.getAvgConeY();
            }

            vscc.slideTo(500,0.7);
            dt.wait(300);
            dt.piddrive(1550);
            dt.strafe(445, 0.3, "right");
            vscc.slideTo(3950, 0.7);
            dt.piddrive(77);
            vscc.openClaw();
            dt.wait(200);
            dt.piddrive(-80);
            dt.strafe(450, 0.3, "left");
            vscc.slideTo(680, 0.6);
            dt.turn(85, 0.2,"left");
            dt.strafe(75,0.3,"right");
            dt.piddrive(820);
            vscc.closeClaw();
            dt.wait(300);
            vscc.slideTo(1500,0.7);
            dt.piddrive(-740);
            dt.turn(90, 0.2,"right");
            dt.piddrive(-70);
            dt.strafe(410,0.3,"right");
            vscc.slideTo(3950,0.7);
            dt.piddrive(120);
            vscc.openClaw();
            dt.wait(300);
            dt.piddrive(-150);
            dt.strafe(470,0.3,"left");
            vscc.slideTo(0,0.7);

            if (position == 3) {
                dt.strafe(800,0.4,"right");
            } else if (position == 1) {
                dt.strafe(800,0.4,"left");
            }

            telemetry.addData("Position", position);
            telemetry.addData("Cone Y", coneY);
            telemetry.update();

            dt.wait(6000);

            break;
        }
    }
}