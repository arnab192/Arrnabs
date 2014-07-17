package com.javelin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


//by prasenjit

public class InfoLogger {
	private static PrintWriter logWriter=null;
	private static String writeInfoLog="Y";
	private static String fileNameWithFormat="";
	public static void printExceptionLog(String className,String methodName,Exception exception) {

		try {
			
		
			if(writeInfoLog.trim().equalsIgnoreCase("Y")){
				if(logWriter==null){
					//String logProprtyPath=	Utility.getProjectPath() + File.separator+ SubmitOrderConstant.PROPERTYFOLDER + File.separator+ SubmitOrderConstant.JAVELIN_INFO_LOG_FOLDER;
					String logProprtyPath =	getLogFilePathByDate(getProjectPath());
					logWriter = new PrintWriter(new BufferedWriter( new FileWriter(new File(logProprtyPath), true)));

				}

				/*logWriter.print("EXCEPTION  TIME      : "+ new Date());
				logWriter.print(" CLASS     : " + className );
				logWriter.println(" METHOD    : " + methodName);
				//logWriter.println("EXCEPTION : " + exception);
				logWriter.println();*/
				exception.printStackTrace(logWriter);
				logWriter.flush();
				logWriter.close();
				logWriter=null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void printExceptionLog(String className,String methodName,Error exception) {

		try {
			
			if(writeInfoLog.trim().equalsIgnoreCase("Y")){
				if(logWriter==null){
					///String logProprtyPath=	getINIPath() + File.separator+ JobConstant.PROPERTYFOLDER + File.separator+ JobConstant.JAVELIN_INFO_LOG_FOLDER;
					String logProprtyPath =	getLogFilePathByDate(getProjectPath());
					logWriter = new PrintWriter(new BufferedWriter( new FileWriter(new File(logProprtyPath), true)));

				}

				logWriter.print("EXCEPTION  TIME      : "+ new Date());
				logWriter.print(" CLASS     : " + className );
				logWriter.println(" METHOD    : " + methodName);
				logWriter.println("EXCEPTION : " + exception);
				logWriter.println();
				//exception.printStackTrace(logWriter);
				logWriter.flush();
				logWriter.close();
				logWriter=null;
			}

		} catch (Exception e) {

		}
	}
	public static void printInfo(String className,String methodName, String info) {
		try {
			
			if(writeInfoLog.trim().equalsIgnoreCase("Y")){
				if(logWriter==null){

					String logProprtyPath =	getLogFilePathByDate(getProjectPath());
					logWriter = new PrintWriter(new BufferedWriter( new FileWriter(new File(logProprtyPath), true)));

				}

				logWriter.print("INFO  TIME   : "+ new Date());
				logWriter.print(" CLASS  : " + className );
				logWriter.print(" METHOD : " + methodName);
				logWriter.println("INFO   : " + info);
				logWriter.println();
				logWriter.flush();
				logWriter.close();
				logWriter=null;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	


	/**
	 * If error code is added into {@link com.javelin.PDRConstant#JAVELIN_LOG_PROPERTY_FILE} then it must be added into this method
	 * 
	 * 
	 */


	private static String dateHolder="";
	private static String getLogFilePathByDate(String rootPath) {
		
		String infoLogFolderPath=rootPath+ File.separator+"logs";

		String filePath = "";
		try {
			//String logFolderPath=configProperty.get("JavelinLogFolderPath");
			File logFolder=new File(infoLogFolderPath);
			if (!logFolder.exists()) {
				logFolder.mkdirs();
			} 
			Date date = new Date();
			String strDate = (date.getYear()+1900)+ "."+getIntLenFormat((date.getMonth()+1)+"",2)+ "."+ getIntLenFormat(date.getDate()+"",2) + "_"+getIntLenFormat(date.getHours()+"",2)+ "."+getIntLenFormat(date.getMinutes()+"",2)+ "."+getIntLenFormat(date.getSeconds()+"",2)+"_";

			if(fileNameWithFormat.trim().equals("")){
				dateHolder =strDate.substring(0, 10);
				fileNameWithFormat=strDate+"_PDR.log";
				
				deleteLogFileBefore7D(infoLogFolderPath,date);
			}
			if(!dateHolder.equals(strDate.substring(0, 10))){
				dateHolder =strDate.substring(0, 10);
				fileNameWithFormat=strDate+"_PDR.log";
				
				deleteLogFileBefore7D(infoLogFolderPath,date);
			}
			filePath = infoLogFolderPath+File.separator+fileNameWithFormat;
			return filePath;
		} catch (Exception e) {
		}
		return filePath;
	}

	private static String getIntLenFormat(String value,int len){
		value=value.trim();
		for(int i=value.length();i<len;i++){
			value=0+value;
		}
		return value;
	}
	private static void deleteLogFileBefore7D(String logFolderPath,Date todayDate){
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String currDate=dateFormat.format(todayDate);
			currDate=currDate.replace("/", "");
			int yearCurr=Integer.parseInt(currDate.substring(0,4));
			int monthCurr=Integer.parseInt(currDate.substring(4,6));
			int dateCurr=Integer.parseInt(currDate.substring(6,8));

			File logfolder= new  File(logFolderPath);
			File[] logFiles=logfolder.listFiles();
			for(File logFile:logFiles){
				String fileName=logFile.getName();
				try {
					String dateInfileName=fileName.substring(0, 10);
					dateInfileName=dateInfileName.replace(".", "");
					int yearFileName=Integer.parseInt(dateInfileName.substring(0,4));
					int monthFileName=Integer.parseInt(dateInfileName.substring(4,6));
					int dateFileName=Integer.parseInt(dateInfileName.substring(6,8));

					if(yearCurr==yearFileName){
						if(monthCurr==monthFileName){
							int dateDiff=dateCurr-dateFileName;
							if(dateDiff>7){
								logFile.delete();
								
							}
						}else{
							int diffMonth=monthCurr-monthFileName;
							if(diffMonth==-1 || diffMonth==1){
								int dateDiff=dateCurr+30-dateFileName;
								if(dateDiff>7){
									logFile.delete();
								}
							}else{
								logFile.delete();
							}
						}
					}else{
						int diffYear=yearCurr-yearFileName;
						if(diffYear==-1 || diffYear==1){
							int dateDiff=dateCurr+31-dateFileName;
							if(dateDiff>7){
								logFile.delete();
							}
						}else{
							logFile.delete();
						}
					}
				} catch (Exception e) {
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}
	
	static String currentDirectory="";
	public static String getProjectPath(){

		if(currentDirectory.equals("")){
			currentDirectory = LinuxTestClass.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			currentDirectory = new File(currentDirectory).getParentFile().getAbsolutePath();
			System.out.println("currentDirectory>>>>>"+currentDirectory);
		}
		return currentDirectory;
	}
}
