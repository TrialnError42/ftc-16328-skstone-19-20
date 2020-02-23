package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


public class RobotHardware {

    public BNO055IMU imu;

    public DcMotor motorFL;
    public DcMotor motorFR;
    public DcMotor motorBL;
    public DcMotor motorBR;

    public DcMotor motorArm;

   public DcMotor motorTape;

    public Servo servoRClamp;
    public Servo servoLClamp;
    public Servo servoClaw;
    public Servo servoClawPivot;

    public static final double LEFT_FLAP_UP_POS     = 0.18;
    public static final double LEFT_FLAP_DOWN_POS   = 0.41;
    public static final double RIGHT_FLAP_UP_POS    = 1.00;
    public static final double RIGHT_FLAP_DOWN_POS  = 0.77;

    public static final double CLAW_INIT_POS = 1.0;
    public static final double CLAW_OPEN_POS = 0.50;
    public static final double CLAW_CLOSE_POS = 0.90;

    public static final double CLAW_PIVOT_INIT_POS = 0.13;
    public static final double CLAW_PIVOT_SKYBRIDGE_POS = 0.70;
    public static final double CLAW_PIVOT_SKYSTONE_APPROACH_POS = 0.66; // was .69
    public static final double CLAW_PIVOT_SKYSTONE_GRAB_POS = 0.62; // was .65
    public static final double CLAW_PIVOT_STRAIGHT_POS = 0.52;
    public static final double CLAW_PIVOT_MOTOR_MOD = -6800;
    public static final double CLAW_PIVOT_MAX_POS = 1.0;
    public static final double CLAW_PIVOT_MIN_POS = 0.2;

    public static final double ARM_POWER_SLOW  = 0.2;
    public static final double ARM_POWER_FAST  = 0.5;

    public static final double ARM_AUTO_DOWN_SPEED_SLOW = -0.2;
    public static final double ARM_AUTO_DOWN_SPEED_FAST = -1.0;
    public static final double ARM_AUTO_DOWN_SLOW_TICKS = 500;
    public static final double ARM_AUTO_UP_SPEED = 0.5;
    public static final double ARM_AUTO_TO_TELEOP_ENC_TICKS = 5350;
    public static final double ARM_AUTO_TO_SKYSTONE_ENC_TICKS = 5600;// it was 5500
    public static final double ARM_AUTO_UP_POS_ENC_TICKS = 1000;
    public static final double ARMEXTENSION_AUTO_UP_SPEED = 2000;
    public static final double ARMEXTENSION_AUTO_DOWN_SPEED = 2000;
    public static final double DTAPE_EXTEND = 5000;


    public static final double WHEEL_DIAMETER = 4.0;
    public static final double DRIVE_MOTOR_TICKS_PER_ROTATION = 537.6;

    public static final double AUTO_SKYSTONE_BITMAP_WAITING_TIMOUT_MS = 5000;
    public static final int AUTO_SKYSTONE_NO_BITMAP_DEFAULT_POS = 2;

    public static final int AUTO_SKYSTONE_SEARCH_X = 924;
    public static final int AUTO_SKYSTONE_SEARCH_W = 38;
    public static final int AUTO_SKYSTONE_SEARCH_POS_1_Y = 566;
    public static final int AUTO_SKYSTONE_SEARCH_POS_2_Y = 300;
    public static final int AUTO_SKYSTONE_SEARCH_POS_3_Y = 38;
    public static final int AUTO_SKYSTONE_SEARCH_H = 76;

    private HardwareMap hardwareMap = null;

    public RobotHardware(HardwareMap aHardwareMap, boolean initIMU) {
        hardwareMap = aHardwareMap;

        if (initIMU) {
            initializeIMU();
        }

        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");
        motorFL.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBL.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorTape = hardwareMap.get(DcMotor.class, "motorTape");

        motorArm = hardwareMap.get(DcMotor.class, "motorArm");
        motorArm.setDirection(DcMotorSimple.Direction.REVERSE);
        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        servoRClamp = hardwareMap.get(Servo.class, "servoRClamp");
        servoLClamp = hardwareMap.get(Servo.class, "servoLClamp");
        servoClaw = hardwareMap.get(Servo.class, "servoClaw");
        servoClawPivot = hardwareMap.get(Servo.class, "servoClawPivot");

        servoLClamp.setPosition(LEFT_FLAP_DOWN_POS);
        servoRClamp.setPosition(RIGHT_FLAP_DOWN_POS);
//        servoClawPivot.setPosition();
    }

    public void initializeIMU() {
        //------------------------------------------------------------
        // IMU - BNO055
        // Set up the parameters with which we will use our IMU.
        // + 9 degrees of freedom
        // + use of calibration file (see calibration program)
        //------------------------------------------------------------

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitImuCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = false;
        //parameters.loggingTag          = "IMU";
        //parameters.mode                = BNO055IMU.SensorMode.NDOF;

        parameters.accelerationIntegrationAlgorithm = null;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    public void startMove(double drive, double strafe, double turn, double scale) {
        double powerFL = (drive + strafe + turn) * scale;
        double powerFR = (drive - strafe - turn) * scale;
        double powerBL = (drive - strafe + turn) * scale;
        double powerBR = (drive + strafe - turn) * scale;

        double maxPower = Math.max(Math.max(Math.abs(powerFL), Math.abs(powerFR)), Math.max(Math.abs(powerBL), Math.abs(powerBR))); // ?? Why are we using MAX function here.
        double max = (maxPower < 1) ? 1 : maxPower;

        motorFL.setPower(Range.clip(powerFL / max, -1, 1));  // What does this clipping doing?
        motorFR.setPower(Range.clip(powerFR / max, -1, 1));
        motorBL.setPower(Range.clip(powerBL / max, -1, 1));
        motorBR.setPower(Range.clip(powerBR / max, -1, 1));
    }

    public void startMove(double drive, double strafe, double turn) {
        startMove(drive, strafe, turn, 1);
    }

    public void stopMove() {
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

    public void setFlaps(boolean isDown) {
        if (isDown) {
            servoRClamp.setPosition(RIGHT_FLAP_DOWN_POS);
            servoLClamp.setPosition(LEFT_FLAP_DOWN_POS);
        } else {
            servoRClamp.setPosition(RIGHT_FLAP_UP_POS);
            servoLClamp.setPosition(LEFT_FLAP_UP_POS);
        }
    }

    public void resetDriveEncoders() {
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
