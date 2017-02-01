package org.usfirst.frc.team3243.robot;

import java.io.IOException;
import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pixy {
	I2C pixy = new I2C(I2C.Port.kOnboard, 0x54);
	byte[] sendData = new byte[6];
	byte[] data = new byte[14];
	
	public void setUp(){
		if(pixy.addressOnly()){
			SmartDashboard.putString("Cam Working", "True");
		}else{
			SmartDashboard.putString("Cam Working", "False");
		}
		sendData[0] = 0x00;
		sendData[1] = (byte) 0xfd;
		sendData[2] = (byte) 0x00;
		sendData[3] = (byte) 0x1f;
		sendData[4] = (byte) 0x00;
		
		pixy.writeBulk(sendData);
	}
	
	public void getData(){
		
		
		pixy.read(0x54, 14, data);
		SmartDashboard.putString("Pixy Data", String.valueOf(data));
		//if(data[0] == 0x55 && data[1] == 0xaa){
			
		}
	}
}
