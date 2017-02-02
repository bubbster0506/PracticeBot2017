package org.usfirst.frc.team3243.robot;

import java.io.IOException;
import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pixy {
	I2C pixy = new I2C(I2C.Port.kOnboard, 0x54);
	byte[] sendData = new byte[6];
	byte[] data = new byte[14];
	
	public class Frame{
		int sync;
		int checksum;
		int signature;
		int xCenter;
		int yCenter;
		int width;
		int height;
	}
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
	
	public int cvt(int msb, int lsb) {
		if(msb< 0){
			msb += 256;
		}
		int value = msb*256;
		if(lsb < 0){
			value += 256;
		}
		value += lsb;
		return value;
	}
	
	public Frame getFrame(byte[] bytes){
		Frame frame = new Frame();
		frame.sync = cvt(bytes[1], bytes[0]);
		frame.checksum = cvt(bytes[3], bytes[2]);
		if(frame.checksum == 0 || frame.checksum == 0xaa55){
			return null;
		}
		frame.signature = cvt(bytes[5], bytes[4]);
		frame.xCenter = cvt(bytes[7], bytes[6]);
		frame.yCenter = cvt(bytes[9], bytes[8]);
		frame.width = cvt(bytes[11], bytes[10]);
		frame.height = cvt(bytes[13], bytes[12]);
		
		return frame;
	}
	
	
	public void getData(){
		pixy.read(0x54, 14, data);
		pixy.readOnly(data, 14);
		if(data[0] == 0x55){
			SmartDashboard.putBoolean("PixyData", true);
		}else{
			SmartDashboard.putBoolean("PixyData", false);
		}
		//if(data[0] == 0x55 && data[1] == 0xaa){
			
		
	}
}
