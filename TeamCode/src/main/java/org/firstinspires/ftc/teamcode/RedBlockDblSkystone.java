package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;

@Autonomous
public class RedBlockDblSkystone extends AutoCommon {

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
            setUpForSkystone(2);
        } else if (skystonePos == 2) {
            setUpForSkystone(0);
        } else {
            setUpForSkystone(-9);
        }

        driveOnHeading(4, 0.3, -90);
        driveOnHeading(4, 0.15, -90);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        sleep(500);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS + 0.2);
        //lift arm by inch
        //moveArmUp(150);
        driveOnHeading(-3, 0.3, -90);

        turnToHeading(-178);
        // Move forward based on where we picked up the skystone position

        if (skystonePos == 1) {
            driveOnHeadingRamp(91, 0.3, 1.0, 15,-180);
        } else if (skystonePos == 2) {
            driveOnHeadingRamp(83, 0.3, 1.0, 15,-180);
        } else {
            driveOnHeadingRamp(74, 0.3, 1.0, 15,-180);
        }

        robot.setFlaps(false);
        turnToHeading(-90);
        driveOnHeading(7, 0.4, -90);
        //lift arm by inch
        moveArmUp(150);
        driveOnHeading(2, 0.3, -90);
       // driveOnHeading(4, 0.2, -90);
        robot.setFlaps(true);
        sleep(500);
        //added program down

        // arc backwards to reposition the platform
        driveOnHeading(-5, 0.5, 260);
        driveOnHeading(-20, 0.5, 250);

        turnToHeading(180, 0.5);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
        //wall push the platform into the
        driveOnHeading(5, 0.5, 180);

        // raise the flaps and wait for them to go up
        robot.setFlaps(false);
        sleep(600);
        // back up
        driveOnHeading(-10, 0.3, 180);
        // put the flaps back down
        robot.setFlaps(true);


        // turn around and move the arm/claw to teleop position

        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);

        // drive to the sky-bridge
        strafeOnHeading(13,0.8,180);
        //we stafe here because it lines up against the wall so we driveonheading 20 inches 100% of the time
        //because in each of the postitio where it lines up on the tray isnt 100% this alows for a very persise alignment to 100% we go 20 inches forward
        //they will convince you to get rid of this but consider why it's here
        turnToHeading(-90);
        driveOnHeading(20, 0.5, -90);
        turnToHeading(0);
        //moveArmUp(175);

        if (skystonePos == 1) {
            driveOnHeadingRamp(86, 0.3, 1.0, 15,2);
            robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
            robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
            turnToHeading(-90);
            strafeOnHeading(-13, 0.5, -90);
        } else if (skystonePos == 2) {
            driveOnHeadingRamp(84, 0.3, 1.0, 15,3);
            robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
            robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
            turnToHeading(-90);
            strafeOnHeading(-8, 0.3, -90);
        } else {
            driveOnHeadingRamp(83, 0.3, 1.0, 15,3);
            robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
            robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
            turnToHeading(-90);
        }


       // driveOnHeading(-2, 0.3, -90);
        moveArmDown(75);
        driveOnHeading(8, 0.3, skystonePos == 1 ? -80 : -90);
        driveOnHeading(1, 0.2, skystonePos == 1 ? -80 : -90);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        sleep(400);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS + 0.2);
        driveOnHeading(-4, 0.5, -90);
        //tapeExtension(600);
        turnToHeading(-180);


        if (skystonePos == 1) {
           // driveOnHeadingRamp(100, 0.5, 1.0, 15,180);
            driveOnHeadingRampTape(100,0.5,1.0,15,177,5000);
        } else if (skystonePos == 2) {
          //  driveOnHeadingRamp(98, 0.5, 1.0, 15,180);
            driveOnHeadingRampTape(100,0.5,1.0,15,180,5000);
        }
        else {
           // driveOnHeadingRamp(90, 0.5, 1.0, 15,180);
        driveOnHeadingRampTape(95,0.5,1.0,15,180,5000);
        }

        //moveArmUp(175);

        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
        sleep(100);
       // driveOnHeadingRamp(-20, 0.5, 1.0,-15,190);



    }
}