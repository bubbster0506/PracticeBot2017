package org.usfirst.frc.team3243.robot;

import edu.wpi.first.wpilibj.Victor;

public class MotorControl {

	Victor drive1 = new Victor(0);
	Victor drive2 = new Victor(1);
	Victor drive3 = new Victor(2);
	Victor drive4 = new Victor(3);
	
	public void drive(Double[] val){
		
		if(true) {//Robot drives FORWARDS
			drive1.set(-val[1]);
			drive2.set(-val[1]);
			drive3.set(val[0]);
			drive4.set(val[0]);
		}else {//Robot drives BACKWARDS
			drive1.set(-val[0]);
			drive2.set(-val[0]);
			drive3.set(val[1]);
			drive4.set(val[1]);
		}
	}
	
}
