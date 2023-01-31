package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="1+1 Left Side")
public class LeftSideAuto extends LinearOpMode {

    @Override
    public void runOpMode() {
        Drivetrain dt = new Drivetrain(this, hardwareMap);
        VSCC vscc = new VSCC(this, hardwareMap);

        ElapsedTime runtime = new ElapsedTime();
        runtime.startTime();

        vscc.initializeCamera();
        waitForStart();

        while (opModeIsActive()) {
            vscc.closeClaw();
            int position = vscc.getPosition();
            dt.wait(500);
            position = vscc.getPosition();

            vscc.slideTo(500,0.6);
            dt.wait(300);
            dt.piddrive(1550);
            dt.strafe(360, 0.3, "right");
            vscc.slideTo(3900, 0.5);
            dt.piddrive(80);
            vscc.openClaw();
            dt.wait(200);
            dt.piddrive(-70);
            dt.strafe(450, 0.4, "left");
            vscc.slideTo(700, 0.3);
            dt.turn(90, 0.3,"left");
            vscc.slideTo(650,0.6);
            dt.piddrive(800);
            vscc.closeClaw();
            dt.wait(300);
            vscc.slideTo(1500,0.5);
            dt.piddrive(-740);
            dt.turn(90, 0.3,"right");
            dt.piddrive(-80);
            dt.strafe(470,0.4,"right");
            vscc.slideTo(3900,0.5);
            dt.piddrive(80);
            vscc.openClaw();
            dt.wait(300);
            dt.piddrive(-100);
            dt.strafe(470,0.4,"left");
            vscc.slideTo(0,0.5);

            if (position == 3) {
                dt.strafe(800,0.4,"right");
            } else if (position == 1) {
                dt.strafe(800,0.4,"left");
            }
            break;
        }
    }
}