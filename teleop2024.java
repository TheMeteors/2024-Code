package org.firstinspires.ftc.teamcode;

import android.util.Size;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//import org.firstinspires.ftc.vision.tfod.TfodProcessor;


@TeleOp(name = "Trucker")
public class Teleopthe2nd extends LinearOpMode {

  private DcMotor backleft;
  private DcMotor frontleft;
  private DcMotor backright;
  private DcMotor CookieElevator;
  private DcMotor Rollo;
  private DcMotor frontright;
  private IMU imu_IMU;
  private DcMotor ClimbRight;
  private DcMotor ClimbLeft;
  private Servo IntakeWrist;
  //private Servo dronecannon;
  //private DcMotor tacotruck;
  private CRServo intake1;
  private CRServo intake2;
  private Servo Dumper;
  private DcMotor extension;
  //This function is executed when this Op Mode is selected from the Driver Station.
   
  @Override
  public void runOpMode() {
    double BackLeftPower;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
    IMU.Parameters gryoParameter;
    double headingDirection;
    double Power;
    double BackRightPower;
    double FrontLeftPower;
    double FrontRightPower;
    double DivideFactor;
    double SpeedMultiple;
    long delayStart = 0;

    double intakeWristUp = 0.08;
    double intakeWristDown = 0.79;
    
    int climbDown = 100;
    int climbUp = 1800;
   
    backleft = hardwareMap.get(DcMotor.class, "back left");
    frontleft = hardwareMap.get(DcMotor.class, "front left");
    backright = hardwareMap.get(DcMotor.class, "back right");
    frontright = hardwareMap.get(DcMotor.class, "front right");
    imu_IMU = hardwareMap.get(IMU.class, "imu");
    IntakeWrist = hardwareMap.get(Servo.class, "IntakeWrist");
    intake1 =  hardwareMap.get(CRServo.class, "Intake1");
    intake2 =  hardwareMap.get(CRServo.class, "Intake2");
    Dumper = hardwareMap.get(Servo.class, "Dumper");
    CookieElevator = hardwareMap.get(DcMotor.class, "elevator");
    Rollo = hardwareMap.get(DcMotor.class, "roller");
    ClimbRight= hardwareMap.get(DcMotor.class, "Climb");
    ClimbLeft= hardwareMap.get(DcMotor.class, "Climb2");
    //thingy1 = hardwareMap.get(Servo.class, "pivot");
    //dronecannon = hardwareMap.get(Servo.class, "launcher");
    //tacotruck = hardwareMap.get(DcMotor.class, "climb");
    
    
        
    // Put initialization blocks here.
    backleft.setDirection(DcMotor.Direction.REVERSE);
    frontleft.setDirection(DcMotor.Direction.REVERSE);
    backright.setDirection(DcMotor.Direction.FORWARD);
    frontright.setDirection(DcMotor.Direction.FORWARD);
    CookieElevator.setDirection(DcMotor.Direction.FORWARD);
    ClimbRight.setDirection(DcMotor.Direction.REVERSE);
    ClimbLeft.setDirection(DcMotor.Direction.FORWARD);
    // Create a new AprilTag Process Builder object
    // Creates a Parameters object for use with an IMU in a REV Robotics Control Hub or Expansion Hub, specifying the hub's arbitrary orientation on the robot via an Orientation block that describes the rotation that would need to be applied in order to rotate the hub from having its logo facing up and the USB ports facing forward, to its actual orientation on the robot.
    gryoParameter = new IMU.Parameters(new RevHubOrientationOnRobot(new Orientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES, 0, 0, -45, 0)));
    // Initializes the IMU with non-default settings. To use this block,
    // plug one of the "new IMU.Parameters" blocks into the parameters socket.
    imu_IMU.initialize(gryoParameter);
    backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CookieElevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    Rollo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    ClimbRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    ClimbLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    IntakeWrist.setPosition(0.0);
    
    
    waitForStart();
    if (opModeIsActive()) {
      
      // Put run blocks here.
      //thingy1.setPosition(.1);
      while (opModeIsActive()){

         if(gamepad2.a){
           //  if (intake1.getDirection() == DcMotor.Direction.FORWARD){
                 
           //intake1.setDirection(DcMotor.Direction.FORWARD);
           //intake2.setDirection(DcMotor.Direction.REVERSE);
           //double intakePower = 0.0;
           //intake1.setPower(intakePower);
           //intake2.setPower(intakePower);
             //}
             //else {
                 
           intake1.setDirection(DcMotor.Direction.FORWARD);
           intake2.setDirection(DcMotor.Direction.REVERSE);
           double intakePower = 1.0;
           intake1.setPower(intakePower);
           intake2.setPower(intakePower);
             //}
          //delayStart = System.currentTimeMillis();
           //if(System.currentTimeMillis() - delayStart >= 500){

         //}
         }
         else if(gamepad2.x){

           double intakePower = 0.0;
           intake1.setPower(intakePower);
           intake2.setPower(intakePower); 
         }
         else if(gamepad2.y){
             //if (intake1.getDirection() == DcMotor.Direction.FORWARD){
              //   intake1.setDirection(DcMotor.Direction.REVERSE);
              //  intake2.setDirection(DcMotor.Direction.FORWARD);
              //  double intakePower =0.0;
              //  intake1.setPower(intakePower);
              //  intake2.setPower(intakePower);   
             //}
             //else {
                 intake1.setDirection(DcMotor.Direction.REVERSE);
                intake2.setDirection(DcMotor.Direction.FORWARD);
                double intakePower =1.0;
                intake1.setPower(intakePower);
                intake2.setPower(intakePower);   
             //}
           //delayStart = System.currentTimeMillis();
           //if(System.currentTimeMillis() - delayStart >= 500){
                
               
           //}
        //   intake1.setDirection(DcMotor.Direction.REVERSE);
        //   intake2.setDirection(DcMotor.Direction.FORWARD);
        //   double intakePower =1.0;
        //   intake1.setPower(intakePower);
        //   intake2.setPower(intakePower); 
         }
         
         if (gamepad2.dpad_up){
           double position = 0.80;
           Dumper.setPosition(position);
         
           
         }
         else if (gamepad2.dpad_down) {
           double position = 0.55;
             Dumper.setPosition(position);
         }
    
       
        if (gamepad2.right_bumper){
           IntakeWrist.setPosition(intakeWristUp);
         }
         //-0.10
        else if (gamepad2.left_bumper){
          IntakeWrist.setPosition(intakeWristDown);
         }
         //0.68
        // POV Mode uses left stick to go forward, and right stick to turn.
          
            double power = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2));
            double theta = -Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x);
            double turn  =  gamepad1.right_stick_x;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            
            double sin = Math.sin(theta - Math.PI/4);
            double cos = Math.cos(theta - Math.PI/4);
            double max = Math.max(Math.abs(sin),Math.abs(cos));
            
            double leftFrontPower = power * sin/max + turn;
            double rightFrontPower = power * sin/max - turn;
            double leftRearPower = power * cos/max + turn;
            double rightRearPower = power * cos/max - turn;
            
            if ((power + Math.abs(turn)) > 1) {
             leftFrontPower /= power + turn;
             rightFrontPower /= power + turn;
              leftRearPower /= power + turn;
             rightRearPower /= power + turn;
             
                
            }
            
            
            //if the absolute value (gamepad_x - gamepad_y) == around 0.01
                //decrease the power a bit 
                //this will move the wheels one way or another*/
                
            double powerMultiplier = 1.0;
            if(gamepad1.left_trigger > 0.0)
            {
                powerMultiplier = 0.5; 
            }
               double powerSuperMultiplier = 1.0;
             if(gamepad1.right_trigger > 0.0)
             {
              powerSuperMultiplier = 0.20;
              
             }
            
            frontleft.setPower(leftFrontPower * powerMultiplier * powerSuperMultiplier);
            backleft.setPower(leftRearPower * powerMultiplier * powerSuperMultiplier);
            frontright.setPower(rightFrontPower * powerMultiplier * powerSuperMultiplier);
            backright.setPower(rightRearPower * powerMultiplier * powerSuperMultiplier);
    
        
    if (gamepad1.x){ //climb right down
        ClimbRight.setPower(1.0);
    }
    if (gamepad1.y){ //climb right up
        ClimbRight.setPower(-1.0);
    }    
    if (gamepad1.a){ //climb arms down
        ClimbRight.setPower(1.0);
        ClimbLeft.setPower(1.0);
    }
    if (gamepad1.b){ //climb arms up
        ClimbRight.setPower(-1.0);
        ClimbLeft.setPower(-1.0);
    }
    if (!gamepad1.a && !gamepad1.b && !gamepad1.x && !gamepad1.y)
    {
        ClimbRight.setPower(0);
        ClimbLeft.setPower(0);
    }
       double Drive = gamepad2.left_stick_y;
        double Roll = gamepad2.right_stick_y;
      
       CookieElevator.setPower(Drive);
       Rollo.setPower(Roll);
      }
      
    }
        
    
  }
  
  private double max(double num1, double num2) {
    double maximum_number;

    if (num1 > num2) {
      maximum_number = num1;
    } else {
      maximum_number = num2;
    }
    return maximum_number;
  }     
  
}

