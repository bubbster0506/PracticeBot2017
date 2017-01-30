package org.usfirst.frc.team3243.robot;

import edu.wpi.first.wpilibj.I2C;

public class Pixy {
	
	PixyPacket vals = new PixyPacket();
	I2C pixy = new I2C(I2C.Port.kOnboard, 0x54);
	PixyPacket[] pkts = new PixyPacket[7];
	String print;
	PixyException pEx = new PixyException(print);
	
	public int cvt(byte upper, byte lower){
		return (((int)upper & 0xff) << 8) | ((int)lower & 0xff);
	}
	
	public PixyPacket read(int Signature) throws PixyException{
		
		int checksum;
		int sig = 0; 
		byte[] rawData = new byte[32];
		try{
			pixy.readOnly(rawData, 32);
		}catch(RuntimeException e){
			
		}
		if(rawData.length < 32){
			return null;
		}
		for(int i = 0; i<=16; i++){
			int syncWord = cvt(rawData[i+1], rawData[i]);
			if(syncWord == 0xaa55){
				syncWord = cvt(rawData[i+3], rawData[i+2]);
				if(syncWord == 0xaa55){
					i -= 2;
				}
				checksum = cvt(rawData[i+5], rawData[i+4]);
				sig = cvt(rawData[i+7], rawData[i+6]);
				if(sig <= 0 || sig > pkts.length){
					break;
				}
				pkts[sig - 1] = new PixyPacket();
				pkts[sig - 1].x = cvt(rawData[sig+9], rawData[sig+8]);
				pkts[sig - 1].y = cvt(rawData[i+11], rawData[i+10]);
				pkts[sig - 1].Width = cvt(rawData[i+13], rawData[i+12]);
				pkts[sig - 1].Heigth = cvt(rawData[i+15], rawData[i+14]);
				if(checksum != sig + pkts[sig - 1].x + pkts[sig - 1].y + pkts[sig - 1].Heigth + pkts[sig - 1].Width){
					pkts[sig - 1] = null;
					throw pEx;
				}
				break;
			}
		}
		
		PixyPacket pkt = pkts[sig - 1];
		pkts[sig - 1] = null;
		return pkt;
	}
	
	public int getX(){
		return vals.x;
	}
	public int getY(){
		return vals.y;
	}
	public int getHeigth(){
		return vals.Heigth;
	}
	public int getWidth(){
		return vals.Width;
	}
}
