package org.usfirst.frc.team3243.robot;

import edu.wpi.first.wpilibj.Joystick;

public class InputManager {
	
	Joystick move = new Joystick(0);
	Double[] driveVal = new Double[2];
	
	public Double[] getDrive(){
		Double x = move.getRawAxis(3);
		Double y = move.getRawAxis(1);
		driveVal[0] = 0.75*x;
		driveVal[1] = 0.75*y;
		
		return driveVal;
	}
}
