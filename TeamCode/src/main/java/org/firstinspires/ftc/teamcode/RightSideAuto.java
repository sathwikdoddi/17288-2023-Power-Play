package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class RightSideAuto extends LinearOpMode {
    PowerPlayDetection cameraPipeline;
    Drivetrain dt = new Drivetrain(this, hardwareMap);
    OpenCvInternalCamera phoneCam;

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        cameraPipeline = new PowerPlayDetection();
        phoneCam.setPipeline(cameraPipeline);

        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened() {
                phoneCam.startStreaming(320,240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }
            @Override
            public void onError(int errorCode) {}
        });

        while (!opModeIsActive()) {
            telemetry.addData("Average Cb", cameraPipeline.getAvg());
            telemetry.addData("Position", cameraPipeline.getPosition());
            telemetry.update();

            waitForStart();
        }

        while (opModeIsActive()) {
            int position = cameraPipeline.getPosition();
            sleep(1000);
            position = cameraPipeline.getPosition();

            dt.drive(800, 0.3);
            if (position == 1) {
                dt.strafe(1000, 0.3, "left");
            } else if (position == 3) {
                dt.strafe(1000, 0.3, "right");
            }
        }
    }
}
