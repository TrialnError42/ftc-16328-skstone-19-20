package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.os.Environment;

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
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.internal.vuforia.VuforiaLocalizerImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        telemetry.addData("Status", "Waiting for Vuforia...");
        telemetry.update();
        initVuforia();

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
        while (opModeIsActive() && Math.abs(robot.motorFL.getCurrentPosition()) < encoderTicks) ;
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

    protected void driveOnHeadingRamp(double driveDistance, double minPower, double maxPower, double rampDistance, double targetHeading) {
        double dir = Math.signum(driveDistance);
        if (dir == 0) return;

        double encoderTicks = inchesToTicks(Math.abs(driveDistance));
        double rampTicks = inchesToTicks(Math.abs(rampDistance));

        robot.resetDriveEncoders();
        robot.startMove(1, 0, 0, Math.abs(minPower) * dir);
        while (opModeIsActive() && Math.abs(robot.motorFL.getCurrentPosition()) < encoderTicks) {
            double turnMod = getHeadingDiff(targetHeading) / 100;
            double startRampPower = minPower + (maxPower - minPower) * (Math.abs(robot.motorFL.getCurrentPosition()) / rampTicks);
            double endRampPower = minPower + (maxPower - minPower) * (Math.abs(encoderTicks - robot.motorFL.getCurrentPosition()) / (rampTicks*2));
            double power = Range.clip(Math.min(startRampPower, endRampPower), minPower, maxPower);
            robot.startMove(Math.abs(power) * dir, 0, Range.clip(turnMod, -0.2, 0.2), 1);
        }
        robot.stopMove();
    }

    protected void driveOnHeadingRampTape(double driveDistance, double minPower, double maxPower, double rampDistance, double targetHeading, double maxTapeTicks) {

        double dir = Math.signum(driveDistance);
        if (dir == 0) return;

        boolean isTapeExtended = false;
        boolean isDoneDriving = false;

        double encoderTicks = inchesToTicks(Math.abs(driveDistance));
        double rampTicks = inchesToTicks(Math.abs(rampDistance));

        robot.resetDriveEncoders();
        robot.startMove(1, 0, 0, Math.abs(minPower) * dir);

        while (opModeIsActive() && ((Math.abs(robot.motorFL.getCurrentPosition()) < encoderTicks) || Math.abs(robot.motorTape.getCurrentPosition()) < maxTapeTicks)) {

            double turnMod = getHeadingDiff(targetHeading) / 100;
            double startRampPower = minPower + (maxPower - minPower) * (Math.abs(robot.motorFL.getCurrentPosition()) / rampTicks);
            double endRampPower = minPower + (maxPower - minPower) * (Math.abs(encoderTicks - robot.motorFL.getCurrentPosition()) / (rampTicks*2));
            double power = Range.clip(Math.min(startRampPower, endRampPower), minPower, maxPower);

            if (!isDoneDriving) {
                robot.startMove(Math.abs(power) * dir, 0, Range.clip(turnMod, -0.2, 0.2), 1);
            }
            else
                robot.stopMove();

            if (isTapeExtended) {
                robot.motorTape.setPower(0.0);
            }
            else {
                robot.motorTape.setPower(1.0);
            }

            if (Math.abs(robot.motorFL.getCurrentPosition()) >= encoderTicks)
                isDoneDriving = true;

            if (Math.abs(robot.motorTape.getCurrentPosition()) >= maxTapeTicks)
                isTapeExtended = true;
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
        while (opModeIsActive() && Math.abs(getHeadingDiff(targetHeading)) > 6) {
            robot.startMove(0, 0, 1, power * Math.signum(getHeadingDiff(targetHeading)));
        }
        robot.stopMove();
    }

    protected void turnToHeading(double targetHeading) {
        while (opModeIsActive() && Math.abs(getHeadingDiff(targetHeading)) > 6) {
            double turnMod = getHeadingDiff(targetHeading) / 100;
            robot.startMove(0, 0, Range.clip(turnMod, -1.0, 1.0));
        }
        robot.stopMove();
    }

    protected void moveArmDown(double ticksToMove) {
        robot.motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while (opModeIsActive() && Math.abs(robot.motorArm.getCurrentPosition()) < Math.abs(ticksToMove)) {
            if (Math.abs(robot.motorArm.getCurrentPosition()) < Math.abs(ticksToMove - robot.ARM_AUTO_DOWN_SLOW_TICKS)) {
                robot.motorArm.setPower(robot.ARM_AUTO_DOWN_SPEED_FAST);
            } else {
                robot.motorArm.setPower(robot.ARM_AUTO_DOWN_SPEED_SLOW);
            }
            telemetry.addData("MotorPos", robot.motorArm.getCurrentPosition());
            telemetry.addData("TickLeft", Math.abs(ticksToMove) - Math.abs(robot.motorArm.getCurrentPosition()));
            telemetry.update();
        }
        robot.motorArm.setPower(0);
    }

    protected void moveArmUp(double ticksToMove) {
        robot.motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorArm.setPower(robot.ARM_AUTO_UP_SPEED);
        while (opModeIsActive() && Math.abs(robot.motorArm.getCurrentPosition()) < Math.abs(ticksToMove)) {
            telemetry.addData("MotorPos", robot.motorArm.getCurrentPosition());
            telemetry.addData("TickLeft", Math.abs(ticksToMove) - Math.abs(robot.motorArm.getCurrentPosition()));
            telemetry.update();
        }
        robot.motorArm.setPower(0);
    }



   //this is new for arm extension
    protected void tapeExtension(double ticksTomove) {
        robot.motorTape.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorTape.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorTape.setPower(robot.DTAPE_EXTEND);
        while  (opModeIsActive() && Math.abs(robot.motorTape.getCurrentPosition()) < Math.abs(ticksTomove)) {
            telemetry.addData("MotorPos", robot.motorTape.getCurrentPosition());
            telemetry.addData("TickLeft", Math.abs(ticksTomove) - Math.abs(robot.motorTape.getCurrentPosition()));
            telemetry.update();
        }
        robot.motorTape.setPower(0);

    }

    private static final String VUFORIA_KEY = "AWAydHD/////AAABmXGZ9mQxg09AvxhSoY5XwiUiKg1MPonVQDDS"
            + "nPNo+YPMZ8VgPFUW0TcIMXrdaUXiSIyJCwCD7AtpPBT3x0GMgxihOuroB4VTSN/eV8W8w9QmYnX2lo0VNuVFs0sQ"
            + "8Loq4jDIf2fPN0UdBHoQegRmV16sdDkYPE9tClFPxAL7oN8h82ETCyP40SZPORsbGZHRCMF5keXzhL6zoNBzD3MT"
            + "XNCTgIyoPy83Oz0RvplOH9IYrYzXemfsCv667hDX3fFkcly4W2oNfMtwf2Z1vuX1S89Mkgu0R+KEVt25PAHtLTf+"
            + "Ri2RMAEtUxW49I8Ic75dNWRno7LC1+NrXDJ5Iis693C/fUcNAC4S5uYRCmNTskz5";

    private VuforiaLocalizerImplSubclass vuforia;

    private void initVuforia() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = new VuforiaLocalizerImplSubclass(parameters);
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            System.out.println("VUFORIA: Image saved to: " + pictureFile);
        } catch (FileNotFoundException e) {
            System.out.println("VUFORIA: File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("VUFORIA: Error accessing file: " + e.getMessage());
        }
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data"
                + "/org.firstinspires.ftc.trialnerror"
                + "/Files");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdir()) {
                System.out.println("VUFORIA: unable to save the file to: " + mediaStorageDir.toString());
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("ddMMyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".png";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private boolean waitForBitmap() {
        vuforia.copyNextToBitmap = true;
        int cntr = 0;
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.reset();
        while (vuforia.copyNextToBitmap) {
            if (elapsedTime.milliseconds() > robot.AUTO_SKYSTONE_BITMAP_WAITING_TIMOUT_MS) {
                return false;
            }
            cntr++;
        }
        return true;
        // Put a timeout making sure that it is not spinning all 30 seconds.  15 seconds max.
    }

    protected int getSkystoneRegionTotal(int searchPosY) {
        int value = 0;
        for (int x = robot.AUTO_SKYSTONE_SEARCH_X; x < robot.AUTO_SKYSTONE_SEARCH_X + robot.AUTO_SKYSTONE_SEARCH_W; x++) {
            for (int y = searchPosY; y < searchPosY+ robot.AUTO_SKYSTONE_SEARCH_H; y++) {
                value += ((vuforia.bitmap.getPixel(x, y) >> 16) & 0xFF);
            }
        }
        return value;
    }

    protected int getSkystonePos() {
        if (!waitForBitmap()) {
            System.out.println("VUFORIA: did not get image from vuforia, timed out");
            return robot.AUTO_SKYSTONE_NO_BITMAP_DEFAULT_POS;
        }

        int pos1RedTotal = getSkystoneRegionTotal(robot.AUTO_SKYSTONE_SEARCH_POS_1_Y);
        int pos2RedTotal = getSkystoneRegionTotal(robot.AUTO_SKYSTONE_SEARCH_POS_2_Y);
        int pos3RedTotal = getSkystoneRegionTotal(robot.AUTO_SKYSTONE_SEARCH_POS_3_Y);

        telemetry.addData("Pos 1", pos1RedTotal);
        telemetry.addData("Pos 2", pos2RedTotal);
        telemetry.addData("Pos 3", pos3RedTotal);
        int pos = (pos1RedTotal < pos2RedTotal && pos1RedTotal < pos3RedTotal) ? 1 : (pos2RedTotal < pos3RedTotal) ? 2 : 3;
        telemetry.addData("Which?", pos);
        telemetry.update();

//        System.out.println("DEBUGGING -> Attempting to save file...");
//        for (int x = 0; x < 2; x++) {
//            for (int y = 0; y < 2; y++) {
//                vuforia.bitmap.setPixel(robot.AUTO_SKYSTONE_SEARCH_X + x*robot.AUTO_SKYSTONE_SEARCH_W, robot.AUTO_SKYSTONE_SEARCH_POS_1_Y + x*robot.AUTO_SKYSTONE_SEARCH_H, 0);
//                vuforia.bitmap.setPixel(robot.AUTO_SKYSTONE_SEARCH_X + x*robot.AUTO_SKYSTONE_SEARCH_W, robot.AUTO_SKYSTONE_SEARCH_POS_2_Y + x*robot.AUTO_SKYSTONE_SEARCH_H, 0);
//                vuforia.bitmap.setPixel(robot.AUTO_SKYSTONE_SEARCH_X + x*robot.AUTO_SKYSTONE_SEARCH_W, robot.AUTO_SKYSTONE_SEARCH_POS_3_Y + x*robot.AUTO_SKYSTONE_SEARCH_H, 0);
//            }
//        }
//        storeImage(vuforia.bitmap);
//        System.out.println("DEBUGGING -> File saved");

        return pos;
    }


//    strafeOnHeading(8, 0.3, 0);
//        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
//    moveArmDown();
//        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
//        if (skystonePos == 1) {
//        driveOnHeading(5, 0.3, 0);
//    } else if (skystonePos == 2) {
//        driveOnHeading(-1, 0.3, 0);
//    } else {
//        driveOnHeading(-9, 0.3, 0);
//    }
//    turnToHeading(-90, 0.3);

    private enum SkystoneState { Strafe, Drive, Turn }
    private static final double SKYSTONE_SETUP_DRIVE_POWER = 0.3;
    private static final double SKYSTONE_SETUP_STRAFE_POWER = 0.3;
    private final double SKYSTONE_SETUP_STRAFE_TICKS = inchesToTicks(8);

    protected void setUpForSkystone(double driveDist) {
        SkystoneState state = SkystoneState.Strafe;
        boolean isArmDown = false;
        boolean isDoneDriving = false;
        double driveTicks = inchesToTicks(Math.abs(driveDist));
        double drivePower = SKYSTONE_SETUP_DRIVE_POWER * Math.signum(driveDist);
        double strafePower = SKYSTONE_SETUP_STRAFE_POWER;

        robot.motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.resetDriveEncoders();
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);


        while (!isArmDown || !isDoneDriving) {
            // arm moving down code
            if (Math.abs(robot.motorArm.getCurrentPosition()) >= Math.abs(robot.ARM_AUTO_TO_SKYSTONE_ENC_TICKS)) {
                isArmDown = true;
            }
            if (isArmDown) {
                robot.motorArm.setPower(0);
            } else {
                if (Math.abs(robot.motorArm.getCurrentPosition()) < Math.abs(robot.ARM_AUTO_TO_SKYSTONE_ENC_TICKS - robot.ARM_AUTO_DOWN_SLOW_TICKS)) {
                    robot.motorArm.setPower(robot.ARM_AUTO_DOWN_SPEED_FAST);
                } else {
                    robot.motorArm.setPower(robot.ARM_AUTO_DOWN_SPEED_SLOW);
                }
            }

            // driving code
//            robot.startMove(1, 0, 0, Math.abs(power) * dir);

            if (isDoneDriving) {
                robot.stopMove();
            } else {
                // check state transitions
                if (state == SkystoneState.Strafe && Math.abs(robot.motorFL.getCurrentPosition()) >= SKYSTONE_SETUP_STRAFE_TICKS) {
                    robot.stopMove();
                    robot.resetDriveEncoders();
                    state = SkystoneState.Drive;
                } else if (state == SkystoneState.Drive && Math.abs(robot.motorFL.getCurrentPosition()) >= driveTicks) {
                    state = SkystoneState.Turn;
                } else if (state == SkystoneState.Turn && Math.abs(getHeadingDiff(-90)) < 3) {
                    isDoneDriving = true;
                    robot.stopMove();
                }
                // apply powers
                double turnMod = getHeadingDiff(state == SkystoneState.Turn ? -90 : 0) / 100;
                double drive = 0;
                double strafe = 0;
                if (state == SkystoneState.Turn) {
                    turnMod = Range.clip(turnMod, -1.0, 1.0);
                } else {
                    turnMod = Range.clip(turnMod, -0.2, 0.2);
                }
                if (state == SkystoneState.Strafe) {
                    strafe = strafePower;
                }
                if (state == SkystoneState.Drive) {
                    drive = drivePower;
                }
                robot.startMove(drive, strafe, turnMod, 1);
            }
        }
        robot.motorArm.setPower(0);
        robot.stopMove();
    }
}
