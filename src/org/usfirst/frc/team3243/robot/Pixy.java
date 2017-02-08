package org.usfirst.frc.team3243.robot;

import java.io.IOException;
import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pixy {
	int start = 0xaa55;
	int startx = 0x55aa;
	int startc = 0xaa56;
	String blockType;
	boolean skipstart;
	I2C pixy = new I2C(I2C.Port.kOnboard, 0x54);
	byte[] sendData = new byte[6];
	byte[] data = new byte[14];
	byte[] block = new byte[100];
	int sig;
	int x;
	int y;
	int width;
	int height;
	int blockCount;
	
	void getStart(){
		int w, lastw;
		lastw = 0xffff;
		
		while(true){
			w = getWord();
			if(w == 0 && lastw == 0){
				blockType = "none";
				return;
			}else if(w == start && lastw == start){
				blockType = "normal";
				return;
			}else if (w == startc && lastw == start){
				blockType = "color";
				return;
			}else if(w == startx){
				getByte();
			}
			lastw = w;
		}
		
	}
	
	int getWord(){
		byte[] buffer = new byte[2];
		pixy.readOnly(buffer, 2);
		return ((buffer[1] >> 8) * 0xff)| buffer[0];
	}
	
	int getByte(){
		byte[] buffer = new byte[1];
		pixy.readOnly(buffer, 1);
		return (buffer[0] * 0xff);
	}
	
	int getBlocks(int maxBlocks){
		block[0] = 0;
		int blocks;
		int i, w, check, sum;
		if(!skipstart){
			if(blockType == "normal" || blockType == "color"){
				return 0;
			}
			
		}else skipstart = true;
		
		for(blockCount = 0; blockCount < maxBlocks && blockCount < 130;){
			check = getWord();
			if (check == start){
				skipstart = true;
				blockType = "normal";
				return blockCount;
			}else if(check == startc){
				skipstart = true;
				blockType = "color";
				return blockCount;
			}else if(check == 0){
				return blockCount;
			}
			blocks = block[0] + blockCount;
			
			
		}
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
	
	
}
