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
    private Servo clawServo1;
    private Servo clawServo2;
    private DcMotor viperSlide;

    @Override
    public void runOpMode() {
        Drivetrain dt = new Drivetrain(this, hardwareMap);
        clawServo1 = hardwareMap.get(Servo.class, "clawServo1");
        clawServo2 = hardwareMap.get(Servo.class, "clawServo2");

        viperSlide = hardwareMap.get(DcMotor.class, "clawMotor");
        viperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        cameraPipeline = new PowerPlayDetection();
        phoneCam.setPipeline(cameraPipeline);

        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened() {

                phoneCam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);

            }
            @Override
            public void onError(int errorCode) {}
        });

        telemetry.addData("Average Cb", cameraPipeline.getAvg());
        telemetry.addData("Position", cameraPipeline.getPosition());
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Average Cb", cameraPipeline.getAvg());
            telemetry.addData("Position", cameraPipeline.getPosition());
            telemetry.addData("Claw", viperSlide.getCurrentPosition());
            telemetry.update();

            int position = cameraPipeline.getPosition();
            sleep(1000);
            position = cameraPipeline.getPosition();

            clawServo1.setPosition(0);
            clawServo2.setPosition(0.2);

            dt.drive(1650, 0.3);
            dt.strafe(425, 0.3, "left");
            moveClaw(3870, 0.3);
            dt.drive(80, 0.3);
            clawServo1.setPosition(0.02);
            clawServo2.setPosition(0);

            sleep(350);
            dt.drive(-100,0.3);

            if(position==1)
            {
                dt.strafe(500,0.3,"left");
            }
            else if(position==2)
            {
                dt.strafe(450,0.3,"right");
            }
            else
            {
                dt.strafe(1459,0.3,"right");
            }


            break;

//            if (position == 1) {
//                dt.strafe(850, 0.3, "left");
//            } else if (position == 3) {
//                dt.strafe(850, 0.3, "right");
//            }
        }
    }

    public void moveClaw(int amount, double power) {
        viperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperSlide.setTargetPosition(amount);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlide.setPower(power);
        while (viperSlide.isBusy()) {
            telemetry.addData("Viper Slide", viperSlide.getCurrentPosition());
            telemetry.update();
        }
        viperSlide.setPower(0);
    }
}
