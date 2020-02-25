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
            setUpForSkystone(3);
        } else if (skystonePos == 2) {
            setUpForSkystone(0);
        } else {
            setUpForSkystone(-6);
        }

        driveOnHeading(18, 0.3, -90);
        driveOnHeading(4, 0.1, -90);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        sleep(500);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS + 0.2);
        //lift arm by inch
        //moveArmUp(150);
        driveOnHeading(-3, 0.3, -90);

        turnToHeading(0);
        // Move forward based on where we picked up the skystone position

        if (skystonePos == 1) {
            driveOnHeadingRamp(74, 0.3, 1.0, 15,-3);
        } else if (skystonePos == 2) {
            driveOnHeadingRamp(79, 0.3, 1.0, 15, -2);
        } else {
            driveOnHeadingRamp(94, 0.3, 1.0, 15,-2);
        }

        robot.setFlaps(false);
        turnToHeading(-90);
        driveOnHeading(10, 0.4, -90);
        //lift arm by inch
        moveArmUp(150);
        driveOnHeading(4, 0.2, -90);
       // driveOnHeading(4, 0.2, -90);
        robot.setFlaps(true);
        sleep(500);
        //added program down

        // arc backwards to reposition the platform
        driveOnHeading(-5, 0.5, 280);
        driveOnHeading(-20, 0.5, 290);

        turnToHeading(0, 0.5);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
        //wall push the platform into the
        driveOnHeading(5, 0.5, 0);

        // raise the flaps and wait for them to go up
        robot.setFlaps(false);
        sleep(600);
        // back up
        driveOnHeading(-10, 0.3, 0);
        // put the flaps back down
       robot.setFlaps(true);


        // turn around and move the arm/claw to teleop position

        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);

        // drive to the sky-bridge
        strafeOnHeading(-13,0.8,0);
        turnToHeading(-90);
        driveOnHeading(20, 0.5, -90);
        turnToHeading(180);
        //moveArmUp(175);

        if (skystonePos == 1) {
           driveOnHeadingRamp(92, 0.3, 1.0, 15,177);
            //driveOnHeadingRampTape(92, 0.3, 1.0, 15,177, 10);
            robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
            robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
            //strafeOnHeading(2, 0.5, -90);
            turnToHeading(-90);

        } else if (skystonePos == 2) {
            driveOnHeadingRamp(88, 0.3, 1.0, 15,178);
            robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
            robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
            turnToHeading(-90);
            strafeOnHeading(9, 0.3, -90);
        } else {
            driveOnHeadingRamp(87, 0.7, 1.0, 15,177);
            robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
            robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
            //TODO: This specific one is strafing and turning. Need to fix it.
            turnToHeading(-90);
            strafeOnHeading(10, 0.4, -90);
            turnToHeading(245);

        }
        moveArmDown(75);
        if (skystonePos == 1) {
            driveOnHeading(8, 0.3, -90);
            driveOnHeading(1, 0.2, -90);
        } else if (skystonePos == 2) {
            driveOnHeading(8, 0.3, -90);
            driveOnHeading(1, 0.2, -90);
        } else {
            driveOnHeading(8, 0.3, 250);
            driveOnHeading(2, 0.2, 250);
        }
           // moveArmDown(75);
       // driveOnHeading(8, 0.3, -90);
       // driveOnHeading(1, 0.2, -90);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        sleep(400);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS + 0.2);
       // driveOnHeadingRampTape(-4,0.5,0.5,-4,-90,500);
        driveOnHeading(-4, 0.5, -90);

        turnToHeading(0);


        if (skystonePos == 1) {
            //driveOnHeadingRamp(90, 0.5, 1.0, 15,-4);
            driveOnHeadingRampTape(93, 0.5, 1.0, 15,-4, 5000);
        } else if (skystonePos == 2) {
            driveOnHeadingRampTape(95,0.5,1.0,15,-4,5000);
           // driveOnHeadingRamp(95, 0.5, 1.0, 15,-4);
        } else {
            driveOnHeadingRampTape(98,1.0,1.0,15,-4,5000);
            strafeOnHeading(2,1.0,0);
            //driveOnHeadingRamp(100, 0.5, 1.0, 15,-4);
        }
        //driveOnHeadingRamp(70, 0.3, 1.0, 15, -180);
       //turnToHeading(160,0.5);
        moveArmUp(175);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
        sleep(100);
        //driveOnHeadingRamp(-20, 0.5, 1.0,-15,0);

    }
}