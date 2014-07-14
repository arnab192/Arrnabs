package com.javelin;

import java.io.File;



public class LinuxTestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinuxTestClass linustest=new LinuxTestClass();

		String ShellScript = "";
		String MediaInfoEXEPath=InfoLogger.getProjectPath()+File.separator+"getMediaInfo";

		String DB_CONNECT = "connect " + "firstspin"+"/"+"hula" + "@" +"P1STSPIN" + ";";
		String strparam="\""+MediaInfoEXEPath+"\""+",\""+MediaInfoEXEPath+"\""+",\""+"crad26238.tmp.TFEPI154100H_E1A1L1H1_11_MPGPGS50.mpg"+"\""+",\""+"26238"+"\""+",\""+DB_CONNECT+"\""+",\""+MediaInfoEXEPath+"\""+",\""+linustest.getSrtFileName("crad26238.tmp.TFEPI154100H_E1A1L1H1_11_MPGPGS50.mpg")+"\""+",\""+1+"\""+",\""+221+"\"";
		InfoLogger.printInfo("LinuxTestClass", "main", "Parameter >>>> "+strparam);
		if(linustest.isUnix()){
			strparam="\""+MediaInfoEXEPath+"\""+" \""+MediaInfoEXEPath+"\""+" \""+"crad26238.tmp.TFEPI154100H_E1A1L1H1_11_MPGPGS50.mpg"+"\""+" \""+"26238"+"\""+" \""+DB_CONNECT+"\""+" \""+MediaInfoEXEPath+"\""+" \""+linustest.getSrtFileName("crad26238.tmp.TFEPI154100H_E1A1L1H1_11_MPGPGS50.mpg")+"\""+" \""+1+"\""+" \""+221+"\"";
			ShellScript = "/bin/bash -c " + MediaInfoEXEPath + File.separator+"GetMediaInfo " + strparam;
			InfoLogger.printInfo("LinuxTestClass", "main", "OS Unix");
		}else{
			ShellScript = "cmd /C " + MediaInfoEXEPath +File.separator+"GetMediaInfo.bat " + strparam;
			InfoLogger.printInfo("LinuxTestClass", "main", "OS Windows");
		}
		try {

			InfoLogger.printInfo("LinuxTestClass", "main", "ShellScript>>>> "+ShellScript);
			Runtime.getRuntime().exec(ShellScript);

		} catch (Exception e) {
			InfoLogger.printExceptionLog("LinuxTestClass", "main", e);
			//e.printStackTrace();
		}finally{

		}


		//-----------------------------------------------------------------------------------------------------------------
		String strParam = "\""+ 221 +"\"," + '"' + "www.adasoftware.com" + '"' + "," + '"' + "javelin_ftp" + '"' + "," + '"' + "" + '"' + "," + '"' + MediaInfoEXEPath+File.separator+"crad26238.tmp.TFEPI154100H_E1A1L1H1_11_MPGPGS50.mpg"  + '"' + "," + '"' + "/"  + '"' + "," + '"' + "LinuxTestFtp.mpg"  + '"' + "," + '"' + "Y" + '"' + "," + '"' + DB_CONNECT + '"';
		//String strbatchPath=System.getenv("PODBatchPath");
		String strbatchPath=InfoLogger.getProjectPath();
		System.out.println("strParam........>>>>"+strbatchPath);



		if(linustest.isUnix()){
			ShellScript = "/bin/bash -c " + strbatchPath +File.separator+ "FTPUpload.sh " + strParam;
			InfoLogger.printInfo("LinuxTestClass", "main", "OS Unix");
		}else{
			ShellScript = "cmd /C " + strbatchPath +File.separator+ "FTPUpload.bat " + strParam;
			InfoLogger.printInfo("LinuxTestClass", "main", "OS Windows");
		}

		try {
			InfoLogger.printInfo("LinuxTestClass", "main", "ShellScript FTP>>>> "+ShellScript);
			Runtime.getRuntime().exec(ShellScript);
		} catch (Exception e) {
			InfoLogger.printExceptionLog("LinuxTestClass", "main", e);
			//e.printStackTrace();
		}finally{

		}


	}
	public boolean isUnix() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}

	private String getSrtFileName(String formattedFileName) {
		String fileName="";
		String ext=" ";
		try {
			fileName=formattedFileName;
			if(fileName.contains(".tmp.")){
				fileName=fileName.substring(fileName.indexOf(".tmp.")+5);
			}
			if(fileName.contains(".")){
				ext=fileName.substring(fileName.lastIndexOf("."));
			}

			if(formattedFileName.contains(ext)){
				fileName=formattedFileName.replace(ext, ".srt");
			}
		} catch (Exception e) {
			fileName=formattedFileName+".srt";
		}
		return fileName;
	}
}
