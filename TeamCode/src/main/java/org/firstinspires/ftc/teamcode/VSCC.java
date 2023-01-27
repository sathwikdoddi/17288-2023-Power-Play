package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class VSCC {
    private DcMotor vS;
    private Servo CS1;
    private Servo CS2;
    OpenCvInternalCamera phoneCam;
    PowerPlayDetection cameraPipeline;

    LinearOpMode opMode;
    HardwareMap hwMap;

    public VSCC(LinearOpMode opMode, HardwareMap hardwareMap) {
        this.opMode = opMode;
        hwMap = hardwareMap;

        vS = hwMap.get(DcMotor.class, "clawMotor");
        CS1 = hwMap.get(Servo.class, "clawServo1");
        CS2 = hwMap.get(Servo.class, "clawServo2");

        vS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void initializeCamera() {
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        cameraPipeline = new PowerPlayDetection();
        phoneCam.setPipeline(cameraPipeline);
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                phoneCam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode) {}
        });
    }
    public void slideUp(int amount, double power) {
        int vSTarget = vS.getCurrentPosition() + amount;
        vS.setTargetPosition(vSTarget);
        vS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        vS.setPower(power);

        while (vS.isBusy()) {
            opMode.telemetry.addData("Viper Slide", vS.getCurrentPosition());
            opMode.telemetry.update();
        }
        vS.setPower(0);
    }
    public void slideTo(int amount, double power) {
        vS.setTargetPosition(amount);
        vS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        vS.setPower(power);

        while (vS.isBusy()) {
            opMode.telemetry.addData("Viper Slide", vS.getCurrentPosition());
            opMode.telemetry.update();
        }
        vS.setPower(0);
    }
    public void openClaw() {
        CS1.setPosition(0);
        CS2.setPosition(0.2);
    }
    public void closeClaw() {
        CS1.setPosition(0.02);
        CS2.setPosition(0);
    }
}
