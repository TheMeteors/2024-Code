package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.CRServo;


/*
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */
 
 



@Autonomous(name="Basic: Linear OpMode", group="Linear OpMode")

public class HendrikAuton extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor leftfront;    
     private DcMotor leftback;
     private DcMotor rightfront;
     private DcMotor rightback;
     
    private DcMotor elevator;
    private DcMotor reacher;
    private Servo intakeWrist;
    private Servo dumper;
    private CRServo intake1;
    private CRServo intake2;
    private DcMotor Climb;
    private DcMotor Climb2;

    double intakeWristUp = 0.04;
    double intakeWristDown = 0.79;
    int climbTargetPosition = 1750;

    @Override
    public void runOpMode() {
    leftfront = hardwareMap.get(DcMotor.class, "front left");
    rightfront = hardwareMap.get(DcMotor.class, "front right");
    leftback = hardwareMap.get(DcMotor.class, "back left");
    rightback = hardwareMap.get(DcMotor.class, "back right");
    Climb = hardwareMap.get(DcMotor.class, "Climb");
    Climb2 = hardwareMap.get(DcMotor.class, "Climb2");

    leftfront.setDirection(DcMotorSimple.Direction.REVERSE);
    leftback.setDirection(DcMotorSimple.Direction.REVERSE);
    rightfront.setDirection(DcMotorSimple.Direction.FORWARD);
    rightback.setDirection(DcMotorSimple.Direction.FORWARD);
    Climb.setDirection(DcMotorSimple.Direction.FORWARD);
    Climb2.setDirection(DcMotorSimple.Direction.REVERSE);
    
    intakeWrist = hardwareMap.get(Servo.class, "IntakeWrist");
    intake1 =  hardwareMap.get(CRServo.class, "Intake1");
    intake2 =  hardwareMap.get(CRServo.class, "Intake2");
    dumper = hardwareMap.get(Servo.class, "Dumper");
    elevator = hardwareMap.get(DcMotor.class, "elevator");
    reacher = hardwareMap.get(DcMotor.class, "roller");
    
    leftback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rightback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    leftfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rightfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    reacher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    Climb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    Climb2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    reacher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    leftfront.setPower(0);
    leftback.setPower(0);
    rightfront.setPower(0);
    rightback.setPower(0);
    
    

    Odometry odometry = new Odometry(rightfront, leftback, leftfront);
    MecanumDrive mecanumDrive = new MecanumDrive(leftback, rightfront, leftfront, rightback, odometry);
    //AUTON STARTS HERE
    waitForStart();
    ElapsedTime time = new ElapsedTime();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            
            mecanumDrive.update();
                    
                
            
            if (time.seconds() > 0 && time.seconds() < 0.75)
            {
                mecanumDrive.driveToPosition(0, 5, 0, 0.4);
            }
            if (time.seconds() > 0.75 && time.seconds() < 1.5)
            {
                elevator.setTargetPosition(-4350);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevator.setPower(-1);
                mecanumDrive.driveToPosition(0, 5, 45, 0.4);
            }
            if (time.seconds() > 1.5 && time.seconds() < 3.5)
            {
                mecanumDrive.driveToPosition(-16, 4, 45, 0.4);
            }
            if (time.seconds() > 3.5 && time.seconds() < 5.5)
            {
                leftfront.setPower(0);
                rightfront.setPower(0);
                leftback.setPower(0);
                rightback.setPower(0);
                dumper.setPosition(0.55);
            }
            if (time.seconds() > 5.5 && time.seconds() < 6)
            {
                dumper.setPosition(0.95);
            }
            if (time.seconds() > 6 && time.seconds() < 6.75)
            {
            reacher.setTargetPosition(-700);
                reacher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                reacher.setPower(-1);
                intakeWrist.setPosition(intakeWristDown);
                intake1.setPower(1);
                intake2.setPower(-1);
                elevator.setTargetPosition(0);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevator.setPower(1);
                mecanumDrive.driveToPosition(-16, 4, 0.2 * 180 / Math.PI, 0.4);
            }
            if (time.seconds() > 6.75 && time.seconds() < 7.45)
            {
                mecanumDrive.driveToPosition(-13.25, 22.5, 0.2 * 180 / Math.PI, 0.4);
            }
            if (time.seconds() > 7.45 && time.seconds() < 8.75)
            {
                mecanumDrive.driveToPosition(-13.25, 22.5, 0.2 * 180 / Math.PI, 0.15);
            }
            if (time.seconds() > 8.75 && time.seconds() < 9.5)
            {
                dumper.setPosition(0.8);
                mecanumDrive.driveToPosition(-13.25, 20, 45, 0.4);
                reacher.setTargetPosition(0);
                reacher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                reacher.setPower(1);
                intakeWrist.setPosition(intakeWristUp);
                intake1.setPower(0);
                intake2.setPower(0);
            }
            if (time.seconds() > 9.5 && time.seconds() < 11.5)
            {
                intake1.setPower(-1);
                intake2.setPower(1);
            }
            if (time.seconds() > 11.5 && time.seconds() < 13.5)
            {
                dumper.setPosition(0.92);
                elevator.setTargetPosition(-4350);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevator.setPower(-1);
                mecanumDrive.driveToPosition(-12, 2.95, 45, 0.4);
            }
            if (time.seconds() > 13.5 && time.seconds() < 15)
            {
                mecanumDrive.driveToPosition(-12, 2.95, 45, 0.4);
                dumper.setPosition(0.55);
            }
            if (time.seconds() > 15 && time.seconds() < 15.5)
            {
                dumper.setPosition(0.95);
            }
            if (time.seconds() > 15.5 && time.seconds() < 17)
            {
                reacher.setTargetPosition(-700);
                mecanumDrive.driveToPosition(-19, 6.5, 2.5, 0.4);
                reacher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                reacher.setPower(-1);
                intakeWrist.setPosition(intakeWristDown);
                intake1.setPower(1);
                intake2.setPower(-1);
                elevator.setTargetPosition(0);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevator.setPower(1);
            }
            if (time.seconds() > 17 && time.seconds() < 17.65)
            {
                mecanumDrive.driveToPosition(-19.75, 22, 0, 0.4);
            }
            if (time.seconds() > 17.65 && time.seconds() < 18.75)
            {
                mecanumDrive.driveToPosition(-19.75, 23, 0, 0.2);
            }
            if (time.seconds() > 18.75 && time.seconds() < 19.5)
            {
                dumper.setPosition(0.8);
                mecanumDrive.driveToPosition(-15.1, 20, 5, 0.15);
                reacher.setTargetPosition(0);
                reacher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                reacher.setPower(1);
                intakeWrist.setPosition(intakeWristUp);
                intake1.setPower(0);
                intake2.setPower(0);
            }
            if (time.seconds() > 19.5 && time.seconds() < 21)
            {
                intake1.setPower(-1);
                intake2.setPower(1);
            }
            if (time.seconds() > 21 && time.seconds() < 23)
            {
                dumper.setPosition(0.92);
                elevator.setTargetPosition(-4350);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevator.setPower(-1);
                mecanumDrive.driveToPosition(-15.5, 3, 45, 0.4);
            }
            if (time.seconds() > 23 && time.seconds() < 25.5)
            {
                mecanumDrive.driveToPosition(-15, 3, 45, 0.4);
                dumper.setPosition(0.55);
            }
            if (time.seconds() > 25.5 && time.seconds() < 28)
            {
                dumper.setPosition(0.95);
                mecanumDrive.driveToPosition(-7, 43, 0, 0.8);
                elevator.setTargetPosition(0);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevator.setPower(1);
            }
            if (time.seconds() > 28)
            {
                mecanumDrive.driveToPosition(11, 43, 80, 0.8);
            }
            /* if (time.seconds() > 28.5)
            { Climb.setTargetPosition(climbTargetPosition);
                Climb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Climb.setPower(0.5);
            Climb2.setTargetPosition(climbTargetPosition);
                Climb2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Climb2.setPower(-0.5);
                mecanumDrive.driveToPosition(10, 37,-90, 0.8);
                
            }*/
            

            telemetry.addData("X", odometry.GetX());
            telemetry.addData("Y", odometry.GetY());
            telemetry.addData("Theta", odometry.GetHeading());
            
            // Show the elapsed game time and wheel power.
            telemetry.addData("N3:", leftfront.getCurrentPosition());
            telemetry.addData("N1:", rightfront.getCurrentPosition());
            telemetry.addData("N2:", leftback.getCurrentPosition());

            telemetry.update();
        }
    }
}
