package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class RedPickBlockPark extends AutoCommon {

    @Override
    public void runOpMode() {
        super.runOpMode();

        driveOnHeading(8, 0.3, 0);
        // Arm position and pick up the block
        robot.motorArm.setPower(0.6);
        sleep(300);
        robot.motorArm.setPower(0);
        //private boolean isClawOpen = false;
        //robot.servoClaw.setPosition(isClawOpen ? robot.CLAW_OPEN_POS : robot.CLAW_CLOSE_POS);

        // move arm to be in the position
        //driveOnHeading(-3, 0.3, 0);

      //  turnToHeading(90, 0.3);
        //driveOnHeading(30, 0.3, 0);
        // drop the block

        //come back and park
        //driveOnHeading(-3, 0.3, 0);
        //robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS);
        //driveOnHeading(-22, 0.3, 90);
        //strafeOnHeading(-2, 0.3, 90);

    }


}
