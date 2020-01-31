package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;

@Autonomous
public class RedBlockSkystone extends AutoCommon {

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
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
        moveArmDown(robot.ARM_AUTO_TO_TELEOP_ENC_TICKS);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);

        if (skystonePos == 1) {
            driveOnHeading(6, 0.3, 0);
        } else if (skystonePos == 2) {
            driveOnHeading(2, 0.3, 0);
        } else {
            driveOnHeading(-4, 0.3, 0);
        }

        turnToHeading(-90, 0.3);
        driveOnHeading(16, 0.3, -90);
        driveOnHeading(3, 0.1, -90);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS-0.1);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        sleep(500);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS+0.2);
        sleep(500);
        driveOnHeading(-14, 0.3, -90);

        //Need to take right turn and move forward
        // Need to use the relative position to drive towards trey

        turnToHeading(135, 0.3);
        driveOnHeading(30, 0.3, 90);


    }


}
