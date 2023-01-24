package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivetrain {
    private DcMotor fL;
    private DcMotor fR;
    private DcMotor bL;
    private DcMotor bR;

    LinearOpMode opMode;

    public Drivetrain(LinearOpMode opMode, HardwareMap hardwareMap) {
        fL = hardwareMap.get(DcMotor.class, "frontLeft");
        fR = hardwareMap.get(DcMotor.class, "frontRight");
        bL = hardwareMap.get(DcMotor.class, "backLeft");
        bR = hardwareMap.get(DcMotor.class, "backRight");
        this.opMode = opMode;

        fL.setDirection(DcMotor.Direction.REVERSE);
        fR.setDirection(DcMotor.Direction.FORWARD);
        bL.setDirection(DcMotor.Direction.REVERSE);
        bR.setDirection(DcMotor.Direction.FORWARD);

        fL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fL.setPower(0);
        fR.setPower(0);
        bL.setPower(0);
        bR.setPower(0);
    }
    public void drive(int forward, double power) {
        fL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fL.setTargetPosition(forward);
        fR.setTargetPosition(forward);
        bL.setTargetPosition(forward);
        bR.setTargetPosition(forward);

        fL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fL.setPower(power);
        fR.setPower(power);
        bL.setPower(power);
        bR.setPower(power);

        while (fL.isBusy() && fR.isBusy()) {
            opMode.telemetry.addData("FR Target", fR.getTargetPosition());
            opMode.telemetry.addData("FR Position", fR.getCurrentPosition());
            opMode.telemetry.addData("FL Target", fL.getTargetPosition());
            opMode.telemetry.addData("FL Position", fL.getCurrentPosition());
            opMode.telemetry.update();
        }

        fL.setPower(0);
        fR.setPower(0);
        bL.setPower(0);
        bR.setPower(0);
    }
    public void strafe(int amount, double power, String direction) {
        fL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if (direction.equals("right")) {
            fL.setTargetPosition(amount);
            fR.setTargetPosition(-amount);
            bL.setTargetPosition(-amount);
            bR.setTargetPosition(amount);
        } else if (direction.equals("left")) {
            fL.setTargetPosition(-amount);
            fR.setTargetPosition(amount);
            bL.setTargetPosition(amount);
            bR.setTargetPosition(-amount);
        }

        fL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fL.setPower(power);
        fR.setPower(power);
        bL.setPower(power);
        bR.setPower(power);

        while (fL.isBusy() && fR.isBusy()) {
            opMode.telemetry.addData("FR Target", fR.getTargetPosition());
            opMode.telemetry.addData("FR Position", fR.getCurrentPosition());
            opMode.telemetry.addData("FL Target", fL.getTargetPosition());
            opMode.telemetry.addData("FL Position", fL.getCurrentPosition());
            opMode.telemetry.update();
        }

        fL.setPower(0);
        fR.setPower(0);
        bL.setPower(0);
        bR.setPower(0);
    }
}
