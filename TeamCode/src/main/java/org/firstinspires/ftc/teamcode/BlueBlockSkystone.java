package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;

@Autonomous
public class BlueBlockSkystone extends AutoCommon {

    @Override
    public void runOpMode() {
        super.runOpMode();

        // get the skystone pos
        CameraDevice.getInstance().setFlashTorchMode(true);
        sleep(100);
        int skystonePos = getSkystonePos();
        CameraDevice.getInstance().setFlashTorchMode(false);

        // drive away from wall
        strafeOnHeading(8, 0.3, 0);

        if (skystonePos == 1) {
            driveOnHeading(8, 0.3, 0);
        } else if (skystonePos == 2) {
            driveOnHeading(2, 0.3, 0);
        } else {
            driveOnHeading(-5, 0.3, 0);
        }

        turnToHeading(-90, 0.3);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
        moveArmDown(robot.ARM_AUTO_TO_SKYSTONE_ENC_TICKS);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);

        driveOnHeading(18, 0.3, -90);
        driveOnHeading(4, 0.1, -90);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        sleep(500);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS + 0.2);
        //lift arm by inch
        moveArmUp(150);
        driveOnHeading(-2, 0.3, -90);

        turnToHeading(0, 0.3);
        // Move forward based on where we picked up the skystone position

        if (skystonePos == 1) {
            driveOnHeadingRamp(73, 0.4, 1.0, 10,0);
        } else if (skystonePos == 2) {
            driveOnHeadingRamp(82, 0.4, 1.0, 10,0);
        } else {
            driveOnHeadingRamp(91, 0.4, 1.0, 10,0);
        }

        robot.setFlaps(false);
        turnToHeading(-90, 0.3);
        driveOnHeading(2, 0.3, -90);
        sleep(200);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
        //lift arm by inch
        moveArmUp(300);
        driveOnHeading(6, 0.4, -90);
        driveOnHeading(4, 0.2, -90);
        robot.setFlaps(true);
        sleep(500);
        //added program down

        // arc backwards to reposition the platform
        driveOnHeadingFlipped(-5, 0.3, 280);

        driveOnHeadingFlipped(-20, 0.3, 290);

        turnToHeading(0, 0.3);
        //wall push the platform into the
        driveOnHeading(5, 0.5, 0);

        // raise the flaps and wait for them to go up
        robot.setFlaps(false);
        sleep(500);
        // back up
        driveOnHeading(-10, 0.3, 0);
        // put the flaps back down
        robot.setFlaps(true);


        // turn around and move the arm/claw to teleop position
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_STRAIGHT_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);

        // drive to the sky-bridge
        turnToHeading(-90, 0.5);
        driveOnHeading(10, 0.5, -90);
        turnToHeading(180, 0.5);
        driveOnHeading(32, 0.3, 180);
        sleep(1000);














        /*
        // get the skystone pos
        CameraDevice.getInstance().setFlashTorchMode(true);
        sleep(100);
        int skystonePos = getSkystonePos();
        CameraDevice.getInstance().setFlashTorchMode(false);

        // drive away from wall
        strafeOnHeading(8, 0.3, 0);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
        moveArmDown(robot.ARM_AUTO_TO_TELEOP_ENC_TICKS);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);

        if (skystonePos == 1) {
            driveOnHeading(4.5, 0.3, 0);
        } else if (skystonePos == 2) {
            driveOnHeading(-1, 0.3, 0);
        } else {
            driveOnHeading(-8, 0.3, 0);
        }

        turnToHeading(-90, 0.3);
        driveOnHeading(18, 0.3, -90);
        driveOnHeading(4, 0.1, -90);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS - 0.1);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        sleep(500);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS + 0.2);
        sleep(500);
        driveOnHeading(-3, 0.3, -90);

        //Need to take right turn and move forward
        // Need to use the relative position to drive towards trey

        turnToHeading(0, 0.3);
        // the following ststement needs t adjusted based on the skystone position

        if (skystonePos == 1) {
            driveOnHeading(73, 0.6, 0);
        } else if (skystonePos == 2) {
            driveOnHeading(83, 0.6, 0);
        } else {
            driveOnHeading(88, 0.6, 0);
        }

        driveOnHeading(3,0.3,0);
        robot.setFlaps(false);
        turnToHeading(-90, 0.5);
       //driveOnHeading(4, 0.5, -90);
        driveOnHeading(15, 0.3, -90);
        sleep(500);
        robot.setFlaps(true);
        sleep(500);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);

        // arc backwards to reposition the platform

        driveOnHeadingFlipped(-15, 0.4, 300);

        turnToHeading(-5, 0.4);


        //wall push the platform into the
       driveOnHeading(8.5,0.4,0);
        // raise the flaps and wait for them to go up
        robot.setFlaps(false);
        sleep(500);
        // back up
         driveOnHeading(-20, 0.7, 180);
        // put the flaps back down
        robot.setFlaps(true);


        // turn around and move the arm/claw to teleop position
        turnToHeading(180, 0.3);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_STRAIGHT_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);

        //lift arm by inch
        moveArmUp(300);

        // drive to the sky-bridge
       strafeOnHeading(-1, 0.8, 180);
        driveOnHeading(28, 0.8, 180);
        sleep(1000);
        */

    }
}