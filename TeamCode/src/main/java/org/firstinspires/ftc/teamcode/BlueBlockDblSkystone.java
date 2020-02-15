package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;

@Autonomous
public class BlueBlockDblSkystone extends AutoCommon {

    @Override
    public void runOpMode() {
        super.runOpMode();

        // get the skystone pos
        CameraDevice.getInstance().setFlashTorchMode(true);
        sleep(100);
        int skystonePos = getSkystonePos();
        CameraDevice.getInstance().setFlashTorchMode(false);

        // drive away from wall
        if (skystonePos == 1) {
            setUpForSkystone(5);
        } else if (skystonePos == 2) {
            setUpForSkystone(-1);
        } else {
            setUpForSkystone(-9);
        }

        driveOnHeading(18, 0.3, -90);
        driveOnHeading(4, 0.1, -90);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        sleep(500);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS + 0.2);
        //lift arm by inch
        moveArmUp(150);
        driveOnHeading(-3, 0.3, -90);

        turnToHeading(-178);
        // Move forward based on where we picked up the skystone position

        if (skystonePos == 1) {
            driveOnHeadingRamp(91, 0.3, 1.0, 15,-180);
        } else if (skystonePos == 2) {
            driveOnHeadingRamp(83, 0.3, 1.0, 15,-180);
        } else {
            driveOnHeadingRamp(71, 0.3, 1.0, 15,-180);
        }

        robot.setFlaps(false);
        turnToHeading(-90);
        driveOnHeading(2, 0.3, -90);
        //lift arm by inch
        moveArmUp(300);
        driveOnHeading(6, 0.4, -90);
        driveOnHeading(4, 0.2, -90);
        robot.setFlaps(true);
        sleep(500);
        //added program down

        // arc backwards to reposition the platform
        driveOnHeading(-5, 0.3, 260);
        driveOnHeading(-20, 0.3, 250);

        turnToHeading(180, 0.3);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
        //wall push the platform into the
        driveOnHeading(5, 0.5, 180);

        // raise the flaps and wait for them to go up
        robot.setFlaps(false);
        sleep(500);
        // back up
        driveOnHeading(-10, 0.3, 180);
        // put the flaps back down
        robot.setFlaps(true);


        // turn around and move the arm/claw to teleop position
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);

        // drive to the sky-bridge
        turnToHeading(-90);
        driveOnHeading(6, 0.5, -90);
        turnToHeading(0);

        if (skystonePos == 1) {
            driveOnHeadingRamp(90, 0.3, 1.0, 15,0);
            robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
            robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
            turnToHeading(-90);
            strafeOnHeading(-20, 0.3, -90);
        } else if (skystonePos == 2) {
            driveOnHeadingRamp(90, 0.3, 1.0, 15,0);
            robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
            robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
            turnToHeading(-90);
            strafeOnHeading(-8, 0.3, -90);
        } else {
            driveOnHeadingRamp(83, 0.3, 1.0, 15,0);
            robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
            robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
            turnToHeading(-90);
        }
        driveOnHeading(-4, 0.5, -90);
        moveArmDown(450);
        driveOnHeading(5, 0.3, -90);
        driveOnHeading(1, 0.2, -90);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        sleep(400);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS + 0.2);
        driveOnHeading(-6, 0.5, -90);
        turnToHeading(-180);

        driveOnHeadingRamp(70, 0.3, 1.0, 15, -180);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
        driveOnHeading(-20, 0.5, -180);


        sleep(1000);

    }
}