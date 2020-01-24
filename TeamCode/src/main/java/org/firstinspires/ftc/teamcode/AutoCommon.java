package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

public class AutoCommon extends LinearOpMode {

    protected RobotHardware robot;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        robot = new RobotHardware(hardwareMap, true);
        initialHeading = getHeading();

        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_INIT_POS);
        robot.servoClaw.setPosition(robot.CLAW_INIT_POS);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Running");
        telemetry.update();
    }

    private double inchesToTicks(double inches) {
        return inches * robot.DRIVE_MOTOR_TICKS_PER_ROTATION / (robot.WHEEL_DIAMETER * Math.PI);
    }

    private double initialHeading = 0;
    protected double getHeading() {
        return robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle - initialHeading;
    }

    protected double getHeadingDiff(double targetHeading) {
        double headingDiff = getHeading() - targetHeading;
        while (headingDiff > 180) {
            headingDiff -= 360;
        }
        while (headingDiff < -180) {
            headingDiff += 360;
        }
        return headingDiff;
    }

    protected void drive(double distance, double power) {
        double dir = Math.signum(distance * power);
        if (dir == 0) return;

        double encoderTicks = inchesToTicks(Math.abs(distance));

        robot.resetDriveEncoders();
        robot.startMove(1, 0, 0, Math.abs(power) * dir);
        while (opModeIsActive() && Math.abs(robot.motorFL.getCurrentPosition()) < encoderTicks);
        robot.stopMove();
    }

    protected void driveOnHeading(double distance, double power, double targetHeading) {
        double dir = Math.signum(distance * power);
        if (dir == 0) return;

        double encoderTicks = inchesToTicks(Math.abs(distance));

        robot.resetDriveEncoders();
        robot.startMove(1, 0, 0, Math.abs(power) * dir);
        while (opModeIsActive() && Math.abs(robot.motorFL.getCurrentPosition()) < encoderTicks) {
            double turnMod = getHeadingDiff(targetHeading) / 100;
            robot.startMove(Math.abs(power) * dir, 0, Range.clip(turnMod, -0.2, 0.2), 1);
        }
        robot.stopMove();
    }

    protected void driveOnHeadingFlipped(double distance, double power, double targetHeading) {
        double dir = Math.signum(distance * power);
        if (dir == 0) return;

        double encoderTicks = inchesToTicks(Math.abs(distance));

        robot.resetDriveEncoders();
        robot.startMove(1, 0, 0, Math.abs(power) * dir);
        while (opModeIsActive() && Math.abs(robot.motorFR.getCurrentPosition()) < encoderTicks) {
            double turnMod = getHeadingDiff(targetHeading) / 100;
            robot.startMove(Math.abs(power) * dir, 0, Range.clip(turnMod, -0.2, 0.2), 1);
        }
        robot.stopMove();
    }

    protected void strafeOnHeading(double distance, double power, double targetHeading) {
        double dir = Math.signum(distance * power);
        if (dir == 0) return;

        double encoderTicks = inchesToTicks(Math.abs(distance));

        robot.resetDriveEncoders();
        robot.startMove(0, 1, 0, Math.abs(power) * dir);
        while (opModeIsActive() && Math.abs(robot.motorFL.getCurrentPosition()) < encoderTicks) {
            double turnMod = getHeadingDiff(targetHeading) / 100;
            robot.startMove(0, Math.abs(power) * dir, Range.clip(turnMod, -0.2, 0.2), 1);
        }
        robot.stopMove();
    }

    protected void turnToHeading(double targetHeading, double power) {
        while (opModeIsActive() && Math.abs(getHeadingDiff(targetHeading)) > 5) {
            robot.startMove(0, 0, 1, power * Math.signum(getHeadingDiff(targetHeading)));
        }
        robot.stopMove();
    }

    protected void moveArmDown(double ticksToMove) {
        robot.motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorArm.setPower(robot.ARM_AUTO_DOWN_SPEED);
        while (opModeIsActive() && Math.abs(robot.motorArm.getCurrentPosition()) < Math.abs(ticksToMove)) {
            telemetry.addData("MotorPos", robot.motorArm.getCurrentPosition());
            telemetry.addData("TickLeft", Math.abs(ticksToMove) - Math.abs(robot.motorArm.getCurrentPosition()));
            telemetry.update();
        }
        robot.motorArm.setPower(0);
    }

}
