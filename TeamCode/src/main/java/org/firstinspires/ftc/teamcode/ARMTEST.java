package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class ARMTEST extends AutoCommon {

    @Override
    public void runOpMode() {
        super.runOpMode();
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_STRAIGHT_POS);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
        long endTime = System.currentTimeMillis() + 4500;
        while (System.currentTimeMillis() < endTime) {
            robot.motorArm.setPower(-0.5);
            sleep(500);
        }
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);

    }
}
