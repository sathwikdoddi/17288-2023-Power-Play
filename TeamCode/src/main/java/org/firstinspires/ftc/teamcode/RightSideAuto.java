package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name="RSAuto")
public class RightSideAuto extends LinearOpMode {
    PowerPlayDetection cameraPipeline;
    OpenCvInternalCamera phoneCam;

    @Override
    public void runOpMode() {
        Drivetrain dt = new Drivetrain(this, hardwareMap);
        VSCC vscc = new VSCC(this, hardwareMap);

        vscc.initializeCamera();
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Average Cb", cameraPipeline.getAvg());
            telemetry.addData("Position", cameraPipeline.getPosition());
            telemetry.update();

            int position = cameraPipeline.getPosition();
            sleep(1000);
            position = cameraPipeline.getPosition();

            vscc.closeClaw();

            dt.drive(1650, 0.3);
            dt.strafe(425, 0.3, "left");
            vscc.slideUp(3870, 0.3);
            dt.drive(80, 0.3);
            vscc.openClaw();
            sleep(100);
            vscc.slideTo(0, 0.3);
            sleep(350);
            dt.drive(-100,0.3);

            if (position==1) {
                dt.strafe(500,0.3,"left");
            } else if (position==2) {
                dt.strafe(450,0.3,"right");
            } else {
                dt.strafe(1459,0.3,"right");
            }
            break;
        }
    }
}