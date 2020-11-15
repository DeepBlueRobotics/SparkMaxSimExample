package frc.robot.lib;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.lib.sim.MockSparkMax;
import frc.robot.lib.sim.MockTalonSRX;
import frc.robot.lib.sim.MockVictorSPX;

public class MotorControllerFactory {
    public static WPI_TalonSRX createTalon(int id) {
        WPI_TalonSRX talon;
        if (RobotBase.isReal()) {
            talon = new WPI_TalonSRX(id);
        } else {
            talon = MockTalonSRX.createMockTalonSRX(id);
        }
    
        // Put all configurations for the talon motor controllers in here.
        talon.enableCurrentLimit(true);
        talon.setNeutralMode(NeutralMode.Brake);
    
        return talon;
    }

    public static WPI_VictorSPX createVictor(int port) {
        WPI_VictorSPX victor;
        if (RobotBase.isReal()) {
            victor = new WPI_VictorSPX(port);
        } else {
            victor = MockVictorSPX.createMockVictorSPX(port);
        }
    
        // Put all configurations for the victor motor controllers in here.
        victor.setNeutralMode(NeutralMode.Brake);
    
        return victor;
    }

    public static CANSparkMax createSparkMax(int id) {
        CANSparkMax spark;
        if (RobotBase.isReal()) {
            spark = new CANSparkMax(id, MotorType.kBrushless);
        } else {
            spark = MockSparkMax.createMockSparkMax(id, MotorType.kBrushless);
        }
    
        // Put all configurations for the victor motor controllers in here.
        spark.setIdleMode(IdleMode.kBrake);
    
        return spark;
    }
}