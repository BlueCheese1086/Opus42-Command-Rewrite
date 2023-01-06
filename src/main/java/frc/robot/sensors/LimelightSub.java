package frc.robot.sensors;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSub extends SubsystemBase {
    //limelight returns in inches
    NetworkTable limelight;

    /**
     * Gets limelight network tables
     */
    public LimelightSub(){
        limelight = NetworkTableInstance.getDefault().getTable("limelight");
    }
 
     /**
      * @return horizontal angle of the current target in degrees
      */
     public double getXAngle() {
         return limelight.getEntry("tx").getDouble(0);
     }
 
     /**
      * @return vertical angle of the current target in degrees (i hope)
      */
     public double getYAngle() {
         return limelight.getEntry("ty").getDouble(0);
     }

     /**
      * Sets light settings
      * ledMode settings:
      *    0 - use the LED Mode set in current pipeline
      *    1 - force off
      *    2 - force blink
      *    3 - force on
      * @param val
      */
     public void setLights(int val) {
         limelight.getEntry("ledMode").setNumber(val);
     }

     /**
      * Turns on the limelight lights
      */
     public void turnOnLights() {
         setLights(3);
     }
     
     /**
      * Turns off the limelight lights
      */
     public void turnOffLights() {
         setLights(1);
     }

     /**
      * Gets current limelight led state
      * 0 - Pipeline setting
      * 1 - Off
      * 2 - Blink
      * 3 - On
      * @return Limelight led state
      */
     public int getLights() {
         return (int)limelight.getEntry("ledMode").getDouble(0.01);
     }
 
     /**
      * Sets camMode:
      *    0 - vision processor
      *    1 - Driver Camera (Increases exposure, disables vision processing)
      * @param val
      */
     public void setCameraMode(int val) {
         limelight.getEntry("camMode").setNumber(val);
     }
}
