package frc.robot.lib.sim;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;

import edu.wpi.first.wpilibj.PWMSpeedController;

//To take full advantage of this class please use Mockito to mock with <code>MockPhoenixController.DEFAULT_ANSWER</code>
class MockPhoenixController {
    public static final ErrorCodeAnswer DEFAULT_ANSWER = new ErrorCodeAnswer();
    private final int portPWM;
    private boolean isInverted;
    // Assign the CAN port to a PWM port so it works with the simulator. Not a fan of this solution though
    // CAN ports should be separate from PWM ports
    protected PWMSpeedController motorPWM;
    // Since we need to keep a record of all the motor's followers
    protected static HashMap<Integer, PWMSpeedController> followMap = new HashMap<Integer, PWMSpeedController>();

    public MockPhoenixController(int portPWM) {
        this.portPWM = portPWM;
        isInverted = false;
    }

    public void set(double speed) {
        speed = (getInverted() ? -1.0 : 1.0) * speed;
        motorPWM.set(speed);
        if (followMap.containsKey(getDeviceID())) followMap.get(getDeviceID()).set(speed); 
    }

    public double get() {
        return motorPWM.get();
    }

    public void follow(IMotorController leader) {
        if (!followMap.containsValue(motorPWM)) followMap.put(leader.getDeviceID(), motorPWM);
    }
    
    public void setInverted(boolean invert) { 
        isInverted = invert; 
    }

    public boolean getInverted() { 
        return isInverted; 
	}
	
	public int getDeviceID() { return portPWM; }
    public ControlMode getControlMode() { return ControlMode.PercentOutput; }
    
    public void valueUpdated() {}
}
